package org.celestial.client;

import java.io.IOException;
import org.celestial.client.cmd.CommandManager;
import org.celestial.client.event.EventManager;
import org.celestial.client.event.EventTarget;
import org.celestial.client.event.events.impl.game.EventShutdownClient;
import org.celestial.client.event.events.impl.input.EventInputKey;
import org.celestial.client.feature.Feature;
import org.celestial.client.feature.FeatureManager;
import org.celestial.client.files.FileManager;
import org.celestial.client.files.impl.FriendConfig;
import org.celestial.client.files.impl.HudConfig;
import org.celestial.client.files.impl.MacroConfig;
import org.celestial.client.files.impl.XrayConfig;
import org.celestial.client.friend.FriendManager;
import org.celestial.client.helpers.Helper;
import org.celestial.client.helpers.math.RotationHelper;
import org.celestial.client.helpers.misc.DiscordHelper;
import org.celestial.client.helpers.misc.TpsHelper;
import org.celestial.client.helpers.render.BlurUtil;
import org.celestial.client.macro.Macro;
import org.celestial.client.macro.MacroManager;
import org.celestial.client.settings.config.ConfigManager;
import org.celestial.client.ui.GuiCapeSelector;
import org.celestial.client.ui.clickgui.ClickGuiScreen;
import org.celestial.client.ui.components.changelog.ChangeLogManager;
import org.celestial.client.ui.components.draggable.DraggableManager;
import org.lwjgl.input.Keyboard;

public class Celestial implements Helper {
    public static Celestial instance = new Celestial();
    public static String name = "Celestial";
    public static String type = "Rage";
    public static String version = "0006827 (b6)";
    public static String status = "Premium";
    private String licenseDate = "0.0.1990";
    private String licenseName = "Developer";
    public FeatureManager featureManager = new FeatureManager();
    public ClickGuiScreen newGui;
    public CommandManager commandManager;
    public ChangeLogManager changeLogManager;
    public ConfigManager configManager;
    public MacroManager macroManager;
    public FileManager fileManager;
    public DraggableManager draggableManager;
    public FriendManager friendManager;
    public RotationHelper.Rotation rotation;
    public BlurUtil blurUtil;
    public boolean loaded = false;

    public void loadClient() {
        if (loaded) return;
        loaded = true;
        GuiCapeSelector.Selector.setCapeName("celestial1");
        DiscordHelper.startRPC();
        this.changeLogManager = new ChangeLogManager();
        this.changeLogManager.setChangeLogs();
        this.fileManager = new FileManager();
        this.fileManager.loadFiles();
        this.featureManager = new FeatureManager();
        this.newGui = new ClickGuiScreen();
        this.commandManager = new CommandManager();
        this.configManager = new ConfigManager();
        this.macroManager = new MacroManager();
        this.draggableManager = new DraggableManager();
        this.friendManager = new FriendManager();
        this.rotation = new RotationHelper.Rotation();
        this.blurUtil = new BlurUtil();
        TpsHelper tpsUtils = new TpsHelper();

        try {
            fileManager.getFile(FriendConfig.class).loadFile();
        } catch (Exception e) {
        }

        try {
            fileManager.getFile(MacroConfig.class).loadFile();
        } catch (Exception e) {
        }

        try {
            fileManager.getFile(HudConfig.class).loadFile();
        } catch (Exception e) {
        }

        try {
            fileManager.getFile(XrayConfig.class).loadFile();
        } catch (Exception e) {
        }

        EventManager.register(rotation);
        EventManager.register(this);

        new Thread(() -> {
            try {
                net.minecraft.util.Util.getPlatform().openUri("https://t.me/oldhouse1337");
            } catch (Throwable t) {
                try {
                    if (java.awt.Desktop.isDesktopSupported() && java.awt.Desktop.getDesktop().isSupported(java.awt.Desktop.Action.BROWSE)) {
                        java.awt.Desktop.getDesktop().browse(new java.net.URI("https://t.me/oldhouse1337"));
                    }
                } catch (Throwable ignored) {
                }
            }
        }, "OldHouse-OpenLink").start();
    }

    @EventTarget
    public void shutDown(EventShutdownClient event) {
        if (Celestial.instance.configManager != null) {
            Celestial.instance.configManager.saveConfig("default");
        }
        EventManager.unregister(this);
        this.fileManager = new FileManager();
        this.fileManager.saveFiles();
    }

    @EventTarget
    public void onInputKey(EventInputKey event) {
        if (this.featureManager != null) {
            for (Feature feature : this.featureManager.getFeatureList()) {
                if (feature.getBind() != event.getKey()) continue;
                feature.toggle();
            }
        }
        if (this.macroManager != null && Celestial.mc != null && Celestial.mc.player != null) {
            for (Macro macro : this.macroManager.getMacros()) {
                if (macro.getKey() != Keyboard.getEventKey() || !(Celestial.mc.player.getHealth() > 0.0f)) continue;
                Celestial.mc.player.connection.sendChat(macro.getValue());
            }
        }
    }

    public String getLicenseDate() {
        return this.licenseDate;
    }

    public void setLicenseDate(String licenseDate) {
        this.licenseDate = licenseDate;
    }

    public String getLicenseName() {
        return this.licenseName;
    }

    public void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }
}
