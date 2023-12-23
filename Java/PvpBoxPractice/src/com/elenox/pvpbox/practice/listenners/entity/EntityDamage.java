package com.elenox.pvpbox.practice.listenners.entity;

import com.elenox.pvpbox.practice.list.ListManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamage implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event){
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();

        if(!(ListManager.allPlayerMatch.contains(player))) event.setCancelled(true);
    }
}
