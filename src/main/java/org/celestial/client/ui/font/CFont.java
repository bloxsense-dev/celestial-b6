package org.celestial.client.ui.font;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class CFont {

    private final float imgSize = 512;
    protected CharData[] charData = new CharData[256];
    protected CharData[][] charPages = new CharData[256][];
    protected DynamicTexture[] texPages = new DynamicTexture[256];
    protected Font font;
    protected boolean antiAlias;
    protected boolean fractionalMetrics;
    protected int fontHeight = -1;
    protected int charOffset = 0;
    protected DynamicTexture tex;

    public CFont(Font font, boolean antiAlias, boolean fractionalMetrics) {
        this.font = font;
        this.antiAlias = antiAlias;
        this.fractionalMetrics = fractionalMetrics;
        this.tex = setupTexture(font, antiAlias, fractionalMetrics, this.charData);
        this.charPages[0] = this.charData;
        this.texPages[0] = this.tex;
    }

    private static final Font[] FALLBACK_FONTS = new Font[] {
        new Font("Segoe UI", Font.PLAIN, 18),
        new Font("Arial", Font.PLAIN, 18),
        new Font(Font.SANS_SERIF, Font.PLAIN, 18)
    };

    public CharData getCharData(char c) {
        int page = (c >> 8) & 0xFF;
        int idx = c & 0xFF;
        CharData[] pData = getPageData(page);
        return pData != null ? pData[idx] : null;
    }

    public DynamicTexture getTextureForPage(int page) {
        if (this.texPages[page] == null && page != 0) {
            getPageData(page);
        }
        return this.texPages[page];
    }

    public synchronized CharData[] getPageData(int page) {
        if (this.charPages[page] != null) {
            return this.charPages[page];
        }
        CharData[] pData = new CharData[256];
        DynamicTexture pTex = setupTexturePage(this.font, this.antiAlias, this.fractionalMetrics, pData, page << 8);
        this.charPages[page] = pData;
        this.texPages[page] = pTex;
        return pData;
    }

    protected DynamicTexture setupTexturePage(Font font, boolean antiAlias, boolean fractionalMetrics, CharData[] chars, int startChar) {
        BufferedImage img = generateFontImagePage(font, antiAlias, fractionalMetrics, chars, startChar);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "png", baos);
            NativeImage nativeImage = NativeImage.read(new ByteArrayInputStream(baos.toByteArray()));
            DynamicTexture dynTex = new DynamicTexture(() -> "celestial_font_page_" + (startChar >> 8), nativeImage);
            dynTex.upload();
            return dynTex;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected BufferedImage generateFontImagePage(Font font, boolean antiAlias, boolean fractionalMetrics, CharData[] chars, int startChar) {
        int imgSize = (int) this.imgSize;
        BufferedImage bufferedImage = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
        g.setFont(font);
        g.setColor(new Color(255, 255, 255, 0));
        g.fillRect(0, 0, imgSize, imgSize);
        g.setColor(Color.WHITE);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, antiAlias ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antiAlias ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
        int charHeight = 0;
        int positionX = 0;
        int positionY = 1;

        for (int i = 0; i < chars.length; ++i) {
            char ch = (char) (startChar + i);
            Font activeFont = font;
            if (!font.canDisplay(ch)) {
                for (Font fb : FALLBACK_FONTS) {
                    Font derived = fb.deriveFont(font.getStyle(), font.getSize2D());
                    if (derived.canDisplay(ch)) {
                        activeFont = derived;
                        break;
                    }
                }
            }
            g.setFont(activeFont);
            FontMetrics fontMetrics = g.getFontMetrics(activeFont);

            CharData charData = new CharData();
            Rectangle2D dimensions = fontMetrics.getStringBounds(String.valueOf(ch), g);
            charData.width = dimensions.getBounds().width + 8;
            charData.height = dimensions.getBounds().height;
            if (positionX + charData.width >= imgSize) {
                positionX = 0;
                positionY += charHeight;
                charHeight = 0;
            }

            if (charData.height > charHeight) {
                charHeight = charData.height;
            }

            charData.storedX = positionX;
            charData.storedY = positionY;
            if (charData.height > this.fontHeight) {
                this.fontHeight = charData.height;
            }

            chars[i] = charData;
            g.drawString(String.valueOf(ch), positionX + 2, positionY + fontMetrics.getAscent());
            positionX += charData.width;
        }

        return bufferedImage;
    }

    protected DynamicTexture setupTexture(Font font, boolean antiAlias, boolean fractionalMetrics, CharData[] chars) {
        BufferedImage img = generateFontImage(font, antiAlias, fractionalMetrics, chars);

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "png", baos);
            NativeImage nativeImage = NativeImage.read(new ByteArrayInputStream(baos.toByteArray()));
            DynamicTexture dynTex = new DynamicTexture(() -> "celestial_font", nativeImage);
            dynTex.upload();
            return dynTex;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    protected BufferedImage generateFontImage(Font font, boolean antiAlias, boolean fractionalMetrics, CharData[] chars) {
        int imgSize = (int) this.imgSize;
        BufferedImage bufferedImage = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
        g.setFont(font);
        g.setColor(new Color(255, 255, 255, 0));
        g.fillRect(0, 0, imgSize, imgSize);
        g.setColor(Color.WHITE);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, antiAlias ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antiAlias ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
        FontMetrics fontMetrics = g.getFontMetrics();
        int charHeight = 0;
        int positionX = 0;
        int positionY = 1;

        for (int i = 0; i < chars.length; ++i) {
            char ch = (char) i;
            CharData charData = new CharData();
            Rectangle2D dimensions = fontMetrics.getStringBounds(String.valueOf(ch), g);
            charData.width = dimensions.getBounds().width + 8;
            charData.height = dimensions.getBounds().height;
            if (positionX + charData.width >= imgSize) {
                positionX = 0;
                positionY += charHeight;
                charHeight = 0;
            }

            if (charData.height > charHeight) {
                charHeight = charData.height;
            }

            charData.storedX = positionX;
            charData.storedY = positionY;
            if (charData.height > this.fontHeight) {
                this.fontHeight = charData.height;
            }

            chars[i] = charData;
            g.drawString(String.valueOf(ch), positionX + 2, positionY + fontMetrics.getAscent());
            positionX += charData.width;
        }

        return bufferedImage;
    }

    public void drawCharData(CharData data, float x, float y) {
        if (data != null) {
            try {
                drawQuad(x, y, data.width, data.height, data.storedX, data.storedY, data.width, data.height);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void drawChar(CharData[] chars, char c, float x, float y) throws ArrayIndexOutOfBoundsException {
        try {
            drawQuad(x, y, chars[c].width, chars[c].height, chars[c].storedX, chars[c].storedY, chars[c].width, chars[c].height);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void drawQuad(float x, float y, float width, float height, float srcX, float srcY, float srcWidth, float srcHeight) {
        float renderSRCX = srcX / this.imgSize;
        float renderSRCY = srcY / this.imgSize;
        float renderSRCWidth = srcWidth / this.imgSize;
        float renderSRCHeight = srcHeight / this.imgSize;
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
        GL11.glVertex2d(x + width, y);
        GL11.glTexCoord2f(renderSRCX, renderSRCY);
        GL11.glVertex2d(x, y);
        GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
        GL11.glVertex2d(x, y + height);
        GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
        GL11.glVertex2d(x, y + height);
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY + renderSRCHeight);
        GL11.glVertex2d(x + width, y + height);
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
        GL11.glVertex2d(x + width, y);
    }

    public int getStringHeight(String text) {
        return getFontHeight();
    }

    public int getFontHeight() {
        return (this.fontHeight - 8) / 2;
    }

    public int getStringWidth(String text) {
        if (text == null) return 0;
        int width = 0;

        for (char c : text.toCharArray()) {
            CharData data = getCharData(c);
            if (data != null) {
                width += data.width - 8 + this.charOffset;
            } else {
                width += 16 + this.charOffset;
            }
        }

        return width / 2;
    }

    public boolean isAntiAlias() {
        return this.antiAlias;
    }

    public void setAntiAlias(boolean antiAlias) {
        if (this.antiAlias != antiAlias) {
            this.antiAlias = antiAlias;
            tex = setupTexture(this.font, antiAlias, this.fractionalMetrics, this.charData);
        }
    }

    public boolean isFractionalMetrics() {
        return this.fractionalMetrics;
    }

    public void setFractionalMetrics(boolean fractionalMetrics) {
        if (this.fractionalMetrics != fractionalMetrics) {
            this.fractionalMetrics = fractionalMetrics;
            tex = setupTexture(this.font, this.antiAlias, fractionalMetrics, this.charData);
        }
    }

    public Font getFont() {
        return this.font;
    }

    public void setFont(Font font) {
        this.font = font;
        tex = setupTexture(font, this.antiAlias, this.fractionalMetrics, this.charData);
    }

    public static class CharData {
        public int width;
        public int height;
        public int storedX;
        public int storedY;
    }
}
