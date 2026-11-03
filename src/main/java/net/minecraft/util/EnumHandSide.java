package net.minecraft.util;

import net.minecraft.world.entity.HumanoidArm;

public enum EnumHandSide {
    LEFT(HumanoidArm.LEFT),
    RIGHT(HumanoidArm.RIGHT);

    private final HumanoidArm arm;

    EnumHandSide(HumanoidArm arm) {
        this.arm = arm;
    }

    public HumanoidArm toArm() {
        return this.arm;
    }

    public static EnumHandSide fromArm(HumanoidArm arm) {
        return arm == HumanoidArm.LEFT ? LEFT : RIGHT;
    }

    public EnumHandSide getOpposite() {
        return this == LEFT ? RIGHT : LEFT;
    }
}
