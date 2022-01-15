package me.honzakomi.adminmode;

import me.honzakomi.adminmode.commands.AdminCommand;
import me.honzakomi.adminmode.database.PlayerItemsDB;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdminMode extends JavaPlugin {

    public static AdminMode plugin;

    public static LuckPerms luckPerms;

    @Override
    public void onEnable() {
        plugin = this;

        luckPerms = LuckPermsProvider.get();

        getCommand("admin").setExecutor(new AdminCommand());

        PlayerItemsDB.setup();
        PlayerItemsDB.get().addDefault("test", "test");
        PlayerItemsDB.save();

        System.out.println("ADMIN MODE PLUGIN LOADED SUCCESSFULLY!");
    }

    @Override
    public void onDisable() {
        PlayerItemsDB.save();

        System.out.println("ADMIN MODE PLUGIN IS SHUTTING DOWN!");
    }
}
