package net.minecraft.world.item;

import net.minecraft.inventory.EntityEquipmentSlot;

public class ArmorItem extends Item {
    public EntityEquipmentSlot armorType = EntityEquipmentSlot.HEAD;
    public int damageReduceAmount = 5;
    public float toughness = 2.0f;

    public static class ArmorMaterialHolder {
        public int getDamageReductionAmount(EntityEquipmentSlot slot) {
            return 3;
        }
    }

    public ArmorItem(Item.Properties properties) {
        super(properties);
    }

    public ArmorItem(Item.Properties properties, EntityEquipmentSlot armorType) {
        super(properties);
        this.armorType = armorType;
    }

    public ArmorMaterialHolder getArmorMaterial() {
        return new ArmorMaterialHolder();
    }

    public static boolean isArmor(Item item) {
        return item != null && (item instanceof ArmorItem || item.isArmor());
    }
}
