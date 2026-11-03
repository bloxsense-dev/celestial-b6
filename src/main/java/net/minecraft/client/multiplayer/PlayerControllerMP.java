package net.minecraft.client.multiplayer;

import net.minecraft.client.Minecraft;

public class PlayerControllerMP extends MultiPlayerGameMode {
    public int blockHitDelay = 0;

    public PlayerControllerMP(Minecraft minecraft, ClientPacketListener connection) {
        super(minecraft, connection);
    }

    public PlayerControllerMP(Minecraft minecraft) {
        super(minecraft, null);
    }
}
