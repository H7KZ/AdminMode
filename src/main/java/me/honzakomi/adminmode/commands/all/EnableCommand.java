package me.honzakomi.adminmode.commands.all;

import me.honzakomi.adminmode.permissions.CheckPermission;
import me.honzakomi.adminmode.variables.Variables;
import org.bukkit.entity.Player;

import static me.honzakomi.adminmode.AdminMode.plugin;

public class EnableCommand {
    public static final String commandName = "enable";

    public static void command(Player p, String[] args) {
        if (args.length >= 1) {
            command(p, args[1]);
            return;
        }

        if (CheckPermission.check(p, Variables.Permissions.enableCommandPermission) || CheckPermission.check(p, Variables.Permissions.allPermissions)) {
            enableYourself(p);
        }
    }

    public static void command(Player p, String t) {
        if (CheckPermission.check(p, Variables.Permissions.enableCommandPermission) || CheckPermission.check(p, Variables.Permissions.allPermissions)) {
            Player target = plugin.getServer().getPlayerExact(t);
            enablePlayer(target);
        }
    }

    private static void enableYourself(Player p) {

    }

    private static void enablePlayer(Player t) {

    }
}
