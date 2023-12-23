package com.elenox.pvpbox.practice.listenners.player;

import com.elenox.pvpbox.practice.list.ListManager;
import com.elenox.pvpbox.practice.list.PracticeList;
import com.elenox.pvpbox.practice.manager.MatchManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        event.setDeathMessage("");
        if(ListManager.allPlayerMatch.contains(event.getEntity())) {
            for(PracticeList list : ListManager.allMatch) {
                if (list.contains(event.getEntity())) {
                    MatchManager.onFinishedMatch(event.getEntity(),list);
                    break;
                }
            }
        }
    }
}
