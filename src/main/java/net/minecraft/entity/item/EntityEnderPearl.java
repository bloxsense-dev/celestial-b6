package net.minecraft.entity.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class EntityEnderPearl extends Entity {
    public EntityEnderPearl(EntityType<?> type, Level level) {
        super(type, level);
    }
}
