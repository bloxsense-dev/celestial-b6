package org.celestial.client.feature.impl.movement;

import net.minecraft.init.MobEffects;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.player.EventUpdate;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.helpers.player.MovementHelper;
import org.celestial.client.settings.impl.BooleanSetting;
import org.celestial.client.settings.impl.NumberSetting;

public class WaterSpeed extends Feature {

    public static NumberSetting speed;
    private final BooleanSetting speedCheck;

    public WaterSpeed() {
        super("WaterSpeed", "Делает вас быстрее в воде", Type.Movement);
        speed = new NumberSetting("Speed Amount", 1, 0.1F, 4, 0.01F, () -> true);
        speedCheck = new BooleanSetting("Speed MobEffect Check", false, () -> true);
        addSettings(speedCheck, speed);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (!mc.player.isPotionActive(MobEffects.SPEED) && speedCheck.getCurrentValue()) {
            return;
        }
        if (mc.player.isInLiquid()) {
            MovementHelper.setSpeed(speed.getCurrentValue());
        }
    }
}
