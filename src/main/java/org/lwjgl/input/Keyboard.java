package org.lwjgl.input;

import org.lwjgl.glfw.GLFW;
import net.minecraft.client.Minecraft;
import java.util.HashMap;
import java.util.Map;

public class Keyboard {
    public static final int KEY_NONE = 0;
    public static final int KEY_ESCAPE = 1;
    public static final int KEY_1 = 2;
    public static final int KEY_2 = 3;
    public static final int KEY_3 = 4;
    public static final int KEY_4 = 5;
    public static final int KEY_5 = 6;
    public static final int KEY_6 = 7;
    public static final int KEY_7 = 8;
    public static final int KEY_8 = 9;
    public static final int KEY_9 = 10;
    public static final int KEY_0 = 11;
    public static final int KEY_MINUS = 12;
    public static final int KEY_EQUALS = 13;
    public static final int KEY_BACK = 14;
    public static final int KEY_TAB = 15;
    public static final int KEY_Q = 16;
    public static final int KEY_W = 17;
    public static final int KEY_E = 18;
    public static final int KEY_R = 19;
    public static final int KEY_T = 20;
    public static final int KEY_Y = 21;
    public static final int KEY_U = 22;
    public static final int KEY_I = 23;
    public static final int KEY_O = 24;
    public static final int KEY_P = 25;
    public static final int KEY_LBRACKET = 26;
    public static final int KEY_RBRACKET = 27;
    public static final int KEY_RETURN = 28;
    public static final int KEY_LCONTROL = 29;
    public static final int KEY_A = 30;
    public static final int KEY_S = 31;
    public static final int KEY_D = 32;
    public static final int KEY_F = 33;
    public static final int KEY_G = 34;
    public static final int KEY_H = 35;
    public static final int KEY_J = 36;
    public static final int KEY_K = 37;
    public static final int KEY_L = 38;
    public static final int KEY_SEMICOLON = 39;
    public static final int KEY_APOSTROPHE = 40;
    public static final int KEY_GRAVE = 41;
    public static final int KEY_LSHIFT = 42;
    public static final int KEY_BACKSLASH = 43;
    public static final int KEY_Z = 44;
    public static final int KEY_X = 45;
    public static final int KEY_C = 46;
    public static final int KEY_V = 47;
    public static final int KEY_B = 48;
    public static final int KEY_N = 49;
    public static final int KEY_M = 50;
    public static final int KEY_COMMA = 51;
    public static final int KEY_PERIOD = 52;
    public static final int KEY_SLASH = 53;
    public static final int KEY_RSHIFT = 54;
    public static final int KEY_MULTIPLY = 55;
    public static final int KEY_LMENU = 56;
    public static final int KEY_SPACE = 57;
    public static final int KEY_CAPITAL = 58;
    public static final int KEY_F1 = 59;
    public static final int KEY_F2 = 60;
    public static final int KEY_F3 = 61;
    public static final int KEY_F4 = 62;
    public static final int KEY_F5 = 63;
    public static final int KEY_F6 = 64;
    public static final int KEY_F7 = 65;
    public static final int KEY_F8 = 66;
    public static final int KEY_F9 = 67;
    public static final int KEY_F10 = 68;
    public static final int KEY_NUMLOCK = 69;
    public static final int KEY_SCROLL = 70;
    public static final int KEY_NUMPAD7 = 71;
    public static final int KEY_NUMPAD8 = 72;
    public static final int KEY_NUMPAD9 = 73;
    public static final int KEY_SUBTRACT = 74;
    public static final int KEY_NUMPAD4 = 75;
    public static final int KEY_NUMPAD5 = 76;
    public static final int KEY_NUMPAD6 = 77;
    public static final int KEY_ADD = 78;
    public static final int KEY_NUMPAD1 = 79;
    public static final int KEY_NUMPAD2 = 80;
    public static final int KEY_NUMPAD3 = 81;
    public static final int KEY_NUMPAD0 = 82;
    public static final int KEY_DECIMAL = 83;
    public static final int KEY_F11 = 87;
    public static final int KEY_F12 = 88;
    public static final int KEY_NUMPADEQUALS = 141;
    public static final int KEY_NUMPADENTER = 156;
    public static final int KEY_RCONTROL = 157;
    public static final int KEY_DIVIDE = 181;
    public static final int KEY_RMENU = 184;
    public static final int KEY_HOME = 199;
    public static final int KEY_UP = 200;
    public static final int KEY_PRIOR = 201;
    public static final int KEY_LEFT = 203;
    public static final int KEY_RIGHT = 205;
    public static final int KEY_END = 207;
    public static final int KEY_DOWN = 208;
    public static final int KEY_NEXT = 209;
    public static final int KEY_INSERT = 210;
    public static final int KEY_DELETE = 211;

    private static final Map<Integer, Integer> lwjglToGlfw = new HashMap<>();
    private static final Map<Integer, Integer> glfwToLwjgl = new HashMap<>();
    private static final Map<Integer, String> keyNames = new HashMap<>();
    private static final Map<String, Integer> keyIndices = new HashMap<>();
    private static int eventKey = 0;
    private static char eventCharacter = 0;
    private static boolean eventKeyState = false;

