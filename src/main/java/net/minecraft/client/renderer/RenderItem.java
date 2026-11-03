package net.minecraft.client.renderer;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.world.item.ItemStack;

public class RenderItem {
    public float zLevel = 0.0f;

    public void renderItemIntoGUI(ItemStack stack, int x, int y) {}

    public void renderItemIntoGUI(ItemStack stack, float x, float y) {
        renderItemIntoGUI(stack, (int) x, (int) y);
    }

    public void renderItemOverlayIntoGUI(FontRenderer fr, ItemStack stack, int x, int y, String textFormat) {}

    public void renderItemOverlays(FontRenderer fr, ItemStack stack, int x, int y) {
        renderItemOverlayIntoGUI(fr, stack, x, y, null);
    }

    public void renderItemAndEffectIntoGUI(ItemStack stack, int x, int y) {
        renderItemIntoGUI(stack, x, y);
    }
}
