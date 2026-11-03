package net.minecraft.inventory;

public enum ClickType {
    PICKUP,
    QUICK_MOVE,
    SWAP,
    CLONE,
    THROW,
    QUICK_CRAFT,
    PICKUP_ALL;

    public net.minecraft.world.inventory.ContainerInput toContainerInput() {
        try {
            return net.minecraft.world.inventory.ContainerInput.valueOf(this.name());
        } catch (Exception e) {
            return net.minecraft.world.inventory.ContainerInput.PICKUP;
        }
    }

}
