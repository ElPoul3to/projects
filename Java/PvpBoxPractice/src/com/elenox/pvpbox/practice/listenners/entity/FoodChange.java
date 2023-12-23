package com.elenox.pvpbox.practice.listenners.entity;

import com.elenox.pvpbox.practice.list.ListManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodChange implements Listener {
    @EventHandler
    public void onFood(FoodLevelChangeEvent event){
        if(!(event.getEntity() instanceof Player))return;

        if(ListManager.allMatch.contains(event.getEntity())){
            event.setCancelled(false);
        }
        if(!(ListManager.allMatch.contains(event.getEntity()))){
            event.setCancelled(true);
        }
    }
}
