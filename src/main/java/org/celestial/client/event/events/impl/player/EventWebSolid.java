package org.celestial.client.event.events.impl.player;

import net.minecraft.world.level.block.WebBlock;
import net.minecraft.util.math.BlockPos;
import org.celestial.client.event.events.callables.EventCancellable;

public class EventWebSolid extends EventCancellable {

    private final WebBlock blockWeb;
    private final BlockPos pos;

    public EventWebSolid(WebBlock blockLiquid, BlockPos pos) {
        this.blockWeb = blockLiquid;
        this.pos = pos;
    }

    public WebBlock getBlock() {
        return blockWeb;
    }

    public BlockPos getPos() {
        return pos;
    }
}
