package org.celestial.client.feature.impl.movement;

import org.celestial.client.Celestial;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.player.EventUpdate;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.feature.impl.combat.KillAura;
import org.celestial.client.feature.impl.misc.TeleportExploit;
import org.celestial.client.helpers.player.MovementHelper;

public class AutoSprint extends Feature {

    public AutoSprint() {
        super("AutoSprint", "Зажимает CTRL за вас, что бы быстро бежать", Type.Movement);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (!(Celestial.instance.featureManager.getFeatureByClass(TeleportExploit.class).getState())) {
            if (!(Celestial.instance.featureManager.getFeatureByClass(Scaffold.class).getState() && Scaffold.sprintoff.getCurrentValue())) {
                if (!(Celestial.instance.featureManager.getFeatureByClass(KillAura.class).getState() && KillAura.sprinting.getCurrentValue() && KillAura.target != null)) {
                    mc.player.setSprinting(MovementHelper.isMoving());
                }
            }
        }
    }
}
