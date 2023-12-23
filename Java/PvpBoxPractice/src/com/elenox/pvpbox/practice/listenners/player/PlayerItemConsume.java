package com.elenox.pvpbox.practice.listenners.player;

import com.elenox.pvpbox.practice.PvpBoxPractice;
import com.elenox.pvpbox.practice.list.ListManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerItemConsume implements Listener {
    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event){
        if(ListManager.playerWhotEditKit.contains(event.getPlayer())){
            Bukkit.getScheduler().runTaskLaterAsynchronously(PvpBoxPractice.INSTANCE, new Runnable() {
                @Override
                public void run() {

                }
            },1);
            event.setCancelled(true);
        }
        if(ListManager.allPlayerMatch.contains(event.getPlayer())){
            if(event.getItem().getType() == Material.POTION){
                Bukkit.getScheduler().runTaskLaterAsynchronously(PvpBoxPractice.INSTANCE, new Runnable() {
                    @Override
                    public void run() {
                        event.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                    }
                },1);
            }
        }
    }
}