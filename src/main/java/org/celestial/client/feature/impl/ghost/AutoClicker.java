package org.celestial.client.feature.impl.ghost;

import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.player.EventUpdate;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.helpers.math.MathematicHelper;
import org.celestial.client.helpers.misc.TimerHelper;
import org.celestial.client.settings.impl.NumberSetting;

public class AutoClicker extends Feature {

    public NumberSetting minCps = new NumberSetting("Min", 6, 1, 20, 1, () -> true, NumberSetting.NumberType.APS);
    public NumberSetting maxCps = new NumberSetting("Max", 10, 1, 20, 1, () -> true, NumberSetting.NumberType.APS);

    public TimerHelper timerHelper = new TimerHelper();

    public AutoClicker() {
        super("AutoClicker", "Кликает определенный cps", Type.Ghost);
        addSettings(minCps, maxCps);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        int cps;
        if (mc.gameSettings.keyBindAttack.isKeyDown() && !mc.player.isUsingItem()) {
            cps = (int) MathematicHelper.randomizeFloat(maxCps.getCurrentValue(), minCps.getCurrentValue());
            if (timerHelper.hasReached(1000 / cps)) {
                mc.clickMouse();
                timerHelper.reset();
            }
        }
    }

}
