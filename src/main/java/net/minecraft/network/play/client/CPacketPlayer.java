package net.minecraft.network.play.client;

import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;

public class CPacketPlayer implements Packet<PacketListener> {
    public double x;
    public double y;
    public double z;
    public float yaw;
    public float pitch;
    public boolean onGround;
    public boolean moving;
    public boolean rotating;

    public CPacketPlayer() {}

    public CPacketPlayer(boolean onGround) {
        this.onGround = onGround;
    }

    public double getX(double defaultValue) {
        return moving ? x : defaultValue;
    }

    public double getY(double defaultValue) {
        return moving ? y : defaultValue;
    }

    public double getZ(double defaultValue) {
        return moving ? z : defaultValue;
    }

    public float getYaw(float defaultValue) {
        return rotating ? yaw : defaultValue;
    }

    public float getPitch(float defaultValue) {
        return rotating ? pitch : defaultValue;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public net.minecraft.network.protocol.game.ServerboundMovePlayerPacket toModernPacket(boolean horizontalCollision) {
        if (moving && rotating) {
            return new net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.PosRot(x, y, z, yaw, pitch, onGround, horizontalCollision);
        }
        if (moving) {
            return new net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.Pos(x, y, z, onGround, horizontalCollision);
        }
        if (rotating) {
            return new net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.Rot(yaw, pitch, onGround, horizontalCollision);
        }
        return new net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.StatusOnly(onGround, horizontalCollision);
    }

    @Override
    public PacketType<CPacketPlayer> type() {
        return null;
    }

    @Override
    public void handle(PacketListener listener) {}

    public static class Position extends CPacketPlayer {
        public Position() {
            this.moving = true;
        }

        public Position(double xIn, double yIn, double zIn, boolean onGroundIn) {
            this.x = xIn;
            this.y = yIn;
            this.z = zIn;
            this.onGround = onGroundIn;
            this.moving = true;
        }
    }

    public static class Rotation extends CPacketPlayer {
        public Rotation() {
            this.rotating = true;
        }

        public Rotation(float yawIn, float pitchIn, boolean onGroundIn) {
            this.yaw = yawIn;
            this.pitch = pitchIn;
            this.onGround = onGroundIn;
            this.rotating = true;
        }
    }

    public static class PositionRotation extends CPacketPlayer {
        public PositionRotation() {
            this.moving = true;
            this.rotating = true;
        }

        public PositionRotation(double xIn, double yIn, double zIn, float yawIn, float pitchIn, boolean onGroundIn) {
            this.x = xIn;
            this.y = yIn;
            this.z = zIn;
            this.yaw = yawIn;
            this.pitch = pitchIn;
            this.onGround = onGroundIn;
            this.moving = true;
            this.rotating = true;
        }
    }
}
