package net.minecraft.network.handshake.client;

import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;

public class C00Handshake implements Packet<PacketListener> {
    @Override
    public PacketType<C00Handshake> type() {
        return null;
    }

    @Override
    public void handle(PacketListener listener) {
    }
}
