package net.minecraft.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class ItemTool extends Item {
    public float damageVsEntity = 4.0f;
    public float efficiency = 4.0f;

    public ItemTool() {
        super();
    }

    public float getDamageVsEntity() {
        return this.damageVsEntity;
    }

    public float getStrVsBlock(ItemStack stack, BlockState state) {
        return this.efficiency;
    }
}
