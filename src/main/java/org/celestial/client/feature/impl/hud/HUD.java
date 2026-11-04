


package org.celestial.client.feature.impl.hud;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import org.celestial.client.Celestial;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.render.EventRender2D;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.settings.impl.BooleanSetting;
import org.celestial.client.settings.impl.ColorSetting;
import org.celestial.client.settings.impl.ListSetting;
import org.celestial.client.settings.impl.NumberSetting;
import org.celestial.client.ui.components.draggable.DraggableModule;

public class HUD extends Feature {
    public static float globalOffset;
    public static ListSetting colorList;
    public static ColorSetting onecolor;
    public static ColorSetting twocolor;
    public static NumberSetting rainbowSaturation;
    public static NumberSetting rainbowBright;
    public static NumberSetting glowRadius;
    public static NumberSetting glowAlpha;
    public static ListSetting logoMode;
    public static BooleanSetting logo;
    public static BooleanSetting logoGlow;
    public static ListSetting logoColor;
    public static ListSetting font;
    public static BooleanSetting clientInfo;
    public static BooleanSetting worldInfo;
    public static BooleanSetting MobEffect;
    public static BooleanSetting potionIcons;
    public static BooleanSetting potionTimeColor;
    public static BooleanSetting armor;
    public static BooleanSetting rustHUD;
    public static ColorSetting customRect;
    public static NumberSetting time;
    public float animation = 0.0f;
    public static BooleanSetting blur = new BooleanSetting("Blur", false, () -> true);

    public HUD() {
        super("HUD", "\u041f\u043e\u043a\u0430\u0437\u044b\u0432\u0430\u0435\u0442 \u0438\u043d\u0444\u043e\u0440\u043c\u0430\u0446\u0438\u044e \u043d\u0430 \u044d\u043a\u0440\u0430\u043d\u0435", Type.Hud);
        colorList = new ListSetting("HUD Color", "Static", () -> true, "Astolfo", "Static", "Fade", "Rainbow", "Pulse", "Custom", "None", "Category");
        onecolor = new ColorSetting("One Color", new Color(200, 78, 205, 25).brighter().getRGB(), () -> HUD.colorList.currentMode.equals("Fade") || HUD.colorList.currentMode.equals("Custom") || HUD.colorList.currentMode.equals("Static"));
        twocolor = new ColorSetting("Two Color", Color.WHITE.getRGB(), () -> HUD.colorList.currentMode.equals("Custom"));
        rainbowSaturation = new NumberSetting("Rainbow Saturation", 0.8f, 0.1f, 1.0f, 0.1f, () -> HUD.colorList.currentMode.equals("Rainbow"));
        rainbowBright = new NumberSetting("Rainbow Brightness", 1.0f, 0.1f, 1.0f, 0.1f, () -> HUD.colorList.currentMode.equals("Rainbow"));
        logo = new BooleanSetting("Logo", true, () -> true);
        logoMode = new ListSetting("Logo Mode", "New", logo::getCurrentValue, "Default", "New", "Dev", "Skeet", "OneTap", "NeverLose");
        logoColor = new ListSetting("Logo Color", "Client", () -> logo.getCurrentValue() && !HUD.logoMode.currentMode.equals("OneTap") && !HUD.logoMode.currentMode.equals("NeverLose"), "Client", "Custom", "Original");
        logoGlow = new BooleanSetting("Logo Glow", true, () -> HUD.logoMode.currentMode.equals("Dev") || HUD.logoMode.currentMode.equals("New"));
        glowAlpha = new NumberSetting("Glow Alpha", 0.3f, 0.1f, 1.0f, 0.1f, () -> HUD.logoMode.currentMode.equals("Dev") && logoGlow.getCurrentValue());
        glowRadius = new NumberSetting("Glow Radius", 25.0f, 5.0f, 50.0f, 5.0f, () -> (HUD.logoMode.currentMode.equals("Dev") || HUD.logoMode.currentMode.equals("New")) && logoGlow.getCurrentValue());
        clientInfo = new BooleanSetting("Client Info", true, () -> true);
        worldInfo = new BooleanSetting("World Info", true, () -> true);
        MobEffect = new BooleanSetting("MobEffect Status", false, () -> true);
        potionIcons = new BooleanSetting("MobEffect Icons", true, MobEffect::getCurrentValue);
        potionTimeColor = new BooleanSetting("MobEffect Time Color", true, MobEffect::getCurrentValue);
        customRect = new ColorSetting("Custom Rect Color", Color.PINK.getRGB(), () -> HUD.logoColor.currentMode.equals("Custom"));
        armor = new BooleanSetting("Armor Status", true, () -> true);
        rustHUD = new BooleanSetting("Rust", false, () -> true);
        font = new ListSetting("FontList", "RobotoRegular", () -> true, "Minecraft", "MontserratRegular", "MontserratLight", "Menlo", "Comfortaa", "SF UI", "Calibri", "Verdana", "CircleRegular", "RobotoRegular", "JetBrains Mono", "LucidaConsole", "Corporative Sans", "Lato", "RaleWay", "Product Sans", "Arial", "Open Sans", "Kollektif", "Ubuntu");
        time = new NumberSetting("Color Time", 30.0f, 1.0f, 100.0f, 1.0f, () -> true);
        this.addSettings(colorList, onecolor, twocolor, rainbowSaturation, rainbowBright, time, logo, blur, logoMode, logoColor, logoGlow, glowRadius, glowAlpha, customRect, clientInfo, worldInfo, MobEffect, potionIcons, potionTimeColor, armor, rustHUD);
    }

    @EventTarget
    public void onRender2D(EventRender2D e) {
        if (!getState() || mc.player == null || mc.level == null) return;
        for (DraggableModule draggableModule : Celestial.instance.draggableManager.getMods()) {
            if (getState() && !draggableModule.name.equals("LogoComponent")) {
                draggableModule.draw();
            }
        }
        float target = (mc.currentScreen instanceof GuiChat) ? 15 : 0;
        float delta = globalOffset - target;
        globalOffset -= delta / Math.max(1, Minecraft.getDebugFPS()) * 10;
        if (!Double.isFinite(globalOffset)) {
            globalOffset = 0;
        }
        if (globalOffset > 15) {
            globalOffset = 15;
        }
        if (globalOffset < 0) {
            globalOffset = 0;
        }
    }
}