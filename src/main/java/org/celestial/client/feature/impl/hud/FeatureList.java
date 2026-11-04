package org.celestial.client.feature.impl.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.world.effect.MobEffectInstance;
import org.celestial.client.Celestial;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.player.EventUpdate;
import org.celestial.client.event.events.impl.render.EventRender2D;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.helpers.misc.ClientHelper;
import org.celestial.client.helpers.palette.PaletteHelper;
import org.celestial.client.helpers.render.ScreenHelper;
import org.celestial.client.helpers.render.rect.RectHelper;
import org.celestial.client.settings.impl.BooleanSetting;
import org.celestial.client.settings.impl.ColorSetting;
import org.celestial.client.settings.impl.ListSetting;
import org.celestial.client.settings.impl.NumberSetting;

import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class FeatureList extends Feature {

    public static ListSetting sortMode;
    public static ListSetting fontRenderType;
    public static ListSetting borderMode;

    public static BooleanSetting backGround;
    public static BooleanSetting backGroundGradient = new BooleanSetting("BackGround Gradient", false, () -> backGround.getCurrentValue());
    public static ColorSetting backGroundColor2 = new ColorSetting("BackGround Color Two", Color.BLACK.getRGB(), () -> backGround.getCurrentValue() && backGroundGradient.getCurrentValue());
    public static ColorSetting backGroundColor = new ColorSetting("BackGround Color", Color.BLACK.getRGB(), () -> backGround.getCurrentValue());
    public static BooleanSetting border;
    public static BooleanSetting rightBorder;
    public static NumberSetting x;
    public static NumberSetting y;
    public static NumberSetting offset;
    public static NumberSetting size;
    public static NumberSetting borderWidth;
    public static NumberSetting rainbowSaturation;
    public static NumberSetting rainbowBright;
    public static NumberSetting fontX;
    public static NumberSetting fontY;
    public static BooleanSetting blur = new BooleanSetting("Blur", false, () -> backGround.getCurrentValue());
    public static BooleanSetting suffix;
    public static ListSetting colorSuffixMode = new ListSetting("Suffix Mode Color", "Default", () -> suffix.getCurrentValue(), "Astolfo", "Default", "Static", "Rainbow", "Custom", "Category");
    public static ColorSetting suffixColor = new ColorSetting("Suffix Color", Color.GRAY.getRGB(), () -> colorSuffixMode.currentMode.equals("Custom") || colorSuffixMode.currentMode.equals("Static") && suffix.getCurrentValue());
    public static ListSetting position = new ListSetting("Position", "Right", () -> true, "Right", "Left");

    public FeatureList() {
        super("FeatureList", "Показывает список всех включенных модулей", Type.Hud);

        borderMode = new ListSetting("Border Mode", "Full", () -> border.getCurrentValue(), "Full", "Single");
        sortMode = new ListSetting("FeatureList Sort", "Length", () -> true, "Length", "Alphabetical");
        fontRenderType = new ListSetting("FontRender Type", "Shadow", () -> true, "Default", "Shadow", "Outline");
        backGround = new BooleanSetting("Background", true, () -> true);
        border = new BooleanSetting("Border", true, () -> true);
        rightBorder = new BooleanSetting("Right Border", true, () -> true);
        suffix = new BooleanSetting("Suffix", true, () -> true);


        rainbowSaturation = new NumberSetting("Rainbow Saturation", 0.8F, 0.1F, 1F, 0.1F, () -> HUD.colorList.currentMode.equals("Rainbow") || colorSuffixMode.currentMode.equals("Rainbow"));
        rainbowBright = new NumberSetting("Rainbow Brightness", 1F, 0.1F, 1F, 0.1F, () -> HUD.colorList.currentMode.equals("Rainbow") || colorSuffixMode.currentMode.equals("Rainbow"));
        fontX = new NumberSetting("FontX", 0, -4, 20, 0.1F, () -> true);
        fontY = new NumberSetting("FontY", 0, -4, 20, 0.01F, () -> true);
        x = new NumberSetting("FeatureList X", 0, 0, 500, 1, () -> !blur.getCurrentValue());
        y = new NumberSetting("FeatureList Y", 0, 0, 500, 1, () -> !blur.getCurrentValue());
        offset = new NumberSetting("Font Offset", 11, 7, 20, 0.5F, () -> true);
        borderWidth = new NumberSetting("Border Width", 1, 0, 10, 0.1F, () -> rightBorder.getCurrentValue());
        addSettings(position, sortMode, fontRenderType, borderMode, colorSuffixMode, suffixColor, fontX, fontY, border, rightBorder, suffix, borderWidth, backGround, backGroundGradient, backGroundColor, backGroundColor2, rainbowSaturation, rainbowBright, x, y, offset);
    }

    private static Feature getNextEnabledFeature(List<Feature> features, int index) {
        for (int i = index; i < features.size(); i++) {
            Feature feature = features.get(i);
            if (feature.getState() && feature.visible) {
                if (!feature.getSuffix().equals("ClickGui") && feature.visible) {
                    return feature;
                }
            }
        }
        return null;
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        String mode = HUD.colorList.getCurrentMode();
        setSuffix(mode);
    }

    @EventTarget
    public void onRender2D(EventRender2D event) {
        float width = event.getResolution().getScaledWidth() - (FeatureList.rightBorder.getCurrentValue() ? borderWidth.getCurrentValue() : 0);
        float y = 1;
        String arraySort = sortMode.getCurrentMode();
        if (Celestial.instance.featureManager.getFeatureByClass(FeatureList.class).getState() && !mc.gameSettings.showDebugInfo) {
            Celestial.instance.featureManager.getFeatureList().sort(arraySort.equalsIgnoreCase("Alphabetical") ? Comparator.comparing(Feature::getLabel) : Comparator.comparingInt(module -> !HUD.font.currentMode.equals("Minecraft") ? -ClientHelper.getFontRender().getStringWidth(suffix.getCurrentValue() ? module.getSuffix() : module.getLabel()) : -mc.fontRendererObj.getStringWidth(suffix.getCurrentValue() ? module.getSuffix() : module.getLabel())));
            for (Feature feature : Celestial.instance.featureManager.getFeatureList()) {
                ScreenHelper animationHelper = feature.getScreenHelper();
                String featureSuffix = suffix.getCurrentValue() ? feature.getSuffix() : feature.getLabel();
                float listOffset = FeatureList.offset.getCurrentValue();
                float length = !HUD.font.currentMode.equals("Minecraft") ? ClientHelper.getFontRender().getStringWidth(featureSuffix) : mc.fontRendererObj.getStringWidth(featureSuffix);
                float featureX = width - length;
                boolean state = feature.getState() && feature.visible;

                if (state) {
                    animationHelper.interpolate(featureX, y, 4F * Minecraft.frameTime / 6);
                } else {
                    animationHelper.interpolate(width, y, 4F * Minecraft.frameTime / 6);
                }

                float yPotion = 2;

                for (MobEffectInstance MobEffectInstance : mc.player.getActivePotionEffects()) {
                    if (MobEffectInstance.getPotion().isBeneficial()) {
                        yPotion = 26;
                    }
                    if (MobEffectInstance.getPotion().isBadEffect()) {
                        yPotion = 26 * 2;
                    }
                }

                float translateY = animationHelper.getY() + yPotion;
                float translateX = animationHelper.getX() - (FeatureList.rightBorder.getCurrentValue() ? 2.5F : 1.5F) - FeatureList.fontX.getCurrentValue();
                int color = 0;
                int colorCustom = HUD.onecolor.getColor();
                int colorCustom2 = HUD.twocolor.getColor();
                double time = HUD.time.getCurrentValue();
                String mode = HUD.colorList.getOptions();
                boolean visible = animationHelper.getX() < width;

                if (visible) {
                    switch (mode.toLowerCase()) {
                        case "rainbow":
                            color = PaletteHelper.rainbow((int) (y * time), FeatureList.rainbowSaturation.getCurrentValue(), FeatureList.rainbowBright.getCurrentValue()).getRGB();
                            break;
                        case "astolfo":
                            color = PaletteHelper.astolfo(false, (int) y * 4).getRGB();
                            break;
                        case "static":
                            color = new Color(colorCustom).getRGB();
                            break;
                        case "custom":
                            color = PaletteHelper.fadeColor(new Color(colorCustom).getRGB(), new Color(colorCustom2).getRGB(), (float) Math.abs(((((System.currentTimeMillis() / time) / time) + y * 6L / 61 * 2) % 2)));
                            break;
                        case "fade":
                            color = PaletteHelper.fadeColor(new Color(colorCustom).getRGB(), new Color(colorCustom).darker().darker().getRGB(), (float) Math.abs(((((System.currentTimeMillis() / time) / time) + y * 6L / 60 * 2) % 2)));
                            break;
                        case "none":
                            color = -1;
                            break;
                        case "category":
                            color = feature.getType().getColor();
                            break;
                    }

                    int colorFuffix = 0;
                    String modeSuffix = FeatureList.colorSuffixMode.getOptions();
                    switch (modeSuffix.toLowerCase()) {
                        case "rainbow":
                            colorFuffix = PaletteHelper.rainbow((int) (y * time), FeatureList.rainbowSaturation.getCurrentValue(), FeatureList.rainbowBright.getCurrentValue()).getRGB();
                            break;
                        case "astolfo":
                            colorFuffix = PaletteHelper.astolfo(false, (int) y * 4).getRGB();
                            break;
                        case "static":
                            colorFuffix = new Color(suffixColor.getColor()).getRGB();
                            break;
                        case "default":
                            colorFuffix = new Color(192, 192, 192).getRGB();
                            break;
                        case "category":
                            colorFuffix = feature.getType().getColor();
                            break;
                    }
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(-FeatureList.x.getCurrentValue(), FeatureList.y.getCurrentValue(), 1);

                    Feature nextFeature = null;
                    int index = Celestial.instance.featureManager.getFeatureList().indexOf(feature) + 1;

                    if (Celestial.instance.featureManager.getFeatureList().size() > index) {
                        nextFeature = getNextEnabledFeature(Celestial.instance.featureManager.getFeatureList(), index);
                    }


                    if (border.getCurrentValue() && borderMode.currentMode.equals("Full")) {
                        RectHelper.drawRect(translateX - 3.5, translateY - 1, translateX - 2, translateY + listOffset - 1, color);
                    }

                    if (nextFeature != null && borderMode.currentMode.equals("Full")) {
                        String name = suffix.getCurrentValue() ? nextFeature.getSuffix() : nextFeature.getLabel();
                        float font = !HUD.font.currentMode.equals("Minecraft") ? ClientHelper.getFontRender().getStringWidth(name) : mc.fontRendererObj.getStringWidth(name);
                        float dif = (length - font);
                        if (FeatureList.border.getCurrentValue() && borderMode.currentMode.equals("Full")) {
                            RectHelper.drawRect(translateX - 3.5, translateY + listOffset + 1, translateX - 2 + dif, translateY + listOffset - 1, color);
                        }
                    } else {
                        if (FeatureList.border.getCurrentValue() && borderMode.currentMode.equals("Full")) {
                            RectHelper.drawRect(translateX - 3.5, translateY + listOffset + 1, width, translateY + listOffset - 1, color);
                        }
                    }

                    if (borderMode.currentMode.equals("Single") && border.getCurrentValue()) {
                        RectHelper.drawRect(translateX - 3.5, translateY - 1, translateX - 2, translateY + listOffset - 1, color);
                    }

                    if (FeatureList.backGround.getCurrentValue()) {
                        if (!backGroundGradient.getCurrentValue()) {
                            RectHelper.drawRect(translateX - 2, translateY - 1, width, translateY + listOffset - 1, backGroundColor.getColor());
                        } else {
                            RectHelper.drawGradientRect(translateX - 2, translateY - 1, width, translateY + listOffset - 1, backGroundColor.getColor(), backGroundColor2.getColor());
                        }
                    }

                    if (!HUD.font.currentMode.equals("Minecraft")) {
                        String modeArrayFont = HUD.font.getOptions();
                        float yOffset = modeArrayFont.equalsIgnoreCase("Verdana") ? 0.5f : modeArrayFont.equalsIgnoreCase("Comfortaa") ? 3 : modeArrayFont.equalsIgnoreCase("CircleRegular") ? 0.5f : modeArrayFont.equalsIgnoreCase("Arial") ? 1.3f : modeArrayFont.equalsIgnoreCase("Kollektif") ? 0.9f : modeArrayFont.equalsIgnoreCase("Product Sans") ? 0.5f : modeArrayFont.equalsIgnoreCase("RaleWay") ? 0.3f : modeArrayFont.equalsIgnoreCase("LucidaConsole") ? 3f : modeArrayFont.equalsIgnoreCase("Lato") ? 1.2f : modeArrayFont.equalsIgnoreCase("Open Sans") ? 0.5f : modeArrayFont.equalsIgnoreCase("SF UI") ? 1.3f : 2f;
                        if (!HUD.font.currentMode.equals("Minecraft") && fontRenderType.currentMode.equals("Shadow")) {
                            if (suffix.getCurrentValue()) {
                                ClientHelper.getFontRender().drawStringWithShadow(feature.getSuffix(), translateX, translateY + yOffset + fontY.getCurrentValue(), colorFuffix);
                            }
                            ClientHelper.getFontRender().drawStringWithShadow(feature.getLabel(), translateX, translateY + yOffset + fontY.getCurrentValue(), color);
                        } else if (!HUD.font.currentMode.equals("Minecraft") && fontRenderType.currentMode.equals("Default")) {
                            if (suffix.getCurrentValue()) {
                                ClientHelper.getFontRender().drawString(feature.getSuffix(), translateX, translateY + yOffset + fontY.getCurrentValue(), colorFuffix);
                            }
                            ClientHelper.getFontRender().drawString(feature.getLabel(), translateX, translateY + yOffset + fontY.getCurrentValue(), color);
                        } else if (!HUD.font.currentMode.equals("Minecraft") && fontRenderType.currentMode.equals("Outline")) {
                            if (suffix.getCurrentValue()) {
                                ClientHelper.getFontRender().drawStringWithOutline(feature.getSuffix(), translateX, translateY + yOffset + fontY.getCurrentValue(), colorFuffix);
                            }
                            ClientHelper.getFontRender().drawStringWithOutline(feature.getLabel(), translateX, translateY + yOffset + fontY.getCurrentValue(), color);
                        }
                    } else if (fontRenderType.currentMode.equals("Shadow")) {
                        if (suffix.getCurrentValue()) {
                            mc.fontRendererObj.drawStringWithShadow(feature.getSuffix(), translateX, translateY + 1 + fontY.getCurrentValue(), colorFuffix);
                        }
                        mc.fontRendererObj.drawStringWithShadow(feature.getLabel(), translateX, translateY + 1 + fontY.getCurrentValue(), color);
                    } else if (fontRenderType.currentMode.equals("Default")) {
                        if (suffix.getCurrentValue()) {
                            mc.fontRendererObj.drawString(feature.getSuffix(), translateX, translateY + 1 + fontY.getCurrentValue(), colorFuffix);
                        }
                        mc.fontRendererObj.drawString(feature.getLabel(), translateX, translateY + 1 + fontY.getCurrentValue(), color);
                    } else if (fontRenderType.currentMode.equals("Outline")) {
                        if (suffix.getCurrentValue()) {
                            mc.fontRendererObj.drawStringWithOutline(feature.getSuffix(), translateX, translateY + 1 + fontY.getCurrentValue(), colorFuffix);
                        }
                        mc.fontRendererObj.drawStringWithOutline(feature.getLabel(), translateX, translateY + 1 + fontY.getCurrentValue(), color);
                    }

                    y += listOffset;

                    if (FeatureList.rightBorder.getCurrentValue()) {
                        float checkY = border.getCurrentValue() ? 0 : 0.6F;
                        RectHelper.drawRect(width, translateY - 1, width + borderWidth.getCurrentValue(), translateY + listOffset - checkY, color);
                    }

                    GlStateManager.popMatrix();
                }
            }
        }
    }
}