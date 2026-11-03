package org.celestial.client.ui.font;

import net.minecraft.util.ResourceLocation;
import java.awt.Font;
import java.io.InputStream;

public class FontUtil {

    public static Font getFontFromTTF(ResourceLocation loc, float fontSize, int fontType) {
        try {
            String path = loc.getResourcePath();
            if (!path.startsWith("/")) path = "/" + path;
            if (!path.startsWith("/assets/minecraft/")) {
                path = "/assets/minecraft/" + (path.startsWith("/") ? path.substring(1) : path);
            }
            InputStream is = FontUtil.class.getResourceAsStream(path);
            if (is == null) {
                String fileName = loc.getResourcePath();
                if (fileName.contains("/")) {
                    fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
                }
                is = FontUtil.class.getResourceAsStream("/assets/minecraft/celestial/font/" + fileName);
            }
            if (is == null) {
                return new Font("SansSerif", fontType, (int) fontSize);
            }
            Font output = Font.createFont(fontType, is);
            output = output.deriveFont(fontSize);
            is.close();
            return output;
        } catch (Exception e) {
            return new Font("SansSerif", fontType, (int) fontSize);
        }
    }
}
