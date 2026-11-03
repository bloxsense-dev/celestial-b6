package org.celestial.client.feature.impl.misc;

import org.celestial.client.Celestial;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.packet.EventMessage;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;

public class ChatAppend extends Feature {

    public ChatAppend() {
        super("ChatAppend", "Дописывает название чита в сообщение", Type.Misc);
    }

    @EventTarget
    public void onChatMessage(EventMessage event) {
        if (event.getMessage().startsWith("/"))
            return;

        event.message = event.message + " | " + Celestial.instance.name;
    }
}
