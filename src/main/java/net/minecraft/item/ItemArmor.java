package net.minecraft.item;

import net.minecraft.inventory.EntityEquipmentSlot;

public class ItemArmor extends Item {
    public int damageReduceAmount = 5;
    public float toughness = 0.0f;
    public EntityEquipmentSlot armorType = EntityEquipmentSlot.CHEST;

    public ItemArmor() {
        super();
    }

    public ItemArmor(int damageReduceAmount, EntityEquipmentSlot armorType) {
        super();
        this.damageReduceAmount = damageReduceAmount;
        this.armorType = armorType;
    }

    public ArmorMaterial getArmorMaterial() {
        return new ArmorMaterial();
    }

    public static class ArmorMaterial {
        public int getDamageReductionAmount(EntityEquipmentSlot slot) {
            return 3;
        }
    }
}
