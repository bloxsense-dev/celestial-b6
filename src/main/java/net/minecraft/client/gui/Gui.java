package net.minecraft.client.gui;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.BufferBuilder;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.BanDetails;
import com.mojang.authlib.yggdrasil.ProfileActionType;
import com.mojang.authlib.yggdrasil.ProfileResult;
import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.GameLoadCookie;
import net.minecraft.client.InputType;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.gui.components.toasts.ToastManager;
import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.client.gui.font.ActiveArea;
import net.minecraft.client.gui.font.EmptyArea;
import net.minecraft.client.gui.font.TextRenderable;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.gui.screens.AccessibilityOnboardingScreen;
import net.minecraft.client.gui.screens.BanNoticeScreens;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.InBedChatScreen;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screens.debug.DebugOptionsScreen;
import net.minecraft.client.gui.screens.social.SocialInteractionsScreen;
import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.quickplay.QuickPlay;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.state.gui.ColoredRectangleRenderState;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import net.minecraft.client.resources.SplashManager;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.util.ARGB;
import net.minecraft.util.CommonLinks;
import net.minecraft.util.Util;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.profiling.Zone;
import net.optifine.Config;
import net.optifine.entity.model.CustomEntityModels;
import net.optifine.reflect.Reflector;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;

public class Gui {
    private final net.minecraft.client.gui.GuiNewChat chatGuiBridge = new net.minecraft.client.gui.GuiNewChat();

    public float zLevel = 0.0F;