    static {
        registerKey(KEY_ESCAPE, GLFW.GLFW_KEY_ESCAPE, "ESCAPE");
        registerKey(KEY_1, GLFW.GLFW_KEY_1, "1");
        registerKey(KEY_2, GLFW.GLFW_KEY_2, "2");
        registerKey(KEY_3, GLFW.GLFW_KEY_3, "3");
        registerKey(KEY_4, GLFW.GLFW_KEY_4, "4");
        registerKey(KEY_5, GLFW.GLFW_KEY_5, "5");
        registerKey(KEY_6, GLFW.GLFW_KEY_6, "6");
        registerKey(KEY_7, GLFW.GLFW_KEY_7, "7");
        registerKey(KEY_8, GLFW.GLFW_KEY_8, "8");
        registerKey(KEY_9, GLFW.GLFW_KEY_9, "9");
        registerKey(KEY_0, GLFW.GLFW_KEY_0, "0");
        registerKey(KEY_MINUS, GLFW.GLFW_KEY_MINUS, "MINUS");
        registerKey(KEY_EQUALS, GLFW.GLFW_KEY_EQUAL, "EQUALS");
        registerKey(KEY_BACK, GLFW.GLFW_KEY_BACKSPACE, "BACK");
        registerKey(KEY_TAB, GLFW.GLFW_KEY_TAB, "TAB");
        registerKey(KEY_Q, GLFW.GLFW_KEY_Q, "Q");
        registerKey(KEY_W, GLFW.GLFW_KEY_W, "W");
        registerKey(KEY_E, GLFW.GLFW_KEY_E, "E");
        registerKey(KEY_R, GLFW.GLFW_KEY_R, "R");
        registerKey(KEY_T, GLFW.GLFW_KEY_T, "T");
        registerKey(KEY_Y, GLFW.GLFW_KEY_Y, "Y");
        registerKey(KEY_U, GLFW.GLFW_KEY_U, "U");
        registerKey(KEY_I, GLFW.GLFW_KEY_I, "I");
        registerKey(KEY_O, GLFW.GLFW_KEY_O, "O");
        registerKey(KEY_P, GLFW.GLFW_KEY_P, "P");
        registerKey(KEY_LBRACKET, GLFW.GLFW_KEY_LEFT_BRACKET, "LBRACKET");
        registerKey(KEY_RBRACKET, GLFW.GLFW_KEY_RIGHT_BRACKET, "RBRACKET");
        registerKey(KEY_RETURN, GLFW.GLFW_KEY_ENTER, "RETURN");
        registerKey(KEY_LCONTROL, GLFW.GLFW_KEY_LEFT_CONTROL, "LCONTROL");
        registerKey(KEY_A, GLFW.GLFW_KEY_A, "A");
        registerKey(KEY_S, GLFW.GLFW_KEY_S, "S");
        registerKey(KEY_D, GLFW.GLFW_KEY_D, "D");
        registerKey(KEY_F, GLFW.GLFW_KEY_F, "F");
        registerKey(KEY_G, GLFW.GLFW_KEY_G, "G");
        registerKey(KEY_H, GLFW.GLFW_KEY_H, "H");
        registerKey(KEY_J, GLFW.GLFW_KEY_J, "J");
        registerKey(KEY_K, GLFW.GLFW_KEY_K, "K");
        registerKey(KEY_L, GLFW.GLFW_KEY_L, "L");
        registerKey(KEY_SEMICOLON, GLFW.GLFW_KEY_SEMICOLON, "SEMICOLON");
        registerKey(KEY_APOSTROPHE, GLFW.GLFW_KEY_APOSTROPHE, "APOSTROPHE");
        registerKey(KEY_GRAVE, GLFW.GLFW_KEY_GRAVE_ACCENT, "GRAVE");
        registerKey(KEY_LSHIFT, GLFW.GLFW_KEY_LEFT_SHIFT, "LSHIFT");
        registerKey(KEY_BACKSLASH, GLFW.GLFW_KEY_BACKSLASH, "BACKSLASH");
        registerKey(KEY_Z, GLFW.GLFW_KEY_Z, "Z");
        registerKey(KEY_X, GLFW.GLFW_KEY_X, "X");
        registerKey(KEY_C, GLFW.GLFW_KEY_C, "C");
        registerKey(KEY_V, GLFW.GLFW_KEY_V, "V");
        registerKey(KEY_B, GLFW.GLFW_KEY_B, "B");
        registerKey(KEY_N, GLFW.GLFW_KEY_N, "N");
        registerKey(KEY_M, GLFW.GLFW_KEY_M, "M");
        registerKey(KEY_COMMA, GLFW.GLFW_KEY_COMMA, "COMMA");
        registerKey(KEY_PERIOD, GLFW.GLFW_KEY_PERIOD, "PERIOD");
        registerKey(KEY_SLASH, GLFW.GLFW_KEY_SLASH, "SLASH");
        registerKey(KEY_RSHIFT, GLFW.GLFW_KEY_RIGHT_SHIFT, "RSHIFT");
        registerKey(KEY_LMENU, GLFW.GLFW_KEY_LEFT_ALT, "LMENU");
        registerKey(KEY_SPACE, GLFW.GLFW_KEY_SPACE, "SPACE");
        registerKey(KEY_CAPITAL, GLFW.GLFW_KEY_CAPS_LOCK, "CAPITAL");
        registerKey(KEY_F1, GLFW.GLFW_KEY_F1, "F1");
        registerKey(KEY_F2, GLFW.GLFW_KEY_F2, "F2");
        registerKey(KEY_F3, GLFW.GLFW_KEY_F3, "F3");
        registerKey(KEY_F4, GLFW.GLFW_KEY_F4, "F4");
        registerKey(KEY_F5, GLFW.GLFW_KEY_F5, "F5");
        registerKey(KEY_F6, GLFW.GLFW_KEY_F6, "F6");
        registerKey(KEY_F7, GLFW.GLFW_KEY_F7, "F7");
        registerKey(KEY_F8, GLFW.GLFW_KEY_F8, "F8");
        registerKey(KEY_F9, GLFW.GLFW_KEY_F9, "F9");
        registerKey(KEY_F10, GLFW.GLFW_KEY_F10, "F10");
        registerKey(KEY_F11, GLFW.GLFW_KEY_F11, "F11");
        registerKey(KEY_F12, GLFW.GLFW_KEY_F12, "F12");
        registerKey(KEY_RCONTROL, GLFW.GLFW_KEY_RIGHT_CONTROL, "RCONTROL");
        registerKey(KEY_RMENU, GLFW.GLFW_KEY_RIGHT_ALT, "RMENU");
        registerKey(KEY_UP, GLFW.GLFW_KEY_UP, "UP");
        registerKey(KEY_LEFT, GLFW.GLFW_KEY_LEFT, "LEFT");
        registerKey(KEY_RIGHT, GLFW.GLFW_KEY_RIGHT, "RIGHT");
        registerKey(KEY_DOWN, GLFW.GLFW_KEY_DOWN, "DOWN");
        registerKey(KEY_INSERT, GLFW.GLFW_KEY_INSERT, "INSERT");
        registerKey(KEY_DELETE, GLFW.GLFW_KEY_DELETE, "DELETE");
        registerKey(KEY_HOME, GLFW.GLFW_KEY_HOME, "HOME");
        registerKey(KEY_END, GLFW.GLFW_KEY_END, "END");
        registerKey(KEY_PRIOR, GLFW.GLFW_KEY_PAGE_UP, "PAGEUP");
        registerKey(KEY_NEXT, GLFW.GLFW_KEY_PAGE_DOWN, "PAGEDOWN");
    }

