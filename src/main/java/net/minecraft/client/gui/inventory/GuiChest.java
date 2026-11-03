package net.minecraft.client.gui.inventory;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.world.inventory.Slot;

public class GuiChest extends GuiScreen {
    public ContainerChest inventorySlots = new ContainerChest();

    public int getInventoryRows() {
        return 6;
    }

    public void handleMouseClick(Slot slot, int slotId, int mouseButton, ClickType type) {
    }
}
