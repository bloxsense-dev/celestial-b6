package org.celestial.client.feature.impl.player;

import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.Entity;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.world.entity.player.Player;
import org.celestial.client.Celestial;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.player.EventUpdate;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.helpers.misc.ChatHelper;

public class PearlLogger extends Feature {

    private boolean canSend;

    public PearlLogger() {
        super("PearlLogger", "Показывает координаты эндер-перла игроков", Type.Player);
    }

    @Override
    public void onEnable() {
        canSend = true;
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (mc.world == null || mc.player == null)
            return;
        Entity enderPearl = null;
        for (Entity e : mc.world.loadedEntityList) {
            if (e instanceof EntityEnderPearl) {
                enderPearl = e;
                break;
            }
        }
        if (enderPearl == null) {
            canSend = true;
            return;
        }
        Player throwerEntity = null;
        for (Player entity : mc.world.playerEntities) {
            if (throwerEntity != null) {
                if (throwerEntity.getDistanceToEntity(enderPearl) <= entity.getDistanceToEntity(enderPearl)) {
                    continue;
                }
            }
            throwerEntity = entity;
        }
        String facing = enderPearl.getHorizontalFacing().toString();
        if (facing.equals("west")) {
            facing = "east";
        } else if (facing.equals("east")) {
            facing = "west";
        }
        if (throwerEntity == mc.player) {
            return;
        }
        String pos = ChatFormatting.GOLD + facing + ChatFormatting.WHITE + " | " + ChatFormatting.LIGHT_PURPLE + enderPearl.getPosition().getX() + " " + enderPearl.getPosition().getY() + " " + enderPearl.getPosition().getZ();
        if (throwerEntity != null && canSend) {
            String throwerName = throwerEntity.getName().getString();
            ChatHelper.addChatMessage(Celestial.instance.friendManager.isFriend(throwerName) ? ChatFormatting.GREEN + throwerName + ChatFormatting.WHITE + " thrown pearl on " + pos : ChatFormatting.RED + throwerName + ChatFormatting.WHITE + " thrown pearl on " + pos);
            canSend = false;
        }
    }
}
