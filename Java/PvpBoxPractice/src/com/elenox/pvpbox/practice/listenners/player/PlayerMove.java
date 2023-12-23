package com.elenox.pvpbox.practice.listenners.player;

import com.elenox.pvpbox.practice.list.ListManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if(ListManager.playerFrozen.contains(player)){
            event.setTo(event.getFrom());
        }
    }
}
