package me.honzakomi.adminmode.tabcompleter;

import me.honzakomi.adminmode.variables.Variables.Commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> result = new ArrayList<>();

        if (strings.length == 1) {
            for (String cmd : Commands.commandNameList) {
                if (cmd.toLowerCase().startsWith(strings[0].toLowerCase())) {
                    result.add(cmd);
                }
            }

            return result;
        }

        return null;
    }
}
