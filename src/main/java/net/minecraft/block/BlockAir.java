package net.minecraft.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BlockAir extends Block {
    public BlockAir() {
        super(BlockBehaviour.Properties.of());
    }

    public BlockAir(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
