package com.elenox.pvpbox.practice.list;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class ListManager {
    // Autres listes
    public static ArrayList<PracticeList> allMatch = new ArrayList<>();
    public static ArrayList<PracticeList> allQueue = new ArrayList<>();
    public static ArrayList<Player> allPlayerMatch = new ArrayList<>();
    public static ArrayList<Player> allPlayerQueue = new ArrayList<>();
    public static PracticeList<UUID> moderatorInVanish = new PracticeList<>("moderatorInVanish");
    public static PracticeList<UUID> playerFrozen = new PracticeList<>("playerFrozen");

    // Listes match Unranked
    public static PracticeList<Player> nodebuffUnranked;
    public static PracticeList<Player> debuffUnranked;
    public static PracticeList<Player> comboUnranked;
    public static PracticeList<Player> builduhcUnranked;
    public static PracticeList<Player> sumoUnranked;
    public static PracticeList<Player> spleefUnranked;
    public static PracticeList<Player> archerUnranked;
    public static PracticeList<Player> elenoxUnranked;
    public static PracticeList<Player> hacheUnranked;
    public static PracticeList<Player> soupeUnranked;

    // Listes match Ranked
    public static PracticeList<Player> nodebuffRanked;
    public static PracticeList<Player> debuffRanked;
    public static PracticeList<Player> comboRanked;
    public static PracticeList<Player> builduhcRanked;
    public static PracticeList<Player> sumoRanked;
    public static PracticeList<Player> spleefRanked;
    public static PracticeList<Player> archerRanked;
    public static PracticeList<Player> elenoxRanked;
    public static PracticeList<Player> hacheRanked;
    public static PracticeList<Player> soupeRanked;

    // Listes queue Unranked
    public static PracticeList<Player> nodebuffUnrankedQueue = new PracticeList<>("nodebuffUnrankedQueue");
    public static PracticeList<Player> debuffUnrankedQueue = new PracticeList<>("debuffUnrankedQueue");
    public static PracticeList<Player> comboUnrankedQueue = new PracticeList<>("comboUnrankedQueue");
    public static PracticeList<Player> builduhcUnrankedQueue = new PracticeList<>("builduhcUnrankedQueue");
    public static PracticeList<Player> sumoUnrankedQueue = new PracticeList<>("sumoUnrankedQueue");
    public static PracticeList<Player> spleefUnrankedQueue = new PracticeList<>("spleefUnrankedQueue");
    public static PracticeList<Player> archerUnrankedQueue = new PracticeList<>("archerUnrankedQueue");
    public static PracticeList<Player> elenoxUnrankedQueue = new PracticeList<>("elenoxUnrankedQueue");
    public static PracticeList<Player> hacheUnrankedQueue = new PracticeList<>("hacheUnrankedQueue");
    public static PracticeList<Player> soupeUnrankedQueue = new PracticeList<>("soupeUnrankedQueue");

    // Listes queue Ranked
    public static PracticeList<Player> nodebuffRankedQueue = new PracticeList<>("nodebuffRankedQueue");
    public static PracticeList<Player> debuffRankedQueue = new PracticeList<>("debuffRanked");
    public static PracticeList<Player> comboRankedQueue = new PracticeList<>("comboRankedQueue");
    public static PracticeList<Player> builduhcRankedQueue = new PracticeList<>("builduhcRankedQueue");
    public static PracticeList<Player> sumoRankedQueue = new PracticeList<>("sumoRankedQueue");
    public static PracticeList<Player> spleefRankedQueue = new PracticeList<>("spleefRankedQueue");
    public static PracticeList<Player> archerRankedQueue = new PracticeList<>("archerRankedQueue");
    public static PracticeList<Player> elenoxRankedQueue = new PracticeList<>("elenoxRankedQueue");
    public static PracticeList<Player> hacheRankedQueue = new PracticeList<>("hacheRankedQueue");
    public static PracticeList<Player> soupeRankedQueue = new PracticeList<>("soupeRankedQueue");

    //Listes EditKit
    public static ArrayList<Player> playerWhotEditKit = new ArrayList<>();
    public static ArrayList<PracticeList> allListEditKit = new ArrayList<>();
    public static PracticeList<Player> nodebuffEditKit = new PracticeList<>("nodebuffEditKit");
    public static PracticeList<Player> debuffEditKit = new PracticeList<>("debuffEditKit");
    public static PracticeList<Player> comboEditKit = new PracticeList<>("comboEditKit");
    public static PracticeList<Player> buildUhcEditKit = new PracticeList<>("buildUhcEditKit");
    public static PracticeList<Player> spleefEditKit = new PracticeList<>("spleefEditKit");
    public static PracticeList<Player> archerEditKit = new PracticeList<>("archerEditKit");
    public static PracticeList<Player> elenoxEditKit = new PracticeList<>("elenoxEditKit");
    public static PracticeList<Player> hacheEditKit = new PracticeList<>("hacheEditKit");
    public static PracticeList<Player> soupeEditKit = new PracticeList<>("soupeEditKit");


    public static void runClearList(){
        for(PracticeList list : allMatch) list.clear();
        for(PracticeList list : allQueue) list.clear();
        for(PracticeList list : allListEditKit) list.clear();
        allMatch.clear();
        allQueue.clear();
        allPlayerMatch.clear();
        allPlayerQueue.clear();
        moderatorInVanish.clear();
        playerFrozen.clear();
        playerWhotEditKit.clear();
    }

    public void addQueueMainList(){
        // Ajout des queues unranked
        allQueue.add(nodebuffUnrankedQueue);
        allQueue.add(debuffUnrankedQueue);
        allQueue.add(comboUnrankedQueue);
        allQueue.add(builduhcUnrankedQueue);
        allQueue.add(sumoUnrankedQueue);
        allQueue.add(spleefUnrankedQueue);
        allQueue.add(archerUnrankedQueue);
        allQueue.add(elenoxUnrankedQueue);
        allQueue.add(hacheUnrankedQueue);
        allQueue.add(soupeUnrankedQueue);

        // Ajout des queues ranked
        allQueue.add(nodebuffRankedQueue);
        allQueue.add(debuffRankedQueue);
        allQueue.add(comboRankedQueue);
        allQueue.add(builduhcRankedQueue);
        allQueue.add(sumoRankedQueue);
        allQueue.add(spleefRankedQueue);
        allQueue.add(archerRankedQueue);
        allQueue.add(elenoxRankedQueue);
        allQueue.add(hacheRankedQueue);
        allQueue.add(soupeRankedQueue);

        //Ajout listes edit kit
        allListEditKit.add(nodebuffEditKit);
        allListEditKit.add(debuffEditKit);
        allListEditKit.add(comboEditKit);
        allListEditKit.add(buildUhcEditKit);
        allListEditKit.add(spleefEditKit);
        allListEditKit.add(archerEditKit);
        allListEditKit.add(elenoxEditKit);
        allListEditKit.add(hacheEditKit);
        allListEditKit.add(soupeEditKit);
    }

}
