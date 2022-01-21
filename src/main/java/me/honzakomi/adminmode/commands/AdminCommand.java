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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static me.honzakomi.adminmode.AdminMode.*;


public class AdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("You need to be a player to use this command!");
            return true;
        }

        Player p = (Player) sender;

        if (command.getName().equalsIgnoreCase("AdminMode")) {

            Group groupAdminAccess = luckPerms.getGroupManager().getGroup(Objects.requireNonNull(config.getString("adminConfig.luckPerms.groups.accessGroupName")));
            if (groupAdminAccess == null) {
                p.sendMessage(ChatColor.RED + "There is an error! Couldn't find group " + config.getString("luckPerms.groups.prefixGroupName") + "! To fix this issue you need to reload the plugin.");
                return true;
            }
            Group groupAdminPrefix = luckPerms.getGroupManager().getGroup(Objects.requireNonNull(config.getString("adminConfig.luckPerms.groups.prefixGroupName")));
            if (groupAdminPrefix == null) {
                p.sendMessage(ChatColor.RED + "There is an error! Couldn't find group " + config.getString("luckPerms.groups.prefixGroupName") + "! To fix this issue you need to reload the plugin.");
                return true;
            }

            String playerUUID = p.getUniqueId().toString();

            //ADMIN MODE ENABLE
            if (isInGroup(p, groupAdminAccess) && !PlayerItemsDB.get().getBoolean(playerUUID + ".isAdminMode")) {

                if (config.getBoolean("adminConfig.saving.saveInv")) {
                    ItemStack[] contentsArray = p.getInventory().getStorageContents();

                    List<ItemStack> contents = new ArrayList<>(Arrays.asList(contentsArray));

                    PlayerItemsDB.get().set(playerUUID + ".playerItems", contents);
                    PlayerItemsDB.get().set(playerUUID + ".playerItemsSave", true);
                    PlayerItemsDB.save();
                }

                if (config.getBoolean("adminConfig.saving.saveArmor")) {
                    ItemStack[] armorContentsArray = p.getInventory().getArmorContents();

                    List<ItemStack> armorContents = new ArrayList<>(Arrays.asList(armorContentsArray));

                    PlayerItemsDB.get().set(playerUUID + ".playerArmor", armorContents);
                    PlayerItemsDB.get().set(playerUUID + ".playerArmorSave", true);
                    PlayerItemsDB.save();
                }

                if (config.getBoolean("adminConfig.saving.saveExp")) {
                    int playerExp = p.getTotalExperience();

                    PlayerItemsDB.get().set(playerUUID + ".playerExp", playerExp);
                    PlayerItemsDB.get().set(playerUUID + ".playerExpSave", true);
                    PlayerItemsDB.save();
                }

                if (config.getBoolean("adminConfig.saving.saveLocation")) {
                    Location playerLoc = p.getLocation();

                    PlayerItemsDB.get().set(playerUUID + ".playerLocation", playerLoc);
                    PlayerItemsDB.get().set(playerUUID + ".playerLocationSave", true);
                    PlayerItemsDB.save();
                }

                if (config.getBoolean("adminConfig.operator.operatorEnter")) {
                    p.setOp(true);
                }

                if (config.getBoolean("adminConfig.erasingEnter.playerInv")) {
                    p.getInventory().clear();
                }

                if (config.getBoolean("adminConfig.erasingEnter.playerArmor")) {
                    p.getInventory().setHelmet(null);
                    p.getInventory().setChestplate(null);
                    p.getInventory().setLeggings(null);
                    p.getInventory().setBoots(null);
                }

                if (config.getBoolean("adminConfig.erasingEnter.playerExp")) {
                    p.setExp(0);
                }

                switch (config.getInt("adminConfig.gameModes.gameModeEnter")) {
                    case 0:
                        p.setGameMode(GameMode.SURVIVAL);
                        break;
                    case 1:
                        p.setGameMode(GameMode.CREATIVE);
                        break;
                    case 2:
                        p.setGameMode(GameMode.ADVENTURE);
                        break;
                    case 3:
                        p.setGameMode(GameMode.SPECTATOR);
                        break;
                }

                PlayerItemsDB.get().set(playerUUID + ".isAdminMode", true);
                PlayerItemsDB.save();

                addToGroup(p, groupAdminPrefix);

                p.sendMessage(Objects.requireNonNull(config.getString("adminConfig.messages.adminModeEnabled")));
                return true;
            }
            //ADMIN MODE DISABLED
            else if (isInGroup(p, groupAdminAccess) && PlayerItemsDB.get().getBoolean(playerUUID + ".isAdminMode")) {

                if (config.getBoolean("adminConfig.erasingReturn.playerInv")) {
                    p.getInventory().clear();
                }

                if (config.getBoolean("adminConfig.erasingReturn.playerArmor")) {
                    p.getInventory().setHelmet(null);
                    p.getInventory().setChestplate(null);
                    p.getInventory().setLeggings(null);
                    p.getInventory().setBoots(null);
                }

                if (PlayerItemsDB.get().getBoolean(playerUUID + ".playerItemsSave")) {
                    List<ItemStack> contentsList = (List<ItemStack>) PlayerItemsDB.get().get(playerUUID + ".playerItems");

                    assert contentsList != null;
                    ItemStack[] contents = contentsList.toArray(new ItemStack[0]);

                    p.getInventory().setContents(contents);
                }

                if (PlayerItemsDB.get().getBoolean(playerUUID + ".playerArmorSave")) {
                    List<ItemStack> armorContentsList = (List<ItemStack>) PlayerItemsDB.get().get(playerUUID + ".playerArmor");

                    assert armorContentsList != null;
                    ItemStack[] armorContents = armorContentsList.toArray(new ItemStack[0]);

                    p.getInventory().setArmorContents(armorContents);
                }

                if (PlayerItemsDB.get().getBoolean(playerUUID + ".playerLocationSave")) {
                    Location playerLoc = (Location) PlayerItemsDB.get().get(playerUUID + ".playerLocation");

                    assert playerLoc != null;
                    p.teleport(playerLoc);
                }

                int playerExp = PlayerItemsDB.get().getInt(playerUUID + ".playerExp");
                p.setTotalExperience(playerExp);

                PlayerItemsDB.get().set(p.getUniqueId().toString(), null);
                PlayerItemsDB.save();

                if (!config.getBoolean("adminConfig.operator.operatorReturn")) {
                    p.setOp(false);
                }

                switch (config.getInt("adminConfig.gameModes.gameModeReturn")) {
                    case 0:
                        p.setGameMode(GameMode.SURVIVAL);
                        break;
                    case 1:
                        p.setGameMode(GameMode.CREATIVE);
                        break;
                    case 2:
                        p.setGameMode(GameMode.ADVENTURE);
                        break;
                    case 3:
                        p.setGameMode(GameMode.SPECTATOR);
                        break;
                }

                removeFromGroup(p, groupAdminPrefix);

                p.sendMessage(Objects.requireNonNull(config.getString("adminConfig.messages.adminModeDisabled")));
                return true;

            } else {

                p.sendMessage(Objects.requireNonNull(config.getString("adminConfig.messages.noAccess")));

                return true;
            }
        }
        return true;
    }

    private boolean isInGroup(OfflinePlayer p, Group g) {
        String pN = p.getName();
        if (pN == null) {
            return false;
        }
        User user = luckPerms.getUserManager().getUser(pN);
        if (user == null) {
            return false;
        }

        InheritanceNode inheritanceNode = InheritanceNode.builder(g).build();

        return user.data().contains(inheritanceNode, NodeEqualityPredicate.EXACT).asBoolean();
    }

    private void addToGroup(OfflinePlayer p, Group g) {
        Group nG = luckPerms.getGroupManager().getGroup(g.getName());
        if (nG == null) {
            return;
        }

        String pN = p.getName();
        if (pN == null) {
            return;
        }
        User user = luckPerms.getUserManager().getUser(pN);
        if (user == null) {
            return;
        }

        InheritanceNode node = InheritanceNode.builder(g).build();
        DataMutateResult result = user.data().add(node);
        if (result == DataMutateResult.FAIL) {
            return;
        }

        luckPerms.getUserManager().saveUser(user);
    }

    private void removeFromGroup(OfflinePlayer p, Group g) {
        String pN = p.getName();
        if (pN == null) {
            return;
        }
        User user = luckPerms.getUserManager().getUser(pN);
        if (user == null) {
            return;
        }

        InheritanceNode node = InheritanceNode.builder(g).build();
        boolean result = user.data().remove(node) != DataMutateResult.FAIL;

        luckPerms.getUserManager().saveUser(user);
    }
}
