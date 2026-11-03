package net.minecraft.network.play.client;

import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.entity.Entity;

public class CPacketUseEntity implements Packet<PacketListener> {
    public enum Action {
        INTERACT,
        ATTACK,
        INTERACT_AT
    }

    private int entityId;
    private Action action;
    private Vec3d hitVec;
    private EnumHand hand;

    public CPacketUseEntity() {}

    public CPacketUseEntity(Entity entity) {
        this.entityId = entity.getId();
        this.action = Action.ATTACK;
    }

    public CPacketUseEntity(Entity entity, Action action) {
        this.entityId = entity.getId();
        this.action = action;
    }

    public CPacketUseEntity(Entity entity, EnumHand hand) {
        this.entityId = entity.getId();
        this.action = Action.INTERACT;
        this.hand = hand;
    }

    public CPacketUseEntity(Entity entity, EnumHand hand, Vec3d hitVec) {
        this.entityId = entity.getId();
        this.action = Action.INTERACT_AT;
        this.hand = hand;
        this.hitVec = hitVec;
    }

    public Action getAction() {
        return action;
    }

    public Entity getEntityFromWorld(net.minecraft.world.level.Level level) {
        return level != null ? level.getEntity(entityId) : null;
    }

    @Override
    public PacketType<CPacketUseEntity> type() {
        return null;
    }

    @Override
    public void handle(PacketListener listener) {}
}
