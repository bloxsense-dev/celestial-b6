package org.celestial.client.feature.impl.player;

import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.player.EventWebSolid;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;

public class SolidWeb extends Feature {

    public SolidWeb() {
        super("SolidWeb", "Делает паутину полноценным блоком", Type.Misc);
    }

    @EventTarget
    public void onWebSolid(EventWebSolid event) {
        event.setCancelled(true);
    }
}
