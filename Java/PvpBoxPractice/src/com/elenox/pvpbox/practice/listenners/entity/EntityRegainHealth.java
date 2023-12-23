package com.elenox.pvpbox.practice.listenners.entity;

import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class EntityRegainHealth implements Listener {
    public void onReaginHealth(EntityRegainHealthEvent event){
        event.setCancelled(true);
    }
}
