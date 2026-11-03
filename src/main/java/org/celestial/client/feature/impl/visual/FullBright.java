package org.celestial.client.feature.impl.visual;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.player.EventUpdate;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.settings.impl.ListSetting;

public class FullBright extends Feature {

    public static ListSetting brightMode;

    public FullBright() {
        super("FullBright", "Убирает темноту в игре", Type.Visuals);
        brightMode = new ListSetting("FullBright Mode", "Gamma", () -> true, "Gamma", "MobEffect");
        addSettings(brightMode);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (getState()) {
            String mode = brightMode.getOptions();
            if (mode.equalsIgnoreCase("Gamma")) {
                mc.gameSettings.gammaSetting = 1000F;
            }
            if (mode.equalsIgnoreCase("MobEffect")) {
                mc.player.addPotionEffect(new MobEffectInstance(MobEffect.getPotionById(16), 817, 1));
            } else {
                mc.player.removePotionEffect(MobEffect.getPotionById(16));
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.gameSettings.gammaSetting = 1F;
        mc.player.removePotionEffect(MobEffect.getPotionById(16));
    }
}
