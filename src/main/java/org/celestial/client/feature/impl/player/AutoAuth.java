package org.celestial.client.feature.impl.player;

import net.minecraft.ChatFormatting;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.packet.EventReceiveMessage;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.helpers.misc.ChatHelper;
import org.celestial.client.ui.notification.NotificationManager;
import org.celestial.client.ui.notification.NotificationType;

public class AutoAuth extends Feature {

    public static String password = "qwerty123";

    public AutoAuth() {
        super("AutoAuth", "Автоматически регестрируется и логинится на серверах", Type.Player);
    }

    @EventTarget
    public void onReceiveMessage(EventReceiveMessage event) {
        if (event.getMessage().contains("/reg") || event.getMessage().contains("/register") || event.getMessage().contains("Зарегистрируйтесь")) {
            mc.player.sendChatMessage("/reg " + password + " " + password);
            ChatHelper.addChatMessage("Your password: " + ChatFormatting.RED + password);
            NotificationManager.publicity("AutoAuth", "You are successfully registered!", 4, NotificationType.SUCCESS);
        } else if (event.getMessage().contains("Авторизуйтесь") || event.getMessage().contains("/l")) {
            mc.player.sendChatMessage("/login " + password);
            NotificationManager.publicity("AutoAuth", "You are successfully login!", 4, NotificationType.SUCCESS);
        }
    }
}
