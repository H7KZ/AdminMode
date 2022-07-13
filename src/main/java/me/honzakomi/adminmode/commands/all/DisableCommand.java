package me.honzakomi.adminmode.commands.all;

import me.honzakomi.adminmode.permissions.CheckPermission;
import org.bukkit.entity.Player;

import static me.honzakomi.adminmode.AdminMode.plugin;
import static me.honzakomi.adminmode.permissions.Permissions.disableCommandPermission;
import static me.honzakomi.adminmode.permissions.Permissions.disablePlayerCommandPermission;
import static me.honzakomi.adminmode.playerData.PlayerData.disableTarget;
import static me.honzakomi.adminmode.playerData.PlayerData.disableYourself;

public class DisableCommand {
    public static final String commandName = "disable";

    public static void command(Player p, String[] args) {
        if (args.length >= 1 && CheckPermission.check(p, disablePlayerCommandPermission)) {
            command(p, args[1]);
            return;
        }

        if (CheckPermission.check(p, disableCommandPermission)) {
            disableYourself(p);
        }
    }

    public static void command(Player p, String t) {
        Player target = plugin.getServer().getPlayerExact(t);

        disableTarget(p, target);
    }
}
