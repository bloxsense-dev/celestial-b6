package org.celestial.client.feature.impl.player;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.util.math.BlockPos;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.player.EventUpdate;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;

public class Eagle extends Feature {

    public Eagle() {
        super("Eagle", "Нажимает шифт когда строишься", Type.Ghost);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        BlockPos pos = new BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ);

        mc.gameSettings.keyBindSneak.pressed = mc.world.getBlockState(pos).getBlock() == Blocks.AIR;
    }
}
