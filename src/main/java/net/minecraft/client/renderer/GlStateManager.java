package net.minecraft.client.renderer;

import org.lwjgl.opengl.GL11;
import com.mojang.blaze3d.opengl.GlStateManager.SourceFactor;
import com.mojang.blaze3d.opengl.GlStateManager.DestFactor;

public class GlStateManager {
    public static void color(float red, float green, float blue, float alpha) {
        GL11.glColor4f(red, green, blue, alpha);
    }

    public static void color(float red, float green, float blue) {
        GL11.glColor3f(red, green, blue);
    }

    public static void resetColor() {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void pushMatrix() {
        GL11.glPushMatrix();
    }

    public static void popMatrix() {
        GL11.glPopMatrix();
    }

    public static void pushAttrib() {
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
    }

    public static void popAttrib() {
        GL11.glPopAttrib();
    }

    public static void scale(double x, double y, double z) {
        GL11.glScaled(x, y, z);
    }

    public static void scale(float x, float y, float z) {
        GL11.glScalef(x, y, z);
    }

    public static void translate(double x, double y, double z) {
        GL11.glTranslated(x, y, z);
    }

    public static void translate(float x, float y, float z) {
        GL11.glTranslatef(x, y, z);
    }

    public static void rotate(float angle, float x, float y, float z) {
        GL11.glRotatef(angle, x, y, z);
    }

    public static void enableBlend() {
        GL11.glEnable(GL11.GL_BLEND);
    }

    public static void disableBlend() {
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void blendFunc(int srcFactor, int dstFactor) {
        GL11.glBlendFunc(srcFactor, dstFactor);
    }

    public static void blendFunc(SourceFactor srcFactor, DestFactor dstFactor) {
        GL11.glBlendFunc(srcFactor.factor, dstFactor.factor);
    }

    public static void tryBlendFuncSeparate(int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
        com.mojang.blaze3d.opengl.GlStateManager._blendFuncSeparate(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
    }

    public static void tryBlendFuncSeparate(SourceFactor srcFactor, DestFactor dstFactor, SourceFactor srcFactorAlpha, DestFactor dstFactorAlpha) {
        com.mojang.blaze3d.opengl.GlStateManager._blendFuncSeparate(srcFactor.factor, dstFactor.factor, srcFactorAlpha.factor, dstFactorAlpha.factor);
    }

    public static void enableTexture2D() {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public static void disableTexture2D() {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    public static void bindTexture(int texture) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
    }

    public static void enableAlpha() {
        com.mojang.blaze3d.opengl.GlStateManager.enableAlphaTest();
    }

    public static void disableAlpha() {
        com.mojang.blaze3d.opengl.GlStateManager.disableAlphaTest();
    }

    public static void enableDepth() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public static void disableDepth() {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
    }

    public static void depthMask(boolean flag) {
        GL11.glDepthMask(flag);
    }

    public static void shadeModel(int mode) {
        try {
            var caps = org.lwjgl.opengl.GL.getCapabilities();
            if (caps != null && caps.glShadeModel != 0) {
                GL11.glShadeModel(mode);
            }
        } catch (Throwable ignored) {}
    }

    public static void enableCull() {
        GL11.glEnable(GL11.GL_CULL_FACE);
    }

    public static void disableCull() {
        GL11.glDisable(GL11.GL_CULL_FACE);
    }

    public static void enableLighting() {
        GL11.glEnable(GL11.GL_LIGHTING);
    }

    public static void disableLighting() {
        GL11.glDisable(GL11.GL_LIGHTING);
    }

    public enum SourceFactor {
        CONSTANT_ALPHA(32771),
        CONSTANT_COLOR(32769),
        DST_ALPHA(772),
        DST_COLOR(774),
        ONE(1),
        ONE_MINUS_CONSTANT_ALPHA(32772),
        ONE_MINUS_CONSTANT_COLOR(32770),
        ONE_MINUS_DST_ALPHA(773),
        ONE_MINUS_DST_COLOR(775),
        ONE_MINUS_SRC_ALPHA(771),
        ONE_MINUS_SRC_COLOR(769),
        SRC_ALPHA(770),
        SRC_ALPHA_SATURATE(776),
        SRC_COLOR(768),
        ZERO(0);

        public final int factor;

        SourceFactor(int factorIn) {
            this.factor = factorIn;
        }
    }

    public enum DestFactor {
        CONSTANT_ALPHA(32771),
        CONSTANT_COLOR(32769),
        DST_ALPHA(772),
        DST_COLOR(774),
        ONE(1),
        ONE_MINUS_CONSTANT_ALPHA(32772),
        ONE_MINUS_CONSTANT_COLOR(32770),
        ONE_MINUS_DST_ALPHA(773),
        ONE_MINUS_DST_COLOR(775),
        ONE_MINUS_SRC_ALPHA(771),
        ONE_MINUS_SRC_COLOR(769),
        SRC_ALPHA(770),
        SRC_COLOR(768),
        ZERO(0);

        public final int factor;

        DestFactor(int factorIn) {
            this.factor = factorIn;
        }
    }

    public static void enableColorLogic() {
        GL11.glEnable(GL11.GL_COLOR_LOGIC_OP);
    }

    public static void disableColorLogic() {
        GL11.glDisable(GL11.GL_COLOR_LOGIC_OP);
    }

    public static void colorLogicOp(LogicOp op) {
        GL11.glLogicOp(op.opcode);
    }

    public enum LogicOp {
        CLEAR(5376),
        AND(5377),
        AND_REVERSE(5378),
        COPY(5379),
        AND_INVERTED(5380),
        NOOP(5381),
        XOR(5382),
        OR(5383),
        NOR(5384),
        EQUIV(5385),
        INVERT(5386),
        OR_REVERSE(5387),
        COPY_INVERTED(5388),
        OR_INVERTED(5389),
        NAND(5390),
        SET(5391);

        public final int opcode;

        LogicOp(int opcodeIn) {
            this.opcode = opcodeIn;
        }
    }

    public static void enable(int cap) {
        GL11.glEnable(cap);
    }
    public static void disable(int cap) {
        GL11.glDisable(cap);
    }
    public static void hint(int target, int mode) {
        GL11.glHint(target, mode);
    }
    public static void glLineWidth(float width) {
        GL11.glLineWidth(width);
    }
    public static void glLineWidth(int width) {
        GL11.glLineWidth((float) width);
    }
    public static void glBegin(int mode) {
        GL11.glBegin(mode);
    }
    public static void glEnd() {
        GL11.glEnd();
    }
    public static void glVertex2f(float x, float y) {
        GL11.glVertex2f(x, y);
    }
    public static void glVertex2f(int x, float y) {
        GL11.glVertex2f((float) x, y);
    }
    public static void glVertex3d(double x, double y, double z) {
        GL11.glVertex3d(x, y, z);
    }
    public static void alphaFunc(int func, float ref) {
        try {
            var caps = org.lwjgl.opengl.GL.getCapabilities();
            if (caps != null && caps.glAlphaFunc != 0) {
                GL11.glAlphaFunc(func, ref);
            }
        } catch (Throwable ignored) {}
    }
    public static void enableColorMaterial() {
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
    }
    public static void disableRescaleNormal() {
        GL11.glDisable(0x803A);
    }
    public static void setActiveTexture(int texture) {
        org.lwjgl.opengl.GL13.glActiveTexture(texture);
    }
}
