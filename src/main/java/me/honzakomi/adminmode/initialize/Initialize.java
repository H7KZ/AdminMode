package me.honzakomi.adminmode.initialize;

import me.honzakomi.adminmode.commands.Commands;
import me.honzakomi.adminmode.database.PlayersDatabase;
import me.honzakomi.adminmode.messages.PlayerMessage;
import me.honzakomi.adminmode.variables.Variables;

import java.util.Objects;

import static me.honzakomi.adminmode.AdminMode.logger;
import static me.honzakomi.adminmode.AdminMode.plugin;

public class Initialize {
    public static void variables() {
        Variables.init();
        logger.info("AdminMode: variables have been registered");
    }

    public static void messages() {
        PlayerMessage.init();
        logger.info("AdminMode: messages have been registered");
    }

    public static void database() {
        PlayersDatabase.setup();
        PlayersDatabase.save();
        logger.info("AdminMode: database has been loaded");
    }

    public static void commands() {
        Objects.requireNonNull(plugin.getCommand("AdminMode")).setExecutor(new Commands());
        logger.info("AdminMode: commands have been registered");
    }
}
