package org.celestial.client.feature.impl.player;

import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.math.BlockPos;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.input.EventMouse;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.helpers.math.RotationHelper;
import org.celestial.client.helpers.misc.ChatHelper;
import org.celestial.client.settings.impl.BooleanSetting;
import org.celestial.client.settings.impl.NumberSetting;

public class ClipHelper extends Feature {

    public static BooleanSetting disableBlockLight;
    public static NumberSetting maxDistance;

    public ClipHelper() {
        super("ClipHelper", "Клипается по Y оси при нажатии на колесо мыши по игроку", Type.Player);
        maxDistance = new NumberSetting("Max Distance", 50, 5, 150, 1, () -> true);
        disableBlockLight = new BooleanSetting("Disable block light", true, () -> true);
        addSettings(maxDistance, disableBlockLight);
    }

    @EventTarget
    public void onMouse(EventMouse event) {
        for (Entity entity : mc.world.loadedEntityList) {
            BlockPos playerPosY = new BlockPos(0, mc.player.posY, 0);
            BlockPos entityPosY = new BlockPos(0, entity.posY, 0);
            if (RotationHelper.isLookingAtEntity(mc.player.rotationYaw, mc.player.rotationPitch, 0.15F, 0.15F, 0.15F, entity, maxDistance.getCurrentValue())) {
                int findToClip = (int) entity.posY;
                if (!playerPosY.equals(entityPosY) && mc.gameSettings.thirdPersonView == 0) {
                    if (event.getKey() == 2) {
                        mc.player.setPositionAndUpdate(mc.player.posX, findToClip, mc.player.posZ);
                        ChatHelper.addChatMessage("Clip to entity " + ChatFormatting.RED + entity.getName() + ChatFormatting.WHITE + " on Y " + ChatFormatting.RED + findToClip);
                    }
                }
            }
        }
    }
}
