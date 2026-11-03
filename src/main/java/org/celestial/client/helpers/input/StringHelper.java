package org.celestial.client.helpers.input;

import java.util.regex.Pattern;

public class StringHelper {

    public static String format(String text) {
        if (text == null) return "";
        return Pattern.compile("(?i)§[0-9A-FK-OR]").matcher(text).replaceAll("");
    }

    public static String format(Object obj) {
        if (obj == null) return "";
        if (obj instanceof net.minecraft.network.chat.Component comp) {
            return format(comp.getString());
        }
        return format(obj.toString());
    }

}
