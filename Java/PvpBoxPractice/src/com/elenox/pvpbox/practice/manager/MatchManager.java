package com.elenox.pvpbox.practice.manager;

import com.elenox.pvpbox.practice.PvpBoxPractice;
import com.elenox.pvpbox.practice.list.ListManager;
import com.elenox.pvpbox.practice.list.PracticeList;
import com.elenox.pvpbox.practice.save.Kits;
import com.elenox.pvpbox.practice.save.Statistiques;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MatchManager {

    public static void systemQueue(Player currentPlayer, PracticeList queue,PracticeList match, String nom,String nomPracticeList){
        if(queue.size() == 0){
            ItemManager.setItemQueue(currentPlayer,nom);
            queue.add(currentPlayer);
            ListManager.allPlayerQueue.add(currentPlayer);
            Bukkit.broadcastMessage(PvpBoxPractice.getPrefix()+" §3"+currentPlayer.getDisplayName()+" §7a rejoint la queue "+nom+"§7.");
        }else{
            Player otherPlayer = (Player) queue.get(0);
            queue.clear();
            ListManager.allPlayerQueue.remove(otherPlayer);

            match = new PracticeList<Player>(nomPracticeList);
            match.add(currentPlayer);
            match.add(otherPlayer);
            match.trimToSize();
            ListManager.allMatch.add(match);


            onStartedMatch(currentPlayer,otherPlayer,match);
        }
    }

    public static void onStartedMatch(Player firstPlayer, Player secondPlayer,PracticeList match){
        ListManager.allPlayerMatch.add(firstPlayer);
        ListManager.allPlayerMatch.add(secondPlayer);
        setItemsAndArmor(firstPlayer,match);
        setItemsAndArmor(secondPlayer,match);
        new ChronoManager(firstPlayer,secondPlayer).runTaskTimer(PvpBoxPractice.INSTANCE,0,20);
    }

    public static void onFinishedMatch(Player currentPlayer,PracticeList list){
        Player winningPlayer = getSecondPlayer(currentPlayer,list);
        Player deadPlayer = getSecondPlayer(winningPlayer,list);
        String nomMatch = whichIsMatch(list);

        if(isMatchRanked(list)){
            updatePoints(winningPlayer,nomMatch,true);
            updatePoints(deadPlayer,nomMatch,false);

            new Statistiques(deadPlayer,nomMatch).addRankedParty();
            new Statistiques(winningPlayer,nomMatch).addRankedParty();
        }else{
            new Statistiques(deadPlayer,nomMatch).addUnrankedParty();
            new Statistiques(winningPlayer,nomMatch).addUnrankedParty();
            new Statistiques(winningPlayer,nomMatch).addUnrankedWins();
        }

        list.clear();
        ListManager.allMatch.remove(list);
        ListManager.allPlayerMatch.remove(deadPlayer);
        ListManager.allPlayerMatch.remove(winningPlayer);
        deadPlayer.sendMessage(PvpBoxPractice.getPrefix()+"§7Vous avez §c§lPerdu§7.");
        winningPlayer.sendMessage(PvpBoxPractice.getPrefix()+"§7Vous avez §a§lGagné§7.");
        deadPlayer.getActivePotionEffects().clear();
        winningPlayer.getActivePotionEffects().clear();


        // Evite que l'épée Unranked se consomme
        Bukkit.getScheduler().runTaskLaterAsynchronously(PvpBoxPractice.INSTANCE, new Runnable() {
            @Override
            public void run() {
                SettingsManager.setSpawnSettings(deadPlayer);
                SettingsManager.setSpawnSettings(winningPlayer);
            }
        },1);
    }


    private static Player getSecondPlayer(Player currentPlayer, PracticeList list){
        if(currentPlayer == list.get(0)){
            return (Player) list.get(1);
        }else{
            return (Player) list.get(0);
        }
    }

    private static boolean isMatchRanked(PracticeList match){
        if(match.getNom().contains("Ranked")){
            return true;
        }else {
            return false;
        }
    }

    private static String whichIsMatch(PracticeList match){
        String nom= "";
        if(match.getNom().contains("nodebuff")){
            nom = "nodebuff";
        }else if(match.getNom().contains("debuff")){
            nom="debuff";
        }else if(match.getNom().contains("combo")){
            nom="combo";
        }else if(match.getNom().contains("builduhc")){
            nom="builduhc";
        }else if(match.getNom().contains("sumo")){
            nom="sumo";
        }else if(match.getNom().contains("spleef")){
            nom="spleef";
        }else if(match.getNom().contains("archer")){
            nom="archer";
        }else if(match.getNom().contains("elenoxkit")){
            nom="elenoxkit";
        }else if(match.getNom().contains("hache")){
            nom="hache";
        }else if(match.getNom().contains("soupe")){
            nom="soupe";
        }
        return nom;
    }

    private static void setItemsAndArmor(Player player,PracticeList match){
        if(match.getNom().contains("nodebuff")){
            new Kits(player).getKit("nodebuff");
            ItemManager.setNodebuff_Debuff_BuildUHCArmor(player);
        }else if(match.getNom().contains("debuff")){
            new Kits(player).getKit("debuff");
            ItemManager.setNodebuff_Debuff_BuildUHCArmor(player);
        }else if(match.getNom().contains("combo")){
            new Kits(player).getKit("combo");
            ItemManager.setComboArmorAndPotionEffects(player);
        }else if(match.getNom().contains("builduhc")){
            new Kits(player).getKit("builduhc");
            ItemManager.setNodebuff_Debuff_BuildUHCArmor(player);
        }else if(match.getNom().contains("sumo")){
            new Kits(player).getKit("sumo");
            ItemManager.setSumoItems(player);
        }else if(match.getNom().contains("spleef")){
            new Kits(player).getKit("spleef");
        }else if(match.getNom().contains("archer")){
            new Kits(player).getKit("archer");
            ItemManager.setArcherArmor(player);
        }else if(match.getNom().contains("elenox")){
            new Kits(player).getKit("elenoxkit");
            ItemManager.setElenoxArmor(player);
        }else if(match.getNom().contains("hache")){
            new Kits(player).getKit("hache");
            ItemManager.setHachArmor(player);
        }else if(match.getNom().contains("soupe")){
            new Kits(player).getKit("soupe");
        }else{
            ItemManager.setSumoItems(player);
        }
    }

    private static void updatePoints(Player player,String nom,boolean win){
        int somme = -1;
        if(win){
            new Statistiques(player,nom).addRankedWins();
            somme = 3;
        }
        new Statistiques(player,nom).addPoints(somme);
    }
}