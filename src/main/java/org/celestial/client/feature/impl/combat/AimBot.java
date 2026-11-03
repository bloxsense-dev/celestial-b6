package org.celestial.client.feature.impl.combat;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.BowItem;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.player.EventPreMotion;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.helpers.math.RotationHelper;

public class AimBot extends Feature {

    public AimBot() {
        super("AimBot", "", Type.Combat);
    }

    @EventTarget
    public void onPreMotion(EventPreMotion event) {
        for (Entity entity : mc.world.loadedEntityList) {
            if (mc.player.getHeldItemMainhand().getItem() instanceof BowItem && mc.player.getDistanceToEntity(entity) < 6 && entity != null) {
                float[] rots = RotationHelper.getRotations(entity, false, 360, 360, 2, 2);
                event.setYaw(rots[0]);
                mc.player.rotationYawHead = rots[0];
                mc.player.renderYawOffset = rots[0];
            }
        }
    }
}
