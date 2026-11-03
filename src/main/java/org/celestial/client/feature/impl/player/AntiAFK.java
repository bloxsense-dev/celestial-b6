package org.celestial.client.feature.impl.player;

import net.minecraft.network.play.client.CPacketPlayer;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.player.EventPreMotion;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.helpers.math.GCDCalcHelper;
import org.celestial.client.helpers.math.MathematicHelper;
import org.celestial.client.helpers.player.MovementHelper;
import org.celestial.client.settings.impl.BooleanSetting;
import org.celestial.client.settings.impl.NumberSetting;

public class AntiAFK extends Feature {

    private final BooleanSetting sendMessage;
    private final BooleanSetting spin;
    private final BooleanSetting click = new BooleanSetting("Click", true, () -> true);
    public NumberSetting sendDelay;
    public BooleanSetting invalidRotation = new BooleanSetting("Invalid Rotation", true, () -> true);
    public BooleanSetting jump = new BooleanSetting("Jump", true, () -> true);
    public float rot = 0;

    public AntiAFK() {
        super("AntiAFK", "Позволяет встать в афк режим, без риска кикнуться", Type.Player);
        spin = new BooleanSetting("Spin", true, () -> true);
        sendMessage = new BooleanSetting("Send Message", true, () -> true);
        sendDelay = new NumberSetting("Send Delay", 500, 100, 1000, 100, sendMessage::getCurrentValue);
        addSettings(spin, sendMessage, click, sendDelay, invalidRotation, jump);
    }

    @EventTarget
    public void onPreMotion(EventPreMotion event) {
        if (!MovementHelper.isMoving()) {
            if (spin.getCurrentValue()) {
                float yaw = GCDCalcHelper.getFixedRotation((float) (Math.floor(spinAim(1F)) + MathematicHelper.randomizeFloat(-4, 1)));
                event.setYaw(yaw);
                mc.player.renderYawOffset = yaw;
                mc.player.rotationPitchHead = 0;
                mc.player.rotationYawHead = yaw;
            }

            if (mc.player.ticksExisted % 60 == 0 && invalidRotation.getCurrentValue()) {
                mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(mc.player.posX + 1, mc.player.posY + 1, mc.player.posZ + 1, mc.player.rotationYaw, mc.player.rotationPitch, true));
            }

            if (mc.player.ticksExisted % 60 == 0 && click.getCurrentValue()) {
                mc.clickMouse();
            }

            if (mc.player.ticksExisted % 40 == 0 && jump.getCurrentValue()) {
                mc.player.jump();
            }

            if (mc.player.ticksExisted % 400 == 0 && sendMessage.getCurrentValue()) {
                mc.player.sendChatMessage("/homehome");
            }
        }
    }

    public float spinAim(float rots) {
        rot += rots;
        return rot;
    }
}
