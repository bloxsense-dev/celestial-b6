package org.celestial.client.feature.impl.visual;

import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.settings.impl.ColorSetting;

import java.awt.*;

public class HitColor extends Feature {

    public static ColorSetting hitColor = new ColorSetting("Hit Color", Color.RED.getRGB(), () -> true);

    public HitColor() {
        super("HitColor", "Изменяет цвет удара", Type.Visuals);
        addSettings(hitColor);
    }
}
