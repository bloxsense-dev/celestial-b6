package net.minecraft.item;

import net.minecraft.world.level.block.Block;

public class ItemBlock extends Item {
    private Block block;

    public ItemBlock() {
        super();
    }

    public ItemBlock(Block block) {
        super();
        this.block = block;
    }

    public Block getBlock() {
        return this.block;
    }
}
