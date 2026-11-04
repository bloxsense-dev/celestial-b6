package org.celestial.client.feature.impl.movement;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockAir;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.inventory.ClickType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.motion.EventSafeWalk;
import org.celestial.client.event.events.impl.motion.EventStrafe;
import org.celestial.client.event.events.impl.player.EventPreMotion;
import org.celestial.client.event.events.impl.player.EventUpdate;
import org.celestial.client.event.events.impl.render.EventRender2D;
import org.celestial.client.event.events.impl.render.EventRender3D;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.helpers.math.MathematicHelper;
import org.celestial.client.helpers.math.RotationHelper;
import org.celestial.client.helpers.misc.TimerHelper;
import org.celestial.client.helpers.player.InventoryHelper;
import org.celestial.client.helpers.player.MovementHelper;
import org.celestial.client.helpers.render.RenderHelper;
import org.celestial.client.helpers.render.rect.RectHelper;
import org.celestial.client.settings.impl.BooleanSetting;
import org.celestial.client.settings.impl.ListSetting;
import org.celestial.client.settings.impl.NumberSetting;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Scaffold extends Feature {

    public static List<Block> invalidBlocks = Arrays.asList(Blocks.ENCHANTING_TABLE, Blocks.FURNACE, Blocks.CARPET.white(), Blocks.CRAFTING_TABLE, Blocks.TRAPPED_CHEST, Blocks.CHEST, Blocks.DISPENSER, Blocks.AIR, Blocks.WATER, Blocks.LAVA, Blocks.FLOWING_WATER, Blocks.FLOWING_LAVA, Blocks.SAND, Blocks.SNOW_LAYER, Blocks.TORCH, Blocks.ANVIL, Blocks.JUKEBOX, Blocks.STONE_BUTTON, Blocks.WOODEN_BUTTON, Blocks.LEVER, Blocks.NOTEBLOCK, Blocks.STONE_PRESSURE_PLATE, Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, Blocks.WOODEN_PRESSURE_PLATE, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, Blocks.STONE_SLAB, Blocks.WOODEN_SLAB, Blocks.STONE_SLAB2, Blocks.RED_MUSHROOM, Blocks.BROWN_MUSHROOM, Blocks.YELLOW_FLOWER, Blocks.RED_FLOWER, Blocks.ANVIL, Blocks.GLASS_PANE, Blocks.STAINED_GLASS_PANE.white(), Blocks.IRON_BARS, Blocks.CACTUS, Blocks.LADDER, Blocks.WEB, Blocks.PUMPKIN);
    public static BlockData data;
    public static boolean isSneaking;
    public static BooleanSetting down;
    public static BooleanSetting sprintoff;
    public static BooleanSetting rotationRandom = new BooleanSetting("Rotation Random", true, () -> true);
    public static NumberSetting rotationSpeed = new NumberSetting("Rotation Speed", 360, 1, 360, 1, () -> true);
    private final TimerHelper time = new TimerHelper();
    private final BooleanSetting jump;
    private final BooleanSetting swing;
    private final NumberSetting delay;
    private final NumberSetting delayRandom;
    private final NumberSetting chance;
    private final NumberSetting speed;
    private final BooleanSetting rotStrafe;
    private final BooleanSetting safewalk;
    private final ListSetting blockRotation;
    private final ListSetting towerMode;
    public NumberSetting rotPitchRandom = new NumberSetting("Rotation Pitch Random", 2, 0, 8, 0.01f, () -> rotationRandom.getCurrentValue());
    public NumberSetting rotYawRandom = new NumberSetting("Rotation Yaw Random", 2, 0, 8, 0.01f, () -> rotationRandom.getCurrentValue());
    public BooleanSetting airCheck = new BooleanSetting("Check Air", true, () -> true);
    public BooleanSetting sneak = new BooleanSetting("Sneak", true, () -> true);
    public NumberSetting sneakChance = new NumberSetting("Sneak Chance", 100, 0, 100, 1, () -> sneak.getCurrentValue());
    public NumberSetting sneakSpeed = new NumberSetting("Sneak Speed", 0.05F, 0.01F, 1, 0.01f, () -> sneak.getCurrentValue());
    public ListSetting sneakMode = new ListSetting("Sneak Mode", "Packet", () -> sneak.getCurrentValue(), "Packet", "Client");
    public NumberSetting rotationOffset = new NumberSetting("Rotation Offset", 0.25F, 0F, 1F, 0.01F, () -> true);
    public NumberSetting placeOffset = new NumberSetting("Place Offset", 0.20F, 0.01F, 0.3F, 0.01F, () -> true);
    private int slot;

    public Scaffold() {
        super("Scaffold", "Автоматически ставит под вас блоки", Type.Movement);

        blockRotation = new ListSetting("BlockRotation Mode", "Matrix", () -> true, "Matrix", "None");

        towerMode = new ListSetting("Tower Mode", "Matrix", () -> true, "Matrix", "NCP", "Default");

        this.chance = new NumberSetting("Chance", 100, 0, 100, 1, () -> true);
        this.delay = new NumberSetting("Min Delay", 0, 0, 300, 1, () -> true);
        this.delayRandom = new NumberSetting("Random Delay", 0, 0, 1000, 1, () -> true);
        this.speed = new NumberSetting("Speed", 0.6f, 0.05f, 1.2f, 0.01f, () -> true);
        sprintoff = new BooleanSetting("Stop Sprinting", true, () -> true);
        safewalk = new BooleanSetting("SafeWalk", true, () -> true);
        this.jump = new BooleanSetting("Jump", false, () -> true);
        down = new BooleanSetting("DownWard", false, () -> true);
        this.swing = new BooleanSetting("SwingHand", false, () -> true);
        this.rotStrafe = new BooleanSetting("Rotation Strafe", false, () -> true);
        addSettings(blockRotation, towerMode, chance, delay, delayRandom, rotationOffset, placeOffset, rotationSpeed, rotationRandom, rotYawRandom, rotPitchRandom, speed, sneak, sneakMode, sneakChance, sneakSpeed, sprintoff, airCheck, safewalk, jump, down, swing, rotStrafe);
    }

    public static int searchBlock() {
        for (int i = 0; i < 45; ++i) {
            ItemStack itemStack = mc.player.inventoryContainer.getSlot(i).getStack();
            if (itemStack.getItem() instanceof BlockItem) {
                return i;
            }
        }
        return -1;
    }

    private boolean canPlace() {
        BlockPos bp1 = new BlockPos(mc.player.posX - placeOffset.getCurrentValue(), mc.player.posY - placeOffset.getCurrentValue(), mc.player.posZ - placeOffset.getCurrentValue());
        BlockPos bp2 = new BlockPos(mc.player.posX - placeOffset.getCurrentValue(), mc.player.posY - placeOffset.getCurrentValue(), mc.player.posZ + placeOffset.getCurrentValue());
        BlockPos bp3 = new BlockPos(mc.player.posX + placeOffset.getCurrentValue(), mc.player.posY - placeOffset.getCurrentValue(), mc.player.posZ + placeOffset.getCurrentValue());
        BlockPos bp4 = new BlockPos(mc.player.posX + placeOffset.getCurrentValue(), mc.player.posY - placeOffset.getCurrentValue(), mc.player.posZ - placeOffset.getCurrentValue());
        return (mc.world.getBlockState(bp1).getBlock() == Blocks.AIR) && (mc.world.getBlockState(bp2).getBlock() == Blocks.AIR) && (mc.world.getBlockState(bp3).getBlock() == Blocks.AIR) && (mc.world.getBlockState(bp4).getBlock() == Blocks.AIR);
    }

    private boolean canSneak() {
        BlockPos bp1 = new BlockPos(mc.player.posX - sneakSpeed.getCurrentValue(), mc.player.posY - sneakSpeed.getCurrentValue(), mc.player.posZ - sneakSpeed.getCurrentValue());
        BlockPos bp2 = new BlockPos(mc.player.posX - sneakSpeed.getCurrentValue(), mc.player.posY - sneakSpeed.getCurrentValue(), mc.player.posZ + sneakSpeed.getCurrentValue());
        BlockPos bp3 = new BlockPos(mc.player.posX + sneakSpeed.getCurrentValue(), mc.player.posY - sneakSpeed.getCurrentValue(), mc.player.posZ + sneakSpeed.getCurrentValue());
        BlockPos bp4 = new BlockPos(mc.player.posX + sneakSpeed.getCurrentValue(), mc.player.posY - sneakSpeed.getCurrentValue(), mc.player.posZ - sneakSpeed.getCurrentValue());
        return (mc.world.getBlockState(bp1).getBlock() == Blocks.AIR) && (mc.world.getBlockState(bp2).getBlock() == Blocks.AIR) && (mc.world.getBlockState(bp3).getBlock() == Blocks.AIR) && (mc.world.getBlockState(bp4).getBlock() == Blocks.AIR);
    }

    @Override
    public void onEnable() {
        this.slot = mc.player.inventory.currentItem;
        data = null;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.player.inventory.currentItem = this.slot;
        mc.timer.timerSpeed = 1f;
        mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        mc.gameSettings.keyBindSneak.pressed = false;
        super.onDisable();
    }

    @EventTarget
    public void onStrafeMotion(EventStrafe eventStrafe) {
        if (rotStrafe.getCurrentValue()) {
            eventStrafe.setCancelled(true);
            MovementHelper.calculateSilentMove(eventStrafe, RotationHelper.Rotation.packetYaw);
        }
    }

    @EventTarget
    public void onSafe(EventSafeWalk eventSafeWalk) {
        if (safewalk.getCurrentValue() && !isSneaking) {
            eventSafeWalk.setCancelled(mc.player.onGround);
        }
    }

    @EventTarget
    public void onPreMotion(EventPreMotion eventUpdate) {
        String tower = towerMode.getCurrentMode();
        this.setSuffix(blockRotation.getCurrentMode());
        if (tower.equalsIgnoreCase("Matrix")) {
            if (!MovementHelper.isMoving()) {
                if (mc.player.onGround && mc.gameSettings.keyBindJump.isKeyDown()) {
                    mc.player.jump();
                }
                if (mc.player.motionY > 0 && !mc.player.onGround) {
                    mc.player.motionY -= 0.00994;
                } else {
                    mc.player.motionY -= 0.00995;
                }
            }
        } else if (tower.equalsIgnoreCase("NCP")) {
            if (!MovementHelper.isMoving()) {
                if (mc.player.onGround && mc.gameSettings.keyBindJump.isKeyDown()) {
                    mc.player.jump();
                }
                float pos = -2F;
                if (mc.player.motionY < 0.1 && !(mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ).add(0, pos, 0)).getBlock() instanceof BlockAir)) {
                    mc.player.motionY -= 190;
                }
            }
        }

        if (mc.gameSettings.keyBindSneak.pressed && down.getCurrentValue()) {
            mc.gameSettings.keyBindSneak.pressed = false;
            isSneaking = true;
        } else {
            isSneaking = false;
        }

        mc.player.motionX *= this.speed.getCurrentValue();
        mc.player.motionZ *= this.speed.getCurrentValue();

        if (InventoryHelper.doesHotbarHaveBlock()) {
        } else if (!(mc.player.getHeldItemOffhand().getItem() instanceof BlockItem) && searchBlock() != -1) {
            mc.playerController.windowClick(0, searchBlock(), 1, ClickType.QUICK_MOVE, mc.player);
        }
        double yDif;
        double posY;
        BlockPos blockPos = isSneaking ? new BlockPos(mc.player).add(1, -1, 0).down() : new BlockPos(mc.player).add(0, -1, 0);
        for (posY = mc.player.posY - 1; posY > 0; posY--) {
            BlockData newData = getBlockData(blockPos);
            if (newData != null) {
                yDif = mc.player.posY - posY;
                if (yDif <= 7) {
                    data = newData;
                }
            }
        }
        if (sprintoff.getCurrentValue()) {
            mc.player.setSprinting(false);
        }
        if (data != null && slot != -1 && !mc.player.isInLiquid()) {
            Vec3d hitVec = getVectorToRotate(data);
            if (blockRotation.getOptions().equalsIgnoreCase("Matrix")) {
                float[] rots = RotationHelper.getRotationVector(hitVec, rotationRandom.getCurrentValue(), rotYawRandom.getCurrentValue(), rotPitchRandom.getCurrentValue(), rotationSpeed.getCurrentValue());
                eventUpdate.setYaw(rots[0]);
                eventUpdate.setPitch(rots[1]);

                if (mc.world.getBlockState(blockPos).getBlock() == Blocks.AIR && !airCheck.getCurrentValue()) {
                    mc.player.renderYawOffset = rots[0];
                    mc.player.rotationYawHead = rots[0];
                    mc.player.rotationPitchHead = rots[1];
                } else {
                    mc.player.renderYawOffset = rots[0];
                    mc.player.rotationYawHead = rots[0];
                    mc.player.rotationPitchHead = rots[1];
                }
            }
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (InventoryHelper.doesHotbarHaveBlock()) {
            if (data != null) {
                int slot = -1;
                int lastItem = mc.player.inventory.currentItem;
                BlockPos pos = data.pos;
                Vec3d hitVec = getVectorToPlace(data);
                for (int i = 0; i < 9; i++) {
                    ItemStack itemStack = mc.player.inventory.getStackInSlot(i);
                    if (isValidItem(itemStack.getItem())) {
                        slot = i;
                    }
                }
                if (slot != -1) {
                    if (jump.getCurrentValue()) {
                        if (!mc.gameSettings.keyBindJump.isKeyDown()) {
                            if (mc.player.onGround) {
                                mc.player.jump();
                            }
                        }
                    }
                    if (!jump.getCurrentValue() && InventoryHelper.doesHotbarHaveBlock() && MovementHelper.isMoving() && !mc.gameSettings.keyBindJump.isKeyDown() && sneak.getCurrentValue() && MathematicHelper.randomizeFloat(0f, 100f) <= sneakChance.getCurrentValue()) {
                        if (InventoryHelper.doesHotbarHaveBlock()) {
                            if (canSneak()) {
                                if (sneakMode.currentMode.equals("Packet")) {
                                    mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_RIDING_JUMP));
                                    mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
                                } else if (sneakMode.currentMode.equals("Client")) {
                                    mc.gameSettings.keyBindSneak.pressed = true;
                                }
                            } else {
                                if (sneakMode.currentMode.equals("Packet")) {
                                    mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                                } else if (sneakMode.currentMode.equals("Client")) {
                                    mc.gameSettings.keyBindSneak.pressed = false;
                                }
                            }
                        }
                    }
                    if (time.hasReached(delay.getCurrentValue() + MathematicHelper.randomizeFloat(0, delayRandom.getCurrentValue()))) {
                        if (canPlace()) {
                            if (MathematicHelper.randomizeFloat(0f, 100f) <= chance.getCurrentValue()) {
                                mc.player.inventory.currentItem = slot;
                            }
                            mc.playerController.processRightClickBlock(mc.player, mc.world, pos, data.face, hitVec, EnumHand.MAIN_HAND);
                            if (swing.getCurrentValue()) {
                                mc.player.swingArm(EnumHand.MAIN_HAND);
                            } else {
                                mc.player.connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
                            }
                            mc.player.inventory.currentItem = lastItem;
                            time.reset();
                        }
                    }
                }
            }
        }
    }

    @EventTarget
    public void onRender(EventRender3D event) {
        if (data != null && slot != -1) {
            double x = mc.player.posX;
            double y = mc.player.posY;
            double z = mc.player.posZ;
            double yaw = mc.player.rotationYaw * 0.017453292;
            BlockPos below = new BlockPos(x - Math.sin(yaw), y - 1, z + Math.cos(yaw));
            RenderHelper.blockEsp(below, Color.WHITE, true);
        }
    }

    @EventTarget
    public void onRender2D(EventRender2D render) {
        float width = render.getResolution().getScaledWidth();
        float height = render.getResolution().getScaledHeight();
        String blockString = this.getBlockCount() + " Blocks";

        GlStateManager.pushMatrix();
        GlStateManager.translate(23, 15, 0);
        RectHelper.drawSkeetRectWithoutBorder(width / 2F + 88 - mc.circleregular.getStringWidth(blockString), height / 2 - mc.circleregular.getStringHeight(blockString) + 59, width / 2F + mc.circleregular.getStringHeight(blockString) + 2, height / 2 - mc.circleregular.getStringHeight(blockString) / 2F - 55);
        mc.circleregular.drawStringWithOutline(blockString, width / 2F + 50 - mc.circleregular.getStringWidth(blockString), height / 2 - 9, -1);
        GlStateManager.popMatrix();
    }

    private int getBlockCount() {
        int blockCount = 0;

        for (int i = 0; i < 45; ++i) {
            if (!mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                continue;
            }

            ItemStack is = mc.player.inventoryContainer.getSlot(i).getStack();
            Item item = is.getItem();

            if (!isValidItem(item)) {
                continue;
            }
            blockCount += is.stackSize;
        }
        return blockCount;
    }

    private boolean isValidItem(Item item) {
        if (item instanceof BlockItem) {
            BlockItem iBlock = (BlockItem) item;
            Block block = iBlock.getBlock();
            return !invalidBlocks.contains(block);
        }
        return false;
    }

    public BlockData getBlockData(BlockPos pos) {
        BlockData blockData = null;
        int i = 0;
        while (blockData == null) {
            if (i >= 2) {
                break;
            }
            if (this.isBlockPosAir(pos.add(0, 0, 1))) {
                blockData = new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
                break;
            }
            if (this.isBlockPosAir(pos.add(0, 0, -1))) {
                blockData = new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
                break;
            }
            if (this.isBlockPosAir(pos.add(1, 0, 0))) {
                blockData = new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
                break;
            }
            if (this.isBlockPosAir(pos.add(-1, 0, 0))) {
                blockData = new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
                break;
            }
            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                if (this.isBlockPosAir(pos.add(0, -1, 0))) {
                    blockData = new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
                    break;
                }
            }
            if (this.isBlockPosAir(pos.add(0, 1, 0)) && isSneaking) {
                blockData = new BlockData(pos.add(0, 1, 0), EnumFacing.DOWN);
                break;
            }
            if (this.isBlockPosAir(pos.add(0, 1, 1)) && isSneaking) {
                blockData = new BlockData(pos.add(0, 1, 1), EnumFacing.DOWN);
                break;
            }
            if (this.isBlockPosAir(pos.add(0, 1, -1)) && isSneaking) {
                blockData = new BlockData(pos.add(0, 1, -1), EnumFacing.DOWN);
                break;
            }
            if (this.isBlockPosAir(pos.add(1, 1, 0)) && isSneaking) {
                blockData = new BlockData(pos.add(1, 1, 0), EnumFacing.DOWN);
                break;
            }
            if (this.isBlockPosAir(pos.add(-1, 1, 0)) && isSneaking) {
                blockData = new BlockData(pos.add(-1, 1, 0), EnumFacing.DOWN);
                break;
            }
            if (this.isBlockPosAir(pos.add(1, 0, 1))) {
                blockData = new BlockData(pos.add(1, 0, 1), EnumFacing.NORTH);
                break;
            }
            if (this.isBlockPosAir(pos.add(-1, 0, -1))) {
                blockData = new BlockData(pos.add(-1, 0, -1), EnumFacing.SOUTH);
                break;
            }
            if (this.isBlockPosAir(pos.add(1, 0, 1))) {
                blockData = new BlockData(pos.add(1, 0, 1), EnumFacing.WEST);
                break;
            }
            if (this.isBlockPosAir(pos.add(-1, 0, -1))) {
                blockData = new BlockData(pos.add(-1, 0, -1), EnumFacing.EAST);
                break;
            }
            if (this.isBlockPosAir(pos.add(-1, 0, 1))) {
                blockData = new BlockData(pos.add(-1, 0, 1), EnumFacing.NORTH);
                break;
            }
            if (this.isBlockPosAir(pos.add(1, 0, -1))) {
                blockData = new BlockData(pos.add(1, 0, -1), EnumFacing.SOUTH);
                break;
            }
            if (this.isBlockPosAir(pos.add(1, 0, -1))) {
                blockData = new BlockData(pos.add(1, 0, -1), EnumFacing.WEST);
                break;
            }
            if (this.isBlockPosAir(pos.add(-1, 0, 1))) {
                blockData = new BlockData(pos.add(-1, 0, 1), EnumFacing.EAST);
                break;
            }
            pos = pos.down();
            ++i;
        }
        return blockData;
    }

    private Vec3d getVectorToPlace(BlockData data) {
        BlockPos pos = data.pos;
        EnumFacing face = data.face;
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.5;
        double z = pos.getZ() + 0.5;
        if (face == EnumFacing.UP || face == EnumFacing.DOWN) {
            x += 0.3;
            z += 0.3;
        } else {
            y += 0.5;
        }
        if (face == EnumFacing.WEST || face == EnumFacing.EAST) {
            z += placeOffset.getCurrentValue();
        }
        if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
            x += placeOffset.getCurrentValue();
        }
        return new Vec3d(x, y, z);
    }

    private Vec3d getVectorToRotate(BlockData data) {
        BlockPos pos = data.pos;
        EnumFacing face = data.face;
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.5;
        double z = pos.getZ() + 0.5;
        if (face == EnumFacing.UP || face == EnumFacing.DOWN) {
            x += 0.4;
            z += 0.4;
        } else {
            y += 0.4;
        }
        if (face == EnumFacing.WEST || face == EnumFacing.EAST) {
            z += rotationOffset.getCurrentValue();
        }
        if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
            x += rotationOffset.getCurrentValue();
        }
        return new Vec3d(x, y, z);
    }

    public boolean isBlockPosAir(BlockPos blockPos) {
        return this.getBlockByPos(blockPos) != Blocks.AIR && !(this.getBlockByPos(blockPos) instanceof LiquidBlock);
    }

    public Block getBlockByPos(BlockPos blockPos) {
        return mc.world.getBlockState(blockPos).getBlock();
    }

    private static class BlockData {
        public BlockPos pos;
        public EnumFacing face;

        private BlockData(BlockPos pos, EnumFacing face) {
            this.pos = pos;
            this.face = face;
        }
    }
}