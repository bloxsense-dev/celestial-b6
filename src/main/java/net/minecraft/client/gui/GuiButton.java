package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;

public class GuiButton {
    public int id;
    public int xPosition;
    public int yPosition;
    public int width;
    public int height;
    public String displayString;
    public boolean enabled = true;
    public boolean visible = true;
    public boolean hovered;

    public GuiButton(int buttonId, int x, int y, String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
    }

    public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        this.id = buttonId;
        this.xPosition = x;
        this.yPosition = y;
        this.width = widthIn;
        this.height = heightIn;
        this.displayString = buttonText;
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {}

    public void drawButtonForegroundLayer(int mouseX, int mouseY) {}

    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }

    public void mouseReleased(int mouseX, int mouseY) {}

    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {}

    public boolean isMouseOver() {
        return this.hovered;
    }

    public void playPressSound(SoundHandler soundHandlerIn) {}

    public int getButtonWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
