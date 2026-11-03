package org.celestial.client.feature.impl.ghost;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.util.math.AxisAlignedBB;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.player.EventPreMotion;
import org.celestial.client.event.events.impl.player.EventUpdate;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.helpers.math.MathematicHelper;
import org.celestial.client.settings.impl.NumberSetting;

public class HitBoxes extends Feature {

    public static NumberSetting expand;

    public HitBoxes() {
        super("HitBoxes", "Увеличивает хитбокс у ентити", Type.Ghost);
        expand = new NumberSetting("Expand", 0.2F, 0.01F, 2.0F, 0.01F, () -> true);
        addSettings(expand);
    }

    @EventTarget
    public void onUpdate(EventPreMotion event) {
        this.setSuffix("" + MathematicHelper.round(expand.getCurrentValue(), 2));

        for (Entity entity : mc.world.playerEntities) {
            if(entity != mc.player){
                float width = entity.width;
                float height = entity.height;
                float expandValue = expand.getCurrentValue();
                entity.setEntityBoundingBox(new AxisAlignedBB(entity.posX - width - expandValue, entity.posY, entity.posZ + width + expandValue, entity.posX + width + expandValue, entity.posY + height + expandValue, entity.posZ - width - expandValue));
            }
        }
    }
}
