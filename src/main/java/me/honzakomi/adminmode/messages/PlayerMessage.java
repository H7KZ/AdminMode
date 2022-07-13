package me.honzakomi.adminmode.messages;

import static me.honzakomi.adminmode.AdminMode.config;

public class PlayerMessage {
    public static String needToBeAPlayer;
    public static String needToHavePermission;
    public static String unknownCommand;
    public static String specifyPlayer;
    public static String playerIsNotOnline;
    public static String playerIsNotAdmin;
    public static String playerIsAdmin;
    public static String reloaded;

    public static void init() {
        needToBeAPlayer = config.getString("messages.notAPlayer");
        needToHavePermission = config.getString("messages.noPermission");
        unknownCommand = config.getString("messages.unknownCommand");
        specifyPlayer = config.getString("messages.noSpecifiedPlayer");
        playerIsNotOnline = config.getString("messages.playerIsNotOnline");
        playerIsNotAdmin = config.getString("messages.playerIsNotAdminMode");
        playerIsAdmin = config.getString("messages.playerIsInAdminMode");
        reloaded = config.getString("messages.reloaded");
    }
}
