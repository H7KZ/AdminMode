package me.honzakomi.adminmode.models;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerModel {
    public Inventory inventory;
    public ItemStack[] armor;
    public float exp;
    public double health;
    public Location location;

    public PlayerModel(Player p) {
        this.inventory = p.getInventory();
        this.armor = p.getInventory().getArmorContents();
        this.exp = p.getExp();
        this.health = p.getHealth();
        this.location = p.getLocation();
    }

    public PlayerModel(Player p, Boolean inventory, Boolean armor, Boolean exp, Boolean health, Boolean location) {
        if (inventory) {
            this.inventory = p.getInventory();
        }
        if (armor) {
            this.armor = p.getInventory().getArmorContents();
        }
        if (exp) {
            this.exp = p.getExp();
        }
        if (health) {
            this.health = p.getHealth();
        }
        if (location) {
            this.location = p.getLocation();
        }
    }
}
