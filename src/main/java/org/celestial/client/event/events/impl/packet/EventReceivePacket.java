package org.celestial.client.event.events.impl.packet;

import net.minecraft.network.protocol.Packet;
import org.celestial.client.event.events.callables.EventCancellable;

public class EventReceivePacket extends EventCancellable {

    private Packet<?> packet;

    public EventReceivePacket(Packet<?> packet) {
        this.packet = packet;
    }

    @SuppressWarnings("unchecked")
    public <T> T getPacket() {
        return (T) this.packet;
    }

    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }
}
