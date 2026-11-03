package net.minecraft.entity.boss;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class EntityDragon extends LivingEntity {
    public EntityDragon(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }
}
