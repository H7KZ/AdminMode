package me.honzakomi.adminmode.commands.all;

import me.honzakomi.adminmode.messages.HelpMessage;
import me.honzakomi.adminmode.permissions.CheckPermission;
import org.bukkit.entity.Player;

import static me.honzakomi.adminmode.permissions.Permissions.*;

public class HelpCommand {
    public static final String commandName = "help";

    public static void command(Player p) {
        if (!CheckPermission.check(p, helpCommandPermission)) {
            return;
        }

        displayHelp(p);
    }

    private static void displayHelp(Player p) {
        if (p.hasPermission(reloadCommandPermission) || p.hasPermission(allPermissions)) {
            for (String s : HelpMessage.contentsWithReload) {
                p.sendMessage(s);
            }
        } else {
            for (String s : HelpMessage.contentsWithoutReload) {
                p.sendMessage(s);
            }
        }
    }
}
