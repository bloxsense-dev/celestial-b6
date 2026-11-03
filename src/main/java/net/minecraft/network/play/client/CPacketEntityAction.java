package net.minecraft.network.play.client;

import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.world.entity.Entity;

public class CPacketEntityAction implements Packet<PacketListener> {
    public enum Action {
        START_SNEAKING,
        STOP_SNEAKING,
        STOP_SLEEPING,
        START_SPRINTING,
        STOP_SPRINTING,
        START_RIDING_JUMP,
        STOP_RIDING_JUMP,
        OPEN_INVENTORY,
        START_FALL_FLYING
    }

    private Entity entity;
    private Action action;
    private int auxData;

    public CPacketEntityAction() {}

    public CPacketEntityAction(Entity entity, Action action) {
        this(entity, action, 0);
    }

    public CPacketEntityAction(Entity entity, Action action, int auxData) {
        this.entity = entity;
        this.action = action;
        this.auxData = auxData;
    }

    public Action getAction() {
        return action;
    }

    @Override
    public PacketType<CPacketEntityAction> type() {
        return null;
    }

    @Override
    public void handle(PacketListener listener) {}
}
