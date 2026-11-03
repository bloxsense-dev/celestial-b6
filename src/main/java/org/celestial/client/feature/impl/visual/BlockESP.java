package org.celestial.client.feature.impl.visual;

import net.minecraft.tileentity.*;
import net.minecraft.util.math.BlockPos;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.render.EventRender3D;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.helpers.misc.ClientHelper;
import org.celestial.client.helpers.render.RenderHelper;
import org.celestial.client.settings.impl.BooleanSetting;
import org.celestial.client.settings.impl.ColorSetting;

import java.awt.*;

public class BlockESP extends Feature {

    public static BooleanSetting enderChest;
    public static BooleanSetting chest;
    public static BooleanSetting clientColor;
    public static ColorSetting spawnerColor;
    public static BooleanSetting espOutline;
    public static ColorSetting chestColor;
    public static ColorSetting enderChestColor;
    public static ColorSetting shulkerColor;
    public static ColorSetting bedColor;
    private final BooleanSetting bed;
    private final BooleanSetting shulker;
    private final BooleanSetting spawner;

    public BlockESP() {
        super("BlockESP", "Подсвечивает опредленные блоки", Type.Visuals);
        chest = new BooleanSetting("Chest", true, () -> true);
        enderChest = new BooleanSetting("Ender Chest", false, () -> true);
        spawner = new BooleanSetting("Spawner", false, () -> true);
        shulker = new BooleanSetting("Shulker", false, () -> true);
        bed = new BooleanSetting("Bed", false, () -> true);
        chestColor = new ColorSetting("Chest Color", new Color(0xFFFFFF).getRGB(), chest::getCurrentValue);
        enderChestColor = new ColorSetting("EnderChest Color", new Color(0xFFFFFF).getRGB(), enderChest::getCurrentValue);
        shulkerColor = new ColorSetting("Shulker Color", new Color(0xFFFFFF).getRGB(), shulker::getCurrentValue);
        spawnerColor = new ColorSetting("Spawner Color", new Color(0xFFFFFF).getRGB(), spawner::getCurrentValue);
        bedColor = new ColorSetting("Bed Color", new Color(0xFFFFFF).getRGB(), bed::getCurrentValue);
        clientColor = new BooleanSetting("Client Colors", false, () -> true);
        espOutline = new BooleanSetting("ESP Outline", false, () -> true);
        addSettings(espOutline, chest, enderChest, spawner, shulker, bed, chestColor, enderChestColor, spawnerColor, shulkerColor, bedColor, clientColor);
    }

    @EventTarget
    public void onRender3D(EventRender3D event) {
        Color colorChest = clientColor.getCurrentValue() ? ClientHelper.getClientColor() : new Color(chestColor.getColor());
        Color enderColorChest = clientColor.getCurrentValue() ? ClientHelper.getClientColor() : new Color(enderChestColor.getColor());
        Color shulkColor = clientColor.getCurrentValue() ? ClientHelper.getClientColor() : new Color(shulkerColor.getColor());
        Color bedColoR = clientColor.getCurrentValue() ? ClientHelper.getClientColor() : new Color(bedColor.getColor());
        Color spawnerColoR = clientColor.getCurrentValue() ? ClientHelper.getClientColor() : new Color(spawnerColor.getColor());
        if (mc.player != null || mc.world != null) {
            for (TileEntity entity : mc.world.loadedTileEntityList) {
                BlockPos pos = entity.getPos();
                if (entity instanceof TileEntityChest && chest.getCurrentValue()) {
                    RenderHelper.blockEsp(pos, new Color(colorChest.getRGB()), espOutline.getCurrentValue());
                } else if (entity instanceof TileEntityEnderChest && enderChest.getCurrentValue()) {
                    RenderHelper.blockEsp(pos, new Color(enderColorChest.getRGB()), espOutline.getCurrentValue());
                } else if (entity instanceof TileEntityBed && bed.getCurrentValue()) {
                    RenderHelper.blockEsp(pos, new Color(bedColoR.getRGB()), espOutline.getCurrentValue());
                } else if (entity instanceof TileEntityShulkerBox && shulker.getCurrentValue()) {
                    RenderHelper.blockEsp(pos, new Color(shulkColor.getRGB()), espOutline.getCurrentValue());
                } else if (entity instanceof TileMonsterSpawner && spawner.getCurrentValue()) {
                    RenderHelper.blockEsp(pos, new Color(spawnerColoR.getRGB()), espOutline.getCurrentValue());
                }
            }
        }
    }
}
