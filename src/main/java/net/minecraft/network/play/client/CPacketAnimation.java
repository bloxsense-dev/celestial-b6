package net.minecraft.network.play.client;

import net.minecraft.network.protocol.game.ServerboundSwingPacket;
import net.minecraft.util.EnumHand;
import net.minecraft.world.InteractionHand;

public class CPacketAnimation extends ServerboundSwingPacket {
    public CPacketAnimation() {
        super(InteractionHand.MAIN_HAND);
    }

    public CPacketAnimation(EnumHand hand) {
        super(hand != null ? hand.toHand() : InteractionHand.MAIN_HAND);
    }

    public CPacketAnimation(InteractionHand hand) {
        super(hand != null ? hand : InteractionHand.MAIN_HAND);
    }

    public EnumHand getEnumHand() {
        return EnumHand.fromHand(super.getHand());
    }
}
