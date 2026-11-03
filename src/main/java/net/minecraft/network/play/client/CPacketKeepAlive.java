package net.minecraft.network.play.client;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.network.protocol.PacketType;

public class CPacketKeepAlive implements Packet<ServerGamePacketListener> {
    private long id;
    public CPacketKeepAlive() {}
    public CPacketKeepAlive(long id) { this.id = id; }
    public long getId() { return id; }
    @Override public PacketType<? extends Packet<ServerGamePacketListener>> type() { return null; }
    @Override public void handle(ServerGamePacketListener listener) {}
}
