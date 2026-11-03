package net.minecraft.item;

public class ItemSword extends Item {
    public float attackDamage = 7.0f;

    public ItemSword() {
        super();
    }

    public float getDamageVsEntity() {
        return this.attackDamage;
    }
}
