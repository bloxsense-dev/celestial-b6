package org.celestial.client.feature.impl.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.network.play.client.*;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;
import org.celestial.client.Celestial;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.motion.EventJump;
import org.celestial.client.event.events.impl.motion.EventStrafe;
import org.celestial.client.event.events.impl.packet.EventAttackSilent;
import org.celestial.client.event.events.impl.packet.EventSendPacket;
import org.celestial.client.event.events.impl.player.EventPostMotion;
import org.celestial.client.event.events.impl.player.EventPreMotion;
import org.celestial.client.event.events.impl.player.EventUpdate;
import org.celestial.client.event.events.impl.render.EventRender3D;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.helpers.math.MathematicHelper;
import org.celestial.client.helpers.math.RotationHelper;
import org.celestial.client.helpers.misc.TimerHelper;
import org.celestial.client.helpers.player.InventoryHelper;
import org.celestial.client.helpers.player.KillAuraHelper;
import org.celestial.client.helpers.player.MovementHelper;
import org.celestial.client.helpers.render.RenderHelper;
import org.celestial.client.helpers.world.EntityHelper;
import org.celestial.client.settings.impl.*;
import org.celestial.client.ui.notification.NotificationManager;
import org.celestial.client.ui.notification.NotificationType;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class KillAura extends Feature {

    /* UTILS */

    public static TimerHelper oldTimerPvp = new TimerHelper();
    public static TimerHelper timer = new TimerHelper();
    public static BooleanSetting players;
    public static BooleanSetting animals;
    public static BooleanSetting team;
    public static BooleanSetting invis;
    public static BooleanSetting pets;
    public static BooleanSetting monsters;
    public static BooleanSetting armorStands;
    public static BooleanSetting walls;
    public static BooleanSetting bypass;
    public static ColorSetting targetHudColor;
    public static LivingEntity target;
    public static NumberSetting range;
    public static NumberSetting fov;
    public static BooleanSetting onlyCrit;
    public static NumberSetting minAps;
    public static NumberSetting maxAps;
    public static NumberSetting hitChance;
    public static BooleanSetting autoDisable;
    public static BooleanSetting nakedPlayer;
    public static BooleanSetting sprinting;
    public static BooleanSetting weaponOnly;
    public static BooleanSetting usingItemCheck;
    public static BooleanSetting shieldBreaker;
    public static BooleanSetting autoBlock;
    public static BooleanSetting clientLook = new BooleanSetting("Client Look", false, () -> true);
    public static BooleanSetting visualYaw;
    public static BooleanSetting visualPitch;
    public static BooleanSetting shieldFixer;
    public static BooleanSetting randomRotation = new BooleanSetting("Random Rotation", true, () -> true);
    public static NumberSetting randomYaw = new NumberSetting("Random Yaw", 2, 0, 4, 0.01F, () -> randomRotation.getCurrentValue());
    public static NumberSetting randomPitch = new NumberSetting("Random Pitch", 2, 0, 4, 0.01F, () -> randomRotation.getCurrentValue());
    public static BooleanSetting shieldBlockCheck;
    public static BooleanSetting attackInInvetory = new BooleanSetting("Attack In Inventory", true, () -> true);
    public static BooleanSetting autoShieldUnPress;
    public static NumberSetting breakerDelay;
    public static NumberSetting critFallDistance;
    public static NumberSetting pitchValue = new NumberSetting("Pitch Value", 0.16F, -4, 4, 0.01f, () -> true);
    public static NumberSetting maxSpeedRotation = new NumberSetting("Max Speed Rotation", 360, 0, 360, 1, () -> true);
    public static NumberSetting minSpeedRotation = new NumberSetting("Min Speed Rotation", 360, 0, 360, 1, () -> true);
    public static NumberSetting attackCoolDown;
    public static BooleanSetting targetHud;
    public static BooleanSetting targetEsp = new BooleanSetting("Target Esp", true, () -> true);
    public static ListSetting targetEspMode = new ListSetting("Target Esp Mode", "Default", () -> targetEsp.getCurrentValue(), "Default", "Sims", "Jello", "Astolfo");
    public static ColorSetting targetEspColor = new ColorSetting("TargetEsp Color", Color.RED.getRGB(), () -> targetEsp.getCurrentValue());
    public static NumberSetting points = new NumberSetting("Points", 32, 2, 64, 1, () -> targetEspMode.currentMode.equals("Astolfo"));
    public static NumberSetting circleRange = new NumberSetting("Circle Range", 3, 0.1F, 6, 0.01F, () -> targetEspMode.currentMode.equals("Astolfo"));
    public static ListSetting rotationMode;
    public static ListSetting targetHudMode;
    public static ListSetting rotationStrafeMode;
    public static ListSetting strafeMode;
    public static ListSetting sort;
    public static ListSetting swingMode = new ListSetting("Swing Mode", "Client", () -> true, "Client", "None", "Server");
    public static ListSetting clickMode;
    public static NumberSetting rotPredict = new NumberSetting("Rotation Predict", 0.05F, 0.0F, 10, 0.001F, () -> true);
    public static BooleanSetting raycast = new BooleanSetting("Ray-Cast", true, () -> true);

    /* SETTINGS */

    private final TimerHelper blockTimer = new TimerHelper();
    private final TimerHelper shieldBreakerTimer = new TimerHelper();
    private double circleAnim;

    private boolean isBlocking;
    private int changeSlotCounter;

    private double direction = 1, yPos, progress = 0;

    private float delta = 0;

    private long lastMS = System.currentTimeMillis();
    private long lastDeltaMS = 0L;

    public KillAura() {
        super("KillAura", "Автоматически бьет сущностей вокруг тебя", Type.Combat);

        rotationMode = new ListSetting("Aim Mode", "Packet", () -> true, "Packet", "None");

        targetHudMode = new ListSetting("TargetHud Mode", "Astolfo", () -> targetHud.getCurrentValue(), "Astolfo", "Small", "Flux", "Novoline Old", "Novoline New");

        rotationStrafeMode = new ListSetting("Rotation Strafe Mode", "Default", () -> !strafeMode.currentMode.equals("None"), "Default", "Silent");

        strafeMode = new ListSetting("Strafe Mode", "None", () -> true, "None", "Always-F");
        sort = new ListSetting("Priority", "Distance", () -> true, "Distance", "Higher Armor", "Blocking Status", "Lowest Armor", "Health", "Angle", "HurtTime");
        clickMode = new ListSetting("PvP Mode", "1.9", () -> true, "1.9", "1.8");
        visualYaw = new BooleanSetting("Visual Yaw", "Отображение визуальной ротации", true, () -> rotationMode.currentMode.equals("Packet"));
        visualPitch = new BooleanSetting("Visual Pitch", "Отображение визуальной ротации", true, () -> rotationMode.currentMode.equals("Packet"));
        fov = new NumberSetting("FOV", "Позволяет редактировать радиус в котором вы можете ударить игрока", 180, 5, 180, 5, () -> true);
        attackCoolDown = new NumberSetting("Cool Down", "Редактирует скорость удара", 0.85F, 0.1F, 1F, 0.01F, () -> clickMode.currentMode.equals("1.9"));
        minAps = new NumberSetting("Min CPS", "Минимальное количество кликов в секунду", 12, 1, 20, 1, () -> clickMode.currentMode.equals("1.8"), NumberSetting.NumberType.APS);
        maxAps = new NumberSetting("Max CPS", "Максимальное количество кликов в секунду", 13, 1, 20, 1, () -> clickMode.currentMode.equals("1.8"), NumberSetting.NumberType.APS);
        hitChance = new NumberSetting("HitChance", "Шанс удара", 100, 1, 100, 5, () -> true);
        range = new NumberSetting("AttackRange", "Дистанция в которой вы можете ударить игрока", 3.6F, 3, 6, 0.1F, () -> true);
        players = new BooleanSetting("Attack Players", "Позволяет бить игроков", true, () -> true);
        armorStands = new BooleanSetting("Attack Armor Stands", "Позволяет бить армор-стенды", true, () -> true);
        monsters = new BooleanSetting("Attack Monsters", "Позволяет бить монстров", true, () -> true);
        pets = new BooleanSetting("Attack Pets", "Позволяет бить прирученных животных", true, () -> true);
        animals = new BooleanSetting("Attack Animals", "Позволяет бить безобидных мобов", false, () -> true);
        team = new BooleanSetting("Attack Teams", "Позволяет бить тимейтов на мини-играх", false, () -> true);
        invis = new BooleanSetting("Attack Invisible", "Позволяет бить невидемых существ", true, () -> true);
        nakedPlayer = new BooleanSetting("Ignore Naked Players", "Не бьет голых игроков", false, () -> true);
        walls = new BooleanSetting("Walls", "Позволяет бить сквозь стены", true, () -> true);
        bypass = new BooleanSetting("Hit Through Walls Bypass", "Обход ударов сквозь стену", true, () -> true);
        sprinting = new BooleanSetting("Stop Sprinting", "Автоматически выключает спринт", false, () -> true);
        weaponOnly = new BooleanSetting("Weapon Only", "Позволяет бить только с оружием в руках", false, () -> true);
        usingItemCheck = new BooleanSetting("Using Item Check", "Не бьет если вы используете меч, еду и т.д", false, () -> true);
        shieldBreaker = new BooleanSetting("Break Shield", "Автоматически ломает щит сопернику", false, () -> true);
        shieldFixer = new BooleanSetting("Shield Sync Fix", "Позволяет бить игроков через щит (обход)", false, () -> true);
        breakerDelay = new NumberSetting("Breaker Delay", "Регулировка ломания щита", 100, 0, 600, 10, () -> shieldBreaker.getCurrentValue());
        autoShieldUnPress = new BooleanSetting("Auto Shield UnPress", "Автоматически отжимает щит если у соперника топор в руках", false, () -> true);
        shieldBlockCheck = new BooleanSetting("Shield Block Check", "Не бьет соперника если он прикрыт щитом", false, () -> true);
        autoBlock = new BooleanSetting("Auto Block", "Автоматически жмет пкм при ударе (нужно для 1.8 серверов)", false, () -> true);
        autoDisable = new BooleanSetting("Auto Disable", "Автоматически выключает киллаура при смерти и т.д", false, () -> true);
        onlyCrit = new BooleanSetting("Only Critical", "Бьет в нужный момент для крита", false, () -> true);
        critFallDistance = new NumberSetting("Criticals Fall Distance", "Регулировка дистанции до земли для крита", 0.2F, 0.1F, 1F, 0.01f, () -> onlyCrit.getCurrentValue());
        targetHud = new BooleanSetting("TargetHUD", "Отображает хп, еду, броню соперника на экране", true, () -> true);
        targetHudColor = new ColorSetting("TargetHUD Color", Color.PINK.getRGB(), () -> targetHud.getCurrentValue() && (targetHudMode.currentMode.equals("Astolfo") || targetHudMode.currentMode.equals("Novoline Old") || targetHudMode.currentMode.equals("Novoline New")));

        addSettings(rotationMode, swingMode, clickMode, sort, attackCoolDown, minAps, maxAps, strafeMode, rotationStrafeMode, targetHud, targetHudMode, targetHudColor, targetEsp, targetEspMode, targetEspColor, circleRange, points, fov, range, hitChance,
                rotPredict, minSpeedRotation, maxSpeedRotation, pitchValue, randomRotation, randomYaw, randomPitch, visualYaw, visualPitch, clientLook, raycast, players, armorStands, monsters, animals, pets, invis, team, nakedPlayer, attackInInvetory, walls, bypass,
                sprinting, weaponOnly, usingItemCheck, autoShieldUnPress, shieldBlockCheck, shieldBreaker, breakerDelay,
                shieldFixer, autoBlock, autoDisable, onlyCrit, critFallDistance);
    }

    @EventTarget
    public void onPreMotion(EventPreMotion event) {
        if (mc.player == null || !mc.player.isAlive()) {
            target = null;
            return;
        }

        KillAuraHelper.toggleOffChecks();
        target = KillAuraHelper.getSortEntities();

        if (target != null && target.isAlive() && target.getHealth() > 0.0F) {
            float[] rots = RotationHelper.getRotations(target, false, maxSpeedRotation.getCurrentValue(), minSpeedRotation.getCurrentValue(), randomYaw.getCurrentValue(), randomPitch.getCurrentValue());
            if (rotationMode.currentMode.equals("Packet")) {
                event.setYaw(rots[0]);
                event.setPitch(rots[1]);
                if (visualYaw.getCurrentValue()) {
                    mc.player.yHeadRot = rots[0];
                    mc.player.yBodyRot = rots[0];
                }
                if (visualPitch.getCurrentValue()) {
                    mc.player.setXRot(rots[1]);
                }
            }

            boolean canAttack = true;
            if (weaponOnly.getCurrentValue()) {
                canAttack = mc.player.getHeldItemMainhand().getItem() instanceof net.minecraft.world.item.SwordItem || mc.player.getHeldItemMainhand().getItem() instanceof net.minecraft.world.item.AxeItem;
            }

            if (canAttack) {
                boolean clickReady = false;
                if (clickMode.currentMode.equals("1.9")) {
                    clickReady = mc.player.getCooledAttackStrength(0) >= attackCoolDown.getCurrentValue();
                } else {
                    int delay = (int) (1000 / MathematicHelper.randomFloat(minAps.getCurrentValue(), maxAps.getCurrentValue()));
                    if (timer.hasReached(delay)) {
                        clickReady = true;
                        timer.reset();
                    }
                }

                if (clickReady) {
                    if (onlyCrit.getCurrentValue()) {
                        if (mc.player.fallDistance <= critFallDistance.getCurrentValue() && !mc.player.onGround) {
                            return;
                        }
                    }

                    mc.playerController.attackEntity(mc.player, target);
                    if (swingMode.currentMode.equals("Client")) {
                        mc.player.swingArm(EnumHand.MAIN_HAND);
                    } else if (swingMode.currentMode.equals("Server")) {
                        mc.player.connection.send(new net.minecraft.network.protocol.game.ServerboundSwingPacket(net.minecraft.world.InteractionHand.MAIN_HAND));
                    }
                }
            }
        }
    }

    @Override
    public void onDisable() {
        target = null;
        super.onDisable();
    }
}