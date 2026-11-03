package org.celestial.client.feature.impl.player;

import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.util.EnumHand;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.player.EventBlockInteract;
import org.celestial.client.event.events.impl.player.EventUpdate;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.settings.impl.ListSetting;
import org.celestial.client.settings.impl.NumberSetting;

public class SpeedMine extends Feature {

    public ListSetting mode;
    public NumberSetting damageValue = new NumberSetting("Damage Value", 0.8F, 0.7F, 4, 0.1F, () -> mode.currentMode.equals("Damage"));

    public SpeedMine() {
        super("SpeedMine", "Позволяет быстро ломать блоки", Type.Player);
        mode = new ListSetting("SpeedMine Mode", "Packet", () -> true, "Packet", "Damage", "MobEffect");
        addSettings(mode, damageValue);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        this.setSuffix(mode.currentMode);
    }

    @EventTarget
    public void onBlockInteract(EventBlockInteract event) {
        switch (mode.currentMode) {
            case "MobEffect":
                mc.player.addPotionEffect(new MobEffectInstance(MobEffects.HASTE, 817, 1));
                break;
            case "Damage":
                if (mc.playerController.curBlockDamageMP >= 0.7) {
                    mc.playerController.curBlockDamageMP = damageValue.getCurrentValue();
                }
                break;
            case "Packet":
                mc.player.swingArm(EnumHand.MAIN_HAND);
                mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getPos(), event.getFace()));
                mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(), event.getFace()));
                event.setCancelled(true);
                break;
        }
    }

    @Override
    public void onDisable() {
        mc.player.removePotionEffect(MobEffects.HASTE);
        super.onDisable();
    }
}