package com.elenox.pvpbox.practice.listenners.player.interact;

import com.elenox.api.ElenoxAPI;
import com.elenox.pvpbox.practice.menu.ValidateModifKit;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class PlayerInteractSignEditKit implements Listener {
    @EventHandler
    public void onInteractSignEditKit(PlayerInteractEvent event){
        Player player =event.getPlayer();
        Action action = event.getAction();

        if(event.getClickedBlock() != null && action == Action.RIGHT_CLICK_BLOCK){
            BlockState bs =event.getClickedBlock().getState();
            if(bs instanceof Sign){
                Sign sign = (Sign) bs;
                if(sign.getLine(0).equalsIgnoreCase("§a[Valider]")){
                    ElenoxAPI.getElenoxAPI().getGuiManager().openGui(player,new ValidateModifKit());
                }
            }
        }
    }
}
