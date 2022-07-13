package me.honzakomi.adminmode.commands.all;

import me.honzakomi.adminmode.database.PlayersDatabase;
import me.honzakomi.adminmode.initialize.Initialize;
import me.honzakomi.adminmode.messages.PlayerMessage;
import me.honzakomi.adminmode.permissions.CheckPermission;
import me.honzakomi.adminmode.variables.Variables;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.honzakomi.adminmode.AdminMode.*;

public class ReloadCommand {
    public static final String commandName = "reload";

    public static void command(CommandSender sender) {
        if (CheckPermission.check(sender, Variables.Permissions.reloadCommandPermission) || CheckPermission.check(sender, Variables.Permissions.allPermissions)) {
            reload();

            if (sender instanceof Player p) {
                p.sendMessage(PlayerMessage.reloaded);
            }
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
