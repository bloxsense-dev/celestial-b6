package net.minecraft.util.math;

import net.minecraft.world.phys.Vec3;

public class Vec3d {
    public final double x;
    public final double y;
    public final double z;
    public double xCoord;
    public double yCoord;
    public double zCoord;

    public Vec3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.xCoord = x;
        this.yCoord = y;
        this.zCoord = z;
    }

    public Vec3d(Vec3 vec) {
        this(vec.x, vec.y, vec.z);
    }

    public Vec3 toVec3() {
        return new Vec3(x, y, z);
    }

    public double distanceTo(Vec3d vec) {
        double d0 = vec.x - this.x;
        double d1 = vec.y - this.y;
        double d2 = vec.z - this.z;
        return Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    }

    public Vec3d add(Vec3d vec) {
        return new Vec3d(this.x + vec.x, this.y + vec.y, this.z + vec.z);
    }

    public Vec3d addVector(double x, double y, double z) {
        return new Vec3d(this.x + x, this.y + y, this.z + z);
    }

    public Vec3d subtract(Vec3d vec) {
        return new Vec3d(this.x - vec.x, this.y - vec.y, this.z - vec.z);
    }

    public double lengthVector() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public Vec3d rotatePitch(float pitch) {
        float f = (float) Math.cos(pitch);
        float f1 = (float) Math.sin(pitch);
        double d0 = this.x;
        double d1 = this.y * (double)f + this.z * (double)f1;
        double d2 = this.z * (double)f - this.y * (double)f1;
        return new Vec3d(d0, d1, d2);
    }

    public Vec3d rotateYaw(float yaw) {
        float f = (float) Math.cos(yaw);
        float f1 = (float) Math.sin(yaw);
        double d0 = this.x * (double)f + this.z * (double)f1;
        double d1 = this.y;
        double d2 = this.z * (double)f - this.x * (double)f1;
        return new Vec3d(d0, d1, d2);
    }
}
