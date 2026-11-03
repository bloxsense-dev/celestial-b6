package net.minecraft.client.settings;

public class KeyBinding {
    public boolean pressed;

    public boolean isKeyDown() {
        return pressed;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public int getKeyCode() {
        return 0;
    }
}
