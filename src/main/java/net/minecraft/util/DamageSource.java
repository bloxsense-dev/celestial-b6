package net.minecraft.util;

import net.minecraft.world.entity.Entity;

public class DamageSource {
    public static final DamageSource GENERIC = new DamageSource();

    public static DamageSource causePlayerDamage(Entity player) {
        return new DamageSource();
    }
}
