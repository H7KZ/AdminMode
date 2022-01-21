package me.honzakomi.adminmode;

import me.honzakomi.adminmode.commands.AdminCommand;
import me.honzakomi.adminmode.database.PlayerItemsDB;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.node.types.PrefixNode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class AdminMode extends JavaPlugin {

    public static AdminMode plugin;

    public static LuckPerms luckPerms;

    public static FileConfiguration config;

    @Override
    public void onEnable() {
        plugin = this;

        config = this.getConfig();

        config.options().copyDefaults(true);
        saveConfig();

        luckPerms = LuckPermsProvider.get();

        luckPerms.getGroupManager().createAndLoadGroup(Objects.requireNonNull(config.getString("adminConfig.luckPerms.groups.accessGroupName")));
        luckPerms.getGroupManager().createAndLoadGroup(Objects.requireNonNull(config.getString("adminConfig.luckPerms.groups.prefixGroupName")));

        Group adminPrefix = luckPerms.getGroupManager().getGroup(Objects.requireNonNull(config.getString("adminConfig.luckPerms.groups.prefixGroupName")));
        assert adminPrefix != null;

        if (!config.getBoolean("adminConfig.loaded")) {
            PrefixNode adminPrefixName = PrefixNode.builder("§c§l[ADMIN] ", 100).build();
            adminPrefix.data().add(adminPrefixName);
            luckPerms.getGroupManager().saveGroup(adminPrefix);
            config.set("adminConfig.loaded", true);
            saveConfig();
        }

        System.out.println("[AdminMode] LuckPerms groups " + Objects.requireNonNull(config.getString("adminConfig.luckPerms.groups.accessGroupName")) + " & " + Objects.requireNonNull(config.getString("adminConfig.luckPerms.groups.prefixGroupName")) + " were successfully created!");

        Objects.requireNonNull(getCommand("AdminMode")).setExecutor(new AdminCommand());

        PlayerItemsDB.setup();
        PlayerItemsDB.save();

        System.out.println("[AdminMode] Admin Mode has been successfully loaded!");
    }

    @Override
    public void onDisable() {
        PlayerItemsDB.save();

        System.out.println("ADMIN MODE PLUGIN IS SHUTTING DOWN!");
    }
}
