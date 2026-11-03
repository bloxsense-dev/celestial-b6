package net.minecraft.client.gui;

public class GuiPageButtonList {
    public interface GuiResponder {
        void setEntryValue(int id, boolean value);
        void setEntryValue(int id, float value);
        void setEntryValue(int id, String value);
    }
}
