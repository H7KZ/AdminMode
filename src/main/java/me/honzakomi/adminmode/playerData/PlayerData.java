package me.honzakomi.adminmode.playerData;

import me.honzakomi.adminmode.database.PlayersDatabase;
import me.honzakomi.adminmode.messages.PlayerMessage;
import org.bukkit.entity.Player;

import java.util.Collection;

import static me.honzakomi.adminmode.AdminMode.plugin;

public class PlayerData {

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

    // ERASE

    private static void erasePlayerData(Player p) {

    }

    // ENABLE

    public static void enableYourself(Player p) {
        if (checkIfPlayerIsAdmin(p)) {
            return;
        }

        savePlayerData(p);

        erasePlayerData(p);
    }

    public static void enableTarget(Player p, Player t) {
        if (checkIfTargetIsAdmin(p, t)) {
            return;
        }

        savePlayerData(p);

        erasePlayerData(p);
    }

    private static void savePlayerData(Player p) {

    }

    // DISABLE

    public static void disableYourself(Player p) {
        erasePlayerData(p);

        loadPlayerData(p);
    }

    public static void disableTarget(Player p, Player t) {
        erasePlayerData(t);

        loadPlayerData(t);
    }

    private static void loadPlayerData(Player p) {

    }
}
