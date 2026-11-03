package net.minecraft.network.play.client;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.network.protocol.PacketType;

public class CPacketCloseWindow implements Packet<ServerGamePacketListener> {
    private int windowId;
    public CPacketCloseWindow() {}
    public CPacketCloseWindow(int windowId) { this.windowId = windowId; }
    public int getWindowId() { return windowId; }
    @Override public PacketType<? extends Packet<ServerGamePacketListener>> type() { return null; }
    @Override public void handle(ServerGamePacketListener listener) {}
}
