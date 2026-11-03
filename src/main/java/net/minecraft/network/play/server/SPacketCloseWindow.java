package net.minecraft.network.play.server;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.PacketType;

public class SPacketCloseWindow implements Packet<ClientGamePacketListener> {
    public int windowId;
    public SPacketCloseWindow() {}
    public SPacketCloseWindow(int windowId) {
        this.windowId = windowId;
    }
    @Override public PacketType<? extends Packet<ClientGamePacketListener>> type() { return null; }
    @Override public void handle(ClientGamePacketListener listener) {}
}
