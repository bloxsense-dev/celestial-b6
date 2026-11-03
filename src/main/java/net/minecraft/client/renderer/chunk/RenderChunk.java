package net.minecraft.client.renderer.chunk;

import net.minecraft.core.BlockPos;

public class RenderChunk {
    private BlockPos position = BlockPos.ZERO;

    public RenderChunk() {}

    public RenderChunk(BlockPos position) {
        this.position = position;
    }

    public BlockPos getPosition() {
        return this.position;
    }

    public void setPosition(BlockPos position) {
        this.position = position;
    }
}
