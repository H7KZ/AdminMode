package me.honzakomi.adminmode.commands.all;

import me.honzakomi.adminmode.database.PlayersDatabase;
import me.honzakomi.adminmode.messages.PlayerMessage;
import me.honzakomi.adminmode.permissions.CheckPermission;
import org.bukkit.entity.Player;

import static me.honzakomi.adminmode.AdminMode.plugin;
import static me.honzakomi.adminmode.permissions.Permissions.enableCommandPermission;
import static me.honzakomi.adminmode.permissions.Permissions.enablePlayerCommandPermission;
import static me.honzakomi.adminmode.playerData.PlayerData.enableTarget;
import static me.honzakomi.adminmode.playerData.PlayerData.enableYourself;

public class EnableCommand {
    public static final String commandName = "enable";

    public static void command(Player p, String[] args) {
        if (args.length > 1 && CheckPermission.check(p, enablePlayerCommandPermission)) {
            command(p, args[1]);
            return;
        }

        if (args.length == 1 && CheckPermission.check(p, enableCommandPermission)) {
            if (PlayersDatabase.get().contains(p.getUniqueId().toString())) {
                p.sendMessage(PlayerMessage.isInAdminMode);
                return;
            }

            enableYourself(p);
        }
    }

    public static void command(Player p, String t) {
        Player target = plugin.getServer().getPlayerExact(t);

        assert target != null;
        if (PlayersDatabase.get().contains(target.getUniqueId().toString())) {
            p.sendMessage(PlayerMessage.targetIsInAdminMode);
            return;
        }

        enableTarget(p, target);
    }
}
