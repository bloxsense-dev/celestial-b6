package net.minecraft.enchantment;

import net.minecraft.util.DamageSource;

public class Enchantment {
    public static Enchantment getEnchantmentByID(int id) {
        return new Enchantment();
    }

    public int calcModifierDamage(int level, DamageSource source) {
        return level;
    }

    public String getName() {
        return "enchantment.default";
    }
}
