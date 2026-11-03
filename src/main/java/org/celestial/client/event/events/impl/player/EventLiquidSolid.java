package org.celestial.client.event.events.impl.player;

import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.util.math.BlockPos;
import org.celestial.client.event.events.callables.EventCancellable;

public class EventLiquidSolid extends EventCancellable {

    private final LiquidBlock blockLiquid;
    private final BlockPos pos;

    public EventLiquidSolid(LiquidBlock blockLiquid, BlockPos pos) {
        this.blockLiquid = blockLiquid;
        this.pos = pos;
    }

    public LiquidBlock getBlock() {
        return blockLiquid;
    }

    public BlockPos getPos() {
        return pos;
    }
}