package net.minecraft.network.play.client;

import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.util.EnumHand;

public class CPacketPlayerTryUseItem implements Packet<PacketListener> {
    private EnumHand hand;

    public CPacketPlayerTryUseItem() {}

    public CPacketPlayerTryUseItem(EnumHand hand) {
        this.hand = hand;
    }

    public EnumHand getHand() {
        return hand;
    }

    @Override
    public PacketType<CPacketPlayerTryUseItem> type() {
        return null;
    }

    @Override
    public void handle(PacketListener listener) {}
}
