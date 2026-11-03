package net.minecraft.entity.passive;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class AbstractHorse extends LivingEntity {
    public AbstractHorse(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }
}
