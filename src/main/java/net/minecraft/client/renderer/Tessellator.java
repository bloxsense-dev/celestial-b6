package net.minecraft.client.renderer;

public class Tessellator {
    private static final Tessellator INSTANCE = new Tessellator();
    private final BufferBuilder buffer = new BufferBuilder();

    public static Tessellator getInstance() {
        return INSTANCE;
    }

    public BufferBuilder getBuffer() {
        return buffer;
    }

    public void draw() {
        buffer.draw();
    }
}
