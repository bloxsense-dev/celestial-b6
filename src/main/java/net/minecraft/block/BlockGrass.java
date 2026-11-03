package net.minecraft.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BlockGrass extends Block {
    public BlockGrass() {
        super(BlockBehaviour.Properties.of());
    }

    public BlockGrass(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
