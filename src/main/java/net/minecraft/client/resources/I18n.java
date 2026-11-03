package net.minecraft.client.resources;

public class I18n {
    public static String format(String translateKey, Object... parameters) {
        return net.minecraft.client.resources.language.I18n.get(translateKey, parameters);
    }
}
