package net.minecraft.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.core.Holder;

public class Potion {
    private final String name;
    private final int id;

    public Potion(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Potion getPotionById(int id) {
        return new Potion(id, "effect." + id);
    }

    public static String getDurationString(PotionEffect effect) {
        if (effect == null) return "0:00";
        int sec = effect.getDuration() / 20;
        int m = sec / 60;
        int s = sec % 60;
        return m + ":" + (s < 10 ? "0" + s : s);
    }

    public int getLiquidColor() {
        return 0xFFFFFF;
    }

    public static int getIdFromPotion(Potion potion) {
        return potion != null ? potion.id : 0;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }
}
