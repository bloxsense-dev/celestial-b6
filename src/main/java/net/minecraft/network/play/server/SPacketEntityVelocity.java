package net.minecraft.network.play.server;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.PacketType;

public class SPacketEntityVelocity implements Packet<ClientGamePacketListener> {
    private int entityID;
    public int motionX;
    public int motionY;
    public int motionZ;

    public SPacketEntityVelocity() {}
    public SPacketEntityVelocity(int entityID, double x, double y, double z) {
        this.entityID = entityID;
        this.motionX = (int)(x * 8000.0);
        this.motionY = (int)(y * 8000.0);
        this.motionZ = (int)(z * 8000.0);
    }
    public int getEntityID() { return entityID; }
    public int getMotionX() { return motionX; }
    public int getMotionY() { return motionY; }
    public int getMotionZ() { return motionZ; }
    @Override public PacketType<? extends Packet<ClientGamePacketListener>> type() { return null; }
    @Override public void handle(ClientGamePacketListener listener) {}
}
