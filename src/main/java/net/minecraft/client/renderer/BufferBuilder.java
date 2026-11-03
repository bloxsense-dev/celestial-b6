package net.minecraft.client.renderer;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.vertex.VertexFormat;

public class BufferBuilder extends WorldBufferBuilder {
    @Override
    public BufferBuilder pos(double x, double y, double z) {
        super.pos(x, y, z);
        return this;
    }

    @Override
    public BufferBuilder color(float r, float g, float b, float a) {
        super.color(r, g, b, a);
        return this;
    }

    @Override
    public BufferBuilder color(int r, int g, int b, int a) {
        super.color(r, g, b, a);
        return this;
    }

    public BufferBuilder color(int colorIn) {
        int a = (colorIn >> 24) & 255;
        int r = (colorIn >> 16) & 255;
        int g = (colorIn >> 8) & 255;
        int b = colorIn & 255;
        return this.color(r, g, b, a);
    }

    @Override
    public BufferBuilder tex(double u, double v) {
        super.tex(u, v);
        return this;
    }
}
