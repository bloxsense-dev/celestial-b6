package org.celestial.client.feature.impl.player;

import net.minecraft.network.play.client.CPacketPlayer;
import org.celestial.client.Celestial;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.player.EventUpdate;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.feature.impl.misc.FreeCam;

public class HurtClip extends Feature {

    public boolean damageToggle = false;

    public HurtClip() {
        super("HurtClip", "Клипает вас под бедрок", Type.Player);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (!damageToggle) {
            for (int i = 0; i < 9; i++) {
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.4, mc.player.posZ, false));
            }
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, true));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, true));
        }
        if (mc.player.hurtTime > 0) {
            mc.player.setPositionAndUpdate(mc.player.posX, -2, mc.player.posZ);
            damageToggle = true;
            toggle();
            Celestial.instance.featureManager.getFeatureByClass(FreeCam.class).setState(true);
        }
    }
}
