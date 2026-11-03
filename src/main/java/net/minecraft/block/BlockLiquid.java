package net.minecraft.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BlockLiquid extends Block {
    public BlockLiquid() {
        super(BlockBehaviour.Properties.of());
    }

    public BlockLiquid(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
