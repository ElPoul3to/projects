package com.elenox.pvpbox.practice.listenners.player;

import com.elenox.api.ElenoxAPI;
import com.elenox.api.player.ElPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        ElPlayer elplayer = ElenoxAPI.getElenoxAPI().getElPlayer(event.getPlayer());
        event.setFormat(elplayer.getDisplayRank()+event.getPlayer().getDisplayName()+" §7» §f"+event.getMessage());
    }
}
