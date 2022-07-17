package me.honzakomi.adminmode.playerData;

import me.honzakomi.adminmode.database.PlayersDatabase;
import me.honzakomi.adminmode.messages.PlayerMessage;
import me.honzakomi.adminmode.messages.TargetMessage;
import me.honzakomi.adminmode.variables.Variables.EnterAdminMode;
import me.honzakomi.adminmode.variables.Variables.LeaveAdminMode;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static me.honzakomi.adminmode.AdminMode.config;
import static me.honzakomi.adminmode.AdminMode.plugin;

public class PlayerData {

    // ENABLE

    public static void enableYourself(Player p) {
        if (checkIfPlayerIsAdmin(p)) {
            return;
        }

        whatToSave(p);

        whatToEraseEnter(p);

        Boolean giveOp = EnterAdminMode.ChangeTo.player.op;
        String gameModeType = EnterAdminMode.ChangeTo.player.gamemode;

        if (giveOp) {
            p.setOp(true);
        }

        p.setGameMode(GameMode.valueOf(gameModeType));

        PlayersDatabase.get().set(p.getUniqueId() + ".canDisableHimself", config.getBoolean("enterAdminMode.save.canDisableHimself"));
        PlayersDatabase.save();
        PlayersDatabase.get().set(p.getUniqueId() + ".beenGivenByAdmin", false);
        PlayersDatabase.save();

        p.sendMessage(PlayerMessage.enabled);
    }

    public static void enableTarget(Player p, Player t) {
        if (checkIfTargetIsAdmin(p, t)) {
            return;
        }

        whatToSave(t);

        whatToEraseEnter(t);

        Boolean giveOp = EnterAdminMode.ChangeTo.target.op;
        String gameModeType = EnterAdminMode.ChangeTo.target.gamemode;

        if (giveOp) {
            t.setOp(true);
        }

        t.setGameMode(GameMode.valueOf(gameModeType));

        PlayersDatabase.get().set(t.getUniqueId() + ".canDisableHimself", config.getBoolean("enterAdminMode.save.canDisableHimself"));
        PlayersDatabase.save();
        PlayersDatabase.get().set(t.getUniqueId() + ".beenGivenByAdmin", true);
        PlayersDatabase.save();

        p.sendMessage(PlayerMessage.targetEnabled);

        t.sendMessage(TargetMessage.enabled);
    }

    // DISABLE

    public static void disableYourself(Player p) {
        whatToEraseLeave(p);

        loadPlayerData(p, false);

        p.sendMessage(PlayerMessage.disabled);
    }

    public static void disableTarget(Player p, Player t) {
        whatToEraseLeave(t);

        loadPlayerData(t, true);

        p.sendMessage(PlayerMessage.targetDisabled);

        t.sendMessage(TargetMessage.disabled);
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
            p.sendMessage(PlayerMessage.targetIsNotAdminMode);
            return false;
        }

        p.sendMessage(PlayerMessage.targetIsInAdminMode);
        return true;
    }

    public static Boolean checkIfPlayerIsAdmin(Player p) {
        if (PlayersDatabase.get().contains(String.valueOf(p.getUniqueId()))) {
            p.sendMessage(PlayerMessage.isInAdminMode);
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
        PlayersDatabase.get().set(uuid + ".gamemode", p.getGameMode());
        PlayersDatabase.save();
        PlayersDatabase.get().set(uuid + ".op", p.isOp());
        PlayersDatabase.save();
    }

    private static void savePlayerData(Player p, Boolean inventory, Boolean armor, Boolean exp, Boolean health, Boolean food, Boolean location, Boolean gamemode, Boolean op) {
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
        if (gamemode) {
            PlayersDatabase.get().set(uuid + ".gamemode", p.getGameMode());
            PlayersDatabase.save();
        }
        if (op) {
            PlayersDatabase.get().set(uuid + ".op", p.isOp());
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

    private static void loadPlayerData(Player p, Boolean isTarget) {
        UUID uuid = p.getUniqueId();

        if (PlayersDatabase.get().get(uuid + ".inv") != null) {
            @SuppressWarnings("unchecked")
            List<ItemStack> inventory = (List<ItemStack>) PlayersDatabase.get().get(uuid + ".inv");
            assert inventory != null;
            p.getInventory().setContents(inventory.toArray(new ItemStack[0]));
        }
        if (PlayersDatabase.get().get(uuid + ".armor") != null) {
            @SuppressWarnings("unchecked")
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

        if (isTarget) {
            if (LeaveAdminMode.ChangeTo.target.savedGamemode) {
                if (PlayersDatabase.get().get(uuid + ".gamemode") != null) {
                    GameMode gamemode = GameMode.valueOf(PlayersDatabase.get().getString(uuid + ".gamemode"));
                    p.setGameMode(gamemode);
                }
            } else {
                String gameModeType = LeaveAdminMode.ChangeTo.target.gamemode;

                p.setGameMode(GameMode.valueOf(gameModeType));
            }

            if (LeaveAdminMode.ChangeTo.target.savedOp) {
                if (PlayersDatabase.get().get(uuid + ".op") != null) {
                    boolean op = PlayersDatabase.get().getBoolean(uuid + ".op");
                    p.setOp(op);
                }
            } else {
                Boolean keepOp = LeaveAdminMode.ChangeTo.target.op;

                if (!keepOp) {
                    p.setOp(false);
                }
            }
        } else {
            if (LeaveAdminMode.ChangeTo.player.savedGamemode) {
                if (PlayersDatabase.get().get(uuid + ".gamemode") != null) {
                    GameMode gamemode = GameMode.valueOf(PlayersDatabase.get().getString(uuid + ".gamemode"));
                    p.setGameMode(gamemode);
                }
            } else {
                String gameModeType = LeaveAdminMode.ChangeTo.player.gamemode;

                p.setGameMode(GameMode.valueOf(gameModeType));
            }

            if (LeaveAdminMode.ChangeTo.player.savedOp) {
                if (PlayersDatabase.get().get(uuid + ".op") != null) {
                    boolean op = PlayersDatabase.get().getBoolean(uuid + ".op");
                    p.setOp(op);
                }
            } else {
                Boolean keepOp = LeaveAdminMode.ChangeTo.player.op;

                if (!keepOp) {
                    p.setOp(false);
                }
            }
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
            Boolean gamemode = EnterAdminMode.Save.gamemode;
            Boolean op = EnterAdminMode.Save.op;
            savePlayerData(p, inv, armor, exp, health, food, location, gamemode, op);
        }
    }

    private static void whatToEraseEnter(Player p) {
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

    private static void whatToEraseLeave(Player p) {
        Boolean allErase = LeaveAdminMode.Erase.all;

        if (allErase) {
            erasePlayerData(p);
        } else {
            Boolean inv = LeaveAdminMode.Erase.inv;
            Boolean armor = LeaveAdminMode.Erase.armor;
            Boolean exp = LeaveAdminMode.Erase.exp;
            Boolean health = LeaveAdminMode.Erase.health;
            Boolean food = LeaveAdminMode.Erase.food;
            erasePlayerData(p, inv, armor, exp, health, food);
        }
    }
}
