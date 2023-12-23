package com.elenox.pvpbox.practice.listenners.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChange implements Listener {
    @EventHandler
    public void onSign(SignChangeEvent event){
        if(event.getPlayer().isOp()){
            event.setLine(0, event.getLine(0).replace('&', '§'));
            event.setLine(1, event.getLine(1).replace('&', '§'));
            event.setLine(2, event.getLine(2).replace('&', '§'));
            event.setLine(3, event.getLine(3).replace('&', '§'));
        }
    }
}
