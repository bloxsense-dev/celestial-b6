package org.celestial.client.feature.impl.player;

import net.minecraft.client.gui.screens.DeathScreen;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.player.EventUpdate;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;

public class AutoRespawn extends Feature {

    public AutoRespawn() {
        super("AutoRespawn", "Автоматический респавн при смерти", Type.Player);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (mc.player.getHealth() < 0 || !mc.player.isEntityAlive() || mc.gui.screen() instanceof DeathScreen) {
            mc.player.respawnPlayer();
            mc.displayGuiScreen(null);
        }
    }
}
