package org.celestial.client.helpers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.protocol.Packet;
import org.celestial.client.helpers.misc.TimerHelper;

import java.util.Random;

public interface Helper {

    Minecraft mc = Minecraft.getMinecraft();
    net.minecraft.client.gui.Gui gui = new net.minecraft.client.gui.Gui();
    Random random = new Random();
    TimerHelper timerHelper = new TimerHelper();
    ScaledResolution sr = new ScaledResolution(mc);

    default void sendPacket(Packet<?> packet) {
        if (mc != null && mc.player != null && mc.player.connection != null) {
            mc.player.connection.send(packet);
        }
    }
}
