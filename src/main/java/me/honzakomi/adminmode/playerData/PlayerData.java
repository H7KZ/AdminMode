package me.honzakomi.adminmode.playerData;

import me.honzakomi.adminmode.database.PlayersDatabase;
import me.honzakomi.adminmode.messages.PlayerMessage;
import me.honzakomi.adminmode.variables.Variables.EnterAdminMode;
import me.honzakomi.adminmode.variables.Variables.LeaveAdminMode;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static me.honzakomi.adminmode.AdminMode.plugin;

public class PlayerData {

    // ENABLE

    public static void enableYourself(Player p) {
        if (checkIfPlayerIsAdmin(p)) {
            return;
        }

        //noinspection DuplicatedCode
        whatToSave(p);

        whatToErase(p);

        Boolean giveOp = EnterAdminMode.ChangeTo.op;
        String gameModeType = EnterAdminMode.ChangeTo.gamemode;

        if (giveOp) {
            p.setOp(true);
        }

        p.setGameMode(GameMode.valueOf(gameModeType));
    }

    public static void enableTarget(Player p, Player t) {
        if (checkIfTargetIsAdmin(p, t)) {
            return;
        }

        //noinspection DuplicatedCode
        whatToSave(t);

        whatToErase(t);

        Boolean giveOp = EnterAdminMode.ChangeTo.op;
        String gameModeType = EnterAdminMode.ChangeTo.gamemode;

        if (giveOp) {
            t.setOp(true);
        }

        t.setGameMode(GameMode.valueOf(gameModeType));
    }

    // DISABLE

    public static void disableYourself(Player p) {
        whatToErase(p);

        loadPlayerData(p);

        Boolean keepOp = LeaveAdminMode.ChangeTo.op;
        String gameModeType = LeaveAdminMode.ChangeTo.gamemode;

        if (keepOp) {
            p.setOp(true);
        }

        p.setGameMode(GameMode.valueOf(gameModeType));
    }

    public static void disableTarget(Player p, Player t) {
        whatToErase(t);

        loadPlayerData(t);

        Boolean keepOp = LeaveAdminMode.ChangeTo.op;
        String gameModeType = LeaveAdminMode.ChangeTo.gamemode;

        if (keepOp) {
            t.setOp(true);
        }

        t.setGameMode(GameMode.valueOf(gameModeType));
    }

    // CHECK

    public static Boolean checkIfTargetIsAdmin(Player p, Player t) {
        Collection<? extends Player> onlinePlayers = plugin.getServer().getOnlinePlayers();

        if (!onlinePlayers.contains(t)) {
            p.sendMessage(PlayerMessage.targetIsNotOnline);
            return false;
        }

        assert t != null;
        if (!PlayersDatabase.get().contains(String.valueOf(t.getUniqueId()))) {
            p.sendMessage(PlayerMessage.targetIsNotAdmin);
            return false;
        }

        p.sendMessage(PlayerMessage.targetIsAdmin);
        return true;
    }

    public static Boolean checkIfPlayerIsAdmin(Player p) {
        if (PlayersDatabase.get().contains(String.valueOf(p.getUniqueId()))) {
            p.sendMessage(PlayerMessage.playerIsAdmin);
            return true;
        }

        return false;
    }

    // SAVE

    private static void savePlayerData(Player p) {
        UUID uuid = p.getUniqueId();
        PlayersDatabase.get().set(uuid + ".inv", Arrays.asList(p.getInventory().getContents()));
        PlayersDatabase.save();
        PlayersDatabase.get().set(uuid + ".armor", Arrays.asList(p.getInventory().getArmorContents()));
        PlayersDatabase.save();
        PlayersDatabase.get().set(uuid + ".level", p.getLevel());
        PlayersDatabase.save();
        PlayersDatabase.get().set(uuid + ".exp", p.getExp());
        PlayersDatabase.save();
        PlayersDatabase.get().set(uuid + ".health", p.getHealth());
        PlayersDatabase.save();
        PlayersDatabase.get().set(uuid + ".location", p.getLocation());
        PlayersDatabase.save();
    }

