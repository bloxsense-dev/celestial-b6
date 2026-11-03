package org.celestial.client.feature.impl.combat;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.player.EventPreMotion;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.helpers.math.RotationHelper;
import org.celestial.client.helpers.misc.TimerHelper;
import org.celestial.client.helpers.player.InventoryHelper;
import org.celestial.client.helpers.world.BlockHelper;
import org.celestial.client.helpers.world.EntityHelper;
import org.celestial.client.settings.impl.BooleanSetting;
import org.celestial.client.settings.impl.NumberSetting;

import java.util.ArrayList;
import java.util.Comparator;

public class AntiCrystal extends Feature {

    private final NumberSetting rangeToBlock;
    private final NumberSetting delay;
    private final BooleanSetting throughWalls;
    private final BooleanSetting bedrockCheck;
    private final BooleanSetting obsidianCheck;
    private final TimerHelper timerHelper = new TimerHelper();
    private final ArrayList<BlockPos> invalidPositions = new ArrayList<>();

    public AntiCrystal() {
        super("AntiCrystal", "Автоматически ставит блок на обсидиан/бедрок в радиусе", Type.Combat);
        this.throughWalls = new BooleanSetting("Through Walls", true, () -> true);
        this.obsidianCheck = new BooleanSetting("Obsidian Check", true, () -> true);
        this.bedrockCheck = new BooleanSetting("Bedrock Check", false, () -> true);
        this.rangeToBlock = new NumberSetting("Range To Block", 5, 3, 6, 0.1F, () -> true);
        this.delay = new NumberSetting("Place Delay", 0, 0, 2000, 100, () -> true);
        addSettings(this.obsidianCheck, this.bedrockCheck, this.throughWalls, this.rangeToBlock, this.delay);
    }

    public static int getSlotWithBlock() {
        for (int i = 0; i < 9; ++i) {
            mc.player.inventory.getStackInSlot(i);
            if (mc.player.inventory.getStackInSlot(i).getItem() instanceof BlockItem) {
                return i;
            }
        }
        return -1;
    }

    private boolean IsValidBlockPos(BlockPos pos) {
        BlockState state = mc.world.getBlockState(pos);
        if ((state.getBlock() == Blocks.OBSIDIAN && this.obsidianCheck.getCurrentValue()) || (state.getBlock() == Block.getBlockById(7) && this.bedrockCheck.getCurrentValue()))
            return mc.world.getBlockState(pos.up()).getBlock() == Blocks.AIR;
        return false;
    }

    @EventTarget
    public void onPreMotion(EventPreMotion event) {

        this.setSuffix("" + (int) this.rangeToBlock.getCurrentValue());

        /* VARIABLES */

        int oldSlot = mc.player.inventory.currentItem;
        BlockPos pos = BlockHelper.getSphere(BlockHelper.getPlayerPos(), rangeToBlock.getCurrentValue(), 6, false, true).stream().filter(this::IsValidBlockPos).min(Comparator.comparing(blockPos -> EntityHelper.getDistanceOfEntityToBlock(mc.player, blockPos))).orElse(null);
        if (InventoryHelper.doesHotbarHaveBlock() && pos != null) {

            /* CHECK DELAY */

            if (timerHelper.hasReached(this.delay.getCurrentValue())) {

                if (getSlotWithBlock() != -1) {

                    /* CHECK BLOCK ABOVE */

                    if (!mc.world.isAirBlock(pos.up(1))) {
                        this.invalidPositions.add(pos);
                    }

                    for (Entity e : mc.world.loadedEntityList) {
                        if (e instanceof EndCrystal) {
                            if (e.getPosition().getX() == pos.getX() && e.getPosition().getZ() == pos.getZ()) {
                                return;
                            }
                        }
                    }

                    /* CHECK INVALID POS */
                    if (!this.invalidPositions.contains(pos)) {

                        /* RAY-TRACE */
                        if (mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(pos.getX(), pos.getY(), pos.getZ()), false, true, false) != null && !throughWalls.getCurrentValue()) {
                            return;
                        }

                        /* SEND ROTATION PACKET */
                        float[] rots = RotationHelper.getRotationVector(new Vec3d(pos.getX() + 0.5, pos.getY() + 1.4, pos.getZ() + 0.5), true, 2, 2, 360);
                        event.setYaw(rots[0]);
                        event.setPitch(rots[1]);
                        mc.player.renderYawOffset = rots[0];
                        mc.player.rotationYawHead = rots[0];
                        mc.player.rotationPitchHead = rots[1];

                        /* SWITCH TO BLOCK */
                        mc.player.inventory.currentItem = getSlotWithBlock();

                        /* CLICK ON BLOCK */
                        mc.playerController.processRightClickBlock(mc.player, mc.world, pos, EnumFacing.UP, new Vec3d(pos.getX(), pos.getY(), pos.getZ()), EnumHand.MAIN_HAND);
                        mc.player.swingArm(EnumHand.MAIN_HAND);

                        /* SWITCH OLD SLOT */
                        mc.player.inventory.currentItem = oldSlot;

                        /* RESET DELAY TIMER */
                        timerHelper.reset();
                    }
                }
            }
        }
    }
}