package net.minecraft.network.play.client;

import net.minecraft.core.BlockPos;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.util.EnumFacing;

public class CPacketPlayerDigging implements Packet<PacketListener> {
    public enum Action {
        START_DESTROY_BLOCK,
        ABORT_DESTROY_BLOCK,
        STOP_DESTROY_BLOCK,
        DROP_ALL_ITEMS,
        DROP_ITEM,
        RELEASE_USE_ITEM,
        SWAP_HELD_ITEMS
    }

    private Action action;
    private BlockPos position;
    private EnumFacing facing;

    public CPacketPlayerDigging() {}

    public CPacketPlayerDigging(Action action, BlockPos position, EnumFacing facing) {
        this.action = action;
        this.position = position;
        this.facing = facing;
    }

    public Action getAction() {
        return action;
    }

    public BlockPos getPosition() {
        return position;
    }

    public EnumFacing getFacing() {
        return facing;
    }

    @Override
    public PacketType<CPacketPlayerDigging> type() {
        return null;
    }

    @Override
    public void handle(PacketListener listener) {}
}
