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
        EnterAdminMode.Save.location = config.getBoolean("enterAdminMode.save.location");

        // ENTER ERASE
        EnterAdminMode.Erase.all = config.getBoolean("enterAdminMode.erase.all");
        EnterAdminMode.Erase.inv = config.getBoolean("enterAdminMode.erase.inv");
        EnterAdminMode.Erase.armor = config.getBoolean("enterAdminMode.erase.armor");
        EnterAdminMode.Erase.exp = config.getBoolean("enterAdminMode.erase.exp");
        EnterAdminMode.Erase.health = config.getBoolean("enterAdminMode.erase.health");

        // ENTER CHANGE TO
        EnterAdminMode.ChangeTo.gamemode = config.getInt("enterAdminMode.changeTo.gamemode");
        EnterAdminMode.ChangeTo.op = config.getBoolean("enterAdminMode.changeTo.op");

        // LEAVE ERASE
        LeaveAdminMode.Erase.all = config.getBoolean("leaveAdminMode.erase.all");
        LeaveAdminMode.Erase.inv = config.getBoolean("leaveAdminMode.erase.inv");
        LeaveAdminMode.Erase.armor = config.getBoolean("leaveAdminMode.erase.armor");
        LeaveAdminMode.Erase.exp = config.getBoolean("leaveAdminMode.erase.exp");
        LeaveAdminMode.Erase.health = config.getBoolean("leaveAdminMode.erase.health");

        // LEAVE CHANGE TO
        LeaveAdminMode.ChangeTo.gamemode = config.getInt("leaveAdminMode.changeTo.gamemode");
        LeaveAdminMode.ChangeTo.op = config.getBoolean("leaveAdminMode.changeTo.op");
    }

    static class EnterAdminMode {
        public static class Save {
            public static Boolean all;
            public static Boolean inv;
            public static Boolean armor;
            public static Boolean exp;
            public static Boolean health;
            public static Boolean location;
        }

        public static class Erase {
            public static Boolean all;
            public static Boolean inv;
            public static Boolean armor;
            public static Boolean exp;
            public static Boolean health;
        }

        public static class ChangeTo {
            public static int gamemode;
            public static Boolean op;
        }
    }

    static class LeaveAdminMode {
        public static class Erase {
            public static Boolean all;
            public static Boolean inv;
            public static Boolean armor;
            public static Boolean exp;
            public static Boolean health;
        }

        public static class ChangeTo {
            public static int gamemode;
            public static Boolean op;
        }
    }

    public static class Commands {
        public static String[] commandNameList = {"help", "enable", "disable", "enable <player name>", "disable <player name>", "check", "reload"};
    }
}
