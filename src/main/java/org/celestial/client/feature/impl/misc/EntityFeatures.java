package org.celestial.client.feature.impl.misc;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.player.EventUpdate;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.settings.impl.BooleanSetting;
import org.celestial.client.settings.impl.NumberSetting;

public class EntityFeatures extends Feature {

    public static BooleanSetting entityControl;
    public static BooleanSetting entitySpeed;
    public static NumberSetting entitySpeedValue;

    public EntityFeatures() {
        super("EntityFeatures", "Позволяет контролировать животных", Type.Misc);
        entityControl = new BooleanSetting("Entity Control", true, () -> true);
        entitySpeed = new BooleanSetting("Entity Speed", true, entityControl::getCurrentValue);
        entitySpeedValue = new NumberSetting("Entity Speed Multiplier", 1, 0, 2, 0.1F, entityControl::getCurrentValue);
        addSettings(entityControl, entitySpeed, entitySpeedValue);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (entityControl.getCurrentValue()) {
            Entity ridingEntity = mc.player.getRidingEntity();
            assert ridingEntity != null;
            if (ridingEntity instanceof AbstractHorse) {
                mc.player.horseJumpPowerCounter = 9;
                mc.player.horseJumpPower = 1f;
            }
        }
        if (entitySpeed.getCurrentValue()) {
            if (mc.player != null && mc.player.getRidingEntity() != null) {
                double forward = mc.player.movementInput.moveForward;
                double strafe = mc.player.movementInput.moveStrafe;
                float yaw = mc.player.rotationYaw;
                if (forward == 0.0 && strafe == 0.0) {
                    mc.player.getRidingEntity().motionX = 0.0;
                    mc.player.getRidingEntity().motionZ = 0.0;
                } else {
                    if (forward != 0.0) {
                        if (strafe > 0.0) {
                            yaw += (float) (forward > 0.0 ? -45 : 45);
                        } else if (strafe < 0.0) {
                            yaw += (float) (forward > 0.0 ? 45 : -45);
                        }
                        strafe = 0.0;
                        if (forward > 0.0) {
                            forward = 1.0;
                        } else if (forward < 0.0) {
                            forward = -1.0;
                        }
                    }
                    double cos = Math.cos(Math.toRadians(yaw + 90.0f));
                    double sin = Math.sin(Math.toRadians(yaw + 90.0f));
                    mc.player.getRidingEntity().motionX = forward * entitySpeedValue.getCurrentValue() * cos + strafe * entitySpeedValue.getCurrentValue() * sin;
                    mc.player.getRidingEntity().motionZ = forward * entitySpeedValue.getCurrentValue() * sin - strafe * entitySpeedValue.getCurrentValue() * cos;
                }
            }
        }
    }
}