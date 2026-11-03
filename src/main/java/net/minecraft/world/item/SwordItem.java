package net.minecraft.world.item;

public class SwordItem extends Item {
    public SwordItem(Item.Properties properties) {
        super(properties);
    }

    public static boolean isSword(Item item) {
        return item != null && (item instanceof SwordItem || item.isSword());
    }

    public float getDamageVsEntity() {
        return 6.0f;
    }
}
