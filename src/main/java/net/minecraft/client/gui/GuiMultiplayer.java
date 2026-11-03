package net.minecraft.client.gui;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;

public class GuiMultiplayer extends JoinMultiplayerScreen {
    public GuiMultiplayer() {
        this(null);
    }

    public GuiMultiplayer(Screen parent) {
        super(parent);
    }
}
