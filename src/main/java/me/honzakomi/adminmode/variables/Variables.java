package me.honzakomi.adminmode.variables;

import static me.honzakomi.adminmode.AdminMode.config;

public class Variables {
    public static void init() {
        // ENTER SAVE
        EnterAdminMode.Save.all = config.getBoolean("enterAdminMode.save.all");
        EnterAdminMode.Save.inv = config.getBoolean("enterAdminMode.save.inv");
        EnterAdminMode.Save.armor = config.getBoolean("enterAdminMode.save.armor");
        EnterAdminMode.Save.exp = config.getBoolean("enterAdminMode.save.exp");
        EnterAdminMode.Save.health = config.getBoolean("enterAdminMode.save.health");
        EnterAdminMode.Save.food = config.getBoolean("enterAdminMode.save.food");
        EnterAdminMode.Save.location = config.getBoolean("enterAdminMode.save.location");
        EnterAdminMode.Save.gamemode = config.getBoolean("enterAdminMode.save.gamemode");
        EnterAdminMode.Save.op = config.getBoolean("enterAdminMode.save.op");

        // ENTER ERASE
        EnterAdminMode.Erase.all = config.getBoolean("enterAdminMode.erase.all");
        EnterAdminMode.Erase.inv = config.getBoolean("enterAdminMode.erase.inv");
        EnterAdminMode.Erase.armor = config.getBoolean("enterAdminMode.erase.armor");
        EnterAdminMode.Erase.exp = config.getBoolean("enterAdminMode.erase.exp");
        EnterAdminMode.Erase.health = config.getBoolean("enterAdminMode.erase.health");
        EnterAdminMode.Erase.food = config.getBoolean("enterAdminMode.erase.food");

        // ENTER CHANGE TO
        EnterAdminMode.ChangeTo.player.gamemode = config.getString("enterAdminMode.changeTo.player.gamemode");
        EnterAdminMode.ChangeTo.player.op = config.getBoolean("enterAdminMode.changeTo.player.op");
        EnterAdminMode.ChangeTo.target.gamemode = config.getString("enterAdminMode.changeTo.target.gamemode");
        EnterAdminMode.ChangeTo.target.op = config.getBoolean("enterAdminMode.changeTo.target.op");

        // LEAVE ERASE
        LeaveAdminMode.Erase.all = config.getBoolean("leaveAdminMode.erase.all");
        LeaveAdminMode.Erase.inv = config.getBoolean("leaveAdminMode.erase.inv");
        LeaveAdminMode.Erase.armor = config.getBoolean("leaveAdminMode.erase.armor");
        LeaveAdminMode.Erase.exp = config.getBoolean("leaveAdminMode.erase.exp");
        LeaveAdminMode.Erase.health = config.getBoolean("leaveAdminMode.erase.health");
        LeaveAdminMode.Erase.food = config.getBoolean("leaveAdminMode.erase.food");

        // LEAVE CHANGE TO PLAYER
        LeaveAdminMode.ChangeTo.player.savedGamemode = config.getBoolean("leaveAdminMode.changeTo.player.savedGamemode");
        LeaveAdminMode.ChangeTo.player.gamemode = config.getString("leaveAdminMode.changeTo.player.gamemode");
        LeaveAdminMode.ChangeTo.player.savedOp = config.getBoolean("leaveAdminMode.changeTo.player.savedOp");
        LeaveAdminMode.ChangeTo.player.op = config.getBoolean("leaveAdminMode.changeTo.player.op");
        // LEAVE CHANGE TO TARGET
        LeaveAdminMode.ChangeTo.target.savedGamemode = config.getBoolean("leaveAdminMode.changeTo.target.savedGamemode");
        LeaveAdminMode.ChangeTo.target.gamemode = config.getString("leaveAdminMode.changeTo.target.gamemode");
        LeaveAdminMode.ChangeTo.target.savedOp = config.getBoolean("leaveAdminMode.changeTo.target.savedOp");
        LeaveAdminMode.ChangeTo.target.op = config.getBoolean("leaveAdminMode.changeTo.target.op");
    }

    public static class EnterAdminMode {
        public static class Save {
            public static Boolean all;
            public static Boolean inv;
            public static Boolean armor;
            public static Boolean exp;
            public static Boolean health;
            public static Boolean food;
            public static Boolean location;
            public static Boolean gamemode;
            public static Boolean op;
        }

        public static class Erase {
            public static Boolean all;
            public static Boolean inv;
            public static Boolean armor;
            public static Boolean exp;
            public static Boolean health;
            public static Boolean food;
        }

        public static class ChangeTo {
            public static class player {
                public static String gamemode;
                public static Boolean op;
            }

            public static class target {
                public static String gamemode;
                public static Boolean op;
            }
        }
    }

    public static class LeaveAdminMode {
        public static class Erase {
            public static Boolean all;
            public static Boolean inv;
            public static Boolean armor;
            public static Boolean exp;
            public static Boolean health;
            public static Boolean food;
        }

        public static class ChangeTo {
            public static class player {
                public static Boolean savedGamemode;
                public static String gamemode;
                public static Boolean savedOp;
                public static Boolean op;
            }

            public static class target {
                public static Boolean savedGamemode;
                public static String gamemode;
                public static Boolean savedOp;
                public static Boolean op;
            }
        }
    }

    public static class Commands {
        public static final String[] commandNameList = {"help", "enable", "disable", "check"};
    }
}
