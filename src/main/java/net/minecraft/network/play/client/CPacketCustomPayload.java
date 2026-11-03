package net.minecraft.network.play.client;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.network.protocol.PacketType;

public class CPacketCustomPayload implements Packet<ServerGamePacketListener> {
    private String channel;
    private PacketBuffer data;

    public CPacketCustomPayload() {}
    public CPacketCustomPayload(String channel, PacketBuffer data) {
        this.channel = channel;
        this.data = data;
    }
    public String getChannelName() { return channel; }
    public PacketBuffer getBufferData() { return data; }
    @Override public PacketType<? extends Packet<ServerGamePacketListener>> type() { return null; }
    @Override public void handle(ServerGamePacketListener listener) {}
}
