package me.honzakomi.adminmode.commands.all;

import me.honzakomi.adminmode.database.PlayersDatabase;
import me.honzakomi.adminmode.initialize.Initialize;
import me.honzakomi.adminmode.messages.PlayerMessage;
import me.honzakomi.adminmode.permissions.CheckPermission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.honzakomi.adminmode.AdminMode.*;
import static me.honzakomi.adminmode.permissions.Permissions.reloadCommandPermission;

public class ReloadCommand {
    public static final String commandName = "reload";

    public static void command(CommandSender sender) {
        if (!CheckPermission.check(sender, reloadCommandPermission)) {
            return;
        }

        if (sender instanceof Player p) {
            p.sendMessage(PlayerMessage.pluginReloading);
        }

        reload();

        if (sender instanceof Player p) {
            p.sendMessage(PlayerMessage.pluginReloaded);
        }
    }

    private static void reload() {
        plugin.reloadConfig();
        config = plugin.getConfig();
        config.options().copyDefaults(true);
        plugin.saveDefaultConfig();

        PlayersDatabase.reload();

        Initialize.variables();

        Initialize.messages();

        logger.info("AdminMode Plugin reloaded!");
    }
}
