package org.lwjgl.opengl;

import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class Display {
    public static int getWidth() {
        try {
            return Minecraft.getInstance().getWindow().getWidth();
        } catch (Throwable t) {
            return 1920;
        }
    }

    public static int getHeight() {
        try {
            return Minecraft.getInstance().getWindow().getHeight();
        } catch (Throwable t) {
            return 1080;
        }
    }

    public static void setTitle(String title) {
        try {
            long handle = Minecraft.getInstance().getWindow().handle;
            GLFW.glfwSetWindowTitle(handle, title);
        } catch (Throwable ignored) {
        }
    }

    public static boolean isActive() {
        return true;
    }

    public static boolean isCreated() {
        return true;
    }

    public static void destroy() {
    }

    public static void update() {
    }
}
