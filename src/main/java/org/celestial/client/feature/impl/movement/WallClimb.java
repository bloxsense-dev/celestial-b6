package org.celestial.client.feature.impl.movement;

import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.player.EventPreMotion;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.helpers.misc.TimerHelper;
import org.celestial.client.helpers.player.MovementHelper;
import org.celestial.client.settings.impl.NumberSetting;

public class WallClimb extends Feature {

    public static NumberSetting climbTicks;
    private final TimerHelper timerHelper = new TimerHelper();

    public WallClimb() {
        super("WallClimb", "Автоматически взберается на стены", Type.Movement);
        climbTicks = new NumberSetting("Climb Ticks", 1, 0, 5, 0.1F, () -> true);
        addSettings(climbTicks);
    }

    @EventTarget
    public void onPreMotion(EventPreMotion event) {
        this.setSuffix("" + climbTicks.getCurrentValue());
        if (MovementHelper.isMoving() && mc.player.isCollidedHorizontally) {
            if (timerHelper.hasReached(climbTicks.getCurrentValue() * 100)) {
                event.setOnGround(true);
                mc.player.onGround = true;
                mc.player.isCollidedVertically = true;
                mc.player.isCollidedHorizontally = true;
                mc.player.isAirBorne = true;
                mc.player.jump();
                timerHelper.reset();
            }
        }
    }
}
