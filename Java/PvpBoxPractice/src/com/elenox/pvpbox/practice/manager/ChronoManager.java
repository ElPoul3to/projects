package com.elenox.pvpbox.practice.manager;

import com.elenox.api.ElenoxAPI;
import com.elenox.pvpbox.practice.PvpBoxPractice;
import com.elenox.pvpbox.practice.list.ListManager;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ChronoManager extends BukkitRunnable {
    private int timer = 10;
    private Player firstPlayer;
    private Player secondPlayer;

    public ChronoManager(Player firstPlayer, Player secondPlayer){
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        ListManager.playerFrozen.add(firstPlayer);
        ListManager.playerFrozen.add(secondPlayer);
    }

    @Override
    public void run() {
        if(timer == 10||timer == 5||timer == 4||timer == 3||timer == 2||timer == 1){
            firstPlayer.sendMessage(PvpBoxPractice.getPrefix()+"§3Le combat comence dans §e"+timer);
            secondPlayer.sendMessage(PvpBoxPractice.getPrefix()+"§3Le combat comence dans §e"+timer);

        }else if(timer == 0){
            ElenoxAPI.getElenoxAPI().getActionBar().sendActionBar(firstPlayer,"§5Bonne chance :) !");
            ElenoxAPI.getElenoxAPI().getActionBar().sendActionBar(secondPlayer,"§5Bonne chance :) !");
            firstPlayer.sendMessage(PvpBoxPractice.getPrefix()+"§3Le match commence !");
            secondPlayer.sendMessage(PvpBoxPractice.getPrefix()+"§3Le match commence !");
            ListManager.playerFrozen.remove(firstPlayer);
            ListManager.playerFrozen.remove(secondPlayer);

            cancel();
        }
        firstPlayer.setLevel(timer);
        secondPlayer.setLevel(timer);

        timer --;
    }
}