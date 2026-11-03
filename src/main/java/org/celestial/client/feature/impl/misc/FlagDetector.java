package org.celestial.client.feature.impl.misc;

import net.minecraft.network.play.server.SPacketPlayerPosLook;
import org.celestial.client.Celestial;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.packet.EventReceivePacket;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.feature.impl.combat.KillAura;
import org.celestial.client.feature.impl.combat.TargetStrafe;
import org.celestial.client.feature.impl.movement.*;
import org.celestial.client.ui.notification.NotificationManager;
import org.celestial.client.ui.notification.NotificationType;

public class FlagDetector extends Feature {

    public FlagDetector() {
        super("FlagDetector", "Автоматически выключает модуль при его детекте", Type.Misc);
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
        if (this.getState()) {
            if (event.getPacket() instanceof SPacketPlayerPosLook) {
                if (Celestial.instance.featureManager.getFeatureByClass(Speed.class).getState()) {
                    featureAlert("Speed");
                    Celestial.instance.featureManager.getFeatureByClass(Speed.class).toggle();
                } else if (Celestial.instance.featureManager.getFeatureByClass(Flight.class).getState()) {
                    featureAlert("Flight");
                    Celestial.instance.featureManager.getFeatureByClass(Flight.class).toggle();
                } else if (Celestial.instance.featureManager.getFeatureByClass(FastClimb.class).getState() && mc.player.isOnLadder() && !mc.player.isUsingItem()) {
                    featureAlert("FastClimb");
                    Celestial.instance.featureManager.getFeatureByClass(FastClimb.class).toggle();
                } else if (Celestial.instance.featureManager.getFeatureByClass(TargetStrafe.class).getState() && KillAura.target != null) {
                    featureAlert("TargetStrafe");
                    Celestial.instance.featureManager.getFeatureByClass(TargetStrafe.class).toggle();
                } else if (Celestial.instance.featureManager.getFeatureByClass(LongJump.class).getState()) {
                    featureAlert("LongJump");
                    Celestial.instance.featureManager.getFeatureByClass(LongJump.class).toggle();
                } else if (Celestial.instance.featureManager.getFeatureByClass(LiquidWalk.class).getState() && mc.player.isInLiquid()) {
                    featureAlert("LiquidWalk");
                    Celestial.instance.featureManager.getFeatureByClass(LiquidWalk.class).toggle();
                } else if (Celestial.instance.featureManager.getFeatureByClass(Timer.class).getState()) {
                    featureAlert("Timer");
                    Celestial.instance.featureManager.getFeatureByClass(Timer.class).toggle();
                }
            }
        }
    }

    public void featureAlert(String feature) {
        NotificationManager.publicity(feature, "Disabling due to lag back", 3, NotificationType.WARNING);
    }
}