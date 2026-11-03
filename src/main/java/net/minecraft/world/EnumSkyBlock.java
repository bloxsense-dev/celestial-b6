package net.minecraft.world;

import net.minecraft.world.level.LightLayer;

public enum EnumSkyBlock {
    SKY(15, LightLayer.SKY),
    BLOCK(0, LightLayer.BLOCK);

    public final int defaultLightValue;
    private final LightLayer lightLayer;

    EnumSkyBlock(int defaultLightValue, LightLayer lightLayer) {
        this.defaultLightValue = defaultLightValue;
        this.lightLayer = lightLayer;
    }

    public LightLayer toLightLayer() {
        return this.lightLayer;
    }

    public static EnumSkyBlock fromLightLayer(LightLayer layer) {
        return layer == LightLayer.SKY ? SKY : BLOCK;
    }
}
