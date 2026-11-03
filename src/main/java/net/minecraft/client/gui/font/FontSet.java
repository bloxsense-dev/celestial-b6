package net.minecraft.client.gui.font;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.font.GlyphBitmap;
import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.font.GlyphProvider;
import com.mojang.blaze3d.font.UnbakedGlyph;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import net.minecraft.client.gui.GlyphSource;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.gui.font.glyphs.EffectGlyph;
import net.minecraft.client.gui.font.glyphs.SpecialGlyphs;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jspecify.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class FontSet implements AutoCloseable {
    private static final float LARGE_FORWARD_ADVANCE = 32.0F;
    private static final BakedGlyph INVISIBLE_MISSING_GLYPH = new BakedGlyph() {
        @Override
        public GlyphInfo info() {
            return SpecialGlyphs.MISSING;
        }

        @Override
        public TextRenderable.@Nullable Styled createGlyph(
            final float x, final float y, final int color, final int shadowColor, final Style style, final float boldOffset, final float shadowOffset
        ) {
            return null;
        }
    };
    private final GlyphStitcher stitcher;
    private final UnbakedGlyph.Stitcher wrappedStitcher = new UnbakedGlyph.Stitcher() {
        @Override
        public BakedGlyph stitch(final GlyphInfo glyphInfo, final GlyphBitmap glyphBitmap) {
            return Objects.requireNonNullElse(FontSet.this.stitcher.stitch(glyphInfo, glyphBitmap), FontSet.this.missingGlyph);
        }

        @Override
        public BakedGlyph getMissing() {
            return FontSet.this.missingGlyph;
        }
    };
    private List<GlyphProvider.Conditional> allProviders = List.of();
    private List<GlyphProvider> activeProviders = List.of();
    private final Int2ObjectMap<IntList> glyphsByWidth = new Int2ObjectOpenHashMap<>();
    private final CodepointMap<FontSet.SelectedGlyphs> glyphCache = new CodepointMap<>(FontSet.SelectedGlyphs[]::new, FontSet.SelectedGlyphs[][]::new);
    private final IntFunction<FontSet.SelectedGlyphs> glyphGetter = this::computeGlyphInfo;
    private BakedGlyph missingGlyph = INVISIBLE_MISSING_GLYPH;
    private final Supplier<BakedGlyph> missingGlyphGetter = () -> this.missingGlyph;
    private final FontSet.SelectedGlyphs missingSelectedGlyphs = new FontSet.SelectedGlyphs(this.missingGlyphGetter, this.missingGlyphGetter);
    private @Nullable EffectGlyph whiteGlyph;
    private final GlyphSource anyGlyphs = new FontSet.Source(false);
    private final GlyphSource nonFishyGlyphs = new FontSet.Source(true);

    public FontSet(final GlyphStitcher stitcher) {
        this.stitcher = stitcher;
    }

    public void reload(final List<GlyphProvider.Conditional> providers, final Set<FontOption> options) {
        this.allProviders = providers;
        this.reload(options);
    }

    public void reload(final Set<FontOption> options) {
        this.activeProviders = List.of();
        this.resetTextures();
        this.activeProviders = this.selectProviders(this.allProviders, options);
    }

    private void resetTextures() {
        this.stitcher.reset();
        this.glyphCache.clear();
        this.glyphsByWidth.clear();
        this.missingGlyph = Objects.requireNonNull(SpecialGlyphs.MISSING.bake(this.stitcher));
        this.whiteGlyph = SpecialGlyphs.WHITE.bake(this.stitcher);
    }

    private List<GlyphProvider> selectProviders(final List<GlyphProvider.Conditional> providers, final Set<FontOption> options) {
        IntSet supportedGlyphs = new IntOpenHashSet();
        List<GlyphProvider> selectedProviders = new ArrayList<>();

        for (GlyphProvider.Conditional conditionalProvider : providers) {
            if (conditionalProvider.filter().apply(options)) {
                selectedProviders.add(conditionalProvider.provider());
                supportedGlyphs.addAll(conditionalProvider.provider().getSupportedGlyphs());
            }
        }

        Set<GlyphProvider> usedProviders = Sets.newHashSet();
        supportedGlyphs.forEach((int codepoint) -> {
            for (GlyphProvider provider : selectedProviders) {
                UnbakedGlyph glyph = provider.getGlyph(codepoint);
                if (glyph != null) {
                    usedProviders.add(provider);
                    if (glyph.info() != SpecialGlyphs.MISSING) {
                        this.glyphsByWidth.computeIfAbsent(Mth.ceil(glyph.info().getAdvance(false)), w -> new IntArrayList()).add(codepoint);
                    }
                    break;
                }
            }
        });
        return selectedProviders.stream().filter(usedProviders::contains).toList();
    }

    @Override
    public void close() {
        this.stitcher.close();
    }

    private static boolean hasFishyAdvance(final GlyphInfo glyph) {
        float advance = glyph.getAdvance(false);
        if (!(advance < 0.0F) && !(advance > 32.0F)) {
            float boldAdvance = glyph.getAdvance(true);
            return boldAdvance < 0.0F || boldAdvance > 32.0F;
        } else {
            return true;
        }
    }

    private FontSet.SelectedGlyphs computeGlyphInfo(final int codepoint) {
        FontSet.DelayedBake firstGlyph = null;

        for (GlyphProvider provider : this.activeProviders) {
            UnbakedGlyph glyph = provider.getGlyph(codepoint);
            if (glyph != null) {
                if (firstGlyph == null) {
                    firstGlyph = new FontSet.DelayedBake(glyph);
                }

                if (!hasFishyAdvance(glyph.info())) {
                    if (firstGlyph.unbaked == glyph) {
                        return new FontSet.SelectedGlyphs(firstGlyph, firstGlyph);
                    }

                    return new FontSet.SelectedGlyphs(firstGlyph, new FontSet.DelayedBake(glyph));
                }
            }
        }

        UnbakedGlyph fallback = this.createAwtFallbackGlyph(codepoint);
        if (fallback != null) {
            FontSet.DelayedBake bake = new FontSet.DelayedBake(fallback);
            return new FontSet.SelectedGlyphs(bake, bake);
        }

        return firstGlyph != null ? new FontSet.SelectedGlyphs(firstGlyph, this.missingGlyphGetter) : this.missingSelectedGlyphs;
    }

    private static final java.awt.Font[] FALLBACK_FONTS = new java.awt.Font[] {
        new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 18),
        new java.awt.Font("Arial", java.awt.Font.PLAIN, 18),
        new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.PLAIN, 18),
        new java.awt.Font(java.awt.Font.DIALOG, java.awt.Font.PLAIN, 18)
    };

    private @Nullable UnbakedGlyph createAwtFallbackGlyph(final int codepoint) {
        if (!Character.isValidCodePoint(codepoint) || Character.isISOControl(codepoint)) {
            return null;
        }
        java.awt.Font chosenFont = null;
        for (java.awt.Font f : FALLBACK_FONTS) {
            if (f.canDisplay(codepoint)) {
                chosenFont = f;
                break;
            }
        }
        if (chosenFont == null) {
            chosenFont = FALLBACK_FONTS[2];
        }

        String str = new String(Character.toChars(codepoint));
        final int imgW = 20;
        final int imgH = 20;
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(imgW, imgH, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g = img.createGraphics();
        g.setRenderingHint(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING, java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_OFF);
        g.setFont(chosenFont);
        java.awt.FontMetrics fm = g.getFontMetrics();
        int charAdvance = fm.charWidth(codepoint);
        if (charAdvance <= 0) charAdvance = 6;
        g.setColor(java.awt.Color.WHITE);
        g.drawString(str, 1, fm.getAscent());
        g.dispose();

        com.mojang.blaze3d.platform.NativeImage nativeImage = new com.mojang.blaze3d.platform.NativeImage(com.mojang.blaze3d.platform.NativeImage.Format.RGBA, imgW, imgH, false);
        for (int y = 0; y < imgH; y++) {
            for (int x = 0; x < imgW; x++) {
                int argb = img.getRGB(x, y);
                int a = (argb >> 24) & 0xFF;
                if (a > 30) {
                    nativeImage.setPixel(x, y, 0xFFFFFFFF);
                } else {
                    nativeImage.setPixel(x, y, 0);
                }
            }
        }

        final float advance = Math.min(Math.max((float) Math.ceil(charAdvance * 0.5f), 4.0f), 14.0f);
        return new UnbakedGlyph() {
            @Override
            public GlyphInfo info() {
                return GlyphInfo.simple(advance);
            }

            @Override
            public BakedGlyph bake(UnbakedGlyph.Stitcher stitcher) {
                return stitcher.stitch(this.info(), new GlyphBitmap() {
                    @Override
                    public int getPixelWidth() {
                        return imgW;
                    }

                    @Override
                    public int getPixelHeight() {
                        return imgH;
                    }

                    @Override
                    public float getOversample() {
                        return 2.0F;
                    }

                    @Override
                    public float getBearingTop() {
                        return 8.0F;
                    }

                    @Override
                    public float getBearingLeft() {
                        return 0.0F;
                    }

                    @Override
                    public void upload(int x, int y, com.mojang.blaze3d.textures.GpuTexture texture) {
                        com.mojang.blaze3d.systems.RenderSystem.getDevice().createCommandEncoder()
                            .writeToTexture(texture, nativeImage, 0, 0, x, y);
                    }

                    @Override
                    public boolean isColored() {
                        return true;
                    }
                });
            }
        };
    }

    private FontSet.SelectedGlyphs getGlyph(final int codepoint) {
        return this.glyphCache.computeIfAbsent(codepoint, this.glyphGetter);
    }

    public BakedGlyph getRandomGlyph(final RandomSource random, final int width) {
        IntList chars = this.glyphsByWidth.get(width);
        return chars != null && !chars.isEmpty() ? this.getGlyph(chars.getInt(random.nextInt(chars.size()))).nonFishy().get() : this.missingGlyph;
    }

    public EffectGlyph whiteGlyph() {
        return Objects.requireNonNull(this.whiteGlyph);
    }

    public GlyphSource source(final boolean nonFishyOnly) {
        return nonFishyOnly ? this.nonFishyGlyphs : this.anyGlyphs;
    }

    @OnlyIn(Dist.CLIENT)
    private class DelayedBake implements Supplier<BakedGlyph> {
        private final UnbakedGlyph unbaked;
        private @Nullable BakedGlyph baked;

        private DelayedBake(final UnbakedGlyph unbaked) {
            this.unbaked = unbaked;
        }

        public BakedGlyph get() {
            if (this.baked == null) {
                this.baked = this.unbaked.bake(FontSet.this.wrappedStitcher);
            }

            return this.baked;
        }
    }

    @OnlyIn(Dist.CLIENT)
    private record SelectedGlyphs(Supplier<BakedGlyph> any, Supplier<BakedGlyph> nonFishy) {
        private Supplier<BakedGlyph> select(final boolean filterFishy) {
            return filterFishy ? this.nonFishy : this.any;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public class Source implements GlyphSource {
        private final boolean filterFishyGlyphs;

        public Source(final boolean filterFishyGlyphs) {
            this.filterFishyGlyphs = filterFishyGlyphs;
        }

        @Override
        public BakedGlyph getGlyph(final int codepoint) {
            return FontSet.this.getGlyph(codepoint).select(this.filterFishyGlyphs).get();
        }

        @Override
        public BakedGlyph getRandomGlyph(final RandomSource random, final int width) {
            return FontSet.this.getRandomGlyph(random, width);
        }
    }
}
