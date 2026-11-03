package com.mojang.authlib.yggdrasil;

import com.mojang.authlib.GameProfile;
import java.net.URL;
import java.util.UUID;

public class YggdrasilUserAuthentication {
    public static URL ROUTE_AUTHENTICATE;
    public static URL ROUTE_INVALIDATE;
    public static URL ROUTE_REFRESH;
    public static URL ROUTE_VALIDATE;
    public static URL ROUTE_SIGNOUT;
    public static String BASE_URL;

    private String username;
    private String password;

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void logIn() {}
    public GameProfile getSelectedProfile() {
        return new GameProfile(UUID.randomUUID(), username != null ? username : "Player");
    }
    public String getAuthenticatedToken() { return "token"; }
}
