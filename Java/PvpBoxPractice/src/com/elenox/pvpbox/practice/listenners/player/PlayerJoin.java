package com.elenox.pvpbox.practice.listenners.player;

import com.elenox.api.ElenoxAPI;
import com.elenox.pvpbox.practice.manager.SettingsManager;
import com.elenox.pvpbox.practice.save.Kits;
import com.elenox.pvpbox.practice.save.Statistiques;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        SettingsManager.setSpawnSettings(player);
        ElenoxAPI.getElenoxAPI().getActionBar().sendActionBar(player,"§9Bienvenue sur le Practice");

        new Statistiques(player);
        new Kits(player);
    }
}
