package org.celestial.client.feature.impl.visual;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.world.entity.player.Player;
import net.minecraft.util.ResourceLocation;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.render.EventRender2D;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.helpers.palette.PaletteHelper;
import org.celestial.client.helpers.render.RenderHelper;
import org.celestial.client.helpers.render.rect.RectHelper;
import org.celestial.client.settings.impl.NumberSetting;

import java.awt.*;

public class Radar extends Feature {

    private final NumberSetting size;
    private final NumberSetting posx;
    private final NumberSetting posy;
    public int scale;

    public Radar() {
        super("Radar", "Показывает радар и игроков на нем", Type.Visuals);
        posx = new NumberSetting("PosX", 860, 0, 900, 1, () -> true);
        posy = new NumberSetting("PosY", 15, 0, 350, 1, () -> true);
        size = new NumberSetting("Size", 100, 30, 300, 1, () -> true);
        addSettings(posx, posy, size);
    }

    @EventTarget
    public void onRender2D(EventRender2D event) {
        double psx = posx.getCurrentValue();
        double psy = posy.getCurrentValue();
        ScaledResolution sr = new ScaledResolution(mc);
        scale = 2;
        int sizeRect = (int) size.getCurrentValue();
        float xOffset = (float) Math.max(0, Math.min(sr.getScaledWidth() - sizeRect, sr.getScaledWidth() - sizeRect - psx));
        float yOffset = (float) Math.max(0, Math.min(sr.getScaledHeight() - sizeRect, psy));
        double playerPosX = mc.player.posX;
        double playerPosZ = mc.player.posZ;
        RectHelper.drawBorderedRect(xOffset + 2.5F, yOffset + 2.5F, (xOffset + sizeRect) - 2.5F, (yOffset + sizeRect) - 2.5F, 0.5F, PaletteHelper.getColor(2), PaletteHelper.getColor(11), false);
        RectHelper.drawBorderedRect(xOffset + 3, yOffset + 3, xOffset + sizeRect - 3, yOffset + sizeRect - 3, 0.2F, PaletteHelper.getColor(2), PaletteHelper.getColor(11), false);
        RectHelper.drawRect(xOffset + (sizeRect / 2F - 0.5), yOffset + 3.5, xOffset + (sizeRect / 2F + 0.2), (yOffset + sizeRect) - 3.5, PaletteHelper.getColor(155, 100));
        RectHelper.drawRect(xOffset + 3.5, yOffset + (sizeRect / 2F - 0.2), (xOffset + sizeRect) - 3.5, yOffset + (sizeRect / 2F + 0.5), PaletteHelper.getColor(155, 100));
        RenderHelper.drawImage(new ResourceLocation("celestial/skeet.png"), xOffset + 3.5F, yOffset + 3.5F, sizeRect - 7, 1, Color.WHITE);
        for (Player Player : mc.world.playerEntities) {
            if (Player == mc.player)
                continue;

            float partialTicks = mc.timer.renderPartialTicks;
            float posX = (float) (Player.posX + (Player.posX - Player.lastTickPosX) * partialTicks - playerPosX) * 2;
            float posZ = (float) (Player.posZ + (Player.posZ - Player.lastTickPosZ) * partialTicks - playerPosZ) * 2;
            int color = (mc.player.canEntityBeSeen(Player) ? new Color(255, 0, 0).getRGB() : new Color(255, 255, 0).getRGB());
            float cos = (float) Math.cos(mc.player.rotationYaw * 0.017453292);
            float sin = (float) Math.sin(mc.player.rotationYaw * 0.017453292);
            float rotY = -(posZ * cos - posX * sin);
            float rotX = -(posX * cos + posZ * sin);
            if (rotY > sizeRect / 2F - 6) {
                rotY = sizeRect / 2F - 6;
            } else if (rotY < -(sizeRect / 2F - 8)) {
                rotY = -(sizeRect / 2F - 8);
            }
            if (rotX > sizeRect / 2F - 5) {
                rotX = sizeRect / 2F - 5;
            } else if (rotX < -(sizeRect / 2F - 5)) {
                rotX = -(sizeRect / 2F - 5);
            }
            RectHelper.drawRect((xOffset + sizeRect / 2F + rotX) - 1.5F, (yOffset + sizeRect / 2F + rotY) - 1.5F, (xOffset + sizeRect / 2F + rotX) + 1.5F, (yOffset + sizeRect / 2F + rotY) + 1.5F, color);
        }
    }
}