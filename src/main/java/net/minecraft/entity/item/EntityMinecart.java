package net.minecraft.entity.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class EntityMinecart extends Entity {
    public EntityMinecart(EntityType<?> type, Level level) {
        super(type, level);
    }
}
