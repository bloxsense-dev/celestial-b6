package net.minecraft.inventory;

import net.minecraft.world.entity.EquipmentSlot;

public enum EntityEquipmentSlot {
    MAINHAND(EquipmentSlot.MAINHAND, 0),
    OFFHAND(EquipmentSlot.OFFHAND, 1),
    FEET(EquipmentSlot.FEET, 0),
    LEGS(EquipmentSlot.LEGS, 1),
    CHEST(EquipmentSlot.CHEST, 2),
    HEAD(EquipmentSlot.HEAD, 3);

    private final EquipmentSlot slot;
    private final int index;

    EntityEquipmentSlot(EquipmentSlot slot, int index) {
        this.slot = slot;
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public EquipmentSlot toSlot() {
        return this.slot;
    }
}
