package org.celestial.client.feature.impl.combat;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.item.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.player.EventUpdate;
import org.celestial.client.event.events.impl.render.EventRender2D;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.helpers.player.InventoryHelper;
import org.celestial.client.helpers.player.MovementHelper;
import org.celestial.client.settings.impl.BooleanSetting;
import org.celestial.client.settings.impl.NumberSetting;

public class AutoTotem extends Feature {

    public static BooleanSetting definiteHealth;
    public static NumberSetting health;
    public static BooleanSetting countTotem;
    public static BooleanSetting checkCrystal;
    public static BooleanSetting inventoryOnly;
    public static BooleanSetting noMoving;
    public static NumberSetting radiusCrystal;

    public AutoTotem() {
        super("AutoTotem", "Автоматически берет в руку тотем при опредленном здоровье", Type.Combat);
        definiteHealth = new BooleanSetting("Definite Health", false, () -> true);
        health = new NumberSetting("Health Amount", 10, 1, 20, 0.5F, () -> definiteHealth.getCurrentValue());
        inventoryOnly = new BooleanSetting("Only Inventory", false, () -> true);
        noMoving = new BooleanSetting("No Moving Swap", false, () -> true);
        countTotem = new BooleanSetting("Count Totem", true, () -> true);
        checkCrystal = new BooleanSetting("Check Crystal", true, () -> true);
        radiusCrystal = new NumberSetting("Distance to Crystal", 6, 1, 8, 1, () -> checkCrystal.getCurrentValue());
        addSettings(definiteHealth, health, inventoryOnly, noMoving, countTotem, checkCrystal, radiusCrystal);
    }

    private int fountTotemCount() {
        int count = 0;
        for (int i = 0; i < mc.player.inventory.getSizeInventory(); i++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
                count++;
            }
        }
        return count;
    }

    private boolean checkCrystal() {
        for (Entity entity : mc.world.loadedEntityList) {
            if ((entity instanceof EndCrystal && mc.player.getDistanceToEntity(entity) <= radiusCrystal.getCurrentValue())) {
                return true;
            }
        }
        return false;
    }

    @EventTarget
    public void onRender2D(EventRender2D event) {
        if (fountTotemCount() > 0 && countTotem.getCurrentValue()) {
            mc.sfuiFontRender.drawStringWithShadow(fountTotemCount() + "", (event.getResolution().getScaledWidth() / 2f + 19), (event.getResolution().getScaledHeight() / 2f), -1);
            for (int i = 0; i < mc.player.inventory.getSizeInventory(); i++) {
                ItemStack stack = mc.player.inventory.getStackInSlot(i);
                if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
                    GlStateManager.pushMatrix();
                    GlStateManager.disableBlend();
                    mc.getRenderItem().renderItemAndEffectIntoGUI(stack, event.getResolution().getScaledWidth() / 2 + 4, event.getResolution().getScaledHeight() / 2 - 7);
                    GlStateManager.popMatrix();
                }
            }
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (noMoving.getCurrentValue() && MovementHelper.isMoving()) {
            return;
        }
        if (inventoryOnly.getCurrentValue() && !(mc.currentScreen instanceof GuiInventory)) {
            return;
        }
        if (definiteHealth.getCurrentValue() && mc.player.getHealth() > health.getCurrentValue()) {
            return;
        }
        if (mc.player.getHeldItemOffhand().getItem() != Items.TOTEM_OF_UNDYING && InventoryHelper.getTotemAtHotbar() != -1) {
            mc.playerController.windowClick(0, InventoryHelper.getTotemAtHotbar(), 1, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, 45, 1, ClickType.PICKUP, mc.player);
        }
        if (checkCrystal() && checkCrystal.getCurrentValue()) {
            if (mc.player.getHeldItemOffhand().getItem() != Items.TOTEM_OF_UNDYING && InventoryHelper.getTotemAtHotbar() != -1) {
                mc.playerController.windowClick(0, InventoryHelper.getTotemAtHotbar(), 1, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, 45, 1, ClickType.PICKUP, mc.player);
            }
        }
    }
}
