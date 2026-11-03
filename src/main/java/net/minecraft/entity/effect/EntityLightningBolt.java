package net.minecraft.entity.effect;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class EntityLightningBolt extends Entity {
    public EntityLightningBolt(EntityType<?> type, Level level) {
        super(type, level);
    }
}
