package org.celestial.client.helpers.player;

import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.cubemob.MagmaCube;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.cubemob.Slime;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.feline.Ocelot;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.player.Player;
import org.celestial.client.Celestial;
import org.celestial.client.feature.impl.combat.AntiBot;
import org.celestial.client.feature.impl.combat.KillAura;
import org.celestial.client.friend.Friend;
import org.celestial.client.helpers.Helper;
import org.celestial.client.helpers.math.RotationHelper;
import org.celestial.client.helpers.world.EntityHelper;
import org.celestial.client.ui.notification.NotificationManager;
import org.celestial.client.ui.notification.NotificationType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KillAuraHelper implements Helper {

    public static boolean canAttack(LivingEntity player) {
        for (Friend friend : Celestial.instance.friendManager.getFriends()) {
            if (!player.getName().equals(friend.getName())) {
                continue;
            }
            return false;
        }
        if ((Celestial.instance.featureManager.getFeatureByClass(AntiBot.class).getState() && AntiBot.isBotPlayer.contains(player))) {
            return false;
        }
        if (KillAura.nakedPlayer.getCurrentValue() && EntityHelper.checkArmor(player)) {
            return false;
        }
        if (player instanceof Slime && !KillAura.monsters.getCurrentValue()) {
            return false;
        }
        if (player instanceof MagmaCube && !KillAura.monsters.getCurrentValue()) {
            return false;
        }
        if (player instanceof EnderDragon && !KillAura.monsters.getCurrentValue()) {
            return false;
        }
        if (player instanceof EnderMan && !KillAura.monsters.getCurrentValue()) {
            return false;
        }
        if (player.isInvisible() && !KillAura.invis.getCurrentValue()) {
            return false;
        }
        if (player instanceof Player || player instanceof Animal || player instanceof Monster || player instanceof Villager) {
            if (player instanceof Player && !KillAura.players.getCurrentValue()) {
                return false;
            }
            if (player instanceof Animal && !KillAura.animals.getCurrentValue()) {
                return false;
            }
            if (player instanceof Monster && !KillAura.monsters.getCurrentValue()) {
                return false;
            }
            if (player instanceof Villager && !KillAura.animals.getCurrentValue()) {
                return false;
            }
            if (player instanceof Ocelot && !KillAura.pets.getCurrentValue()) {
                return false;
            }
            if (player instanceof Wolf && !KillAura.pets.getCurrentValue()) {
                return false;
            }
        }
        if (!canSeeEntityAtFov(player, KillAura.fov.getCurrentValue() * 2)) {
            return false;
        }
        if (!range(player, KillAura.range.getCurrentValue())) {
            return false;
        }

        if (!player.canEntityBeSeen(mc.player)) {
            return KillAura.walls.getCurrentValue();
        }
        return player != mc.player;
    }

    public static boolean canSeeEntityAtFov(Entity entityLiving, float scope) {
        double diffX = entityLiving.posX - mc.player.posX;
        double diffZ = entityLiving.posZ - mc.player.posZ;
        float yaw = (float) (Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0);
        double difference = angleDifference(yaw, mc.player.rotationYaw);
        return difference <= scope;
    }

    public static double angleDifference(float oldYaw, float newYaw) {
        float yaw = Math.abs(oldYaw - newYaw) % 360;
        if (yaw > 180) {
            yaw = 360 - yaw;
        }
        return yaw;
    }

    private static boolean range(LivingEntity entity, float range) {
        return mc.player.getDistanceToEntity(entity) <= range;
    }

    public static void toggleOffChecks() {
        KillAura killAura = (KillAura) Celestial.instance.featureManager.getFeatureByClass(KillAura.class);
        if (mc.gui.screen() instanceof DeathScreen && !mc.player.isEntityAlive() || mc.player.ticksExisted <= 1 && KillAura.autoDisable.getCurrentValue()) {
            killAura.toggle();
            NotificationManager.publicity(killAura.getLabel(), "KillAura was toggled off!", 2, NotificationType.SUCCESS);
        }
    }

    public static LivingEntity getSortEntities() {
        List<LivingEntity> entity = new ArrayList<>();
        for (Entity e : mc.world.loadedEntityList) {
            if (e instanceof LivingEntity) {
                LivingEntity player = (LivingEntity) e;
                if (mc.player.getDistanceToEntity(player) < KillAura.range.getCurrentValue() && (canAttack(player))) {
                    if (player.getHealth() > 0) {
                        entity.add(player);
                    } else {
                        entity.remove(player);
                    }
                }
            }
        }

        String sortMode = KillAura.sort.getOptions();
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
        } else if (sortMode.equalsIgnoreCase("Blocking Status")) {
            if (KillAura.target != null) {
                entity.sort(KillAura.target.isBlocking() ? Comparator.comparing(LivingEntity::isBlocking) : Comparator.comparingDouble(mc.player::getDistanceToEntity));
            }
        }

        if (entity.isEmpty())
            return null;

        return entity.get(0);
    }
}
