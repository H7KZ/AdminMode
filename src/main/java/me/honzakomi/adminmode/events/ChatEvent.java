package me.honzakomi.adminmode.events;

import me.honzakomi.adminmode.AdminMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.EventListener;

public class ChatEvent implements EventListener {

    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent e) {
        if (AdminMode.admins.hasPlayer(e.getPlayer())) {
            e.setMessage(AdminMode.admins.getPrefix() + e.getFormat());
        }
    }
}
