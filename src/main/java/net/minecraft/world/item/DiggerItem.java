package net.minecraft.world.item;

public class DiggerItem extends Item {
    public DiggerItem(Item.Properties properties) {
        super(properties);
    }

    public static boolean isTool(Item item) {
        if (item == null) return false;
        String name = item.getDescriptionId();
        return name.contains("pickaxe") || name.contains("axe") || name.contains("shovel") || name.contains("hoe");
    }

    public float getDamageVsEntity() {
        return 4.0f;
    }

    public float getStrVsBlock(ItemStack stack, net.minecraft.world.level.block.state.BlockState state) {
        return 4.0f;
    }
}
