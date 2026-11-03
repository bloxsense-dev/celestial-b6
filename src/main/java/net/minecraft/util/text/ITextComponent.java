package net.minecraft.util.text;

public interface ITextComponent {
    String getFormattedText();
    String getUnformattedText();
    ITextComponent appendText(String text);
}
