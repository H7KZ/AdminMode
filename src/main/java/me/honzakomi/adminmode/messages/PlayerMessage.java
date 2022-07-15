package me.honzakomi.adminmode.messages;

import static me.honzakomi.adminmode.AdminMode.config;

public class PlayerMessage {
    public static String needToBeAPlayer;
    public static String noPermission;
    public static String unknownCommand;
    public static String specifyPlayer;
    public static String targetIsNotOnline;
    public static String targetIsNotAdmin;
    public static String targetIsAdmin;
    public static String playerIsAdmin;
    public static String playerIsNotAdmin;
    public static String pluginReloaded;

    public static void init() {
        noPermission = config.getString("messages.noPermissionPlayer");

        needToBeAPlayer = config.getString("messages.notAPlayer");

        unknownCommand = config.getString("messages.unknownCommand");

        specifyPlayer = config.getString("messages.noSpecifiedPlayer");

        targetIsNotOnline = config.getString("messages.targetIsNotOnline");

        targetIsNotAdmin = config.getString("messages.targetIsNotAdminMode");

        targetIsAdmin = config.getString("messages.targetIsInAdminMode");

        playerIsAdmin = config.getString("messages.playerIsInAdminMode");

        playerIsNotAdmin = config.getString("messages.playerIsNotInAdminMode");

        pluginReloaded = config.getString("messages.reloaded");
    }
}
