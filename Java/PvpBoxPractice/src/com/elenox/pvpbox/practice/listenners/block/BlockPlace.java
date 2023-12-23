package com.elenox.pvpbox.practice.listenners.block;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        if(event.getPlayer().isOp() && event.getPlayer().getGameMode().equals(GameMode.CREATIVE)){
            event.setBuild(true);
        }else {
            event.setBuild(false);
        }
    }
}
