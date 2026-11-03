package net.minecraft.entity.passive;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class EntityVillager extends LivingEntity {
    public EntityVillager(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }
}
