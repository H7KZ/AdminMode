package me.honzakomi.adminmode.commands.all;

import me.honzakomi.adminmode.messages.PlayerMessage;
import me.honzakomi.adminmode.permissions.CheckPermission;
import org.bukkit.entity.Player;

import static me.honzakomi.adminmode.AdminMode.plugin;
import static me.honzakomi.adminmode.permissions.Permissions.checkCommandPermission;
import static me.honzakomi.adminmode.playerData.PlayerData.checkIfTargetIsAdmin;

public class CheckCommand {
    public static final String commandName = "check";

    public static void command(Player p, String[] args) {
        if (!CheckPermission.check(p, checkCommandPermission)) {
            return;
        }

        if (args.length < 1) {
            p.sendMessage(PlayerMessage.specifyPlayer);
            return;
        }

        Player target = plugin.getServer().getPlayerExact(args[0]);

        checkIfTargetIsAdmin(p, target);
    }
}