    private static void savePlayerData(Player p, Boolean inventory, Boolean armor, Boolean exp, Boolean health, Boolean food, Boolean location) {
        UUID uuid = p.getUniqueId();
        if (inventory) {
            PlayersDatabase.get().set(uuid + ".inv", p.getInventory().getContents());
            PlayersDatabase.save();
        }
        if (armor) {
            PlayersDatabase.get().set(uuid + ".armor", p.getInventory().getArmorContents());
            PlayersDatabase.save();
        }
        if (exp) {
            PlayersDatabase.get().set(uuid + ".level", p.getLevel());
            PlayersDatabase.save();
        }
        if (health) {
            PlayersDatabase.get().set(uuid + ".exp", p.getExp());
            PlayersDatabase.save();
        }
        if (food) {
            PlayersDatabase.get().set(uuid + ".health", p.getHealth());
            PlayersDatabase.save();
        }
        if (location) {
            PlayersDatabase.get().set(uuid + ".location", p.getLocation());
            PlayersDatabase.save();
        }
    }

    // ERASE

    private static void erasePlayerData(Player p) {
        p.getInventory().clear();

        p.getInventory().setArmorContents(null);

        p.setLevel(0);

        p.setExp(0);

        p.setHealth(20);

        p.setFoodLevel(20);
    }

    private static void erasePlayerData(Player p, Boolean inventory, Boolean armor, Boolean exp, Boolean health, Boolean food) {
        if (inventory) {
            p.getInventory().clear();
        }

        if (armor) {
            p.getInventory().setArmorContents(null);
        }

        if (exp) {
            p.setLevel(0);
            p.setExp(0);
        }

        if (health) {
            p.setHealth(20);
        }

        if (food) {
            p.setFoodLevel(20);
        }
    }

    // LOAD

    private static void loadPlayerData(Player p) {
        UUID uuid = p.getUniqueId();

        if (PlayersDatabase.get().get(uuid + ".inv") != null) {
            List<ItemStack> inventory = (List<ItemStack>) PlayersDatabase.get().get(uuid + ".inv");
            assert inventory != null;
            p.getInventory().setContents(inventory.toArray(new ItemStack[0]));
        }
        if (PlayersDatabase.get().get(uuid + ".armor") != null) {
            List<ItemStack> armor = (List<ItemStack>) PlayersDatabase.get().get(uuid + ".armor");
            assert armor != null;
            p.getInventory().setArmorContents(armor.toArray(new ItemStack[0]));
        }
        if (PlayersDatabase.get().get(uuid + ".level") != null) {
            int level = PlayersDatabase.get().getInt(uuid + ".level");
            p.setLevel(level);
        }
        if (PlayersDatabase.get().get(uuid + ".exp") != null) {
            float exp = Float.parseFloat(Objects.requireNonNull(PlayersDatabase.get().getString(uuid + ".exp")));
            p.setExp(exp);
        }
        if (PlayersDatabase.get().get(uuid + ".health") != null) {
            float health = Float.parseFloat(Objects.requireNonNull(PlayersDatabase.get().getString(uuid + ".health")));
            p.setHealth(health);
        }
        if (PlayersDatabase.get().get(uuid + ".food") != null) {
            int food = PlayersDatabase.get().getInt(uuid + ".food");
            p.setFoodLevel(food);
        }
        if (PlayersDatabase.get().get(uuid + ".location") != null) {
            Location location = (Location) PlayersDatabase.get().get(uuid + ".location");
            assert location != null;
            p.teleport(location);
        }

        PlayersDatabase.get().set(uuid.toString(), null);
        PlayersDatabase.save();
    }

    // METHODS

    private static void whatToSave(Player p) {
        Boolean allEnter = EnterAdminMode.Save.all;

        if (allEnter) {
            savePlayerData(p);
        } else {
            Boolean inv = EnterAdminMode.Save.inv;
            Boolean armor = EnterAdminMode.Save.armor;
            Boolean exp = EnterAdminMode.Save.exp;
            Boolean health = EnterAdminMode.Save.health;
            Boolean food = EnterAdminMode.Save.food;
            Boolean location = EnterAdminMode.Save.location;
            savePlayerData(p, inv, armor, exp, health, food, location);
        }
    }

    private static void whatToErase(Player p) {
        Boolean allErase = EnterAdminMode.Erase.all;

        if (allErase) {
            erasePlayerData(p);
        } else {
            Boolean inv = EnterAdminMode.Erase.inv;
            Boolean armor = EnterAdminMode.Erase.armor;
            Boolean exp = EnterAdminMode.Erase.exp;
            Boolean health = EnterAdminMode.Erase.health;
            Boolean food = EnterAdminMode.Erase.food;
            erasePlayerData(p, inv, armor, exp, health, food);
        }
    }
}
