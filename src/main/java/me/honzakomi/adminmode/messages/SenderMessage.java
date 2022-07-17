package me.honzakomi.adminmode.messages;

import static me.honzakomi.adminmode.AdminMode.config;

public class SenderMessage {
    public static String specifyCommand;
    public static String noPermission;
    public static String notAPlayer;

    public static void init() {
        specifyCommand = config.getString("messages.sender.specifyCommand");

        noPermission = config.getString("messages.sender.noPermission");

        notAPlayer = config.getString("messages.sender.notAPlayer");
    }
}
