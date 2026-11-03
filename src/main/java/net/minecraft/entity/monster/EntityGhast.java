package net.minecraft.entity.monster;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public abstract class EntityGhast extends EntityMob {
    public EntityGhast(EntityType<? extends LivingEntity> type, Level level) { super(type, level); }
}
