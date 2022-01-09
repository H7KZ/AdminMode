package me.honzakomi.adminmode;

import me.honzakomi.adminmode.commands.AdminCommand;
import me.honzakomi.adminmode.database.PlayerItemsDB;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import static org.bukkit.Bukkit.getServer;

public final class AdminMode extends JavaPlugin {

    public static AdminMode plugin;

    public static Team admins;

    @Override
    public void onEnable() {
        plugin = this;

        Scoreboard board = getServer().getScoreboardManager().getNewScoreboard();
        admins = board.getTeam("admins");
        if (admins == null) {
            admins = board.registerNewTeam("admins");
        }
        admins.setPrefix(ChatColor.RED + "[Admin]");

        getCommand("admin").setExecutor(new AdminCommand());

        PlayerItemsDB.setup();
        PlayerItemsDB.save();

        System.out.println("ADMIN MODE PLUGIN LOADED SUCCESSFULLY!");
    }

    @Override
    public void onDisable() {
        PlayerItemsDB.save();

        System.out.println("ADMIN MODE PLUGIN IS SHUTTING DOWN!");
    }
}
