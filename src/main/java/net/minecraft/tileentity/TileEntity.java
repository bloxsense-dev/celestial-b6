package net.minecraft.tileentity;

public interface TileEntity {
    net.minecraft.util.math.BlockPos getPos();

    default void markDirty() {}

    default boolean hasWorld() { return true; }

    default boolean isInvalid() { return false; }
}
