package me.honzakomi.adminmode.commands.all;

import me.honzakomi.adminmode.database.PlayersDatabase;
import me.honzakomi.adminmode.messages.PlayerMessage;
import me.honzakomi.adminmode.permissions.CheckPermission;
import me.honzakomi.adminmode.variables.Variables;
import org.bukkit.entity.Player;

import java.util.Collection;

import static me.honzakomi.adminmode.AdminMode.plugin;

public class CheckCommand {
    public static final String commandName = "check";

    public static void command(Player p, String[] args) {
        if (CheckPermission.check(p, Variables.Permissions.checkCommandPermission) || CheckPermission.check(p, Variables.Permissions.allPermissions)) {
            if (args.length > 1) {
                checkIfPlayerIsAdmin(p, args[1]);
                return;
            }

            p.sendMessage(PlayerMessage.specifyPlayer);
        }
    }

    private static void checkIfPlayerIsAdmin(Player p, String t) {
        Player target = plugin.getServer().getPlayerExact(t);
        Collection<? extends Player> onlinePlayers = plugin.getServer().getOnlinePlayers();

        if (!onlinePlayers.contains(target)) {
            p.sendMessage(PlayerMessage.playerIsNotOnline);
            return;
        }

        assert target != null;
        if (!PlayersDatabase.get().contains(String.valueOf(target.getUniqueId()))) {
            p.sendMessage(PlayerMessage.playerIsNotAdmin);
            return;
        }

        p.sendMessage(PlayerMessage.playerIsAdmin);
    }
}
