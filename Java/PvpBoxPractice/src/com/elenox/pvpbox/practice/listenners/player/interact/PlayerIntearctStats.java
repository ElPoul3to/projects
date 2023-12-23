package com.elenox.pvpbox.practice.listenners.player.interact;

import com.elenox.api.ElenoxAPI;
import com.elenox.pvpbox.practice.menu.StatistiquesKit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerIntearctStats implements Listener {
    @EventHandler
    public void onInteractUnranked(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack is = event.getItem();
        Action act = event.getAction();

        if(is == null)return;

        if(is.getType() == Material.PLAYER_HEAD  && is.getItemMeta().getDisplayName().equalsIgnoreCase("§eVos Statistiques")){
            if(act == Action.RIGHT_CLICK_BLOCK || act == Action.RIGHT_CLICK_AIR){
                ElenoxAPI.getElenoxAPI().getGuiManager().openGui(player,new StatistiquesKit());
            }
        }
    }
}
