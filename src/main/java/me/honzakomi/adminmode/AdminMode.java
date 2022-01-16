package me.honzakomi.adminmode;

import me.honzakomi.adminmode.commands.AdminCommand;
import me.honzakomi.adminmode.database.PlayerItemsDB;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.node.types.PrefixNode;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class AdminMode extends JavaPlugin {

    public static AdminMode plugin;

    public static LuckPerms luckPerms;

    @Override
    public void onEnable() {
        plugin = this;

        luckPerms = LuckPermsProvider.get();

        luckPerms.getGroupManager().createAndLoadGroup("adminModeAccess");
        luckPerms.getGroupManager().createAndLoadGroup("adminModePrefix");

        Group adminPrefix = luckPerms.getGroupManager().getGroup("adminModePrefix");
        PrefixNode adminPrefixName = PrefixNode.builder("&c&l[ADMIN] &r", 150).build();
        adminPrefix.data().add(adminPrefixName);
        luckPerms.getGroupManager().saveGroup(adminPrefix);

        Objects.requireNonNull(getCommand("admin")).setExecutor(new AdminCommand());

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
