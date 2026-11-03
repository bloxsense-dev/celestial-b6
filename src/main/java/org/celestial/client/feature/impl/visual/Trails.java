package org.celestial.client.feature.impl.visual;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.play.client.CPacketPlayer;
import org.lwjgl.opengl.GL11;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.packet.EventSendPacket;
import org.celestial.client.event.events.impl.render.EventRender3D;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.helpers.misc.ClientHelper;
import org.celestial.client.helpers.palette.PaletteHelper;
import org.celestial.client.helpers.render.RenderHelper;
import org.celestial.client.settings.impl.ColorSetting;
import org.celestial.client.settings.impl.ListSetting;
import org.celestial.client.settings.impl.NumberSetting;

import java.awt.*;
import java.util.ArrayList;

public class Trails extends Feature {

    public ListSetting colorMode;
    public ColorSetting customColor;
    public NumberSetting width;
    public NumberSetting height;
    public NumberSetting sizeToRemove;

    public ArrayList<Point> points = new ArrayList<>();
    public float pointCount = 0;

    public Trails() {
        super("Trails", "Оставляет линию ходьбы", Type.Visuals);
        colorMode = new ListSetting("Color", "Rainbow", () -> true, "Rainbow", "Astolfo", "Client", "Custom");
        customColor = new ColorSetting("Custom Color", new Color(0xFFFFFF).getRGB(), () -> colorMode.currentMode.equals("Custom"));
        width = new NumberSetting("Width", 2F, 1F, 6F, 1F, () -> true);
        height = new NumberSetting("Height", 0, 0, 6F, 0.01F, () -> true);
        sizeToRemove = new NumberSetting("Time", 150, 10, 300, 10F, () -> true);
        addSettings(colorMode, customColor, width, height, sizeToRemove);
    }

    @EventTarget
    public void onRender3D(EventRender3D event) {
        String colorModeValue = colorMode.getOptions();

        if (!getState())
            return;
        
        double x = mc.getRenderManager().renderPosX;
        double y = mc.getRenderManager().renderPosY;
        double z = mc.getRenderManager().renderPosZ;

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDepthMask(false);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glLineWidth(width.getCurrentValue());
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        float size = Math.max(1, sizeToRemove.getCurrentValue()) * 10;
        points.removeIf(point -> System.currentTimeMillis() - point.getTime() > size);
        long fade = System.currentTimeMillis() - (long) size;
        // Adjacent vertical pairs form a ribbon, not a diagonal zigzag line.
        GL11.glBegin(GL11.GL_QUAD_STRIP);
        for (Point point : points) {
            float alpha;
            float offset = (point.getTime() - fade) / size;
            if (offset < 0 || offset > 1) {
                pointCount = points.indexOf(point);
                continue;
            }
            alpha = offset;

            Color customColorValue = Color.white;
            if (colorModeValue.equalsIgnoreCase("Rainbow")) {
                customColorValue = PaletteHelper.rainbow((int) x * 300, 1, 1);
            } else if (colorModeValue.equalsIgnoreCase("Client")) {
                customColorValue = ClientHelper.getClientColor();
            } else if (colorModeValue.equalsIgnoreCase("Custom")) {
                customColorValue = new Color(customColor.getColor());
            } else if (colorModeValue.equalsIgnoreCase("Astolfo")) {
                customColorValue = PaletteHelper.astolfo(false, (int) alpha);
            }

            RenderHelper.setColor(customColorValue, alpha * 0.05f);
            GL11.glVertex3d(point.getX() - x, point.getY() - y + height.getCurrentValue() + 1, point.getZ() - z);
            RenderHelper.setColor(customColorValue, alpha * 0.55f);
            GL11.glVertex3d(point.getX() - x, point.getY() - y + height.getCurrentValue() + 0.02, point.getZ() - z);
        }

        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINE_STRIP);
        for (Point point : points) {
            float alpha = Math.max(0, Math.min(1, (point.getTime() - fade) / size));
            Color color = switch (colorModeValue) {
                case "Client" -> ClientHelper.getClientColor();
                case "Custom" -> new Color(customColor.getColor());
                case "Astolfo" -> PaletteHelper.astolfo(false, (int) alpha);
                default -> PaletteHelper.rainbow((int) x * 300, 1, 1);
            };
            RenderHelper.setColor(color, alpha);
            GL11.glVertex3d(point.getX() - x, point.getY() - y + height.getCurrentValue() + 0.02, point.getZ() - z);
        }
        GL11.glEnd();

        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GlStateManager.resetColor();
        GL11.glPopMatrix();
    }

    @EventTarget
    public void sendPacket(EventSendPacket event) {
        if (!getState() || mc.player == null)
            return;
        if (event.getPacket() instanceof CPacketPlayer) {
            CPacketPlayer packet = (CPacketPlayer) event.getPacket();
            double px = packet.getX(mc.player.posX), py = packet.getY(mc.player.posY), pz = packet.getZ(mc.player.posZ);
            if (!points.isEmpty()) {
                Point last = points.get(points.size() - 1);
                double distance = Math.pow(px-last.x, 2) + Math.pow(py-last.y, 2) + Math.pow(pz-last.z, 2);
                if (distance > 64) points.clear(); // Do not draw across teleports.
                else if (distance < 0.0001) return;
            }
            points.add(new Point(px, py, pz, System.currentTimeMillis()));
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        points.clear();
        pointCount = 0;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        points.clear();
        pointCount = 0;
    }

    public static class Point {

        public double x;
        public double y;
        public double z;
        public long time;

        public Point(double x, double y, double z, long time) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.time = time;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getZ() {
            return z;
        }

        public long getTime() {
            return time;
        }
    }
}
