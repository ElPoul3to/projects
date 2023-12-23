package com.elenox.pvpbox.practice.listenners;

import com.elenox.pvpbox.practice.PvpBoxPractice;
import com.elenox.pvpbox.practice.listenners.block.BlockBreak;
import com.elenox.pvpbox.practice.listenners.block.BlockExplode;
import com.elenox.pvpbox.practice.listenners.block.BlockPlace;
import com.elenox.pvpbox.practice.listenners.block.SignChange;
import com.elenox.pvpbox.practice.listenners.entity.*;
import com.elenox.pvpbox.practice.listenners.inventory.InventoryClick;
import com.elenox.pvpbox.practice.listenners.player.*;
import com.elenox.pvpbox.practice.listenners.player.interact.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class ListenerManager {

    private void registerEvent(Listener listener){
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(listener, PvpBoxPractice.INSTANCE);
    }

    public void runListenner(){
        this.registerEvent(new PlayerJoin());
        this.registerEvent(new PlayerInteractLeaveQueue());
        this.registerEvent(new PlayerQuit());
        this.registerEvent(new PlayerIntearctRanked());
        this.registerEvent(new PlayerInteractUnranked());
        this.registerEvent(new PlayerChangeHand());
        this.registerEvent(new FoodChange());
        this.registerEvent(new BlockExplode());
        this.registerEvent(new EntitySpawn());
        this.registerEvent(new PlayerDeath());
        this.registerEvent(new PlayerRespawn());
        this.registerEvent(new PlayerIntearctStats());
        this.registerEvent(new InventoryClick());
        this.registerEvent(new EntityDamage());
        this.registerEvent(new BlockPlace());
        this.registerEvent(new PlayerMove());
        this.registerEvent(new PlayerInteractClassement());
        this.registerEvent(new PlayerChat());
        this.registerEvent(new PlayerInteractEditKit());
        this.registerEvent(new SignChange());
        this.registerEvent(new PlayerInteractSignEditKit());
        this.registerEvent(new EntityRegainHealth());
        this.registerEvent(new PlayerItemConsume());
        this.registerEvent(new PlayerDropItem());
        this.registerEvent(new BlockBreak());
        this.registerEvent(new EntityExplode());
    }
}
