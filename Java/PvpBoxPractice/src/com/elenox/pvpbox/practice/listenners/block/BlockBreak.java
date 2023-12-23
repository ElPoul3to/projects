package com.elenox.pvpbox.practice.listenners.block;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        event.setCancelled(true);

        if(event.getPlayer().isOp() && event.getPlayer().getGameMode().equals(GameMode.CREATIVE)){
            event.setCancelled(false);
        }
    }
}
