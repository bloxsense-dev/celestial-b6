package net.minecraft.network.play.client;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.network.protocol.PacketType;

public class CPacketInput implements Packet<ServerGamePacketListener> {
    public CPacketInput() {}
    public CPacketInput(float strafeSpeed, float forwardSpeed, boolean jumping, boolean sneaking) {}
    @Override public PacketType<? extends Packet<ServerGamePacketListener>> type() { return null; }
    @Override public void handle(ServerGamePacketListener listener) {}
}
