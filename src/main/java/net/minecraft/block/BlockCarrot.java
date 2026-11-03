package net.minecraft.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BlockCarrot extends Block {
    public BlockCarrot() {
        super(BlockBehaviour.Properties.of());
    }

    public BlockCarrot(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
