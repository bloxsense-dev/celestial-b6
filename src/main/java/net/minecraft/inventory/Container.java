package net.minecraft.inventory;

import java.util.List;
import net.minecraft.world.inventory.Slot;

public class Container {
    public int windowId;
    public List<Slot> inventorySlots = new java.util.ArrayList<>();

    public Slot getSlot(int index) {
        if (index >= 0 && index < inventorySlots.size()) {
            return inventorySlots.get(index);
        }
        return null;
    }
}
