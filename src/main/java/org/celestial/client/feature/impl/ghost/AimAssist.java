package org.celestial.client.feature.impl.ghost;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.util.math.MathHelper;
import org.celestial.client.Celestial;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.player.EventPreMotion;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.friend.Friend;
import org.celestial.client.helpers.math.GCDCalcHelper;
import org.celestial.client.helpers.math.MathematicHelper;
import org.celestial.client.helpers.math.RotationHelper;
import org.celestial.client.helpers.world.EntityHelper;
import org.celestial.client.settings.impl.BooleanSetting;
import org.celestial.client.settings.impl.ListSetting;
import org.celestial.client.settings.impl.NumberSetting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AimAssist extends Feature {

    public static NumberSetting range;
    public static BooleanSetting players;
    public static BooleanSetting mobs;
    public static BooleanSetting team;
    public static BooleanSetting walls;
    public static BooleanSetting invis;
    public static BooleanSetting click;
    public static NumberSetting fov;
    public static NumberSetting predict;
    public static NumberSetting rotYawSpeed;
    public static NumberSetting rotPitchSpeed;
    public static NumberSetting rotYawRandom;
    public static NumberSetting rotPitchRandom;
    public static ListSetting sort;
    public static ListSetting part;

    public AimAssist() {
        super("AimAssist", "Автоматический аим на сущностей вокруг тебя", Type.Ghost);
        sort = new ListSetting("AssistSort Mode", "Distance", () -> true, "Distance", "Higher Armor", "Lowest Armor", "Health", "Angle", "HurtTime");
        part = new ListSetting("Aim-Part Mode", "Chest", () -> true, "Chest", "Head", "Leggings", "Boots");
        range = new NumberSetting("Range", 4, 1, 10, 0.1F, () -> true);
        players = new BooleanSetting("Players", true, () -> true);
        mobs = new BooleanSetting("Mobs", false, () -> true);
        team = new BooleanSetting("Team Check", false, () -> true);
        walls = new BooleanSetting("Walls", false, () -> true);
        invis = new BooleanSetting("Invisible", false, () -> true);
        click = new BooleanSetting("Click Only", false, () -> true);
        predict = new NumberSetting("Aim Predict", 0.5f, 0.5f, 5, 0.1f, () -> true);
        fov = new NumberSetting("Assist FOV", 180, 5, 180, 5, () -> true);
        rotYawSpeed = new NumberSetting("Rotation Yaw Speed", 1, 0.1F, 5, 0.1F, () -> true);
        rotPitchSpeed = new NumberSetting("Rotation Pitch Speed", 1, 0.1F, 5, 0.1F, () -> true);
        rotYawRandom = new NumberSetting("Yaw Randomize", 1, 0, 3, 0.1F, () -> true);
        rotPitchRandom = new NumberSetting("Pitch Randomize", 1, 0, 3, 0.1F, () -> true);
        addSettings(range, players, mobs, walls, invis, team, click, predict, fov, rotYawSpeed, rotPitchSpeed, rotPitchRandom, rotYawRandom, sort, part);
    }

    public static boolean canSeeEntityAtFov(Entity entityIn, float scope) {
        double diffX = entityIn.posX - mc.player.posX;
        double diffZ = entityIn.posZ - mc.player.posZ;
        float newYaw = (float) (Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0);
        double difference = angleDifference(newYaw, mc.player.rotationYaw);
        return difference <= scope;
    }

    public static double angleDifference(double a, double b) {
        float yaw360 = (float) (Math.abs(a - b) % 360.0);
        if (yaw360 > 180.0f) {
            yaw360 = 360.0f - yaw360;
        }
        return yaw360;
    }

    public static LivingEntity getSortEntities() {
        List<LivingEntity> entity = new ArrayList<>();
        for (Entity e : mc.world.loadedEntityList) {
            if (e instanceof LivingEntity) {
                LivingEntity player = (LivingEntity) e;
                if (mc.player.getDistanceToEntity(player) < range.getCurrentValue() && (canAssist(player))) {
                    if (player.getHealth() > 0) {
                        entity.add(player);
                    } else {
                        entity.remove(player);
                    }
                }
            }
        }

        String sortMode = sort.getOptions();

        if (sortMode.equalsIgnoreCase("Angle")) {
            entity.sort(((o1, o2) -> (int) (Math.abs(RotationHelper.getAngleEntity(o1) - mc.player.rotationYaw) - Math.abs(RotationHelper.getAngleEntity(o2) - mc.player.rotationYaw))));
        } else if (sortMode.equalsIgnoreCase("Higher Armor")) {
            entity.sort(Comparator.comparing(LivingEntity::getTotalArmorValue).reversed());
        } else if (sortMode.equalsIgnoreCase("Lowest Armor")) {
            entity.sort(Comparator.comparing(LivingEntity::getTotalArmorValue));
        } else if (sortMode.equalsIgnoreCase("Health")) {
            entity.sort((o1, o2) -> (int) (o1.getHealth() - o2.getHealth()));
        } else if (sortMode.equalsIgnoreCase("Distance")) {
            entity.sort(Comparator.comparingDouble(mc.player::getDistanceToEntity));
        } else if (sortMode.equalsIgnoreCase("HurtTime")) {
            entity.sort(Comparator.comparing(LivingEntity::getHurtTime).reversed());
        }

        if (entity.isEmpty())
            return null;

        return entity.get(0);
    }

    public static boolean canAssist(LivingEntity player) {
        for (Friend friend : Celestial.instance.friendManager.getFriends()) {
            if (!player.getName().equals(friend.getName())) {
                continue;
            }
            return false;
        }

        if (player instanceof Player || player instanceof Animal || player instanceof Monster || player instanceof Villager) {
            if (player instanceof Player && !players.getCurrentValue()) {
                return false;
            }
            if (player instanceof Animal && !mobs.getCurrentValue()) {
                return false;
            }
            if (player instanceof Monster && !mobs.getCurrentValue()) {
                return false;
            }
            if (player instanceof Villager && !mobs.getCurrentValue()) {
                return false;
            }
        }
        if (player.isInvisible() && !invis.getCurrentValue()) {
            return false;
        }

        if (!canSeeEntityAtFov(player, fov.getCurrentValue() * 2)) {
            return false;
        }
        if (team.getCurrentValue() && EntityHelper.isTeamWithYou(player)) {
            return false;
        }
        if (!player.canEntityBeSeen(mc.player)) {
            return walls.getCurrentValue();
        }
        return player != mc.player;
    }

    @EventTarget
    public void onPreMotion(EventPreMotion event) {
        LivingEntity entity = getSortEntities();
        if (entity != null) {
            if (mc.player.getDistanceToEntity(entity) <= range.getCurrentValue()) {
                if (entity != mc.player) {
                    float[] rots = getRotationsForAssist(entity);

                    if (click.getCurrentValue() && !(mc.gameSettings.keyBindAttack.isKeyDown()))
                        return;

                    if (canAssist(entity) && entity.getHealth() > 0) {
                        mc.player.rotationYaw = rots[0];
                        mc.player.rotationPitch = rots[1];
                    }
                }
            }
        }
    }

    private float[] getRotationsForAssist(LivingEntity entityIn) {
        float yaw = RotationHelper.updateRotation(GCDCalcHelper.getFixedRotation(mc.player.rotationYaw + MathematicHelper.randomizeFloat(-rotYawRandom.getCurrentValue(), rotYawRandom.getCurrentValue())), getRotation(entityIn, predict.getCurrentValue())[0], rotYawSpeed.getCurrentValue() * 10);
        float pitch = RotationHelper.updateRotation(GCDCalcHelper.getFixedRotation(mc.player.rotationPitch + MathematicHelper.randomizeFloat(-rotPitchRandom.getCurrentValue(), rotPitchRandom.getCurrentValue())), getRotation(entityIn, predict.getCurrentValue())[1], rotPitchSpeed.getCurrentValue() * 10);
        return new float[]{yaw, pitch};
    }

    private float[] getRotation(Entity e, float predictValue) {

        String mode = part.getOptions();
        float aimPoint = 0;
        if (mode.equalsIgnoreCase("Head")) {
            aimPoint = 0f;
        } else if (mode.equalsIgnoreCase("Chest")) {
            aimPoint = 0.5f;
        } else if (mode.equalsIgnoreCase("Leggings")) {
            aimPoint = 0.9f;
        } else if (mode.equalsIgnoreCase("Boots")) {
            aimPoint = 1.3f;
        }

        double xDelta = e.posX + (e.posX - e.prevPosX) * predictValue - mc.player.posX - mc.player.motionX * predictValue;
        double zDelta = e.posZ + (e.posZ - e.prevPosZ) * predictValue - mc.player.posZ - mc.player.motionZ * predictValue;
        double diffY = e.posY + e.getEyeHeight() - (mc.player.posY + mc.player.getEyeHeight() + aimPoint);

        double distance = MathHelper.sqrt(xDelta * xDelta + zDelta * zDelta);

        float yaw = (float) ((MathHelper.atan2(zDelta, xDelta) * 180.0D / Math.PI) - 90.0F) + MathematicHelper.randomizeFloat(-rotYawRandom.getCurrentValue(), rotYawRandom.getCurrentValue());
        float pitch = ((float) (-(MathHelper.atan2(diffY, distance) * 180.0D / Math.PI))) + MathematicHelper.randomizeFloat(-rotPitchRandom.getCurrentValue(), rotPitchRandom.getCurrentValue());

        yaw = (mc.player.rotationYaw + GCDCalcHelper.getFixedRotation(MathHelper.wrapDegrees(yaw - mc.player.rotationYaw)));
        pitch = mc.player.rotationPitch + GCDCalcHelper.getFixedRotation(MathHelper.wrapDegrees(pitch - mc.player.rotationPitch));
        pitch = MathHelper.clamp(pitch, -90F, 90F);
        return new float[]{yaw, pitch};
    }
}
