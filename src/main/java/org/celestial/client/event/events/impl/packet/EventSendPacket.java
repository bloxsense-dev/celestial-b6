package org.celestial.client.event.events.impl.packet;

import net.minecraft.network.protocol.Packet;
import org.celestial.client.event.events.callables.EventCancellable;

public class EventSendPacket extends EventCancellable {

    private final Packet<?> packet;

    public EventSendPacket(Packet<?> packet) {
        this.packet = packet;
    }

    @SuppressWarnings("unchecked")
    public <T> T getPacket() {
        return (T) this.packet;
    }
}
