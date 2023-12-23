package com.elenox.pvpbox.practice.listenners.player;

import com.elenox.pvpbox.practice.list.ListManager;
import com.elenox.pvpbox.practice.list.PracticeList;
import com.elenox.pvpbox.practice.manager.MatchManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        final Player player = event.getPlayer();

        if(ListManager.allPlayerMatch.contains(player)) {
            for(PracticeList match : ListManager.allMatch) {
                if (match.contains(player)) {
                    MatchManager.onFinishedMatch(player,match);
                    break;
                }
            }
        }else if (ListManager.allPlayerQueue.contains(player)){
            for(PracticeList queue : ListManager.allQueue){
                if(queue.contains(player)){
                    ListManager.allPlayerQueue.remove(player);
                    queue.remove(player);
                    break;
                }
            }
        }else if(ListManager.playerWhotEditKit.contains(player)){
            for(PracticeList list : ListManager.allListEditKit){
                if(list.contains(player)){
                    ListManager.playerWhotEditKit.remove(player);
                    list.remove(player);
                    break;
                }
            }
        }
    }
}
