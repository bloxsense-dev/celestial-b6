package net.minecraft.network.play.server;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.SoundCategory;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.PacketType;

public class SPacketSoundEffect implements Packet<ClientGamePacketListener> {
    public SPacketSoundEffect() {}
    public SoundCategory getCategory() { return SoundCategory.NEUTRAL; }
    public SoundEvent getSound() { return null; }
    @Override public PacketType<? extends Packet<ClientGamePacketListener>> type() { return null; }
    @Override public void handle(ClientGamePacketListener listener) {}
}
