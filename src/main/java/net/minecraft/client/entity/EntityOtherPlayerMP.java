package net.minecraft.client.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;

public class EntityOtherPlayerMP extends AbstractClientPlayer {
    public EntityOtherPlayerMP(ClientLevel level, GameProfile gameProfile) {
        super(level, gameProfile);
        this.noPhysics = true;
    }
}
