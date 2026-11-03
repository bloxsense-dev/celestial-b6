package org.lwjgl;

import java.awt.Desktop;
import java.io.File;

public class Sys {
    public static long getTime() {
        return System.currentTimeMillis();
    }
    public static long getTimerResolution() {
        return 1000L;
    }
    public static void openURL(String url) {
        try {
            Desktop.getDesktop().open(new File(url));
        } catch (Throwable ignored) {}
    }
}
