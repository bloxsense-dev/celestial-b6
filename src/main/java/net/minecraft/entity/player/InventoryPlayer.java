package net.minecraft.entity.player;

import net.minecraft.world.item.ItemStack;

public abstract class InventoryPlayer {
    public int currentItem;

    public ItemStack getStackInSlot(int slot) {
        return ItemStack.EMPTY;
    }

    public ItemStack armorItemInSlot(int slot) {
        return ItemStack.EMPTY;
    }
}
