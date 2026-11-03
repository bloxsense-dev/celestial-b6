package net.minecraft.network.play.server;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.PacketType;

public class SPacketExplosion implements Packet<ClientGamePacketListener> {
    private double x, y, z;
    private float strength;
    public float motionX, motionY, motionZ;

    public SPacketExplosion() {}
    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }
    public float getStrength() { return strength; }
    public float getMotionX() { return motionX; }
    public float getMotionY() { return motionY; }
    public float getMotionZ() { return motionZ; }
    @Override public PacketType<? extends Packet<ClientGamePacketListener>> type() { return null; }
    @Override public void handle(ClientGamePacketListener listener) {}
}
