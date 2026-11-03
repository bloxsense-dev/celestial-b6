package net.minecraft.world.item;

public class ItemSeeds extends Item {
    public ItemSeeds(Item.Properties properties) {
        super(properties);
    }

    public static boolean isSeed(Item item) {
        return item == Items.WHEAT_SEEDS || item == Items.PUMPKIN_SEEDS || item == Items.MELON_SEEDS || item == Items.BEETROOT_SEEDS || item == Items.TORCHFLOWER_SEEDS || item == Items.PITCHER_POD;
    }
}
