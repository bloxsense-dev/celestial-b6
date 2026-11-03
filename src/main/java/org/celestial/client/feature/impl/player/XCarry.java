package org.celestial.client.feature.impl.player;

import net.minecraft.network.play.client.CPacketCloseWindow;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.packet.EventSendPacket;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;

public class XCarry extends Feature {

    public XCarry() {
        super("XCarry", "Позволяет хранить предметы в слотах для крафта", Type.Player);
    }

    @EventTarget
    public void onSendPacket(EventSendPacket event) {
        if (event.getPacket() instanceof CPacketCloseWindow) {
            event.setCancelled(true);
        }
    }
}
