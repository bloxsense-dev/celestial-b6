package net.minecraft.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BlockWeb extends Block {
    public BlockWeb() {
        super(BlockBehaviour.Properties.of());
    }

    public BlockWeb(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
