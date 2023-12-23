package com.elenox.pvpbox.practice.listenners.player;

import com.elenox.pvpbox.practice.PvpBoxPractice;
import com.elenox.pvpbox.practice.manager.SettingsManager;
import com.elenox.pvpbox.practice.manager.ZoneManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawn implements Listener {
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
        Bukkit.getScheduler().runTaskLaterAsynchronously(PvpBoxPractice.INSTANCE, new Runnable() {
            @Override
            public void run() {
                event.setRespawnLocation(ZoneManager.getSpawn());
                SettingsManager.setSpawnSettings(event.getPlayer());
            }
        },1);
    }
}
