package org.celestial.client.feature.impl.visual;

import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.settings.impl.BooleanSetting;
import org.celestial.client.settings.impl.ColorSetting;
import org.celestial.client.settings.impl.ListSetting;

import java.awt.*;

public class Chams extends Feature {

    public static ColorSetting colorChams;
    public static BooleanSetting clientColor;
    public static ListSetting chamsMode;

    public Chams() {
        super("Chams", "Подсвечивает игроков сквозь стены", Type.Visuals);
        chamsMode = new ListSetting("Chams Mode", "Fill", () -> true, "Fill", "Outline", "Walls");
        clientColor = new BooleanSetting("Client Colored", false, () -> !chamsMode.currentMode.equals("Walls"));
        colorChams = new ColorSetting("Chams Color", new Color(0xFFFFFF).getRGB(), () -> !chamsMode.currentMode.equals("Walls") && !clientColor.getCurrentValue());
        addSettings(chamsMode, colorChams, clientColor);
    }
}