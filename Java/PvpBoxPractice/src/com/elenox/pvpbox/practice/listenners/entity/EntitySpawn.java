package com.elenox.pvpbox.practice.listenners.entity;

import com.elenox.pvpbox.practice.manager.ZoneManager;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntitySpawn implements Listener {
    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event){
        EntityType entityType = event.getEntityType();
        event.setCancelled(true);

        if(entityType == EntityType.CREEPER || entityType == EntityType.ENDER_PEARL || entityType == EntityType.SPLASH_POTION ||
                entityType == EntityType.FISHING_HOOK || entityType == EntityType.ARROW){
            event.setCancelled(false);

        }
        if(ZoneManager.cuboidEditKit.isIn(event.getLocation())){
            event.setCancelled(true);
        }
    }
}