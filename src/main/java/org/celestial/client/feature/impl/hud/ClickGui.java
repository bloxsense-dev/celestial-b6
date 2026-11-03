/*
 * Decompiled with CFR 0.150.
 */
package org.celestial.client.feature.impl.hud;

import java.awt.Color;
import org.celestial.client.Celestial;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.settings.impl.BooleanSetting;
import org.celestial.client.settings.impl.ColorSetting;
import org.celestial.client.settings.impl.ListSetting;
import org.celestial.client.settings.impl.NumberSetting;

public class ClickGui
        extends Feature {
    public static ListSetting scrollMode;
    public static NumberSetting scrollSpeed;
    public static BooleanSetting scrollInversion;
    public static BooleanSetting gradientBackground;
    public static ColorSetting color;
    public static BooleanSetting girl;
    public static BooleanSetting arrows;
    public static ListSetting panelMode;
    public static ListSetting girlMode;

    public ClickGui() {
        super("ClickGui", "Открывает клик гуй чита", Type.Hud);
        this.setBind(54);
        scrollMode = new ListSetting("Scroll Mode", "All Panels", "All Panels", "One Panel");
        scrollSpeed = new NumberSetting("Scroll Speed", 15.0f, 5.0f, 30.0f, 1.0f, () -> true);
        scrollInversion = new BooleanSetting("Scroll Inversion", true, () -> true);
        color = new ColorSetting("Gui Color", new Color(200, 78, 205, 25).getRGB(), () -> true);
        gradientBackground = new BooleanSetting("Gradient Background", true, () -> true);
        arrows = new BooleanSetting("Arrows", true, () -> true);
        girl = new BooleanSetting("Girl", true, () -> true);
        this.addSettings(color, panelMode, gradientBackground, scrollMode, scrollSpeed, scrollInversion, arrows, girl, girlMode);
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(Celestial.instance.newGui);
        Celestial.instance.featureManager.getFeatureByClass(ClickGui.class).setState(false);
        super.onEnable();
    }

    static {
        girlMode = new ListSetting("Girl Mode", "Girl1", () -> girl.getCurrentValue(), "Girl1", "Girl2", "Girl3", "Girl4", "Girl5", "Girl6", "Random");
    }
}

