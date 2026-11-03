package org.celestial.client.helpers.misc;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.celestial.client.helpers.Helper;

public class ChatHelper implements Helper {

    public static String chatPrefix = "\u00a77[" + ChatFormatting.LIGHT_PURPLE + "Celestial" + ChatFormatting.RESET + "\u00a77] " + ChatFormatting.RESET;

    public static void addChatMessage(String message) {
        if (mc.player != null) {
            mc.player.displayClientMessage(Component.literal(chatPrefix + message), false);
        }
    }
}
