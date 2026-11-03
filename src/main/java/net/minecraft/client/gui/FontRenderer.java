package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;

public class FontRenderer {
    public int FONT_HEIGHT = 9;
    public int fontHeight = 9;

    public int drawString(String text, float x, float y, int color) {
        return drawString(text, (int) x, (int) y, color);
    }

    public int drawString(String text, int x, int y, int color) {
        return drawStringWithShadow(text, x, y, color);
    }

    public int drawStringWithShadow(String text, double x, double y, int color) {
        return drawStringWithShadow(text, (float) x, (float) y, color);
    }

    public int drawStringWithShadow(String text, float x, float y, int color) {
        return (int) x + getStringWidth(text);
    }

    public void drawStringWithOutline(String text, float x, float y, int color) {
        drawString(text, x - 0.5f, y, 0x000000);
        drawString(text, x + 0.5f, y, 0x000000);
        drawString(text, x, y - 0.5f, 0x000000);
        drawString(text, x, y + 0.5f, 0x000000);
        drawString(text, x, y, color);
    }

    public int getStringWidth(net.minecraft.network.chat.Component component) {
        return component == null ? 0 : getStringWidth(component.getString());
    }

    public void drawStringWithOutline(net.minecraft.network.chat.Component component, float x, float y, int color) {
        if (component != null) drawStringWithOutline(component.getString(), x, y, color);
    }

    public int drawString(net.minecraft.network.chat.Component component, float x, float y, int color) {
        return component == null ? 0 : drawString(component.getString(), x, y, color);
    }

    public int drawStringWithShadow(net.minecraft.network.chat.Component component, float x, float y, int color) {
        return component == null ? 0 : drawStringWithShadow(component.getString(), x, y, color);
    }

    public int getStringWidth(String text) {
        if (text == null || text.isEmpty()) return 0;
        try {
            if (Minecraft.getInstance() != null && Minecraft.getInstance().font != null) {
                return Minecraft.getInstance().font.width(text);
            }
        } catch (Throwable ignored) {}
        return text.length() * 6;
    }

    public int getFontHeight() {
        return FONT_HEIGHT;
    }

    public int getStringHeight(String text) {
        return FONT_HEIGHT;
    }

    public int getStringHeight(net.minecraft.network.chat.Component text) {
        return FONT_HEIGHT;
    }

    public int getStringHeight() {
        return FONT_HEIGHT;
    }

    public void drawCenteredString(String text, float x, float y, int color) {
        drawString(text, x - getStringWidth(text) / 2.0f, y, color);
    }

    public String trimStringToWidth(String text, int width) {
        return trimStringToWidth(text, width, false);
    }

    public String trimStringToWidth(String text, int width, boolean reverse) {
        if (text == null) return "";
        StringBuilder sb = new StringBuilder();
        int curWidth = 0;
        int start = reverse ? text.length() - 1 : 0;
        int step = reverse ? -1 : 1;
        for (int i = start; i >= 0 && i < text.length(); i += step) {
            char c = text.charAt(i);
            int charWidth = 6;
            if (curWidth + charWidth > width) break;
            curWidth += charWidth;
            if (reverse) sb.insert(0, c); else sb.append(c);
        }
        return sb.toString();
    }
}
