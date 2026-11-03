package net.minecraft.network.play.server;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.PacketType;

public class SPacketTimeUpdate implements Packet<ClientGamePacketListener> {
    public SPacketTimeUpdate() {}
    @Override public PacketType<? extends Packet<ClientGamePacketListener>> type() { return null; }
    @Override public void handle(ClientGamePacketListener listener) {}
}
