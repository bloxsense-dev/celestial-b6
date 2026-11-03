package net.minecraft.entity.projectile;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class EntityDragonFireball extends Entity {
    public EntityDragonFireball(EntityType<?> type, Level level) {
        super(type, level);
    }
}
