package org.celestial.client.feature.impl.combat;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.util.math.BlockPos;
import org.celestial.client.Celestial;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.player.EventPreMotion;
import org.celestial.client.event.events.impl.player.EventUpdate;
import org.celestial.client.event.events.impl.render.EventRender3D;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.helpers.render.RenderHelper;
import org.celestial.client.settings.impl.BooleanSetting;
import org.celestial.client.settings.impl.NumberSetting;

import java.awt.*;

public class TargetStrafe extends Feature {

    public static NumberSetting range;
    public static NumberSetting spd;
    public static NumberSetting boostValue;
    public static NumberSetting boostTicks;
    public static NumberSetting speedIfUsing;
    public static BooleanSetting reversed;
    public static NumberSetting reversedDistance;
    public static BooleanSetting boost;
    public static BooleanSetting autoJump;
    public static BooleanSetting smartStrafe;
    public static BooleanSetting usingItemCheck;
    public static BooleanSetting autoShift;
    public BooleanSetting trajectory = new BooleanSetting("Trajectory", true, () -> true);
    public BooleanSetting me = new BooleanSetting("Me", true, () -> trajectory.getCurrentValue());
    public NumberSetting width = new NumberSetting("Width", 2, 1, 6, 0.1F, () -> trajectory.getCurrentValue());
    private float wrap = 0F;
    private boolean switchDir = true;

    public TargetStrafe() {
        super("TargetStrafe", "Стрейфит вокруг сущностей", Type.Movement);
        spd = new NumberSetting("Strafe Speed", 0.23F, 0.1F, 2, 0.01F, () -> true);
        range = new NumberSetting("Strafe Distance", 2.4F, 0.1F, 6, 0.1F, () -> true);
        boost = new BooleanSetting("DamageBoost", false, () -> true);
        boostValue = new NumberSetting("Boost Value", 0.5F, 0.1F, 4, 0.01F, boost::getCurrentValue);
        boostTicks = new NumberSetting("Boost Ticks", 8, 0, 9, 1, boost::getCurrentValue);
        reversed = new BooleanSetting("Reversed", false, () -> true);
        reversedDistance = new NumberSetting("Reversed Distance", 3, 1, 6, 0.1F, () -> reversed.getCurrentValue());
        autoJump = new BooleanSetting("AutoJump", true, () -> true);
        autoShift = new BooleanSetting("AutoShift", false, () -> true);
        smartStrafe = new BooleanSetting("Smart Strafe", true, () -> true);
        usingItemCheck = new BooleanSetting("Using Item Check", false, () -> true);
        speedIfUsing = new NumberSetting("Speed if using", 0.1F, 0.1F, 2, 0.01F, usingItemCheck::getCurrentValue);
        addSettings(boost, boostTicks, boostValue, trajectory, me, width, reversed, reversedDistance, autoJump, autoShift, usingItemCheck, speedIfUsing, smartStrafe, spd, range);
    }

    @Override
    public void onEnable() {
        this.wrap = 0F;
        this.switchDir = true;
        super.onEnable();
    }

    public boolean needToSwitch(double x, double z) {
        if (mc.player.isCollidedHorizontally || mc.gameSettings.keyBindLeft.isPressed() || mc.gameSettings.keyBindRight.isPressed()) {
            return true;
        }
        for (int i = (int) (mc.player.posY + 4); i >= 0; --i) {
            BlockPos playerPos;
            blockFIRE:
            {
                blockLAVA:
                {
                    playerPos = new BlockPos(x, i, z);
                    if (mc.world.getBlockState(playerPos).getBlock().equals(Blocks.LAVA)) break blockLAVA;
                    if (!mc.world.getBlockState(playerPos).getBlock().equals(Blocks.FIRE)) break blockFIRE;
                }
                return true;
            }
            if (mc.world.isAirBlock(playerPos)) continue;
            return false;
        }
        return true;
    }

