package me.honzakomi.adminmode.commands.all;

import me.honzakomi.adminmode.permissions.CheckPermission;
import me.honzakomi.adminmode.variables.Variables;
import org.bukkit.entity.Player;

import static me.honzakomi.adminmode.AdminMode.plugin;

public class DisableCommand {
    public static final String commandName = "disable";

    public static void command(Player p, String[] args) {
        if (args.length >= 1) {
            command(p, args[1]);
            return;
        }

        if (CheckPermission.check(p, Variables.Permissions.disableCommandPermission) || CheckPermission.check(p, Variables.Permissions.allPermissions)) {
            disableYourself(p);
        }


    }

    public static void command(Player p, String t) {
        if (CheckPermission.check(p, Variables.Permissions.disablePlayerCommandPermission) || CheckPermission.check(p, Variables.Permissions.allPermissions)) {
            Player target = plugin.getServer().getPlayerExact(t);
            disablePlayer(target);
        }


    }

    private static void disableYourself(Player p) {

    }

    private static void disablePlayer(Player t) {

    }
}
