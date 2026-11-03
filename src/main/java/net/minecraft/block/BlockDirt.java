package net.minecraft.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BlockDirt extends Block {
    public BlockDirt() {
        super(BlockBehaviour.Properties.of());
    }

    public BlockDirt(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
