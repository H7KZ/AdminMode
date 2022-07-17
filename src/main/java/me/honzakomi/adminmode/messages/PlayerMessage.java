package me.honzakomi.adminmode.messages;

import static me.honzakomi.adminmode.AdminMode.config;

public class PlayerMessage {
    public static String pluginReloading;
    public static String pluginReloaded;
    public static String noPermission;
    public static String unknownCommand;
    public static String specifyPlayer;
    public static String enabled;
    public static String disabled;
    public static String targetEnabled;
    public static String targetDisabled;
    public static String targetIsNotOnline;
    public static String targetIsNotAdminMode;
    public static String targetIsInAdminMode;
    public static String isInAdminMode;
    public static String isNotInAdminMode;

    public static void init() {
        pluginReloading = config.getString("messages.player.pluginReloading");

        pluginReloaded = config.getString("messages.player.pluginReloaded");

        noPermission = config.getString("messages.player.noPermission");

        unknownCommand = config.getString("messages.player.unknownCommand");

        specifyPlayer = config.getString("messages.player.specifyCommand");

        enabled = config.getString("messages.player.enabled");

        disabled = config.getString("messages.player.disabled");

        targetEnabled = config.getString("messages.player.targetEnabled");

        targetDisabled = config.getString("messages.player.targetDisabled");

        targetIsNotOnline = config.getString("messages.player.targetIsNotOnline");

        targetIsNotAdminMode = config.getString("messages.player.targetIsNotAdminMode");

        targetIsInAdminMode = config.getString("messages.player.targetIsInAdminMode");

        isInAdminMode = config.getString("messages.player.isInAdminMode");

        isNotInAdminMode = config.getString("messages.player.isNotInAdminMode");
    }
}
