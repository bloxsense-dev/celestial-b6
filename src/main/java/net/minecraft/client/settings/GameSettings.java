package net.minecraft.client.settings;

import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;

public class GameSettings extends Options {
    public GameSettings(Minecraft minecraft, File gameDirectory) {
        super(minecraft, gameDirectory);
    }
}
