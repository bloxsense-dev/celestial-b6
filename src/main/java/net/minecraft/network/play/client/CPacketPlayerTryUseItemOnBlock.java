package net.minecraft.network.play.client;

import net.minecraft.core.BlockPos;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;

public class CPacketPlayerTryUseItemOnBlock implements Packet<PacketListener> {
    private BlockPos position;
    private EnumFacing placedBlockDirection;
    private EnumHand hand;
    private float facingX, facingY, facingZ;

    public CPacketPlayerTryUseItemOnBlock() {}

    public CPacketPlayerTryUseItemOnBlock(BlockPos posIn, EnumFacing placedBlockDirectionIn, EnumHand handIn, float facingXIn, float facingYIn, float facingZIn) {
        this.position = posIn;
        this.placedBlockDirection = placedBlockDirectionIn;
        this.hand = handIn;
        this.facingX = facingXIn;
        this.facingY = facingYIn;
        this.facingZ = facingZIn;
    }

    public BlockPos getPos() {
        return position;
    }

    public EnumFacing getDirection() {
        return placedBlockDirection;
    }

    public EnumHand getHand() {
        return hand;
    }

    @Override
    public PacketType<CPacketPlayerTryUseItemOnBlock> type() {
        return null;
    }

    @Override
    public void handle(PacketListener listener) {}
}
