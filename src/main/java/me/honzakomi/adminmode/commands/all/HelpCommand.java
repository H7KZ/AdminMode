package me.honzakomi.adminmode.commands.all;

import me.honzakomi.adminmode.messages.HelpMessage;
import me.honzakomi.adminmode.permissions.CheckPermission;
import me.honzakomi.adminmode.variables.Variables;
import org.bukkit.entity.Player;

public class HelpCommand {
    public static final String commandName = "help";

    public static void command(Player p) {
        if (CheckPermission.check(p, Variables.Permissions.helpCommandPermission) || CheckPermission.check(p, Variables.Permissions.allPermissions)) {
            displayHelp(p);
        }
    }

    private static void displayHelp(Player p) {
        if (p.hasPermission(Variables.Permissions.reloadCommandPermission) || p.hasPermission(Variables.Permissions.allPermissions)) {
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
