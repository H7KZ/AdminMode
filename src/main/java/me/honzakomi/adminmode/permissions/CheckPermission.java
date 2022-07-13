package me.honzakomi.adminmode.permissions;

import me.honzakomi.adminmode.messages.PlayerMessage;
import me.honzakomi.adminmode.messages.SenderMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.honzakomi.adminmode.permissions.Permissions.allPermissions;

public class CheckPermission {
    public static Boolean check(Player p, String permission) {
        if (p.hasPermission(permission) || p.hasPermission(allPermissions)) {
            return true;
        }

        p.sendMessage(PlayerMessage.noPermission);

        return false;
    }

    public static Boolean check(CommandSender s, String permission) {
        if (s.hasPermission(permission) || s.hasPermission(allPermissions)) {
            return true;
        }

        s.sendMessage(SenderMessage.noPermission);

        return false;
    }
}
