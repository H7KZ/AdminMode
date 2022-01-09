package me.honzakomi.adminmode.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player)
        {
            sender.sendMessage("You need to be a player to use this command!");
            return true;
        }

        Player p = (Player) sender;

        if (command.getName().equalsIgnoreCase("admin")) {
            if (p.hasPermission("adminMode.access")) {
                //save inv, save where player was standing, give creative, give op, give prefix, set value admin to true
            } else {
                p.sendMessage(ChatColor.RED + "You need to have an access to this command!");
                return true;
            }
        }
        return true;
    }
}
