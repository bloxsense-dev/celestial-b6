package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;

public class ScaledResolution {
    private final Minecraft mc;

    public ScaledResolution(Minecraft mc) {
        this.mc = mc;
    }

    public int getScaledWidth() {
        return this.mc != null && this.mc.getWindow() != null ? this.mc.getWindow().getGuiScaledWidth() : 1920;
    }

    public int getScaledHeight() {
        return this.mc != null && this.mc.getWindow() != null ? this.mc.getWindow().getGuiScaledHeight() : 1080;
    }

    public double getScaledWidth_double() {
        return (double) this.getScaledWidth();
    }

    public double getScaledHeight_double() {
        return (double) this.getScaledHeight();
    }

    public int getScaleFactor() {
        return this.mc != null && this.mc.getWindow() != null ? (int) this.mc.getWindow().getGuiScale() : 2;
    }
}
