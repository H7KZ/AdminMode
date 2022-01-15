package me.honzakomi.adminmode.commands;

import me.honzakomi.adminmode.database.PlayerItemsDB;
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

import static me.honzakomi.adminmode.AdminMode.*;


public class AdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player))
        {
            sender.sendMessage("You need to be a player to use this command!");
            return true;
        }

        Player p = (Player) sender;

        if (command.getName().equalsIgnoreCase("admin")) {
                Group groupAdmin = luckPerms.getGroupManager().getGroup("admin");

                if (groupAdmin == null) { return true; }

                if (isInGroup(p, "admin") && !PlayerItemsDB.get().getBoolean(p.getUniqueId().toString() + ".isAdminMode")) {

                    String playerUUID = p.getUniqueId().toString();

                    ItemStack[] contents = p.getInventory().getContents();

                    ItemStack[] armorContents = p.getInventory().getArmorContents();

                    int playerExp = p.getTotalExperience();

                    Location playerLoc = p.getLocation();

                    GameMode playerGM = p.getGameMode();

                    PlayerItemsDB.get().set(playerUUID + ".isAdminMode", true);
                    PlayerItemsDB.get().set(playerUUID + ".playerExp", playerExp);
                    PlayerItemsDB.get().set(playerUUID + ".playerItems", contents);
                    PlayerItemsDB.get().set(playerUUID + ".playerArmor", armorContents);
                    PlayerItemsDB.get().set(playerUUID + ".playerLocation", playerLoc);
                    PlayerItemsDB.get().set(playerUUID + ".playerGameMode", playerGM);
                    PlayerItemsDB.save();

                    p.getInventory().clear();
                    p.getInventory().setHelmet(null);
                    p.getInventory().setChestplate(null);
                    p.getInventory().setLeggings(null);
                    p.getInventory().setBoots(null);

                    p.setOp(true);
                    p.setGameMode(GameMode.CREATIVE);

                    p.sendMessage(ChatColor.GREEN + "You are in ADMIN MODE! To Exit type /admin");
                    return true;

                } else if (isInGroup(p, "admin") && PlayerItemsDB.get().getBoolean(p.getUniqueId().toString() + ".isAdminMode")) {

                    String playerUUID = p.getUniqueId().toString();

                    ItemStack[] contents = (ItemStack[]) PlayerItemsDB.get().get(playerUUID + ".playerItems");
                    ItemStack[] armorContents = (ItemStack[]) PlayerItemsDB.get().get(playerUUID + ".playerArmor");
                    Location playerLoc = (Location) PlayerItemsDB.get().get(playerUUID + ".playerLocation");
                    int playerExp = PlayerItemsDB.get().getInt(playerUUID + ".playerExp");
                    GameMode playerGM = (GameMode) PlayerItemsDB.get().get(playerUUID + ".playerGameMode");

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

                    assert playerGM != null;
                    p.setGameMode(playerGM);

                    p.sendMessage(ChatColor.YELLOW + "You have exited the ADMIN MODE! To enable it again type /admin");
                    return true;

                } else {

                    p.sendMessage(ChatColor.RED + "You need to have an access to this command!");

                    return true;
                }
        }
        return true;
    }

    private boolean isInGroup(OfflinePlayer player, String group) {
        String playerName = player.getName();
        if (playerName == null) {
            return false;
        }
        User user = luckPerms.getUserManager().getUser(playerName);
        if (user == null) {
            return false;
        }

        InheritanceNode inheritanceNode = InheritanceNode.builder(group).build();

        return user.data().contains(inheritanceNode, NodeEqualityPredicate.EXACT).asBoolean();
    }
}
