package net.minecraft.util;

import java.util.Optional;
import java.util.UUID;
import net.minecraft.client.User;

public class Session extends User {
    private String username;
    private String playerID;
    private String token;
    private String sessionType;

    public Session(String usernameIn, String playerIDIn, String tokenIn, String sessionTypeIn) {
        super(usernameIn, UUID.randomUUID(), tokenIn, Optional.empty(), Optional.empty());
        this.username = usernameIn;
        this.playerID = playerIDIn;
        this.token = tokenIn;
        this.sessionType = sessionTypeIn;
    }

    public String getUsername() { return username; }
    public String getPlayerID() { return playerID; }
    public String getToken() { return token; }
    public String getSessionType() { return sessionType; }
    public String getSessionID() { return "token:" + token + ":" + playerID; }
}
