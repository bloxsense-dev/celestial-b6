/*
 * Decompiled with CFR 0.150.
 */
package org.celestial.client.feature.impl.misc;

import org.celestial.client.feature.Feature;
import org.celestial.client.feature.impl.Type;
import org.celestial.client.settings.impl.BooleanSetting;

public class StreamerMode
        extends Feature {
    public static BooleanSetting ownName;
    public static BooleanSetting otherNames;
    public static BooleanSetting friendNames;
    public static BooleanSetting skinSpoof;
    public static BooleanSetting tabSpoof;
    public static BooleanSetting scoreBoardSpoof;
    public static BooleanSetting warpSpoof;
    public static BooleanSetting authSpoof;

    public StreamerMode() {
        super("StreamerMode", "Позволяет скрывать информацию о себе и других игроках на видео или стриме", Type.Misc);
        ownName = new BooleanSetting("Own Name", true, () -> true);
        otherNames = new BooleanSetting("Other Names", true, () -> true);
        friendNames = new BooleanSetting("Friend Names", false, () -> true);
        tabSpoof = new BooleanSetting("Tab Spoof", true, () -> true);
        skinSpoof = new BooleanSetting("Skin Spoof", true, () -> true);
        scoreBoardSpoof = new BooleanSetting("ScoreBoard Spoof", true, () -> true);
        warpSpoof = new BooleanSetting("Warp Spoof", true, () -> true);
        authSpoof = new BooleanSetting("Auth Spoof", true, () -> true);
        this.addSettings(ownName, otherNames, friendNames, tabSpoof, skinSpoof, scoreBoardSpoof, warpSpoof, authSpoof);
    }
}

