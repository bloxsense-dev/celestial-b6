package net.minecraft.util;

import net.minecraft.core.Direction;

public enum EnumFacing {
    DOWN(Direction.DOWN),
    UP(Direction.UP),
    NORTH(Direction.NORTH),
    SOUTH(Direction.SOUTH),
    WEST(Direction.WEST),
    EAST(Direction.EAST);

    public static final EnumFacing[] VALUES = values();
    public static final EnumFacing[] HORIZONTALS = new EnumFacing[] { NORTH, SOUTH, WEST, EAST };

    private final Direction direction;

    EnumFacing(Direction direction) {
        this.direction = direction;
    }

    public Direction toDirection() {
        return this.direction;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public String getName() {
        return this.direction.getName();
    }

    public int getIndex() {
        return this.direction.get3DDataValue();
    }

    public EnumFacing getOpposite() {
        return fromDirection(this.direction.getOpposite());
    }

    public int getFrontOffsetX() {
        return this.direction.getStepX();
    }

    public int getFrontOffsetY() {
        return this.direction.getStepY();
    }

    public int getFrontOffsetZ() {
        return this.direction.getStepZ();
    }

    public int getXOffset() {
        return this.direction.getStepX();
    }

    public int getYOffset() {
        return this.direction.getStepY();
    }

    public int getZOffset() {
        return this.direction.getStepZ();
    }

    public Axis getAxis() {
        return switch (this.direction.getAxis()) {
            case X -> Axis.X;
            case Y -> Axis.Y;
            case Z -> Axis.Z;
        };
    }

    public static EnumFacing fromDirection(Direction dir) {
        if (dir == null) return UP;
        return switch (dir) {
            case DOWN -> DOWN;
            case UP -> UP;
            case NORTH -> NORTH;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            case EAST -> EAST;
        };
    }

    public static EnumFacing byIndex(int index) {
        return VALUES[Math.floorMod(index, VALUES.length)];
    }

    public static EnumFacing getFront(int index) {
        return byIndex(index);
    }

    public static EnumFacing byName(String name) {
        if (name == null) return null;
        for (EnumFacing facing : VALUES) {
            if (facing.getName().equalsIgnoreCase(name)) return facing;
        }
        return null;
    }

    public enum Axis {
        X, Y, Z
    }
}
