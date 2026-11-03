package org.celestial.client.ui.components.draggable.impl;

import net.minecraft.ChatFormatting;
import org.celestial.client.Celestial;
import org.celestial.client.feature.impl.hud.HUD;
import org.celestial.client.helpers.misc.ClientHelper;
import org.celestial.client.ui.components.draggable.DraggableModule;
import org.celestial.security.utils.LicenseUtil;

public class ClientInfoComponent extends DraggableModule {

    public ClientInfoComponent() {
        super("ClientInfoComponent", sr.getScaledWidth() - mc.robotoRegularFontRender.getStringWidth(Celestial.instance.type + " - " + ChatFormatting.WHITE + Celestial.instance.version + ChatFormatting.RESET + " - " + "Celestial"), sr.getScaledHeight() - 20);
    }

    @Override
    public int getWidth() {
        return mc.robotoRegularFontRender.getStringWidth(Celestial.instance.type + " - " + ChatFormatting.WHITE + Celestial.instance.version + ChatFormatting.RESET + " - Celestial");
    }

    @Override
    public int getHeight() {
        return 15;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        if (HUD.clientInfo.getCurrentValue()) {

            String buildStr = Celestial.instance.type + " - " + ChatFormatting.WHITE + Celestial.instance.version + ChatFormatting.RESET + " - " + "Celestial";
            if (mc.player != null && mc.world != null) {
                mc.robotoRegularFontRender.drawStringWithShadow(buildStr, getX(), getY(), ClientHelper.getClientColor().getRGB());

            }
        }
        super.render(mouseX, mouseY);
    }

    @Override
    public void draw() {
        if (HUD.clientInfo.getCurrentValue()) {

            String buildStr = Celestial.instance.type + " - " + ChatFormatting.WHITE + Celestial.instance.version + ChatFormatting.RESET + " - " + "Celestial";
            if (mc.player != null && mc.world != null) {
                mc.robotoRegularFontRender.drawStringWithShadow(buildStr, getX(), getY(), ClientHelper.getClientColor().getRGB());
            }
        }
        super.draw();
    }
}