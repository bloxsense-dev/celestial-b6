package net.minecraft.network.play.client;

import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;

public class CPacketHeldItemChange implements Packet<PacketListener> {
    private int slotId;

    public CPacketHeldItemChange() {}

    public CPacketHeldItemChange(int slotId) {
        this.slotId = slotId;
    }

    public int getSlotId() {
        return slotId;
    }

    @Override
    public PacketType<CPacketHeldItemChange> type() {
        return null;
    }

    @Override
    public void handle(PacketListener listener) {}
}
