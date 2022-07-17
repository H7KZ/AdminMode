package me.honzakomi.adminmode.messages;

import static me.honzakomi.adminmode.AdminMode.config;

public class TargetMessage {
    public static String enabled;
    public static String disabled;

    public static void init() {
        enabled = config.getString("messages.target.enabled");

        disabled = config.getString("messages.target.disabled");
    }
}
