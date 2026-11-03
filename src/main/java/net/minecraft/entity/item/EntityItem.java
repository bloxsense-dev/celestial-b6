package net.minecraft.entity.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;

public abstract class EntityItem extends Entity {
    public EntityItem(EntityType<?> type, Level level) {
        super(type, level);
    }

    public ItemStack getEntityItem() {
        return ItemStack.EMPTY;
    }
}
