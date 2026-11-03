package org.celestial.client.feature.impl.hud;

import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.settings.impl.BooleanSetting;

public class Notifications extends Feature {

    public static BooleanSetting state;

    public Notifications() {
        super("Notifications", "Показывает необходимую информацию о модулях", Type.Hud);
        state = new BooleanSetting("Module State", true, () -> true);
        addSettings(state);
    }
}