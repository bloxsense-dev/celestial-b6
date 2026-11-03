package net.minecraft.network.play.server;

import net.minecraft.core.BlockPos;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.PacketType;

public class SPacketMultiBlockChange implements Packet<ClientGamePacketListener> {
    public SPacketMultiBlockChange() {}
    public BlockUpdateData[] getChangedBlocks() {
        return new BlockUpdateData[0];
    }
    @Override public PacketType<? extends Packet<ClientGamePacketListener>> type() { return null; }
    @Override public void handle(ClientGamePacketListener listener) {}

    public static class BlockUpdateData {
        public BlockPos getPos() { return BlockPos.ZERO; }
        public IBlockState getBlockState() { return null; }
    }
}
