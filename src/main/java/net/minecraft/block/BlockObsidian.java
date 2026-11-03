package net.minecraft.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BlockObsidian extends Block {
    public BlockObsidian() {
        super(BlockBehaviour.Properties.of());
    }

    public BlockObsidian(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
