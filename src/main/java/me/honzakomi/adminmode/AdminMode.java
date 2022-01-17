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
        PrefixNode adminPrefixName = PrefixNode.builder(Objects.requireNonNull(config.getString("adminConfig.luckPerms.groups.prefixOfTheGroup")), config.getInt("adminConfig.luckPerms.groups.priorityOfTheGroup")).build();
        assert adminPrefix != null;
        adminPrefix.data().add(adminPrefixName);
        luckPerms.getGroupManager().saveGroup(adminPrefix);
        System.out.println("[AdminMode] LuckPerms groups " + Objects.requireNonNull(config.getString("adminConfig.luckPerms.groups.accessGroupName")) + " & " + Objects.requireNonNull(config.getString("adminConfig.luckPerms.groups.prefixGroupName")) + " were successfully created!");

        Objects.requireNonNull(getCommand(Objects.requireNonNull(config.getString("adminConfig.commands.nameOfTheMainCommand")))).setExecutor(new AdminCommand());

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
