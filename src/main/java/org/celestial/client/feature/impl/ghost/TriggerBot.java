package org.celestial.client.feature.impl.ghost;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.util.EnumHand;
import org.celestial.client.Celestial;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.player.EventUpdate;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.friend.Friend;
import org.celestial.client.helpers.player.MovementHelper;
import org.celestial.client.helpers.world.EntityHelper;
import org.celestial.client.settings.impl.BooleanSetting;
import org.celestial.client.settings.impl.NumberSetting;

public class TriggerBot extends Feature {

    public static NumberSetting range;
    public static BooleanSetting players;
    public static BooleanSetting mobs;
    public static BooleanSetting team;
    public static BooleanSetting onlyCrit = new BooleanSetting("Only Crits", false, () -> true);
    public static NumberSetting critFallDist = new NumberSetting("Fall Distance", 0.2F, 0.08F, 1, 0.01f, () -> onlyCrit.getCurrentValue());

    public TriggerBot() {
        super("TriggerBot", "Автоматически наносит удар при наводке на сущность", Type.Ghost);
        players = new BooleanSetting("Players", true, () -> true);
        mobs = new BooleanSetting("Mobs", false, () -> true);
        team = new BooleanSetting("Team Check", false, () -> true);
        range = new NumberSetting("Trigger Range", 4, 1, 6, 0.1f, () -> true);
        addSettings(range, players, mobs, team, onlyCrit, critFallDist);
    }

    public static boolean canTrigger(LivingEntity player) {
        for (Friend friend : Celestial.instance.friendManager.getFriends()) {
            if (!player.getName().equals(friend.getName())) {
                continue;
            }
            return false;
        }

        if (team.getCurrentValue() && EntityHelper.isTeamWithYou(player)) {
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
        return player != mc.player;
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        Entity entity = mc.objectMouseOver.entityHit;
        if (entity == null || mc.player.getDistanceToEntity(entity) > range.getCurrentValue() || entity instanceof EndCrystal || entity.isDead || ((LivingEntity) entity).getHealth() <= 0.0f) {
            return;
        }

        if (MovementHelper.isBlockAboveHead()) {
            if (!(mc.player.fallDistance >= critFallDist.getCurrentValue()) && mc.player.getCooledAttackStrength(0.8F) == 1 && onlyCrit.getCurrentValue() && !mc.player.isOnLadder() && !mc.player.isInLiquid() && !mc.player.isInWeb && mc.player.getRidingEntity() == null) {
                return;
            }
        } else {
            if (mc.player.fallDistance != 0 && onlyCrit.getCurrentValue() && !mc.player.isOnLadder() && !mc.player.isInLiquid() && !mc.player.isInWeb && mc.player.getRidingEntity() == null) {
                return;
            }
        }

        if (canTrigger((LivingEntity) entity)) {
            if (mc.player.getCooledAttackStrength(0) == 1) {
                mc.playerController.attackEntity(mc.player, entity);
                mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        }
    }
}
