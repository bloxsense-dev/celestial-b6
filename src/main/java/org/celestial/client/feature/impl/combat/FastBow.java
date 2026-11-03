package org.celestial.client.feature.impl.combat;

import net.minecraft.world.item.BowItem;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.player.EventUpdate;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.settings.impl.NumberSetting;

public class FastBow extends Feature {

    private final NumberSetting ticks;

    public FastBow() {
        super("FastBow", "При зажатии на ПКМ игрок быстро стреляет из лука", Type.Combat);
        this.ticks = new NumberSetting("Bow Ticks", 1.5f, 1.5f, 10, 0.5f, () -> true);
        addSettings(ticks);
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        if (mc.player.inventory.getCurrentItem().getItem() instanceof BowItem && mc.player.isBowing() && mc.player.getItemInUseMaxCount() >= ticks.getCurrentValue()) {
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            mc.player.stopActiveHand();
        }
    }
}
