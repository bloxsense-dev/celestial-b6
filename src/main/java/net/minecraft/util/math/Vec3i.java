package net.minecraft.util.math;

public class Vec3i extends net.minecraft.core.Vec3i {
    public Vec3i(int xIn, int yIn, int zIn) {
        super(xIn, yIn, zIn);
    }

    public Vec3i(double xIn, double yIn, double zIn) {
        super((int) Math.floor(xIn), (int) Math.floor(yIn), (int) Math.floor(zIn));
    }
}
