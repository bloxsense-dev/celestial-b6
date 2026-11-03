package org.celestial.client.friend;

public class Friend {

    private String name;

    public Friend(String name) {
        this.name = name;
    }

    public Friend(net.minecraft.network.chat.Component comp) {
        this(comp != null ? comp.getString() : "");
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
