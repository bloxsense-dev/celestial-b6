package net.minecraft.client.gui;

import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.LanguageSelectScreen;
import net.minecraft.client.resources.language.LanguageManager;

public class GuiLanguage extends LanguageSelectScreen {
    public GuiLanguage(Screen parent, Options options, LanguageManager languageManager) {
        super(parent, options, languageManager);
    }
}
