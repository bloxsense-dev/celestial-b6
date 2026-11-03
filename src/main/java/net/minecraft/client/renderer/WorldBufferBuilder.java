package net.minecraft.client.renderer;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.vertex.VertexFormat;

public class WorldBufferBuilder {
    private int drawMode;
    private boolean isDrawing;
    private double curX, curY, curZ;
    private boolean hasPos;
    private float curR = 1f, curG = 1f, curB = 1f, curA = 1f;
    private boolean hasColor;
    private double curU, curV;
    private boolean hasTex;

    public void begin(int glMode, VertexFormat format) {
        this.drawMode = glMode;
        this.isDrawing = true;
        this.hasPos = false;
        this.hasColor = false;
        this.hasTex = false;
        GL11.glBegin(glMode);
    }

    public WorldBufferBuilder pos(double x, double y, double z) {
        if (hasPos) {
            endVertex();
        }
        this.curX = x;
        this.curY = y;
        this.curZ = z;
        this.hasPos = true;
        return this;
    }

    public WorldBufferBuilder color(float r, float g, float b, float a) {
        this.curR = r;
        this.curG = g;
        this.curB = b;
        this.curA = a;
        this.hasColor = true;
        return this;
    }

    public WorldBufferBuilder color(int r, int g, int b, int a) {
        return this.color(r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f);
    }

    public WorldBufferBuilder tex(double u, double v) {
        this.curU = u;
        this.curV = v;
        this.hasTex = true;
        return this;
    }

    public void endVertex() {
        if (hasColor) {
            GL11.glColor4f(curR, curG, curB, curA);
            hasColor = false;
        }
        if (hasTex) {
            GL11.glTexCoord2d(curU, curV);
            hasTex = false;
        }
        if (hasPos) {
            GL11.glVertex3d(curX, curY, curZ);
            hasPos = false;
        }
    }

    public void draw() {
        if (hasPos) {
            endVertex();
        }
        if (isDrawing) {
            GL11.glEnd();
            isDrawing = false;
        }
    }
}
