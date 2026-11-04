package net.minecraft.client.gui;

public class GuiTextField {
    private int id;
    private FontRenderer fontRenderer;
    public int xPosition;
    public int yPosition;
    public int width;
    public int height;
    private String text = "";
    private int maxStringLength = 32;
    private boolean isFocused;
    public boolean isVisible = true;

    public GuiTextField(int componentId, FontRenderer fontrendererObj, int x, int y, int widthIn, int heightIn) {
        this.id = componentId;
        this.fontRenderer = fontrendererObj;
        this.xPosition = x;
        this.yPosition = y;
        this.width = widthIn;
        this.height = heightIn;
    }

    public void drawTextBox() {}

    public String getText() {
        return this.text;
    }

    public void setText(String textIn) {
        this.text = textIn != null ? textIn : "";
    }

    public int getMaxStringLength() {
        return this.maxStringLength;
    }

    public void setMaxStringLength(int length) {
        this.maxStringLength = length;
    }

    public boolean isFocused() {
        return this.isFocused;
    }

    public void setFocused(boolean isFocusedIn) {
        this.isFocused = isFocusedIn;
    }

    public boolean textboxKeyTyped(char typedChar, int keyCode) {
        if (!this.isFocused) return false;
        if (keyCode == 14) {             
            if (!this.text.isEmpty()) {
                this.text = this.text.substring(0, this.text.length() - 1);
                return true;
            }
        } else if (typedChar >= 32 && typedChar != 127) {
            if (this.text.length() < this.maxStringLength) {
                this.text += typedChar;
                return true;
            }
        }
        return false;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        this.isFocused = mouseX >= this.xPosition && mouseX < this.xPosition + this.width && mouseY >= this.yPosition && mouseY < this.yPosition + this.height;
    }

    public void updateCursorCounter() {}
}
