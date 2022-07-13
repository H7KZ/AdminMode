package me.honzakomi.adminmode;

import me.honzakomi.adminmode.bstats.Metrics;
import me.honzakomi.adminmode.database.PlayersDatabase;
import me.honzakomi.adminmode.initialize.Initialize;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class AdminMode extends JavaPlugin {

    public static AdminMode plugin;
    public static Logger logger;
    public static FileConfiguration config;

    @Override
    public void onEnable() {
        plugin = this;

        logger = plugin.getLogger();

        new Metrics(this, 15751);

        //CONFIG FILE LOAD & COPY DEFAULTS
        config = plugin.getConfig();
        config.options().copyDefaults(true);
        saveDefaultConfig();

        Initialize.variables();

        Initialize.messages();

        Initialize.database();

        Initialize.commands();

        logger.info("AdminMode Plugin has started with no signs of errors!");
    }

    @Override
    public void onDisable() {
        PlayersDatabase.save();

        logger.info("AdminMode Plugin has been successfully shut down!");
    }
}