    private float toDegree(double x, double z) {
        return (float) (Math.atan2(z - mc.player.posZ, x - mc.player.posX) * 180.0 / Math.PI) - 90F;
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (KillAura.target == null)
            return;
        if (mc.player.getDistanceToEntity(KillAura.target) <= KillAura.range.getCurrentValue()) {
            if (autoShift.getCurrentValue()) {
                mc.gameSettings.keyBindSneak.setPressed(this.getState() && KillAura.target != null && mc.player.fallDistance > KillAura.critFallDistance.getCurrentValue() + 0.1);
            }
        }
    }

    @EventTarget
    public void onRender3D(EventRender3D event) {
        if (trajectory.getCurrentValue() && KillAura.target != null) {
            RenderHelper.drawCircle3D(KillAura.target, range.getCurrentValue() - 0.00625, event.getPartialTicks(), 15, width.getCurrentValue(), new Color(0, 0, 0).getRGB());
            RenderHelper.drawCircle3D(KillAura.target, range.getCurrentValue() + 0.00625, event.getPartialTicks(), 15, width.getCurrentValue(), new Color(0, 0, 0).getRGB());
            RenderHelper.drawCircle3D(KillAura.target, range.getCurrentValue(), event.getPartialTicks(), 15, 2, -1);
        }
    }

    @EventTarget
    public void onPreMotion(EventPreMotion event) {
        if (KillAura.target == null)
            return;
        this.setSuffix("" + range.getCurrentValue());
        if (mc.player.getDistanceToEntity(KillAura.target) <= KillAura.range.getCurrentValue()) {
            if (KillAura.target.getHealth() > 0) {
                if (autoJump.getCurrentValue() && Celestial.instance.featureManager.getFeatureByClass(KillAura.class).getState() && Celestial.instance.featureManager.getFeatureByClass(TargetStrafe.class).getState()) {
                    if (mc.player.onGround) {
                        mc.player.jump();
                    }
                }
            }
            if (KillAura.target.getHealth() > 0) {
                LivingEntity target = KillAura.target;
                if (target == null || mc.player.ticksExisted < 20)
                    return;
                float speed = mc.player.hurtTime > boostTicks.getCurrentValue() && boost.getCurrentValue() && !mc.player.onGround ? boostValue.getCurrentValue() : (mc.player.isUsingItem() || mc.gameSettings.keyBindUseItem.isKeyDown()) && usingItemCheck.getCurrentValue() ? speedIfUsing.getCurrentValue() : spd.getCurrentValue();
                this.wrap = (float) Math.atan2((mc.player.posZ - target.posZ), (mc.player.posX - target.posX));
                this.wrap += this.switchDir ? speed / mc.player.getDistanceToEntity(target) : -(speed / mc.player.getDistanceToEntity(target));
                double x = target.posX + range.getCurrentValue() * Math.cos(this.wrap);
                double z = (target.posZ + range.getCurrentValue() * Math.sin(this.wrap));
                if (smartStrafe.getCurrentValue() && this.needToSwitch(x, z)) {
                    this.switchDir = !this.switchDir;
                    this.wrap += 2 * (this.switchDir ? speed / mc.player.getDistanceToEntity(target) : -(speed / mc.player.getDistanceToEntity(target)));
                    x = target.posX + range.getCurrentValue() * Math.cos(this.wrap);
                    z = target.posZ + range.getCurrentValue() * Math.sin(this.wrap);
                }
                if (mc.player.hurtTime > boostTicks.getCurrentValue() && boost.getCurrentValue() && !mc.player.onGround) {
                    mc.player.jumpMovementFactor *= 60;
                }
                float searchValue = Celestial.instance.featureManager.getFeatureByClass(TargetStrafe.class).getState() && reversed.getCurrentValue() && mc.player.getDistanceToEntity(KillAura.target) < reversedDistance.getCurrentValue() ? -90 : 0;
                float reversedValue = (!mc.gameSettings.keyBindLeft.isKeyDown() && !mc.gameSettings.keyBindRight.isKeyDown() && !mc.player.isCollidedHorizontally ? searchValue : 0);
                mc.player.motionX = speed * -Math.sin((float) Math.toRadians(toDegree(x + reversedValue, z + reversedValue)));
                mc.player.motionZ = speed * Math.cos((float) Math.toRadians(toDegree(x + reversedValue, z + reversedValue)));
            }
        }
    }
}