package org.celestial.client.feature.impl.movement;

import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.player.EventUpdate;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.helpers.player.MovementHelper;
import org.celestial.client.settings.impl.NumberSetting;

public class Strafe extends Feature {

    public NumberSetting speed = new NumberSetting("Strafe Speed", 0.1F, 0.1F, 1, 0.01F, () -> true);

    public Strafe() {
        super("Strafe", "Ты можешь стрейфиться", Type.Movement);
        addSettings(speed);
    }

    @EventTarget
    public void onPlayerTick(EventUpdate e) {
        if (!mc.gameSettings.keyBindForward.pressed)
            return;

        MovementHelper.strafePlayer(speed.getCurrentValue());
    }
}