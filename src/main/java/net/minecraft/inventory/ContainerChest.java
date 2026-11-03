package net.minecraft.inventory;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class ContainerChest extends AbstractContainerMenu {
    public int numRows = 6;
    private final IInventory lowerChestInventory = new IInventory();

    public ContainerChest(MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }

    public ContainerChest() {
        super(null, 0);
    }

    public IInventory getLowerChestInventory() {
        return lowerChestInventory;
    }

    @Override
    public boolean stillValid(net.minecraft.world.entity.player.Player player) {
        return true;
    }

    @Override
    public net.minecraft.world.item.ItemStack quickMoveStack(net.minecraft.world.entity.player.Player player, int slotIndex) {
        return net.minecraft.world.item.ItemStack.EMPTY;
    }

    public static class IInventory {
        public net.minecraft.world.item.ItemStack getStackInSlot(int index) {
            return net.minecraft.world.item.ItemStack.EMPTY;
        }
    }
}
