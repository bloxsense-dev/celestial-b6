package net.minecraft.network.play.client;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.network.protocol.PacketType;

public class CPacketChatMessage implements Packet<ServerGamePacketListener> {
    private String message;
    public CPacketChatMessage() {}
    public CPacketChatMessage(String message) { this.message = message; }
    public String getMessage() { return message; }
    @Override public PacketType<? extends Packet<ServerGamePacketListener>> type() { return null; }
    @Override public void handle(ServerGamePacketListener listener) {}
}
