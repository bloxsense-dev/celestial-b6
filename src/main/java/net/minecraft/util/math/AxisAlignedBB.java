package net.minecraft.util.math;

import net.minecraft.world.phys.AABB;

public class AxisAlignedBB extends AABB {
    public AxisAlignedBB(double x1, double y1, double z1, double x2, double y2, double z2) {
        super(x1, y1, z1, x2, y2, z2);
    }

    public AxisAlignedBB(AABB aabb) {
        super(aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ);
    }

    public AxisAlignedBB(net.minecraft.core.BlockPos pos) {
        super(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
    }

    public AxisAlignedBB addCoord(double x, double y, double z) {
        double d0 = this.minX;
        double d1 = this.minY;
        double d2 = this.minZ;
        double d3 = this.maxX;
        double d4 = this.maxY;
        double d5 = this.maxZ;

        if (x < 0.0D) d0 += x;
        if (x > 0.0D) d3 += x;
        if (y < 0.0D) d1 += y;
        if (y > 0.0D) d4 += y;
        if (z < 0.0D) d2 += z;
        if (z > 0.0D) d5 += z;

        return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
    }

    public AxisAlignedBB expand(double x, double y, double z) {
        return new AxisAlignedBB(this.inflate(x, y, z));
    }

    public boolean isVecInside(Vec3d vec) {
        return this.contains(vec.xCoord, vec.yCoord, vec.zCoord);
    }

    public RayTraceResult calculateIntercept(Vec3d start, Vec3d end) {
        java.util.Optional<net.minecraft.world.phys.Vec3> opt = this.clip(start.toVec3(), end.toVec3());
        return opt.map(v -> new RayTraceResult(RayTraceResult.Type.BLOCK, new Vec3d(v), null)).orElse(null);
    }
}
