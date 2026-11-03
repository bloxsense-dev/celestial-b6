package net.minecraft.network.play.server;

import net.minecraft.core.BlockPos;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.PacketType;

public class SPacketBlockChange implements Packet<ClientGamePacketListener> {
    private BlockPos blockPosition = BlockPos.ZERO;
    private IBlockState blockState;

    public SPacketBlockChange() {}
    public BlockPos getBlockPosition() { return blockPosition; }
    public IBlockState getBlockState() { return blockState; }
    @Override public PacketType<? extends Packet<ClientGamePacketListener>> type() { return null; }
    @Override public void handle(ClientGamePacketListener listener) {}
}
