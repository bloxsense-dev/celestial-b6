package net.minecraft.util.math;

public class BlockPos extends net.minecraft.core.BlockPos {
    public static final BlockPos ORIGIN = new BlockPos(0, 0, 0);

    public BlockPos(int x, int y, int z) {
        super(x, y, z);
    }

    public BlockPos(double x, double y, double z) {
        super((int) x, (int) y, (int) z);
    }

    public BlockPos(Vec3d vec) {
        super((int) vec.x, (int) vec.y, (int) vec.z);
    }

    public BlockPos(net.minecraft.core.BlockPos pos) {
        super(pos.getX(), pos.getY(), pos.getZ());
    }

    public BlockPos(net.minecraft.core.Vec3i vec) {
        super(vec.getX(), vec.getY(), vec.getZ());
    }

    public BlockPos(net.minecraft.world.entity.Entity entity) {
        super((int) entity.getX(), (int) entity.getY(), (int) entity.getZ());
    }

    public BlockPos up() {
        return new BlockPos(super.above());
    }

    public BlockPos up(int n) {
        return new BlockPos(super.above(n));
    }

    public BlockPos down() {
        return new BlockPos(super.below());
    }

    public BlockPos down(int n) {
        return new BlockPos(super.below(n));
    }

    public BlockPos north() {
        return new BlockPos(super.north());
    }

    public BlockPos north(int n) {
        return new BlockPos(super.north(n));
    }

    public BlockPos south() {
        return new BlockPos(super.south());
    }

    public BlockPos south(int n) {
        return new BlockPos(super.south(n));
    }

    public BlockPos east() {
        return new BlockPos(super.east());
    }

    public BlockPos east(int n) {
        return new BlockPos(super.east(n));
    }

    public BlockPos west() {
        return new BlockPos(super.west());
    }

    public BlockPos west(int n) {
        return new BlockPos(super.west(n));
    }

    public BlockPos add(int x, int y, int z) {
        return new BlockPos(super.offset(x, y, z));
    }

    public BlockPos add(double x, double y, double z) {
        return new BlockPos(super.offset((int) x, (int) y, (int) z));
    }

    public BlockPos offset(net.minecraft.util.EnumFacing facing) {
        return new BlockPos(super.relative(facing.toDirection()));
    }

    public BlockPos offset(net.minecraft.util.EnumFacing facing, int n) {
        return new BlockPos(super.relative(facing.toDirection(), n));
    }
}
