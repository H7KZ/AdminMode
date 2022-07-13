package me.honzakomi.adminmode.commands;

import me.honzakomi.adminmode.commands.all.*;
import me.honzakomi.adminmode.messages.PlayerMessage;
import me.honzakomi.adminmode.messages.SenderMessage;
import me.honzakomi.adminmode.permissions.CheckPermission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.honzakomi.adminmode.permissions.Permissions.adminModeCommandPermission;


public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!command.getName().equalsIgnoreCase("adminmode")) {
            return true;
        }

        if (!CheckPermission.check(sender, adminModeCommandPermission)) {
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(SenderMessage.specifyCommand);
            return true;
        }

        if (args[0].equalsIgnoreCase(ReloadCommand.commandName)) {
            ReloadCommand.command(sender);
            return true;
        }

        if (!(sender instanceof Player p)) {
            sender.sendMessage(PlayerMessage.needToBeAPlayer);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case HelpCommand.commandName -> HelpCommand.command(p);
            case EnableCommand.commandName -> EnableCommand.command(p, args);
            case DisableCommand.commandName -> DisableCommand.command(p, args);
            case CheckCommand.commandName -> CheckCommand.command(p, args);
            default -> p.sendMessage(PlayerMessage.unknownCommand);
        }

        return true;
    }
}
