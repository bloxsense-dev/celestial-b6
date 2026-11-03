package org.celestial.client.event.events.impl.packet;

import net.minecraft.world.entity.Entity;
import org.celestial.client.event.events.callables.EventCancellable;

public class EventAttackClient extends EventCancellable {

    private final Entity entity;

    public EventAttackClient(Entity targetEntity) {
        this.entity = targetEntity;
    }

    public Entity getEntity() {
        return entity;
    }
}
