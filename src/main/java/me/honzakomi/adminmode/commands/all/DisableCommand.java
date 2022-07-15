package me.honzakomi.adminmode.commands.all;

import me.honzakomi.adminmode.database.PlayersDatabase;
import me.honzakomi.adminmode.messages.PlayerMessage;
import me.honzakomi.adminmode.permissions.CheckPermission;
import org.bukkit.entity.Player;

import static me.honzakomi.adminmode.AdminMode.plugin;
import static me.honzakomi.adminmode.permissions.Permissions.disableCommandPermission;
import static me.honzakomi.adminmode.permissions.Permissions.disablePlayerCommandPermission;
import static me.honzakomi.adminmode.playerData.PlayerData.*;

public class DisableCommand {
    public static final String commandName = "disable";

    public static void command(Player p, String[] args) {
        if (args.length > 1 && CheckPermission.check(p, disablePlayerCommandPermission)) {
            command(p, args[1]);
            return;
        }

        if (CheckPermission.check(p, disableCommandPermission)) {
            if (!PlayersDatabase.get().contains(p.getUniqueId().toString())) {
                p.sendMessage(PlayerMessage.playerIsNotAdmin);
                return;
            }

            disableYourself(p);
        }
    }

    public static void command(Player p, String t) {
        Player target = plugin.getServer().getPlayerExact(t);

        assert target != null;
        if (!PlayersDatabase.get().contains(target.getUniqueId().toString())) {
            p.sendMessage(PlayerMessage.targetIsNotAdmin);
            return;
        }

        disableTarget(p, target);
    }
}
