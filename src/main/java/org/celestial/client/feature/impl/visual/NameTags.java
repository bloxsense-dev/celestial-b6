package org.celestial.client.feature.impl.visual;

import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import optifine.CustomColors;
import org.lwjgl.opengl.GL11;
import org.celestial.client.Celestial;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.render.EventRender3D;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.feature.impl.misc.StreamerMode;
import org.celestial.client.helpers.math.MathematicHelper;
import org.celestial.client.helpers.palette.PaletteHelper;
import org.celestial.client.helpers.render.rect.RectHelper;
import org.celestial.client.settings.impl.BooleanSetting;
import org.celestial.client.settings.impl.ListSetting;
import org.celestial.client.settings.impl.NumberSetting;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class NameTags extends Feature {

    public BooleanSetting armor;
    public BooleanSetting backGround;
    public NumberSetting opacity;
    public NumberSetting size;
    public ListSetting hpMode = new ListSetting("Health Mode", "HP", () -> true, "HP", "Percentage");

    public NameTags() {
        super("NameTags", "Показывает игроков, ник, броню и их здоровье сквозь стены", Type.Visuals);
        size = new NumberSetting("NameTags Size", 0.5f, 0.2f, 2, 0.01f, () -> true);
        backGround = new BooleanSetting("NameTags Background", true, () -> true);
        opacity = new NumberSetting("Background Opacity", 120, 0, 255, 10, () -> backGround.getCurrentValue());
        armor = new BooleanSetting("Show Armor", true, () -> true);
        addSettings(hpMode, size, backGround, opacity, armor);
    }

    @EventTarget
    public void onRender3d(EventRender3D event) {
        if (mc.world == null || mc.player == null || mc.fontRenderer == null) return;
        for (Player entity : mc.world.playerEntities) {
            if (entity != null && entity.isAlive() && (entity != mc.player || !mc.options.getCameraType().isFirstPerson())) {
                double x = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * event.getPartialTicks()) - mc.getRenderManager().renderPosX;
                double y = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * event.getPartialTicks()) - mc.getRenderManager().renderPosY;
                double z = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * event.getPartialTicks()) - mc.getRenderManager().renderPosZ;

                String tag;
                if (Celestial.instance.featureManager.getFeatureByClass(StreamerMode.class).getState() && StreamerMode.otherNames.getCurrentValue()) {
                    tag = "Protected";
                } else if (Celestial.instance.friendManager.isFriend(entity.getName())) {
                    tag = ChatFormatting.GREEN + "[F] " + ChatFormatting.RESET + entity.getDisplayName().getUnformattedText();
                } else {
                    tag = entity.getDisplayName().getUnformattedText();
                }

                y += (entity.isSneaking() ? 0.5F : 0.7F);
                float distance = (float) (Math.min(Math.max(1.2f * (mc.player.getDistanceToEntity(entity) * 0.15f), 1.25f), 6) * 0.015f);
                int health = (int) entity.getHealth();
                if (health <= entity.getMaxHealth() * 0.25F) {
                    tag = tag + "\u00a74";
                } else if (health <= entity.getMaxHealth() * 0.5F) {
                    tag = tag + "\u00a76";
                } else if (health <= entity.getMaxHealth() * 0.75F) {
                    tag = tag + "\u00a7e";
                } else if (health <= entity.getMaxHealth()) {
                    tag = tag + "\u00a72";
                }

                String hp;
                if (hpMode.currentMode.equals("Percentage")) {
                    hp = MathematicHelper.round(entity.getHealth() / entity.getMaxHealth() * 100F, 1) + "% ";
                } else {
                    hp = MathematicHelper.round(entity.getHealth(), 1) + " ";
                }

                tag = tag + " " + hp;

                float scale = distance;
                scale = scale * size.getCurrentValue() * 2;
                GL11.glPushMatrix();
                GL11.glTranslatef((float) x, (float) y + 1.4F, (float) z);
                GL11.glNormal3f(1, 1, 1);
                float fixed = mc.gameSettings.thirdPersonView == 2 ? -1 : 1;
                GL11.glRotatef(-mc.getRenderManager().playerViewY, 0, 1, 0);
                GL11.glRotatef(mc.getRenderManager().playerViewX, fixed, 0, 0);
                GL11.glScalef(-scale, -scale, scale);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                int width = mc.fontRenderer.getStringWidth(tag) / 2;
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

                if (backGround.getCurrentValue()) {
                    RectHelper.drawRect(-width - 2, -(mc.fontRenderer.getFontHeight() + 1), width + 2, 2, PaletteHelper.getColor(0, (int) opacity.getCurrentValue()));
                }

                mc.fontRenderer.drawStringWithShadow(tag, MathematicHelper.getMiddle(-width - 2, width + 2) - width, -(mc.fontRenderer.getFontHeight() - 1), Color.WHITE.getRGB());
                if (armor.getCurrentValue()) {
                    renderArmor(entity, 0, -(mc.fontRenderer.getFontHeight() + 1) - 20);
                }

                float yPotion = (float) (y - 45);
                for (MobEffectInstance effectPotion : entity.getActivePotionEffects()) {
                    GL11.glDisable(GL11.GL_DEPTH_TEST);
                    MobEffect effect = MobEffect.getPotionById(CustomColors.getPotionId(effectPotion.getEffectName()));
                    if (effect != null) {

                        String getPotionColor = "";
                        if ((effectPotion.getDuration() < 200)) {
                            getPotionColor = ChatFormatting.RED.toString();
                        } else if (effectPotion.getDuration() < 400) {
                            getPotionColor = ChatFormatting.GOLD.toString();
                        } else if (effectPotion.getDuration() > 400) {
                            getPotionColor = ChatFormatting.GRAY.toString();
                        }

                        String durationString = MobEffect.getDurationString(effectPotion);

                        String level = I18n.format(effect.getName());
                        if (effectPotion.getAmplifier() == 1) {
                            level = level + " " + ChatFormatting.GRAY + I18n.format("enchantment.level.2") + " (" + getPotionColor + durationString + ")";
                        } else if (effectPotion.getAmplifier() == 2) {
                            level = level + " " + ChatFormatting.GRAY + I18n.format("enchantment.level.3") + " (" + getPotionColor + durationString + ")";
                        } else if (effectPotion.getAmplifier() == 3) {
                            level = level + " " + ChatFormatting.GRAY + I18n.format("enchantment.level.4") + " (" + getPotionColor + durationString + ")";
                        }

                        mc.fontRenderer.drawStringWithShadow(level, MathematicHelper.getMiddle(-width - 2, width + 2) - width, yPotion, effectPotion.getPotion().getLiquidColor());
                    }
                    yPotion -= 10;
                    GL11.glEnable(GL11.GL_DEPTH_TEST);
                }

                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glPopMatrix();
            }
        }
    }

    public void renderArmor(Player player, int x, int y) {
        Inventory items = player.inventory;
        ItemStack offhand = player.getHeldItemOffhand();
        ItemStack inHand = player.getHeldItemMainhand();
        ItemStack boots = items.armorItemInSlot(0);
        ItemStack leggings = items.armorItemInSlot(1);
        ItemStack body = items.armorItemInSlot(2);
        ItemStack helm = items.armorItemInSlot(3);
        ItemStack[] stuff;
        stuff = new ItemStack[]{offhand, inHand, helm, body, leggings, boots};
        ArrayList<ItemStack> stacks = new ArrayList<>();
        ItemStack[] array;
        int length = (array = stuff).length;

        for (int j = 0; j < length; j++) {
            ItemStack i = array[j];
            if (i != null && !i.isEmpty()) {
                i.getItem();
                stacks.add(i);
            }
        }
        int width = 18 * stacks.size() / 2;
        x -= width;
        GlStateManager.disableDepth();
        for (ItemStack stack : stacks) {
            renderItem(player, stack, x, y);
            x += 18;
        }
        GlStateManager.enableDepth();
    }

    public void renderItem(Player e, ItemStack stack, int x, int y) {
        if (stack != null && !stack.isEmpty()) {
            // GUI items use the native renderer; the legacy RenderItem is a stub.
            // Project the billboard's local item rectangle into GUI coordinates.
            float[] model = new float[16], projection = new float[16];
            GL11.glGetFloatv(GL11.GL_MODELVIEW_MATRIX, model);
            GL11.glGetFloatv(GL11.GL_PROJECTION_MATRIX, projection);
            var matrix = new org.joml.Matrix4f().set(projection).mul(new org.joml.Matrix4f().set(model));
            var origin = matrix.transform(new org.joml.Vector4f(x, y, 0, 1));
            var end = matrix.transform(new org.joml.Vector4f(x + 16, y + 16, 0, 1));
            if (origin.w <= 0 || end.w <= 0) return;
            var window = mc.getWindow();
            float sx = (origin.x / origin.w + 1) * window.getGuiScaledWidth() / 2f;
            float sy = (1 - origin.y / origin.w) * window.getGuiScaledHeight() / 2f;
            float ex = (end.x / end.w + 1) * window.getGuiScaledWidth() / 2f;
            float ey = (1 - end.y / end.w) * window.getGuiScaledHeight() / 2f;
            var gui = new net.minecraft.client.gui.GuiGraphicsExtractor(mc, mc.gameRenderer.gameRenderState().guiRenderState, 0, 0);
            gui.pose().translate(sx, sy).scale((ex - sx) / 16, (ey - sy) / 16);
            gui.item(e, stack, 0, 0, 0);
            gui.itemDecorations(mc.font, stack, 0, 0);
        }
        if (stack != null && !stack.isEmpty()) renderEnchant(stack, x + 2, y - 18);
    }

    public void renderEnchant(ItemStack item, float x, int y) {
        int encY = y + 5;
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(item);
        for (Enchantment enchantment : enchantments.keySet()) {
            int level = EnchantmentHelper.getEnchantmentLevel(enchantment, item);
            mc.fontRenderer.drawStringWithShadow((String.valueOf(enchantment.getName().substring(12).charAt(0)).toUpperCase() + level), x, encY, 16777215);
            encY -= 12;
        }
    }
}
