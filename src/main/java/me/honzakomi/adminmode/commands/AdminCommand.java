package me.honzakomi.adminmode.commands;

import me.honzakomi.adminmode.database.PlayerItemsDB;
import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeEqualityPredicate;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.honzakomi.adminmode.AdminMode.*;


public class AdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("You need to be a player to use this command!");
            return true;
        }

        Player p = (Player) sender;

        if (command.getName().equalsIgnoreCase("admin") && args.length == 0) {

            Group groupAdminAccess = luckPerms.getGroupManager().getGroup("adminModeAccess");
            if (groupAdminAccess == null) {
                sender.sendMessage(ChatColor.RED + "There is an error! I couldn't find group adminModeAccess! To fix this issue you need to reload the plugin.");
                return true;
            }
            Group groupAdminPrefix = luckPerms.getGroupManager().getGroup("adminModeAccess");
            if (groupAdminPrefix == null) {
                sender.sendMessage(ChatColor.RED + "There is an error! I couldn't find group adminModePrefix! To fix this issue you need to reload the plugin.");
                return true;
            }

            String playerUUID = p.getUniqueId().toString();

            if (isInGroup(p) && !PlayerItemsDB.get().getBoolean(playerUUID + ".isAdminMode")) {

                ItemStack[] contentsArray = p.getInventory().getContents();

                ItemStack[] armorContentsArray = p.getInventory().getArmorContents();

                List<ItemStack> contents = new ArrayList<>();

                for (ItemStack i : contentsArray) {
                    if (i != null) {
                        contents.add(i);
                    }
                }

                List<ItemStack> armorContents = new ArrayList<>();

                for (ItemStack i : armorContentsArray) {
                    if (i != null) {
                        contents.add(i);
                    }
                }

                int playerExp = p.getTotalExperience();

                Location playerLoc = p.getLocation();

                PlayerItemsDB.get().set(playerUUID + ".isAdminMode", true);
                PlayerItemsDB.get().set(playerUUID + ".playerExp", playerExp);
                PlayerItemsDB.get().set(playerUUID + ".playerItems", contents);
                PlayerItemsDB.get().set(playerUUID + ".playerArmor", armorContents);
                PlayerItemsDB.get().set(playerUUID + ".playerLocation", playerLoc);
                PlayerItemsDB.save();

                p.getInventory().clear();
                p.getInventory().setHelmet(null);
                p.getInventory().setChestplate(null);
                p.getInventory().setLeggings(null);
                p.getInventory().setBoots(null);

                p.setOp(true);
                p.setGameMode(GameMode.CREATIVE);

                addToGroup(p);

                p.sendMessage(ChatColor.GREEN + "You are in ADMIN MODE! To Exit type /admin");
                return true;

            } else if (isInGroup(p) && PlayerItemsDB.get().getBoolean(playerUUID + ".isAdminMode")) {

                List<ItemStack> contentsList = (List<ItemStack>) PlayerItemsDB.get().get(playerUUID + ".playerItems");
                List<ItemStack> armorContentsList = (List<ItemStack>) PlayerItemsDB.get().get(playerUUID + ".playerArmor");
                ItemStack[] contents = contentsList.toArray(new ItemStack[0]);
                ItemStack[] armorContents = armorContentsList.toArray(new ItemStack[0]);
                Location playerLoc = (Location) PlayerItemsDB.get().get(playerUUID + ".playerLocation");
                int playerExp = PlayerItemsDB.get().getInt(playerUUID + ".playerExp");

                p.getInventory().clear();
                p.getInventory().setHelmet(null);
                p.getInventory().setChestplate(null);
                p.getInventory().setLeggings(null);
                p.getInventory().setBoots(null);

                PlayerItemsDB.get().set(p.getUniqueId().toString(), null);
                PlayerItemsDB.save();

                assert contents != null;
                p.getInventory().setContents(contents);

                p.getInventory().setArmorContents(armorContents);

                p.setTotalExperience(playerExp);

                assert playerLoc != null;
                p.teleport(playerLoc);

                p.setOp(false);

                p.setGameMode(GameMode.SURVIVAL);

                removeFromGroup(p);

                p.sendMessage(ChatColor.YELLOW + "You have exited the ADMIN MODE! To enable it again type /admin");
                return true;

            } else {

                p.sendMessage(ChatColor.RED + "You need to have an access to this command!");

                return true;
            }
        }
        return true;
    }

    private boolean isInGroup(OfflinePlayer player) {
        String playerName = player.getName();
        if (playerName == null) {
            return false;
        }
        User user = luckPerms.getUserManager().getUser(playerName);
        if (user == null) {
            return false;
        }

        InheritanceNode inheritanceNode = InheritanceNode.builder("adminModeAccess").build();

        return user.data().contains(inheritanceNode, NodeEqualityPredicate.EXACT).asBoolean();
    }

    private boolean addToGroup(OfflinePlayer player) {
        Group newGroup = luckPerms.getGroupManager().getGroup("adminModePrefix");
        if (newGroup == null) {
            return false;
        }

        String playerName = player.getName();
        if (playerName == null) {
            return false;
        }
        User user = luckPerms.getUserManager().getUser(playerName);
        if (user == null) {
            return false;
        }

        InheritanceNode node = InheritanceNode.builder("adminModePrefix").build();
        DataMutateResult result = user.data().add(node);
        if (result == DataMutateResult.FAIL) {
            return false;
        }

        luckPerms.getUserManager().saveUser(user);
        return true;
    }

    private boolean removeFromGroup(OfflinePlayer player) {
        String playerName = player.getName();
        if (playerName == null) {
            return false;
        }
        User user = luckPerms.getUserManager().getUser(playerName);
        if (user == null) {
            return false;
        }

        InheritanceNode groupNode = InheritanceNode.builder("adminModePrefix").build();
        boolean result = user.data().remove(groupNode) != DataMutateResult.FAIL;

        luckPerms.getUserManager().saveUser(user);
        return result;
    }
}
