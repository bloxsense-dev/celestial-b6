package net.minecraft.entity.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class EntityArmorStand extends LivingEntity {
    public EntityArmorStand(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }
}
