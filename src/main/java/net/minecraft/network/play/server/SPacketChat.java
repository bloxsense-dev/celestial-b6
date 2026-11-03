package net.minecraft.network.play.server;

import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.PacketType;

public class SPacketChat implements Packet<ClientGamePacketListener> {
    private ITextComponent chatComponent = new TextComponentString("");
    private ChatType type = ChatType.CHAT;

    public SPacketChat() {}
    public ITextComponent getChatComponent() { return chatComponent; }
    public ChatType getChatType() { return type; }
    @Override public PacketType<? extends Packet<ClientGamePacketListener>> type() { return null; }
    @Override public void handle(ClientGamePacketListener listener) {}
}
