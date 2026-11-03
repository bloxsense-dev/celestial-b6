package org.celestial.client.feature.impl.misc;

import net.minecraft.network.play.client.CPacketConfirmTeleport;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.packet.EventSendPacket;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.settings.impl.BooleanSetting;

public class PortalFeatures extends Feature {

    public static BooleanSetting chat = new BooleanSetting("Chat", true, () -> true);
    public static BooleanSetting cancelTeleport = new BooleanSetting("Cancel Teleport", true, () -> true);

    public PortalFeatures() {
        super("PortalFeatures", "Позволяет открыть чат в портале", Type.Misc);
        addSettings(chat, cancelTeleport);
    }

    @EventTarget
    public void onSendPacket(EventSendPacket event) {
        if (event.getPacket() instanceof CPacketConfirmTeleport && cancelTeleport.getCurrentValue()) {
            event.setCancelled(true);
        }
    }
}
