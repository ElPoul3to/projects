package com.elenox.pvpbox.practice.listenners.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;

public class BlockExplode implements Listener {
    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event){
        event.blockList().clear();
    }
}
