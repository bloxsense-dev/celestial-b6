package org.celestial.client.ui.components.draggable;

import org.celestial.client.ui.components.draggable.impl.*;

import java.util.ArrayList;

public class DraggableManager {

    public ArrayList<DraggableModule> mods = new ArrayList<>();

    public DraggableManager() {
        mods.add(new ClientInfoComponent());
        mods.add(new InfoComponent());
        mods.add(new WaterMarkComponent());
        mods.add(new PotionComponent());
        mods.add(new ArmorComponent());
        mods.add(new TargetHUDComponent());
    }

    private DraggableModule selected;
    private boolean wasDown;
    private int offsetX, offsetY;
    private int previousWidth, previousHeight;

    public void updateLayout(int width, int height) {
        for (DraggableModule mod : mods) {
            int x = mod.getX(), y = mod.getY();
            if (previousWidth == 0) {
                if (mod.name.equals("ClientInfoComponent") && !mod.layoutLoaded) {
                    x = width - mod.getWidth() - 4;
                    y = height - 16;
                }
                if (mod.name.equals("InfoComponent") && x == 100 && y == 200) {
                    x = 4;
                    y = height - 44;
                }
            } else if (width != previousWidth || height != previousHeight) {
                x = Math.round(x * (float) width / previousWidth);
                y = Math.round(y * (float) height / previousHeight);
            }
            mod.drag.setXPosition(Math.max(0, Math.min(x, Math.max(0, width - mod.getWidth()))));
            mod.drag.setYPosition(Math.max(0, Math.min(y, Math.max(0, height - mod.getHeight()))));
        }
        previousWidth = width;
        previousHeight = height;
    }

    public void edit(int mouseX, int mouseY, boolean down, boolean editing, int width, int height) {
        if (editing && down && !wasDown) {
            for (int i = mods.size() - 1; i >= 0; --i) {
                DraggableModule mod = mods.get(i);
                if (mouseX >= mod.getX() && mouseX <= mod.getX() + mod.getWidth()
                        && mouseY >= mod.getY() && mouseY <= mod.getY() + mod.getHeight()) {
                    selected = mod;
                    offsetX = mod.getX() - mouseX;
                    offsetY = mod.getY() - mouseY;
                    break;
                }
            }
        }
        if (selected != null) {
            if (editing) {
                selected.drag.setXPosition(Math.max(0, Math.min(mouseX + offsetX, Math.max(0, width - selected.getWidth()))));
                selected.drag.setYPosition(Math.max(0, Math.min(mouseY + offsetY, Math.max(0, height - selected.getHeight()))));
            }
            if (!editing || !down) {
                selected = null;
                for (org.celestial.client.files.FileManager.CustomFile file : org.celestial.client.files.FileManager.files) {
                    if (file instanceof org.celestial.client.files.impl.HudConfig hud) hud.saveFile();
                }
            }
        }
        wasDown = down;
    }

    public void drawHandles() {
        for (DraggableModule mod : mods) {
            int x = mod.getX(), y = mod.getY(), w = mod.getWidth(), h = mod.getHeight();
            int color = mod == selected ? 0xCCFFFFFF : 0x557F7F7F;
            net.minecraft.client.gui.Gui.drawRect(x, y, x + w, y + 1, color);
            net.minecraft.client.gui.Gui.drawRect(x, y + h - 1, x + w, y + h, color);
            net.minecraft.client.gui.Gui.drawRect(x, y, x + 1, y + h, color);
            net.minecraft.client.gui.Gui.drawRect(x + w - 1, y, x + w, y + h, color);
        }
    }

    public ArrayList<DraggableModule> getMods() {
        return mods;
    }

    public void setMods(ArrayList<DraggableModule> mods) {
        this.mods = mods;
    }
}