package org.celestial.client.feature.impl.visual;

import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.settings.impl.ColorSetting;

import java.awt.*;

public class WorldColor extends Feature {

    public static ColorSetting worldColor = new ColorSetting("World Color", Color.RED.getRGB(), () -> true);

    public WorldColor() {
        super("WorldColor", "Меняет цвет игры", Type.Visuals);
        addSettings(worldColor);
    }
}
