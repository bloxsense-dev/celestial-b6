package net.minecraft.entity.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class EntityXPOrb extends Entity {
    public EntityXPOrb(EntityType<?> type, Level level) {
        super(type, level);
    }
}
