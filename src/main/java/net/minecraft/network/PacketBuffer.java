package net.minecraft.network;

import io.netty.buffer.ByteBuf;

public class PacketBuffer extends FriendlyByteBuf {
    public PacketBuffer(ByteBuf byteBuf) {
        super(byteBuf);
    }

    public PacketBuffer writeString(String string) {
        this.writeUtf(string);
        return this;
    }
}
