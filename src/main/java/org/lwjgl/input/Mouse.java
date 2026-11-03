package org.lwjgl.input;

import org.lwjgl.glfw.GLFW;
import net.minecraft.client.Minecraft;

public class Mouse {
    private static int eventButton = -1;
    private static boolean eventButtonState = false;
    private static int dWheel = 0;

    public static boolean isButtonDown(int button) {
        try {
            long handle = Minecraft.getInstance().getWindow().handle;
            return GLFW.glfwGetMouseButton(handle, button) == GLFW.GLFW_PRESS;
        } catch (Throwable t) {
            return false;
        }
    }

    public static int getX() {
        try {
            Minecraft mc = Minecraft.getInstance();
            return (int) mc.mouseHandler.xpos();
        } catch (Throwable t) {
            return 0;
        }
    }

    public static int getY() {
        try {
            Minecraft mc = Minecraft.getInstance();
            return (int) (mc.getWindow().getHeight() - mc.mouseHandler.ypos());
        } catch (Throwable t) {
            return 0;
        }
    }

    public static boolean hasWheel() {
        return true;
    }

    public static int getDWheel() {
        int val = dWheel;
        dWheel = 0;
        return val;
    }

    public static void setDWheel(int delta) {
        dWheel = delta;
    }

    public static int getEventButton() {
        return eventButton;
    }

    public static boolean getEventButtonState() {
        return eventButtonState;
    }

    public static void setEventButton(int button, boolean state) {
        eventButton = button;
        eventButtonState = state;
    }
}