    private static void registerKey(int lwjglKey, int glfwKey, String name) {
        lwjglToGlfw.put(lwjglKey, glfwKey);
        glfwToLwjgl.put(glfwKey, lwjglKey);
        keyNames.put(lwjglKey, name);
        keyIndices.put(name.toUpperCase(), lwjglKey);
    }

    public static boolean isKeyDown(int key) {
        if (key == KEY_NONE) return false;
        Integer glfwKey = lwjglToGlfw.get(key);
        if (glfwKey == null) glfwKey = key;
        try {
            long handle = Minecraft.getInstance().getWindow().handle;
            return GLFW.glfwGetKey(handle, glfwKey) == GLFW.GLFW_PRESS;
        } catch (Throwable t) {
            return false;
        }
    }

    public static String getKeyName(int key) {
        if (key == KEY_NONE) return "NONE";
        String name = keyNames.get(key);
        return name != null ? name : "KEY_" + key;
    }

    public static int getKeyIndex(String name) {
        if (name == null || name.isEmpty() || name.equalsIgnoreCase("NONE")) return KEY_NONE;
        Integer idx = keyIndices.get(name.toUpperCase());
        if (idx != null) return idx;
        try {
            return Integer.parseInt(name);
        } catch (Exception e) {
            return KEY_NONE;
        }
    }

    public static int toLwjglKey(int glfwKey) {
        Integer k = glfwToLwjgl.get(glfwKey);
        return k != null ? k : glfwKey;
    }

    public static int toGlfwKey(int lwjglKey) {
        Integer k = lwjglToGlfw.get(lwjglKey);
        return k != null ? k : lwjglKey;
    }

    public static void setEvent(int key, char character, boolean state) {
        eventKey = key;
        eventCharacter = character;
        eventKeyState = state;
    }

    public static int getEventKey() {
        return eventKey;
    }

    public static char getEventCharacter() {
        return eventCharacter;
    }

    public static boolean getEventKeyState() {
        return eventKeyState;
    }

    public static void enableRepeatEvents(boolean enable) {
    }
}
