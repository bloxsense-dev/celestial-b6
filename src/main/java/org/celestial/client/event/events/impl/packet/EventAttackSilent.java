package org.celestial.client.event.events.impl.packet;

import net.minecraft.world.entity.Entity;
import org.celestial.client.event.events.callables.EventCancellable;

public class EventAttackSilent extends EventCancellable {

    private final Entity targetEntity;

    public EventAttackSilent(Entity targetEntity) {
        this.targetEntity = targetEntity;
    }

    public Entity getTargetEntity() {
        return this.targetEntity;
    }
}
