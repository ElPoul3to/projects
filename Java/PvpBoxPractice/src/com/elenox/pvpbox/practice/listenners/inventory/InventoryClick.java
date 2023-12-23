package com.elenox.pvpbox.practice.listenners.inventory;

import com.elenox.pvpbox.practice.list.ListManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {

    @EventHandler
    public void onClickInv(InventoryClickEvent event){
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();

        if(ListManager.allPlayerMatch.contains(player)){
            event.setCancelled(false);
        }

        if(player.isOp()){
            event.setCancelled(false);
        }
    }
}
