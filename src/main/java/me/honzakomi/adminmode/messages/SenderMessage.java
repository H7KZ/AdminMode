package me.honzakomi.adminmode.messages;

import static me.honzakomi.adminmode.AdminMode.config;

public class SenderMessage {
    public static String specifyCommand;
    public static String noPermission;

    public static void init() {
        specifyCommand = config.getString("messages.specifyCommand");

        noPermission = config.getString("messages.noPermissionSender");
    }
}
