package org.celestial.client.ui.components.draggable.impl;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.util.ResourceLocation;
import optifine.CustomColors;
import org.celestial.client.feature.impl.hud.HUD;
import org.celestial.client.helpers.misc.ClientHelper;
import org.celestial.client.ui.components.draggable.DraggableModule;
import org.celestial.client.ui.font.MCFontRenderer;

import java.awt.*;
import java.util.List;
import java.util.*;

public class PotionComponent extends DraggableModule {

    protected Gui gui = new Gui();

    public PotionComponent() {
        super("PotionComponent", 2, 320);
    }

    @Override
    public int getWidth() {
        return 100;
    }

    @Override
    public int getHeight() {
        return 150;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        int xOff = 21;
        int yOff = 14;
        int counter = 16;

        Collection<MobEffectInstance> collection = mc.player.getActivePotionEffects();

        if (collection.isEmpty()) {
            drag.setCanRender(false);
        } else {
            drag.setCanRender(true);
            GlStateManager.color(1F, 1F, 1F, 1F);
            GlStateManager.disableLighting();
            int listOffset = 23;
            if (collection.size() > 5) {
                listOffset = 132 / (collection.size() - 1);
            }
            List<MobEffectInstance> potions = new ArrayList<>(mc.player.getActivePotionEffects());
            potions.sort(Comparator.comparingDouble(effect -> mc.fontRendererObj.getStringWidth((Objects.requireNonNull(MobEffect.getPotionById(CustomColors.getPotionId(effect.getEffectName()))).getName()))));

            for (MobEffectInstance MobEffect : potions) {
                MobEffect effect = MobEffect.getPotionById(CustomColors.getPotionId(MobEffect.getEffectName()));
                GlStateManager.color(1F, 1F, 1F, 1F);

                assert effect != null;
                if (effect.hasStatusIcon() && HUD.potionIcons.getCurrentValue()) {
                    mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
                    int statusIconIndex = effect.getStatusIconIndex();
                    gui.drawTexturedModalRect((float) ((getX() + xOff) - 20), (getY() + counter) - yOff, statusIconIndex % 8 * 18, 198 + statusIconIndex / 8 * 18, 18, 18);
                }

                String level = I18n.format(effect.getName());
                if (MobEffect.getAmplifier() == 1) {
                    level = level + " " + I18n.format("enchantment.level.2");
                } else if (MobEffect.getAmplifier() == 2) {
                    level = level + " " + I18n.format("enchantment.level.3");
                } else if (MobEffect.getAmplifier() == 3) {
                    level = level + " " + I18n.format("enchantment.level.4");
                }

                int getPotionColor = -1;
                if ((MobEffect.getDuration() < 200)) {
                    getPotionColor = new Color(215, 59, 59).getRGB();
                } else if (MobEffect.getDuration() < 400) {
                    getPotionColor = new Color(231, 143, 32).getRGB();
                } else if (MobEffect.getDuration() > 400) {
                    getPotionColor = new Color(172, 171, 171).getRGB();
                }

                String durationString = MobEffect.getDurationString(MobEffect);

                if (HUD.font.currentMode.equals("Minecraft")) {
                    MCFontRenderer.drawStringWithOutline(mc.fontRendererObj, level, getX() + xOff, (getY() + counter) - yOff, -1);
                    MCFontRenderer.drawStringWithOutline(mc.fontRendererObj, durationString, getX() + xOff, (getY() + counter + 10) - yOff, HUD.potionTimeColor.getCurrentValue() ? getPotionColor : -1);
                } else {
                    ClientHelper.getFontRender().drawStringWithOutline(level, getX() + xOff, (getY() + counter) - yOff, -1);
                    ClientHelper.getFontRender().drawStringWithOutline(durationString, getX() + xOff, (getY() + counter + 10) - yOff, HUD.potionTimeColor.getCurrentValue() ? getPotionColor : -1);
                }
                counter += listOffset;
            }
            super.draw();
        }
        super.render(mouseX, mouseY);
    }

    @Override
    public void draw() {
        if (HUD.MobEffect.getCurrentValue()) {
            int xOff = 21;
            int yOff = 14;
            int counter = 16;

            Collection<MobEffectInstance> collection = mc.player.getActivePotionEffects();

            if (collection.isEmpty()) {
                drag.setCanRender(false);
            } else {
                drag.setCanRender(true);
                GlStateManager.color(1F, 1F, 1F, 1F);
                GlStateManager.disableLighting();
                int listOffset = 23;
                if (collection.size() > 5) {
                    listOffset = 132 / (collection.size() - 1);
                }
                List<MobEffectInstance> potions = new ArrayList<>(mc.player.getActivePotionEffects());
                potions.sort(Comparator.comparingDouble(effect -> mc.fontRendererObj.getStringWidth((Objects.requireNonNull(MobEffect.getPotionById(CustomColors.getPotionId(effect.getEffectName()))).getName()))));

                for (MobEffectInstance MobEffect : potions) {
                    MobEffect effect = MobEffect.getPotionById(CustomColors.getPotionId(MobEffect.getEffectName()));
                    GlStateManager.color(1F, 1F, 1F, 1F);

                    assert effect != null;
                    if (effect.hasStatusIcon() && HUD.potionIcons.getCurrentValue()) {
                        mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
                        int statusIconIndex = effect.getStatusIconIndex();
                        gui.drawTexturedModalRect((float) ((getX() + xOff) - 20), (getY() + counter) - yOff, statusIconIndex % 8 * 18, 198 + statusIconIndex / 8 * 18, 18, 18);
                    }

                    String level = I18n.format(effect.getName());
                    if (MobEffect.getAmplifier() == 1) {
                        level = level + " " + I18n.format("enchantment.level.2");
                    } else if (MobEffect.getAmplifier() == 2) {
                        level = level + " " + I18n.format("enchantment.level.3");
                    } else if (MobEffect.getAmplifier() == 3) {
                        level = level + " " + I18n.format("enchantment.level.4");
                    }

                    int getPotionColor = -1;
                    if ((MobEffect.getDuration() < 200)) {
                        getPotionColor = new Color(215, 59, 59).getRGB();
                    } else if (MobEffect.getDuration() < 400) {
                        getPotionColor = new Color(231, 143, 32).getRGB();
                    } else if (MobEffect.getDuration() > 400) {
                        getPotionColor = new Color(172, 171, 171).getRGB();
                    }

                    String durationString = MobEffect.getDurationString(MobEffect);

                    if (HUD.font.currentMode.equals("Minecraft")) {
                        MCFontRenderer.drawStringWithOutline(mc.fontRendererObj, level, getX() + xOff, (getY() + counter) - yOff, -1);
                        MCFontRenderer.drawStringWithOutline(mc.fontRendererObj, durationString, getX() + xOff, (getY() + counter + 10) - yOff, HUD.potionTimeColor.getCurrentValue() ? getPotionColor : -1);
                    } else {
                        ClientHelper.getFontRender().drawStringWithOutline(level, getX() + xOff, (getY() + counter) - yOff, -1);
                        ClientHelper.getFontRender().drawStringWithOutline(durationString, getX() + xOff, (getY() + counter + 10) - yOff, HUD.potionTimeColor.getCurrentValue() ? getPotionColor : -1);
                    }
                    counter += listOffset;
                }
            }
            super.draw();
        }
    }
}
