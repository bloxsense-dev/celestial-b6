package net.minecraft.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BlockSand extends Block {
    public BlockSand() {
        super(BlockBehaviour.Properties.of());
    }

    public BlockSand(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
