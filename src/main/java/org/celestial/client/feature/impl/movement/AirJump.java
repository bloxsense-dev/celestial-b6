package org.celestial.client.feature.impl.movement;

import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.player.EventPreMotion;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;

public class AirJump extends Feature {

    public AirJump() {
        super("AirJump", "Позволяет прыгать по воздуху", Type.Movement);
    }

    @EventTarget
    public void onPreUpdate(EventPreMotion event) {
        if (mc.gameSettings.keyBindJump.pressed) {
            mc.player.onGround = true;
        }
    }
}
