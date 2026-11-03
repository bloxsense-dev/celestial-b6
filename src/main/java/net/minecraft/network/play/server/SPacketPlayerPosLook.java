package net.minecraft.network.play.server;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.PacketType;

public class SPacketPlayerPosLook implements Packet<ClientGamePacketListener> {
    public double x;
    public double y;
    public double z;
    public float yaw;
    public float pitch;

    public SPacketPlayerPosLook() {}
    public SPacketPlayerPosLook(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }
    public float getYaw() { return yaw; }
    public float getPitch() { return pitch; }
    @Override public PacketType<? extends Packet<ClientGamePacketListener>> type() { return null; }
    @Override public void handle(ClientGamePacketListener listener) {}
}
