package com.elenox.pvpbox.practice.listenners.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class PlayerChangeHand implements Listener {
    @EventHandler
    public void onChangeHand(PlayerSwapHandItemsEvent event){
        event.setCancelled(true);
    }
}
