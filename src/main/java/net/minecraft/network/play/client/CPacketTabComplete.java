package net.minecraft.network.play.client;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.network.protocol.PacketType;

public class CPacketTabComplete implements Packet<ServerGamePacketListener> {
    public CPacketTabComplete() {}
    public CPacketTabComplete(String message, BlockPos target, boolean hasTarget) {}
    @Override public PacketType<? extends Packet<ServerGamePacketListener>> type() { return null; }
    @Override public void handle(ServerGamePacketListener listener) {}
}
