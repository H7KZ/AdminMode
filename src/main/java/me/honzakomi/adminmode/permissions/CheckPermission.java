package me.honzakomi.adminmode.permissions;

import me.honzakomi.adminmode.messages.PlayerMessage;
import me.honzakomi.adminmode.variables.Variables;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckPermission {
    public static Boolean check(Player p, String permission) {
        if (!p.hasPermission(permission)) {
            p.sendMessage(PlayerMessage.needToHavePermission);
            return false;
        }

        return true;
    }

    public static Boolean check(CommandSender s, String permission) {
        if (!s.hasPermission(permission)) {
            s.sendMessage("AdminMode: You don't have permission to use this command!");
            return false;
        }

        return true;
    }
}
