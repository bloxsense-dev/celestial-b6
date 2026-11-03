package net.minecraft.client.entity;

import java.util.UUID;
import net.minecraft.util.ResourceLocation;

public class AbstractClientPlayer extends net.minecraft.client.player.AbstractClientPlayer {
    public AbstractClientPlayer(net.minecraft.client.multiplayer.ClientLevel level, com.mojang.authlib.GameProfile profile) {
        super(level, profile);
    }

    public static UUID getOfflineUUID(String username) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }

    public static ResourceLocation getLocationSkin(String username) {
        return new ResourceLocation("textures/entity/steve.png");
    }
}
