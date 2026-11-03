package net.minecraft.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BlockFarmland extends Block {
    public BlockFarmland() {
        super(BlockBehaviour.Properties.of());
    }

    public BlockFarmland(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
