package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.celestial.client.helpers.render.rect.RectHelper;

public class GuiScreen extends Screen {
    public Minecraft mc = Minecraft.getInstance();
    public int width;
    public int height;
    public java.util.List<GuiButton> buttonList = new java.util.ArrayList<>();
    public int eventButton = 0;

    protected void actionPerformed(GuiButton button) throws java.io.IOException {}

    public void drawString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
        if (fontRendererIn != null) {
            fontRendererIn.drawStringWithShadow(text, (float)x, (float)y, color);
        }
    }

    public GuiScreen() {
        super(Component.literal(""));
        this.mc = Minecraft.getInstance();
    }

    public GuiScreen(Component title) {
        super(title);
        this.mc = Minecraft.getInstance();
    }

    @Override
    protected void init() {
        this.mc = Minecraft.getInstance();
        this.width = this.mc.getWindow().getGuiScaledWidth();
        this.height = this.mc.getWindow().getGuiScaledHeight();
        this.initGui();
    }

    public void initGui() {
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (int i = 0; i < this.buttonList.size(); ++i) {
            this.buttonList.get(i).drawButton(this.mc, mouseX, mouseY, partialTicks);
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws java.io.IOException {
        if (mouseButton == 0) {
            for (int i = 0; i < this.buttonList.size(); ++i) {
                GuiButton guibutton = this.buttonList.get(i);
                if (guibutton.mousePressed(this.mc, mouseX, mouseY)) {
                    guibutton.playPressSound(this.mc.getSoundHandler());
                    this.actionPerformed(guibutton);
                }
            }
        }
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
    }

    protected void keyTyped(char typedChar, int keyCode) throws java.io.IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            this.mc.displayGuiScreen(null);
        }
    }

    public void updateScreen() {
    }

    public void onGuiClosed() {
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public boolean isPauseScreen() {
        return doesGuiPauseGame();
    }

    @Override
    public void extractRenderState(final net.minecraft.client.gui.GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float partialTicks) {
        if (this.mc != null && this.mc.getWindow() != null) {
            this.width = this.mc.getWindow().getGuiScaledWidth();
            this.height = this.mc.getWindow().getGuiScaledHeight();
            this.mc.displayWidth = this.mc.getWindow().getWidth();
            this.mc.displayHeight = this.mc.getWindow().getHeight();
        }
    }

    @Override
    public void extractBackground(final net.minecraft.client.gui.GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a) {
    }

    public void drawDefaultBackground() {
        drawGradientRect(0, 0, this.width, this.height, 0x90000000, 0x90000000);
    }

    public void drawGradientRect(float left, float top, float right, float bottom, int startColor, int endColor) {
        RectHelper.drawGradientRect(left, top, right, bottom, startColor, endColor);
    }

    public void drawWorldBackground(int tint) {
    }

    @Override
    public boolean mouseClicked(net.minecraft.client.input.MouseButtonEvent event, boolean doubleClick) {
        Mouse.setEventButton(event.button(), true);
        try {
            this.mouseClicked((int) event.x(), (int) event.y(), event.button());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean mouseReleased(net.minecraft.client.input.MouseButtonEvent event) {
        Mouse.setEventButton(event.button(), false);
        this.mouseReleased((int) event.x(), (int) event.y(), event.button());
        return true;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Mouse.setEventButton(button, true);
        try {
            this.mouseClicked((int) mouseX, (int) mouseY, button);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        Mouse.setEventButton(button, false);
        this.mouseReleased((int) mouseX, (int) mouseY, button);
        return true;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        Mouse.setDWheel((int) (scrollY * 120.0));
        return true;
    }

    @Override
    public boolean keyPressed(net.minecraft.client.input.KeyEvent event) {
        int lwjglKey = Keyboard.toLwjglKey(event.key());
        Keyboard.setEvent(lwjglKey, (char) 0, true);
        try {
            this.keyTyped((char) 0, lwjglKey);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        int lwjglKey = Keyboard.toLwjglKey(keyCode);
        Keyboard.setEvent(lwjglKey, (char) 0, true);
        try {
            this.keyTyped((char) 0, lwjglKey);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean charTyped(net.minecraft.client.input.CharacterEvent event) {
        char codePoint = (char) event.codepoint();
        Keyboard.setEvent(0, codePoint, true);
        try {
            this.keyTyped(codePoint, 0);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean charTyped(char codePoint, int modifiers) {
        Keyboard.setEvent(0, codePoint, true);
        try {
            this.keyTyped(codePoint, 0);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean isCtrlKeyDown() {
        return Minecraft.ON_OSX ? org.lwjgl.input.Keyboard.isKeyDown(219) || org.lwjgl.input.Keyboard.isKeyDown(220) : org.lwjgl.input.Keyboard.isKeyDown(29) || org.lwjgl.input.Keyboard.isKeyDown(157);
    }

    public static boolean isShiftKeyDown() {
        return org.lwjgl.input.Keyboard.isKeyDown(42) || org.lwjgl.input.Keyboard.isKeyDown(54);
    }

    public static boolean isAltKeyDown() {
        return org.lwjgl.input.Keyboard.isKeyDown(56) || org.lwjgl.input.Keyboard.isKeyDown(184);
    }

    public static boolean isKeyComboCtrlA(int keyID) {
        return keyID == 30 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }

    public static boolean isKeyComboCtrlC(int keyID) {
        return keyID == 46 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }

    public static boolean isKeyComboCtrlV(int keyID) {
        return keyID == 47 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }

    public static boolean isKeyComboCtrlX(int keyID) {
        return keyID == 45 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }

    public static String getClipboardString() {
        try {
            java.awt.datatransfer.Transferable t = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            if (t != null && t.isDataFlavorSupported(java.awt.datatransfer.DataFlavor.stringFlavor)) {
                return (String) t.getTransferData(java.awt.datatransfer.DataFlavor.stringFlavor);
            }
        } catch (Throwable ignored) {}
        return "";
    }

    public static void setClipboardString(String copyText) {
        try {
            java.awt.datatransfer.StringSelection ss = new java.awt.datatransfer.StringSelection(copyText);
            java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
        } catch (Throwable ignored) {}
    }

    public void func_193975_a(boolean focused) {}

    @Override
    public void tick() {
        super.tick();
        this.updateScreen();
    }

    @Override
    public void removed() {
        super.removed();
        this.onGuiClosed();
    }

    public int imageWidth;
    public int imageHeight;

}
