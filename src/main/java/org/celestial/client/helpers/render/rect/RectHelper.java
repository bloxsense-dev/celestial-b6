package org.celestial.client.helpers.render.rect;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import org.celestial.client.helpers.Helper;

import java.awt.*;

public class RectHelper implements Helper {

    public static long delta = 0L;

    public static void drawRoundedRect(double x, double y, double width, double height, float radius, Color color) {

        float x2 = (float) (x + ((radius / 2F) + 0.5F));
        float y2 = (float) (y + ((radius / 2F) + 0.5F));
        float width2 = (float) (width - ((radius / 2F) + 0.5F));
        float height2 = (float) (height - ((radius / 2F) + 0.5F));

        drawRect(x2, y2, x2 + width2, y2 + height2, color.getRGB());

        polygon(x, y, radius * 2, 360, true, color);
        polygon(x + width2 - radius + 1.2, y, radius * 2, 360, true, color);

        polygon(x + width2 - radius + 1.2, y + height2 - radius + 1, radius * 2, 360, true, color);
        polygon(x, y + height2 - radius + 1, radius * 2, 360, true, color);

        GL11.glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);
        drawRect(x2 - radius / 2 - 0.5F, y2 + radius / 2, x2 + width2, y2 + height2 - radius / 2, color.getRGB());
        drawRect(x2, y2 + radius / 2, x2 + width2 + radius / 2 + 0.5f, y2 + height2 - radius / 2, color.getRGB());
        drawRect(x2 + radius / 2, y2 - radius / 2 - 0.5F, x2 + width2 - radius / 2, y + height2 - radius / 2, color.getRGB());
        drawRect(x2 + radius / 2, y2, x2 + width2 - radius / 2, y2 + height2 + radius / 2 + 0.5f, color.getRGB());
    }

    public static void glColor(Color color) {
        float red = (float)color.getRed() / 255.0f;
        float green = (float)color.getGreen() / 255.0f;
        float blue = (float)color.getBlue() / 255.0f;
        float alpha = (float)color.getAlpha() / 255.0f;
        GlStateManager.color(red, green, blue, alpha);
    }

    public static void drawGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
        float f = (float)(col1 >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(col1 >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(col1 >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(col1 & 0xFF) / 255.0f;
        float f4 = (float)(col2 >> 24 & 0xFF) / 255.0f;
        float f5 = (float)(col2 >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(col2 >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(col2 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glVertex2d(left, top);
        GL11.glVertex2d(left, bottom);
        GL11.glColor4f(f5, f6, f7, f4);
        GL11.glVertex2d(right, bottom);
        GL11.glVertex2d(right, top);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }

    public static void renderShadowVertical(Color c, float lineWidth, double startAlpha, int size, double posX, double posY1, double posY2, boolean right, boolean edges) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glDisable(2884);
        RectHelper.renderShadowVertical(lineWidth, startAlpha, size, posX, posY1, posY2, right, edges, (float)c.getRed() / 255.0f, (float)c.getGreen() / 255.0f, (float)c.getBlue() / 255.0f);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2884);
        GL11.glEnable(3008);
        GL11.glEnable(3553);
        GL11.glEnable(3042);
    }

    public static void renderShadowHorizontal(Color c, float lineWidth, double startAlpha, int size, double posY, double posX1, double posX2, boolean up, boolean edges) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glDisable(2884);
        RectHelper.renderShadowHorizontal(lineWidth, startAlpha, size, posY, posX1, posX2, up, edges, (float)c.getRed() / 255.0f, (float)c.getGreen() / 255.0f, (float)c.getBlue() / 255.0f);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2884);
        GL11.glEnable(3008);
        GL11.glEnable(3553);
        GL11.glEnable(3042);
    }

    public static void renderShadowHorizontal(float lineWidth, double startAlpha, int size, double posY, double posX1, double posX2, boolean up, boolean edges, float red, float green, float blue) {
        double alpha = startAlpha;
        GlStateManager.alphaFunc(516, 0.0f);
        GL11.glLineWidth(lineWidth);
        if (!up) {
            for (double y = 0.0; y < (double)size; y += 0.5) {
                GL11.glColor4d(red, green, blue, alpha);
                GL11.glBegin(1);
                GL11.glVertex2d(posX1 - (edges ? y : 0.0), posY + y);
                GL11.glVertex2d(posX2 + (edges ? y : 0.0), posY + y);
                GL11.glEnd();
                alpha = startAlpha - y / (double)size;
            }
        } else {
            for (double y = 0.5; y < (double)size; y += 0.5) {
                GL11.glColor4d(red, green, blue, alpha);
                GL11.glBegin(1);
                GL11.glVertex2d(posX1 - (edges ? y : 0.0) - 0.5, posY - y);
                GL11.glVertex2d(posX2 + (edges ? y : 0.0) - 0.5, posY - y);
                GL11.glEnd();
                alpha = startAlpha - y / (double)size;
            }
        }
    }


    public static void renderShadowVertical(float lineWidth, double startAlpha, int size, double posX, double posY1, double posY2, boolean right, boolean edges, float red, float green, float blue) {
        double alpha = startAlpha;
        GlStateManager.alphaFunc(516, 0.0f);
        GL11.glLineWidth(lineWidth);
        if (right) {
            for (double x = 0.5; x < (double)size; x += 0.5) {
                GL11.glColor4d(red, green, blue, alpha);
                GL11.glBegin(1);
                GL11.glVertex2d(posX + x, posY1 - (edges ? x : 0.0));
                GL11.glVertex2d(posX + x, posY2 + (edges ? x : 0.0));
                GL11.glEnd();
                alpha = startAlpha - x / (double)size;
            }
        } else {
            for (double x = 0.0; x < (double)size; x += 0.5) {
                GL11.glColor4d(red, green, blue, alpha);
                GL11.glBegin(1);
                GL11.glVertex2d(posX - x, posY1 - (edges ? x : 0.0));
                GL11.glVertex2d(posX - x, posY2 + (edges ? x : 0.0));
                GL11.glEnd();
                alpha = startAlpha - x / (double)size;
            }
        }
    }

    public static void drawHLine(double x, double y, double x1, double y1, float width, int color) {
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(red, green, blue, alpha);
        GL11.glPushMatrix();
        GL11.glLineWidth(width);
        GL11.glBegin(3);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x1, y1);
        GL11.glEnd();
        GL11.glLineWidth(1.0f);
        GL11.glPopMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void drawBorderedRect(double x, double y, double width, double height, int color, double lwidth) {
        RectHelper.drawHLine(x, y, width, y, (float)lwidth, color);
        RectHelper.drawHLine(width, y, width, height, (float)lwidth, color);
        RectHelper.drawHLine(x, height, width, height, (float)lwidth, color);
        RectHelper.drawHLine(x, height, x, y, (float)lwidth, color);
    }

    public static void drawBorderedRect(double x, double y, double x2, double y2, double width, int color1, int color2) {
        RectHelper.drawRect(x, y, x2, y2, color2);
        RectHelper.drawBorderedRect(x, y, x2, y2, color1, width);
    }

    public static void drawColorRect(double left, double top, double right, double bottom, Color color1, Color color2, Color color3, Color color4) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        RectHelper.glColor(color2);
        GL11.glVertex2d(left, bottom);
        RectHelper.glColor(color3);
        GL11.glVertex2d(right, bottom);
        RectHelper.glColor(color4);
        GL11.glVertex2d(right, top);
        RectHelper.glColor(color1);
        GL11.glVertex2d(left, top);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
        Gui.drawRect(0, 0, 0, 0, 0);
    }

    public static void drawVerticalGradientSmoothRect(float left, float top, float right, float bottom, int color, int color2) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        gui.drawGradientRect(left, top, right, bottom, color, color2);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        gui.drawGradientRect(left * 2 - 1, top * 2, left * 2, bottom * 2 - 1, color, color2);
        gui.drawGradientRect(left * 2, top * 2 - 1, right * 2, top * 2, color, color2);
        gui.drawGradientRect(right * 2, top * 2, right * 2 + 1, bottom * 2 - 1, color, color2);
        gui.drawGradientRect(left * 2, bottom * 2 - 1, right * 2, bottom * 2, color, color2);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glScalef(2F, 2F, 2F);
    }

    public static void drawRectWithEdge(double x, double y, double width, double height, Color color, Color colorTwo) {
        RectHelper.drawRect(x, y, x + width, y + height, color.getRGB());
        int colorRgb = colorTwo.getRGB();
        RectHelper.drawRect(x - 1.0, y, x, y + height, colorRgb);
        RectHelper.drawRect(x + width, y, x + width + 1.0, y + height, colorRgb);
        RectHelper.drawRect(x - 1.0, y - 1.0, x + width + 1.0, y, colorRgb);
        RectHelper.drawRect(x - 1.0, y + height, x + width + 1.0, y + height + 1.0, colorRgb);
    }

    public static void drawVerticalGradientRect(float left, float top, float right, float bottom, int color, int color2) {
        gui.drawGradientRect(left, top, right, bottom, color, color2);
    }

    public static void drawVerticalGradientBetterRect(float x, float y, float width, float height, int color, int color2) {
        gui.drawGradientRect(x, y, x + width, y + height, color, color2);
    }

    public static void polygon(double x, double y, double sideLength, double amountOfSides, boolean filled, Color color) {
        sideLength /= 2;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GlStateManager.disableAlpha();
        GL11.glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);
        if (!filled) {
            GL11.glLineWidth(1);
        }
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBegin(filled ? GL11.GL_TRIANGLE_FAN : GL11.GL_LINE_STRIP);

        for (double i = 0; i <= amountOfSides; i++) {
            double angle = i * (Math.PI * 2) / amountOfSides;
            GL11.glVertex2d(x + (sideLength * Math.cos(angle)) + sideLength, y + (sideLength * Math.sin(angle)) + sideLength);
        }

        GL11.glColor4f(1, 1, 1, 1);
        GL11.glEnd();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.enableAlpha();
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }


    public static void drawRectBetter(double x, double y, double width, double height, int color) {
        drawRect(x, y, x + width, y + height, color);
    }

    public static void drawGradientRectBetter(float x, float y, float width, float height, int color, int color2) {
        drawGradientRect(x, y, x + width, y + height, color, color2);
    }

    public static void drawSmoothGradientRect(double left, double top, double right, double bottom, int color, int color2) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        drawGradientRect(left, top, right, bottom, color, color2);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        drawGradientRect(left * 2 - 1, top * 2, left * 2, bottom * 2 - 1, color, color2);
        drawGradientRect(left * 2, top * 2 - 1, right * 2, top * 2, color, color2);
        drawGradientRect(right * 2, top * 2, right * 2 + 1, bottom * 2 - 1, color, color2);
        drawGradientRect(left * 2, bottom * 2 - 1, right * 2, bottom * 2, color, color2);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glScalef(2F, 2F, 2F);
    }


    public static void drawSmoothRectBetter(float x, float y, float width, float height, int color) {
        drawSmoothRect(x, y, x + width, y + height, color);
    }

    public static void drawRect(double left, double top, double right, double bottom, int color) {
        if (left < right)
        {
            double i = left;
            left = right;
            right = i;
        }

        if (top < bottom)
        {
            double j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f, f1, f2, f3);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos((double)left, (double)bottom, 0.0D).endVertex();
        bufferbuilder.pos((double)right, (double)bottom, 0.0D).endVertex();
        bufferbuilder.pos((double)right, (double)top, 0.0D).endVertex();
        bufferbuilder.pos((double)left, (double)top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawSmoothRect(float left, float top, double right, double bottom, int color) {
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        RectHelper.drawRect(left, top, right, bottom, color);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        RectHelper.drawRect(left * 2.0f - 1.0f, top * 2.0f, left * 2.0f, bottom * 2.0 - 1.0, color);
        RectHelper.drawRect(left * 2.0f, top * 2.0f - 1.0f, right * 2.0, top * 2.0f, color);
        RectHelper.drawRect(right * 2.0, top * 2.0f, right * 2.0 + 1.0, bottom * 2.0 - 1.0, color);
        RectHelper.drawRect(left * 2.0f, bottom * 2.0 - 1.0, right * 2.0, bottom * 2.0, color);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }

    public static void drawGradientRect(double left, double top, double right, double bottom, int color, int color2) {
        float f = (float) (color >> 24 & 255) / 255.0F;
        float f1 = (float) (color >> 16 & 255) / 255.0F;
        float f2 = (float) (color >> 8 & 255) / 255.0F;
        float f3 = (float) (color & 255) / 255.0F;
        float f4 = (float) (color2 >> 24 & 255) / 255.0F;
        float f5 = (float) (color2 >> 16 & 255) / 255.0F;
        float f6 = (float) (color2 >> 8 & 255) / 255.0F;
        float f7 = (float) (color2 & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(left, top, gui.zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos(left, bottom, gui.zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos(right, bottom, gui.zLevel).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos(right, top, gui.zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawSkeetButton(float x, float y, float right, float bottom) {
        drawSmoothRect(x - 31.0f, y - 43.0f, right + 31.0f, bottom - 30.0f, new Color(0, 0, 0, 255).getRGB());
        drawSmoothRect(x - 30.5f, y - 42.5f, right + 30.5f, bottom - 30.5f, new Color(45, 45, 45, 255).getRGB());
        gui.drawGradientRect((int) x - 30, (int) y - 42, right + 30, bottom - 31, new Color(48, 48, 48, 255).getRGB(), new Color(19, 19, 19, 255).getRGB());
    }

    public static void drawSkeetRectWithoutBorder(float x, float y, float right, float bottom) {
        drawSmoothRect(x - 41f, y - 61f, right + 41f, bottom + 61f, new Color(48, 48, 48, 255).getRGB());
        drawSmoothRect(x - 40.0f, y - 60.0f, right + 40.0f, bottom + 60.0f, new Color(17, 17, 17, 255).getRGB());
    }

    public static void drawSkeetRect(float x, float y, float right, float bottom) {
        drawRect(x - 46.5f, y - 66.5f, right + 46.5f, bottom + 66.5f, new Color(0, 0, 0, 255).getRGB());
        drawRect(x - 46.0f, y - 66.0f, right + 46.0f, bottom + 66.0f, new Color(48, 48, 48, 255).getRGB());
        drawRect(x - 44.5f, y - 64.5f, right + 44.5f, bottom + 64.5f, new Color(33, 33, 33, 255).getRGB());
        drawRect(x - 43.5f, y - 63.5f, right + 43.5f, bottom + 63.5f, new Color(0, 0, 0, 255).getRGB());
        drawRect(x - 43.0f, y - 63.0f, right + 43.0f, bottom + 63.0f, new Color(9, 9, 9, 255).getRGB());
        drawRect(x - 40.5f, y - 60.5f, right + 40.5f, bottom + 60.5f, new Color(48, 48, 48, 255).getRGB());
        drawRect(x - 40.0f, y - 60.0f, right + 40.0f, bottom + 60.0f, new Color(17, 17, 17, 255).getRGB());
    }

    public static void drawBorderedRect(float left, float top, float right, float bottom, float borderWidth, int insideColor, int borderColor, boolean borderIncludedInBounds) {
        drawRect(left - (!borderIncludedInBounds ? borderWidth : 0), top - (!borderIncludedInBounds ? borderWidth : 0), right + (!borderIncludedInBounds ? borderWidth : 0), bottom + (!borderIncludedInBounds ? borderWidth : 0), borderColor);
        drawRect(left + (borderIncludedInBounds ? borderWidth : 0), top + (borderIncludedInBounds ? borderWidth : 0), right - ((borderIncludedInBounds ? borderWidth : 0)), bottom - ((borderIncludedInBounds ? borderWidth : 0)), insideColor);
    }

    public static void drawBorder(double left, double top, double right, double bottom, double borderWidth, int insideColor, int borderColor, boolean borderIncludedInBounds) {
        RectHelper.drawRect(left - (!borderIncludedInBounds ? borderWidth : 0.0), top - (!borderIncludedInBounds ? borderWidth : 0.0), right + (!borderIncludedInBounds ? borderWidth : 0.0), bottom + (!borderIncludedInBounds ? borderWidth : 0.0), borderColor);
        RectHelper.drawRect(left + (borderIncludedInBounds ? borderWidth : 0.0), top + (borderIncludedInBounds ? borderWidth : 0.0), right - (borderIncludedInBounds ? borderWidth : 0.0), bottom - (borderIncludedInBounds ? borderWidth : 0.0), insideColor);
    }

    public static void drawOutlineRect(float x, float y, float width, float height, Color color, Color colorTwo) {
        drawRect(x, y, x + width, y + height, color.getRGB());
        int colorRgb = colorTwo.getRGB();
        drawRect(x - 1, y, x, y + height, colorRgb);
        drawRect(x + width, y, x + width + 1, y + height, colorRgb);
        drawRect(x - 1, y - 1, x + width + 1, y, colorRgb);
        drawRect(x - 1, y + height, x + width + 1, y + height + 1, colorRgb);
    }
}
