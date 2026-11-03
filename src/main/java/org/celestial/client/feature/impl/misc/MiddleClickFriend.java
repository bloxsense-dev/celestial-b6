package org.celestial.client.feature.impl.misc;

import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.LivingEntity;
import org.celestial.client.Celestial;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.input.EventMouse;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.friend.Friend;
import org.celestial.client.helpers.misc.ChatHelper;
import org.celestial.client.ui.notification.NotificationManager;
import org.celestial.client.ui.notification.NotificationType;

public class MiddleClickFriend extends Feature {

    public MiddleClickFriend() {
        super("MiddleClickFriend", "Добавляет игрока в френд лист при нажатии на кнопку мыши", Type.Misc);
    }

    @EventTarget
    public void onMouseEvent(EventMouse event) {
        if (event.getKey() == 2 && mc.pointedEntity instanceof LivingEntity) {
            String targetName = mc.pointedEntity.getName().getString();
            if (Celestial.instance.friendManager.getFriends().stream().anyMatch(friend -> friend.getName().equals(targetName))) {
                Celestial.instance.friendManager.getFriends().remove(Celestial.instance.friendManager.getFriend(targetName));
                ChatHelper.addChatMessage(ChatFormatting.RED + "Removed " + ChatFormatting.RESET + "'" + targetName + "'" + " as Friend!");
                NotificationManager.publicity("MCF", "Removed " + "'" + targetName + "'" + " as Friend!", 4, NotificationType.INFO);
            } else {
                Celestial.instance.friendManager.addFriend(new Friend(targetName));
                ChatHelper.addChatMessage(ChatFormatting.GREEN + "Added " + ChatFormatting.RESET + targetName + " as Friend!");
                NotificationManager.publicity("MCF", "Added " + targetName + " as Friend!", 4, NotificationType.SUCCESS);
            }
        }
    }
}