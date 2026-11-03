package org.celestial.client.cmd.impl;

import net.minecraft.client.Minecraft;
import org.celestial.client.cmd.CommandAbstract;
import org.celestial.client.helpers.misc.ChatHelper;

public class ClipCommand extends CommandAbstract {

    Minecraft mc =  Minecraft.getMinecraft();

    public ClipCommand() {
        super("clip", "clip | hclip", "§6.clip | (hclip) + | - §3<value> | down", "clip", "hclip");
    }

    @Override
    public void execute(String... args) {
        if (args.length > 1) {
            if (args[0].equalsIgnoreCase("clip")) {
                try {
                    if (args[1].equals("down")) {
                        mc.player.setPositionAndUpdate(mc.player.posX, -2, mc.player.posZ);
                    }
                    if (args[1].equals("+")) {
                        mc.player.setPositionAndUpdate(mc.player.posX, mc.player.posY + Double.parseDouble(args[2]), mc.player.posZ);
                    }
                    if (args[1].equals("-")) {
                        mc.player.setPositionAndUpdate(mc.player.posX, mc.player.posY - Double.parseDouble(args[2]), mc.player.posZ);
                    }
                } catch (Exception ignored) {
                }
            }
            if (args[0].equalsIgnoreCase("hclip")) {
                double x = mc.player.posX;
                double y = mc.player.posY;
                double z = mc.player.posZ;
                double yaw = mc.player.rotationYaw * 0.017453292;
                try {
                    if (args[1].equals("+")) {
                        mc.player.setPositionAndUpdate(x - Math.sin(yaw) * Double.parseDouble(args[2]), y, z + Math.cos(yaw) * Double.parseDouble(args[2]));
                    }
                    if (args[1].equals("-")) {
                        mc.player.setPositionAndUpdate(x + Math.sin(yaw) * Double.parseDouble(args[2]), y, z - Math.cos(yaw) * Double.parseDouble(args[2]));
                    }
                } catch (Exception ignored) {
                }
            }
        } else {
            ChatHelper.addChatMessage(getUsage());
        }
    }
}
