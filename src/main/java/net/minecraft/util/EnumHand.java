package net.minecraft.util;

import net.minecraft.world.InteractionHand;

public enum EnumHand {
    MAIN_HAND(InteractionHand.MAIN_HAND),
    OFF_HAND(InteractionHand.OFF_HAND);

    private final InteractionHand hand;

    EnumHand(InteractionHand hand) {
        this.hand = hand;
    }

    public InteractionHand toHand() {
        return this.hand;
    }

    public static EnumHand fromHand(InteractionHand hand) {
        return hand == InteractionHand.MAIN_HAND ? MAIN_HAND : OFF_HAND;
    }
}
