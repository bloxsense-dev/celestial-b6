package net.minecraft.util;

import java.util.regex.Pattern;

public class StringUtils {
    private static final Pattern PATTERN_CONTROL_CODE = Pattern.compile("(?i)\\u00a7[0-9A-FK-OR]");

    public static String stripControlCodes(String text) {
        return text == null ? null : PATTERN_CONTROL_CODE.matcher(text).replaceAll("");
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
