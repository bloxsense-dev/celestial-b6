package net.minecraft.util.text;

public class TextComponentString implements ITextComponent {
    private String text;

    public TextComponentString(String text) {
        this.text = text != null ? text : "";
    }

    @Override
    public String getFormattedText() {
        return this.text;
    }

    @Override
    public String getUnformattedText() {
        return this.text;
    }

    @Override
    public ITextComponent appendText(String text) {
        this.text += text;
        return this;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
