package org.celestial.client.feature.impl.visual;

import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.settings.impl.NumberSetting;

public class ItemPhysics extends Feature {

    public static NumberSetting physicsSpeed;

    public ItemPhysics() {
        super("ItemPhysics", "Добавляет физику предметов при их выбрасивании", Type.Visuals);
        physicsSpeed = new NumberSetting("Physics Speed", 0.5F, 0.1F, 5, 0.5F, () -> true);
        addSettings(physicsSpeed);
    }
}
