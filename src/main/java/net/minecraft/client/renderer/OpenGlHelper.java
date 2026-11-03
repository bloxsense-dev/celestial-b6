package net.minecraft.client.renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class OpenGlHelper {
    public static int lightmapTexUnit = org.lwjgl.opengl.GL13.GL_TEXTURE1;
    public static int defaultTexUnit = org.lwjgl.opengl.GL13.GL_TEXTURE0;

    public static void glBlendFunc(int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
        com.mojang.blaze3d.opengl.GlStateManager._blendFuncSeparate(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
    }

    public static void setLightmapTextureCoords(int target, float x, float y) {
    }
}
