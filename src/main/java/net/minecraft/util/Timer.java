package net.minecraft.util;

public class Timer {
    public float renderPartialTicks = 1.0f;
    public float elapsedPartialTicks = 1.0f;
    public float timerSpeed = 1.0f;
    public long lastSyncSysClock = System.currentTimeMillis();
    public float tickLength = 50.0f;

    public Timer(float tps) {
        this.tickLength = 1000.0f / tps;
    }

    public Timer() {
        this(20.0f);
    }
}
