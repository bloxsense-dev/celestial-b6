package org.celestial.client.feature.impl.visual;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHandSide;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.player.EventTransformSideFirstPerson;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.settings.impl.NumberSetting;

public class ViewModel extends Feature {

    public static NumberSetting rightx;
    public static NumberSetting righty;
    public static NumberSetting rightz;
    public static NumberSetting leftx;
    public static NumberSetting lefty;
    public static NumberSetting leftz;

    public ViewModel() {
        super("ViewModel", "Позволяет редактировать позицию предметов в руке", Type.Visuals);
        rightx = new NumberSetting("RightX", 0, -2, 2, 0.1F, () -> true);
        righty = new NumberSetting("RightY", 0.2F, -2, 2, 0.1F, () -> true);
        rightz = new NumberSetting("RightZ", 0.2F, -2, 2, 0.1F, () -> true);
        leftx = new NumberSetting("LeftX", 0, -2, 2, 0.1F, () -> true);
        lefty = new NumberSetting("LeftY", 0.2F, -2, 2, 0.1F, () -> true);
        leftz = new NumberSetting("LeftZ", 0.2F, -2, 2, 0.1F, () -> true);
        addSettings(rightx, righty, rightz, leftx, lefty, leftz);
    }

    @EventTarget
    public void onSidePerson(EventTransformSideFirstPerson event) {
        if (event.getEnumHandSide() == EnumHandSide.RIGHT) {
            GlStateManager.translate(rightx.getCurrentValue(), righty.getCurrentValue(), rightz.getCurrentValue());
        }
        if (event.getEnumHandSide() == EnumHandSide.LEFT) {
            GlStateManager.translate(-leftx.getCurrentValue(), lefty.getCurrentValue(), leftz.getCurrentValue());
        }
    }
}