    public void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
        float f = (float)(startColor >> 24 & 255) / 255.0F;
        float f1 = (float)(startColor >> 16 & 255) / 255.0F;
        float f2 = (float)(startColor >> 8 & 255) / 255.0F;
        float f3 = (float)(startColor & 255) / 255.0F;
        float f4 = (float)(endColor >> 24 & 255) / 255.0F;
        float f5 = (float)(endColor >> 16 & 255) / 255.0F;
        float f6 = (float)(endColor >> 8 & 255) / 255.0F;
        float f7 = (float)(endColor & 255) / 255.0F;
        org.lwjgl.opengl.GL11.glDisable(3553);
        org.lwjgl.opengl.GL11.glEnable(3042);
        org.lwjgl.opengl.GL11.glBlendFunc(770, 771);
        org.lwjgl.opengl.GL11.glShadeModel(7425);
        org.lwjgl.opengl.GL11.glBegin(7);
        org.lwjgl.opengl.GL11.glColor4f(f1, f2, f3, f);
        org.lwjgl.opengl.GL11.glVertex3f((float)right, (float)top, this.zLevel);
        org.lwjgl.opengl.GL11.glVertex3f((float)left, (float)top, this.zLevel);
        org.lwjgl.opengl.GL11.glColor4f(f5, f6, f7, f4);
        org.lwjgl.opengl.GL11.glVertex3f((float)left, (float)bottom, this.zLevel);
        org.lwjgl.opengl.GL11.glVertex3f((float)right, (float)bottom, this.zLevel);
        org.lwjgl.opengl.GL11.glEnd();
        org.lwjgl.opengl.GL11.glShadeModel(7424);
        org.lwjgl.opengl.GL11.glDisable(3042);
        org.lwjgl.opengl.GL11.glEnable(3553);
    }

    public void drawGradientRect(float left, float top, float right, float bottom, int startColor, int endColor) {
        drawGradientRect((int)left, (int)top, (int)right, (int)bottom, startColor, endColor);
    }

    public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
        float u0 = u / textureWidth;
        float v0 = v / textureHeight;
        float u1 = (u + width) / textureWidth;
        float v1 = (v + height) / textureHeight;
        org.lwjgl.opengl.GL11.glBegin(org.lwjgl.opengl.GL11.GL_QUADS);
        org.lwjgl.opengl.GL11.glTexCoord2f(u0, v1);
        org.lwjgl.opengl.GL11.glVertex2f(x, y + height);
        org.lwjgl.opengl.GL11.glTexCoord2f(u1, v1);
        org.lwjgl.opengl.GL11.glVertex2f(x + width, y + height);
        org.lwjgl.opengl.GL11.glTexCoord2f(u1, v0);
        org.lwjgl.opengl.GL11.glVertex2f(x + width, y);
        org.lwjgl.opengl.GL11.glTexCoord2f(u0, v0);
        org.lwjgl.opengl.GL11.glVertex2f(x, y);
        org.lwjgl.opengl.GL11.glEnd();
    }

    public net.minecraft.client.gui.GuiNewChat getChatGUI() {
        return this.chatGuiBridge;
    }
    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        org.lwjgl.opengl.GL11.glBegin(org.lwjgl.opengl.GL11.GL_QUADS);
        org.lwjgl.opengl.GL11.glTexCoord2f((float)textureX * f, (float)(textureY + height) * f1);
        org.lwjgl.opengl.GL11.glVertex2f((float)x, (float)(y + height));
        org.lwjgl.opengl.GL11.glTexCoord2f((float)(textureX + width) * f, (float)(textureY + height) * f1);
        org.lwjgl.opengl.GL11.glVertex2f((float)(x + width), (float)(y + height));
        org.lwjgl.opengl.GL11.glTexCoord2f((float)(textureX + width) * f, (float)textureY * f1);
        org.lwjgl.opengl.GL11.glVertex2f((float)(x + width), (float)y);
        org.lwjgl.opengl.GL11.glTexCoord2f((float)textureX * f, (float)textureY * f1);
        org.lwjgl.opengl.GL11.glVertex2f((float)x, (float)y);
        org.lwjgl.opengl.GL11.glEnd();
    }

    public void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV) {
        drawTexturedModalRect((int) xCoord, (int) yCoord, minU, minV, maxU, maxV);
    }

    public static void drawScaledCustomSizeModalRect(float x, float y, float u, float v, float uWidth, float vHeight, float width, float height, float tileWidth, float tileHeight) {
        float f = 1.0F / tileWidth;
        float f1 = 1.0F / tileHeight;
        org.lwjgl.opengl.GL11.glBegin(org.lwjgl.opengl.GL11.GL_QUADS);
        org.lwjgl.opengl.GL11.glTexCoord2f(u * f, (v + vHeight) * f1);
        org.lwjgl.opengl.GL11.glVertex2f(x, y + height);
        org.lwjgl.opengl.GL11.glTexCoord2f((u + uWidth) * f, (v + vHeight) * f1);
        org.lwjgl.opengl.GL11.glVertex2f(x + width, y + height);
        org.lwjgl.opengl.GL11.glTexCoord2f((u + uWidth) * f, v * f1);
        org.lwjgl.opengl.GL11.glVertex2f(x + width, y);
        org.lwjgl.opengl.GL11.glTexCoord2f(u * f, v * f1);
        org.lwjgl.opengl.GL11.glVertex2f(x, y);
        org.lwjgl.opengl.GL11.glEnd();
    }

    public static void drawRect(int left, int top, int right, int bottom, int color) {
        if (left < right) {
            int i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            int j = top;
            top = bottom;
            bottom = j;
        }
        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        org.lwjgl.opengl.GL11.glEnable(3042);
        org.lwjgl.opengl.GL11.glDisable(3553);
        org.lwjgl.opengl.GL11.glBlendFunc(770, 771);
        org.lwjgl.opengl.GL11.glColor4f(f, f1, f2, f3);
        org.lwjgl.opengl.GL11.glBegin(7);
        org.lwjgl.opengl.GL11.glVertex2f((float)left, (float)bottom);
        org.lwjgl.opengl.GL11.glVertex2f((float)right, (float)bottom);
        org.lwjgl.opengl.GL11.glVertex2f((float)right, (float)top);
        org.lwjgl.opengl.GL11.glVertex2f((float)left, (float)top);
        org.lwjgl.opengl.GL11.glEnd();
        org.lwjgl.opengl.GL11.glEnable(3553);
        org.lwjgl.opengl.GL11.glDisable(3042);
    }
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Component SOCIAL_INTERACTIONS_NOT_AVAILABLE = Component.translatable("multiplayer.socialInteractions.not_available");
    public static final Component SAVING_LEVEL = Component.translatable("menu.savingLevel");
    private final Minecraft minecraft;
    public final Hud hud;
    private final GuiRenderState guiRenderState;
    private Screen screen;
    private Overlay overlay;
    private boolean clientLevelTeardownInProgress;
    private final SplashManager splashManager;
    private final ToastManager toastManager;
    private final ChatListener chatListener;
    private TutorialToast socialInteractionsToast;
    private boolean guiLoadingVisible = false;

    public Gui() {
        this.minecraft = Minecraft.getInstance();
        this.hud = null;
        this.splashManager = null;
        this.toastManager = null;
        this.chatListener = null;
        this.guiRenderState = null;
    }

    public Gui(Minecraft minecraft, Hud hud, GuiRenderState guiRenderState) {
        this.minecraft = minecraft;
        this.hud = hud;
        this.splashManager = new SplashManager(minecraft.getUser());
        this.toastManager = new ToastManager(minecraft, minecraft.options);
        this.chatListener = new ChatListener(minecraft);
        this.chatListener.setMessageDelay(minecraft.options.chatDelay().get());
        this.guiRenderState = guiRenderState;
    }

    public void registerReloadListeners(ReloadableResourceManager resourceManager) {
        resourceManager.registerReloadListener(this.splashManager);
        this.hud.registerReloadListeners(resourceManager);
    }

    public void tick() {
        if (this.overlay != null) {
            this.overlay.tick();
        }
        ProfilerFiller profiler = Profiler.get();
        this.chatListener.tick();
        this.hud.tick(this.minecraft.isPaused());
        profiler.push("screen");
        if (this.screen != null) {
            this.screen.tick();
        }
        LocalPlayer player = this.minecraft.player;
        if (this.screen != null || player == null) {
            if (this.screen instanceof InBedChatScreen inBedScreen && !player.isSleeping()) {
                inBedScreen.onPlayerWokeUp();
            }
        } else if (player.isDeadOrDying() && !(this.screen instanceof DeathScreen)) {
            this.setScreen(null);
        } else if (player.isSleeping() && this.minecraft.level != null) {
            this.hud.getChat().openScreen(ChatComponent.ChatMethod.MESSAGE, InBedChatScreen::new);
        }

        if (this.screen != null) {
            try {
                this.screen.tick();
            } catch (Throwable t) {
                CrashReport report = CrashReport.forThrowable(t, "Ticking screen");
                this.screen.fillCrashDetails(report);
                throw new ReportedException(report);
            }
        }

        profiler.pop();
        if (this.overlay != null) {
            this.overlay.tick();
        }

        if (!this.minecraft.getDebugOverlay().showDebugScreen()) {
            this.hud.clearCache();
        }
    }

    public static void drawRect(double left, double top, double right, double bottom, int color) {
        org.celestial.client.helpers.render.rect.RectHelper.drawRect(left, top, right, bottom, color);
    }

    public void update() {
        this.toastManager.update();

        try {
            if (this.screen != null) {
                this.screen.handleDelayedNarration();
            }
        } catch (Throwable t) {
            CrashReport report = CrashReport.forThrowable(t, "Narrating screen");
            CrashReportCategory category = report.addCategory("Screen details");
            category.setDetail("Screen name", () -> this.screen.getClass().getCanonicalName());
            throw new ReportedException(report);
        }
    }

    public void extractRenderState(DeltaTracker deltaTracker, boolean shouldRenderLevel, boolean resourcesLoaded) {
        ProfilerFiller profiler = Profiler.get();
        int xMouse = (int)this.minecraft.mouseHandler.getScaledXPos(this.minecraft.getWindow());
        int yMouse = (int)this.minecraft.mouseHandler.getScaledYPos(this.minecraft.getWindow());
        profiler.push("gui");
        this.guiRenderState.reset();
        GuiGraphicsExtractor graphics = new GuiGraphicsExtractor(this.minecraft, this.guiRenderState, xMouse, yMouse);
        if (shouldRenderLevel) {
            profiler.push("hud");
            this.hud.extractRenderState(graphics, deltaTracker);
            profiler.pop();
        }

        if (this.guiLoadingVisible != (this.minecraft.gui.overlay() != null)) {
            if (this.minecraft.gui.overlay() != null) {
                LoadingOverlay.registerTextures(this.minecraft.getTextureManager());
                if (this.minecraft.gui.overlay() instanceof LoadingOverlay) {
                    LoadingOverlay rlpg = (LoadingOverlay)this.minecraft.gui.overlay();
                    rlpg.update();
                }
            }

            this.guiLoadingVisible = this.minecraft.gui.overlay() != null;
        }

        if (this.overlay != null) {
            profiler.push("overlay");

            try {
                this.overlay.extractRenderState(graphics, xMouse, yMouse, deltaTracker.getGameTimeDeltaTicks());
            } catch (Throwable t) {
                CrashReport report = CrashReport.forThrowable(t, "Extracting overlay render state");
                CrashReportCategory category = report.addCategory("Overlay details");
                category.setDetail("Overlay name", () -> this.overlay.getClass().getCanonicalName());
                throw new ReportedException(report);
            }

            profiler.pop();
        } else if (resourcesLoaded && this.screen != null) {
            profiler.push("screen");

            try {
                if (Config.isCustomEntityModels()) {
                    CustomEntityModels.onRenderScreen(this.minecraft.gui.screen);
                }

                if (Reflector.ForgeHooksClient_drawScreen.exists()) {
                    Reflector.callVoid(
                        Reflector.ForgeHooksClient_drawScreen, this.minecraft.gui.screen, graphics, xMouse, yMouse, deltaTracker.getGameTimeDeltaTicks()
                    );
                } else {
                    this.screen.extractRenderStateWithTooltipAndSubtitles(graphics, xMouse, yMouse, deltaTracker.getGameTimeDeltaTicks());
                }
            } catch (Throwable t) {
                CrashReport report = CrashReport.forThrowable(t, "Rendering screen");
                CrashReportCategory category = report.addCategory("Screen render details");
                category.setDetail("Screen name", () -> this.screen.getClass().getCanonicalName());
                this.minecraft.mouseHandler.fillMousePositionDetails(category, this.minecraft.getWindow());
                throw new ReportedException(report);
            }

            if (SharedConstants.DEBUG_CURSOR_POS) {
                this.minecraft.mouseHandler.drawDebugMouseInfo(this.minecraft.font, graphics);
            }

            profiler.pop();
        }

        if (shouldRenderLevel) {
            this.hud.extractSavingIndicator(graphics, deltaTracker);
        }

        if (resourcesLoaded) {
            try (Zone ignored = profiler.zone("toasts")) {
                this.toastManager().extractRenderState(graphics);
            }
        }

        if (!(this.screen instanceof DebugOptionsScreen)) {
            this.hud.extractDebugOverlay(graphics);
        }

        this.hud.extractDeferredSubtitles();
        if (SharedConstants.DEBUG_ACTIVE_TEXT_AREAS) {
            this.renderActiveTextDebug();
        }

        profiler.pop();
        graphics.applyCursor(this.minecraft.getWindow());
    }

    @Contract(pure = true)
    public Screen screen() {
        return this.screen;
    }

    public Screen getScreen() {
        return this.screen;
    }

    public void setScreen(Screen screen) {
        if (screen instanceof TitleScreen || (screen != null && screen.getClass().getSimpleName().contains("Onboarding"))) {
            screen = new GuiMainMenu();
        }

        if (SharedConstants.IS_RUNNING_IN_IDE && Thread.currentThread() != this.minecraft.getRunningThread()) {
            LOGGER.error("setScreen called from non-game thread");
        }

        if (this.screen != null) {
            this.screen.removed();
        } else {
            this.minecraft.setLastInputType(InputType.NONE);
        }

        if (screen == null) {
            if (this.clientLevelTeardownInProgress) {
                throw new IllegalStateException("Trying to return to in-game GUI during disconnection");
            }

            if (this.minecraft.level == null) {
                screen = new GuiMainMenu();
            } else if (this.minecraft.player.isDeadOrDying()) {
                if (this.minecraft.player.shouldShowDeathScreen()) {
                    screen = new DeathScreen(null, this.minecraft.level.getLevelData().isHardcore(), this.minecraft.player);
                } else {
                    this.minecraft.player.respawn();
                }
            } else {
                screen = this.hud.getChat().restoreChatScreen();
            }
        }

        this.screen = screen;
        if (this.screen != null) {
            this.screen.added();
        }

        if (screen != null) {
            this.minecraft.mouseHandler.releaseMouse();
            KeyMapping.releaseAll();
            screen.init(this.minecraft.getWindow().getGuiScaledWidth(), this.minecraft.getWindow().getGuiScaledHeight());
        } else {
            this.minecraft.textInputManager().stopTextInput();
            if (this.minecraft.level != null) {
                KeyMapping.restoreToggleStatesOnScreenClosed();
            }

            this.minecraft.getSoundManager().resume();
            this.minecraft.mouseHandler.grabMouse();
        }

        this.minecraft.updateTitle();
    }

    @Contract(pure = true)
    public Overlay overlay() {
        return this.overlay;
    }

    public void setOverlay(Overlay overlay) {
        this.overlay = overlay;
    }

    public boolean isPausing() {
        return this.screen != null && this.screen.isPauseScreen() || this.overlay != null && this.overlay.isPausing();
    }

    public SplashManager splashManager() {
        return this.splashManager;
    }

    public ToastManager toastManager() {
        return this.toastManager;
    }

    public ChatListener chatListener() {
        return this.chatListener;
    }

    public void addSocialInteractionsToast() {
        Component title = Component.translatable("tutorial.socialInteractions.title");
        Component message = Component.translatable("tutorial.socialInteractions.description", Tutorial.key("socialInteractions"));
        this.socialInteractionsToast = new TutorialToast(this.minecraft.font, TutorialToast.Icons.SOCIAL_INTERACTIONS, title, message, true, 8000);
        this.toastManager.addToast(this.socialInteractionsToast);
    }

    public void setPauseScreen(boolean suppressPauseMenuIfWeReallyArePausing, boolean canGameReallyBePaused) {
        if (this.screen == null) {
            if (canGameReallyBePaused) {
                this.setScreen(new PauseScreen(!suppressPauseMenuIfWeReallyArePausing));
            } else {
                this.setScreen(new PauseScreen(true));
            }
        }
    }

    public void handleKeybinds() {
        Options options = this.minecraft.options;

        while (options.keyToggleGui.consumeClick()) {
            this.hud.toggle();
        }

        while (options.keyAdvancements.consumeClick()) {
            this.setScreen(new AdvancementsScreen(this.minecraft.player.connection.getAdvancements()));
        }

        while (options.keySocialInteractions.consumeClick()) {
            if (!this.minecraft.isMultiplayerServer() && !SharedConstants.DEBUG_SOCIAL_INTERACTIONS) {
                this.chatListener.handleOverlay(SOCIAL_INTERACTIONS_NOT_AVAILABLE);
                this.minecraft.getNarrator().saySystemNow(SOCIAL_INTERACTIONS_NOT_AVAILABLE);
            } else {
                if (this.socialInteractionsToast != null) {
                    this.socialInteractionsToast.hide();
                    this.socialInteractionsToast = null;
                }

                this.setScreen(new SocialInteractionsScreen());
            }
        }

        while (options.keyChat.consumeClick()) {
            this.openChatScreen(ChatComponent.ChatMethod.MESSAGE);
        }

        if (this.screen == null && this.overlay == null && options.keyCommand.consumeClick()) {
            this.openChatScreen(ChatComponent.ChatMethod.COMMAND);
        }
    }

    public void openChatScreen(ChatComponent.ChatMethod chatMethod) {
        if (this.minecraft.player != null) {
            this.hud.getChat().openScreen(chatMethod, ChatScreen::new);
        }
    }

    public void openChatAndAddText(ChatComponent.ChatMethod chatMethod, String text) {
        this.openChatScreen(ChatComponent.ChatMethod.COMMAND);
        if (this.screen instanceof ChatScreen chatScreen) {
            chatScreen.insertText(text, false);
        }
    }

    public Runnable buildInitialScreens(GameLoadCookie cookie) {
        return () -> {
            if (cookie != null && cookie.quickPlayData().isEnabled()) {
                QuickPlay.connect(this.minecraft, cookie.quickPlayData().variant(), cookie.realmsClient());
            } else {
                this.setScreen(new GuiMainMenu());
            }
        };
    }

    private boolean addInitialScreens(List<Function<Runnable, Screen>> screens) {
        boolean onboardingScreenAdded = false;
        if (this.minecraft.options.onboardAccessibility || SharedConstants.DEBUG_FORCE_ONBOARDING_SCREEN) {
            screens.add(next -> new AccessibilityOnboardingScreen(this.minecraft.options, next));
            onboardingScreenAdded = true;
        }

        BanDetails multiplayerBan = this.minecraft.multiplayerBan();
        if (multiplayerBan != null) {
            screens.add(next -> BanNoticeScreens.create(result -> {
                if (result) {
                    Util.getPlatform().openUri(CommonLinks.SUSPENSION_HELP);
                }

                next.run();
            }, multiplayerBan));
        }

        ProfileResult profileResult = this.minecraft.getProfileResult();
        if (profileResult != null) {
            GameProfile profile = profileResult.profile();
            Set<ProfileActionType> actions = profileResult.actions();
            if (actions.contains(ProfileActionType.FORCED_NAME_CHANGE)) {
                screens.add(onClose -> BanNoticeScreens.createNameBan(profile.name(), onClose));
            }

            if (actions.contains(ProfileActionType.USING_BANNED_SKIN)) {
                screens.add(BanNoticeScreens::createSkinBan);
            }
        }

        return onboardingScreenAdded;
    }

    public boolean canInterruptScreen() {
        return (this.screen == null || this.screen.canInterruptWithAnotherScreen()) && !this.clientLevelTeardownInProgress;
    }

    public void setClientLevelTeardownInProgress(boolean clientLevelTeardownInProgress) {
        this.clientLevelTeardownInProgress = clientLevelTeardownInProgress;
    }

    private void renderActiveTextDebug() {
        this.guiRenderState.nextStratum();
        this.guiRenderState
            .forEachText(
                text -> text.ensurePrepared()
                    .visit(
                        new Font.GlyphVisitor() {
                            private int index;

                            @Override
                            public void acceptGlyph(TextRenderable.Styled glyph) {
                                this.renderDebugMarkers(glyph, false);
                            }

                            @Override
                            public void acceptEmptyArea(EmptyArea empty) {
                                this.renderDebugMarkers(empty, true);
                            }

                            private void renderDebugMarkers(ActiveArea glyph, boolean isEmpty) {
                                int intensity = (isEmpty ? 128 : 255) - (this.index++ & 1) * 64;
                                Style style = glyph.style();
                                int red = style.getClickEvent() != null ? intensity : 0;
                                int green = style.getHoverEvent() != null ? intensity : 0;
                                int blue = red != 0 && green != 0 ? 0 : intensity;
                                int color = ARGB.color(128, red, green, blue);
                                Gui.this.guiRenderState
                                    .addGuiElement(
                                        new ColoredRectangleRenderState(
                                            RenderPipelines.GUI,
                                            TextureSetup.noTexture(),
                                            text.pose,
                                            (int)glyph.activeLeft(),
                                            (int)glyph.activeTop(),
                                            (int)glyph.activeRight(),
                                            (int)glyph.activeBottom(),
                                            color,
                                            color,
                                            text.scissor
                                        )
                                    );
                            }
                        }
                    )
            );
    }


    public void drawGradientRect(double left, double top, double right, double bottom, int startColor, int endColor) {
        drawGradientRect((int) left, (int) top, (int) right, (int) bottom, startColor, endColor);
    }
}
