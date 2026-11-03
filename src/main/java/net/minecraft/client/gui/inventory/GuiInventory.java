package net.minecraft.client.gui.inventory;

import net.minecraft.world.entity.LivingEntity;

public class GuiInventory extends GuiContainer {
    public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, LivingEntity ent) {
    }

    public static void drawEntityOnScreen(float posX, float posY, int scale, float mouseX, float mouseY, LivingEntity ent) {
        drawEntityOnScreen((int) posX, (int) posY, scale, mouseX, mouseY, ent);
    }
}
