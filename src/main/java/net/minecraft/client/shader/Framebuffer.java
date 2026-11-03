package net.minecraft.client.shader;

public class Framebuffer {
    public int framebufferTexture = 0;
    public int framebufferWidth;
    public int framebufferHeight;

    public Framebuffer(int width, int height, boolean useDepth) {
        this.framebufferWidth = width;
        this.framebufferHeight = height;
    }

    public void bindFramebuffer(boolean setViewport) {
    }

    public void framebufferClear() {
    }

    public void deleteFramebuffer() {
    }
}
