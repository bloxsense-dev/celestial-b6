package net.minecraft.util.math;

import net.minecraft.util.Mth;

public class MathHelper {
    public static final float PI = (float) Math.PI;
    public static final float PI2 = (float) (Math.PI * 2.0);

    public static float sin(float value) {
        return Mth.sin(value);
    }

    public static float cos(float value) {
        return Mth.cos(value);
    }

    public static float sqrt(float value) {
        return Mth.sqrt(value);
    }

    public static float sqrt(double value) {
        return (float) Math.sqrt(value);
    }

    public static int floor(float value) {
        return Mth.floor(value);
    }

    public static int floor(double value) {
        return Mth.floor(value);
    }

    public static long lfloor(double value) {
        return Mth.lfloor(value);
    }

    public static float abs(float value) {
        return Mth.abs(value);
    }

    public static int abs(int value) {
        return Mth.abs(value);
    }

    public static int clamp(int num, int min, int max) {
        return Mth.clamp(num, min, max);
    }

    public static float clamp(float num, float min, float max) {
        return Mth.clamp(num, min, max);
    }

    public static double clamp(double num, double min, double max) {
        return Mth.clamp(num, min, max);
    }

    public static double wrapDegrees(double value) {
        return Mth.wrapDegrees(value);
    }

    public static float wrapDegrees(float value) {
        return Mth.wrapDegrees(value);
    }

    public static double atan2(double y, double x) {
        return Math.atan2(y, x);
    }
}
