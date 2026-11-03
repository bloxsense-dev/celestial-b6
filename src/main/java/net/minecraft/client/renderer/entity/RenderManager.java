package net.minecraft.client.renderer.entity;

import net.minecraft.world.entity.Entity;

public class RenderManager {
    public double renderPosX;
    public double renderPosY;
    public double renderPosZ;
    public float playerViewX;
    public float playerViewY;

    public void setPlayerViewY(float y) { this.playerViewY = y; }
    public void setRenderShadow(boolean renderShadow) {}
    public void doRenderEntity(Entity entity, double x, double y, double z, float yaw, float partialTicks, boolean p_188391_10_) {}
    public void renderEntityStatic(Entity entity, float partialTicks, boolean p_188388_3_) {}
}
