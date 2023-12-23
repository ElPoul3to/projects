package com.elenox.pvpbox.practice.listenners.player.interact;

import com.elenox.api.ElenoxAPI;
import com.elenox.pvpbox.practice.menu.EditKitKits;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractEditKit implements Listener {
    @EventHandler
    public void onInteractClassement(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack is = event.getItem();
        Action act = event.getAction();

        if(is == null)return;

        if(is.getType() == Material.ANVIL  && is.getItemMeta().getDisplayName().equalsIgnoreCase("§8Editeur des Kits")){
            if(act == Action.RIGHT_CLICK_BLOCK || act == Action.RIGHT_CLICK_AIR){
                ElenoxAPI.getElenoxAPI().getGuiManager().openGui(player,new EditKitKits());
            }
        }
    }
}
