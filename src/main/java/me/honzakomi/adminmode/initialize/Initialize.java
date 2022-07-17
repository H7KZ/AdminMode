package me.honzakomi.adminmode.initialize;

import me.honzakomi.adminmode.commands.Commands;
import me.honzakomi.adminmode.database.PlayersDatabase;
import me.honzakomi.adminmode.messages.PlayerMessage;
import me.honzakomi.adminmode.messages.SenderMessage;
import me.honzakomi.adminmode.messages.TargetMessage;
import me.honzakomi.adminmode.tabcompleter.TabCompleter;
import me.honzakomi.adminmode.variables.Variables;

import java.util.Objects;

import static me.honzakomi.adminmode.AdminMode.logger;
import static me.honzakomi.adminmode.AdminMode.plugin;

public class Initialize {
    public static void commands() {
        Objects.requireNonNull(plugin.getCommand("AdminMode")).setExecutor(new Commands());
        Objects.requireNonNull(plugin.getCommand("AdminMode")).setTabCompleter(new TabCompleter());
        logger.info("AdminMode: commands have been registered");
    }

    public static void variables() {
        Variables.init();
        logger.info("AdminMode: variables have been registered");
    }

    public static void messages() {
        PlayerMessage.init();
        TargetMessage.init();
        SenderMessage.init();
        logger.info("AdminMode: messages have been registered");
    }

    public static void database() {
        PlayersDatabase.setup();
        PlayersDatabase.save();
        logger.info("AdminMode: database has been loaded");
    }
}
