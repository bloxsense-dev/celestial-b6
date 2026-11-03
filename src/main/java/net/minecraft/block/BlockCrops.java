package net.minecraft.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class BlockCrops extends Block {
    public BlockCrops() {
        super(BlockBehaviour.Properties.of());
    }

    public BlockCrops(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public boolean canGrow(Level world, BlockPos pos, BlockState state, boolean isClient) {
        return false;
    }

    public boolean canGrow(Level world, BlockPos pos, net.minecraft.block.state.IBlockState state, boolean isClient) {
        return false;
    }
}
