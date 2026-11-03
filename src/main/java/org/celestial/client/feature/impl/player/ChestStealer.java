package org.celestial.client.feature.impl.player;

import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.world.item.*;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.player.EventPreMotion;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.helpers.misc.TimerHelper;
import org.celestial.client.settings.impl.NumberSetting;

public class ChestStealer extends Feature {

    private final NumberSetting delay;
    public TimerHelper timer = new TimerHelper();

    public ChestStealer() {
        super("ChestStealer", "Автоматически забирает вещи из сундуков", Type.Player);
        delay = new NumberSetting("Stealer Speed", 10, 0, 100, 1, () -> true);
        addSettings(delay);
    }

    @EventTarget
    public void onUpdate(EventPreMotion event) {
        this.setSuffix("" + (int) delay.getCurrentValue());

        float delay = this.delay.getCurrentValue() * 10;

        if (mc.player.openContainer instanceof ContainerChest) {
            ContainerChest container = (ContainerChest) mc.player.openContainer;
            for (int index = 0; index < container.inventorySlots.size(); ++index) {
                if (container.getLowerChestInventory().getStackInSlot(index).getItem() != Item.getItemById(0) && timer.hasReached((delay))) {
                    ChestStealer.mc.playerController.windowClick(container.windowId, index, 0, ClickType.QUICK_MOVE, mc.player);
                    timer.reset();
                    continue;
                }
                if (!isEmpty(container))
                    continue;
                mc.player.closeScreen();
            }
        }
    }

    public boolean isWhiteItem(ItemStack itemStack) {
        return (itemStack.getItem() instanceof ArmorItem || itemStack.getItem() instanceof EnderpearlItem || itemStack.getItem() instanceof SwordItem || itemStack.getItem() instanceof DiggerItem || itemStack.getItem().isFood() || itemStack.getItem() instanceof PotionItem || itemStack.getItem() instanceof BlockItem || itemStack.getItem() instanceof ArrowItem || itemStack.getItem() instanceof CompassItem);
    }

    private boolean isEmpty(Container container) {
        for (int index = 0; index < container.inventorySlots.size(); index++) {
            if (isWhiteItem(container.getSlot(index).getStack()))
                return false;
        }
        return true;
    }
}