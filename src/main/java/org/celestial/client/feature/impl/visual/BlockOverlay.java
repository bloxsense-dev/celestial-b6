package org.celestial.client.feature.impl.visual;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.util.math.BlockPos;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.render.EventRender3D;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.helpers.misc.ClientHelper;
import org.celestial.client.helpers.palette.PaletteHelper;
import org.celestial.client.helpers.render.RenderHelper;
import org.celestial.client.settings.impl.BooleanSetting;
import org.celestial.client.settings.impl.ColorSetting;
import org.celestial.client.settings.impl.ListSetting;

import java.awt.*;

public class BlockOverlay extends Feature {

    public ListSetting colorMode = new ListSetting("Color Box Mode", "Custom", () -> true, "Astolfo", "Rainbow", "Client", "Custom");
    public ColorSetting colorPicker = new ColorSetting("Color BlockOverlay", -1, () -> colorMode.currentMode.equals("Custom"));
    public BooleanSetting outline = new BooleanSetting("Outline BlockOverlay", false, () -> true);

    public BlockOverlay() {
        super("BlockOverlay", "Показывает блоки на которые вы навелись", Type.Visuals);
        addSettings(colorMode, colorPicker, outline);
    }

    @EventTarget
    public void onRender3D(EventRender3D event) {
        int color = 0;
        switch (colorMode.currentMode) {
            case "Client":
                color = ClientHelper.getClientColor().getRGB();
                break;
            case "Custom":
                color = colorPicker.getColor();
                break;
            case "Astolfo":
                color = PaletteHelper.astolfo(false, mc.objectMouseOver.getBlockPos().getY()).getRGB();
                break;
            case "Rainbow":
                color = PaletteHelper.rainbow(300, 1, 1).getRGB();
                break;
        }
        if (mc.world.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock() != Blocks.AIR) {
            GlStateManager.pushMatrix();
            RenderHelper.blockEsp(new BlockPos(mc.objectMouseOver.getBlockPos().getX(), mc.objectMouseOver.getBlockPos().getY(), mc.objectMouseOver.getBlockPos().getZ()), new Color(color), outline.getCurrentValue());
            GlStateManager.popMatrix();
        }
    }

}
