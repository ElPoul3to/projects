package com.elenox.pvpbox.practice.save;

import com.elenox.pvpbox.practice.PvpBoxPractice;
import com.elenox.pvpbox.practice.list.PracticeList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Statistiques {
    private Player player;
    private String nom;
    private HashMap<OfflinePlayer,Integer> classementNonTrié;
    private HashMap<OfflinePlayer,Integer> classementTrié;
    private PracticeList<OfflinePlayer> playersClassement;
    private PracticeList<Integer> pointsClassement;
    private ArrayList<PracticeList> classement;
    private File file = new File(PvpBoxPractice.INSTANCE.getDataFolder(),"stats.yml");
    private YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
    private ConfigurationSection configSection;

    public Statistiques(Player player,String nom){
        this.player = player;
        this.nom = nom;
        configSection = config.getConfigurationSection("players."+player.getUniqueId().toString());
    }

    public Statistiques(Player playerJoin){
        this.player = playerJoin;
        configSection = config.getConfigurationSection("players."+player.getUniqueId().toString());

        if(configSection == null){
            this.setAllStats("nodebuff");
            this.setAllStats("debuff");
            this.setAllStats("combo");
            this.setAllStats("builduhc");
            this.setAllStats("sumo");
            this.setAllStats("spleef");
            this.setAllStats("archer");
            this.setAllStats("elenoxkit");
            this.setAllStats("hache");
            this.setAllStats("soupe");
            this.save();
        }
    }
    // Tous en double sauf point en int

    //Points seulement en Ranked
    public void addPoints(int somme){
        config.set("players."+player.getUniqueId().toString()+"."+this.nom+".points", configSection.getDouble(this.nom+".points")+somme);
        this.save();
    }
    public void addUnrankedParty(){
        config.set("players."+player.getUniqueId().toString()+"."+this.nom+".unranked.party", configSection.getDouble(this.nom+".unranked.party")+1d);
        this.save();
    }
    public void addUnrankedWins(){
        config.set("players."+player.getUniqueId().toString()+"."+this.nom+".unranked.wins", configSection.getDouble(this.nom+".unranked.wins")+1d);
        this.save();
    }
    public void addRankedParty(){
        config.set("players."+player.getUniqueId().toString()+"."+this.nom+".ranked.party",configSection.getDouble(this.nom+".ranked.party")+1d);
        this.save();
    }
    public void addRankedWins(){
        config.set("players."+player.getUniqueId().toString()+"."+this.nom+".ranked.wins",configSection.getDouble(this.nom+".ranked.wins")+1d);
        this.save();
    }


    public int getPoints(){
        return config.getInt("players."+player.getUniqueId().toString()+"."+this.nom+".points");
    }
    public double getRankedParty(){
        return config.getDouble("players."+player.getUniqueId().toString()+"."+this.nom+".ranked.party");
    }
    public double getRankedWins(){
        return config.getDouble("players."+player.getUniqueId().toString()+"."+this.nom+".ranked.wins");
    }
    public double getUnrankedParty(){
        return config.getDouble("players."+player.getUniqueId().toString()+"."+this.nom+".unranked.party");
    }
    public double getUnrankedWins(){
        return config.getDouble("players."+player.getUniqueId().toString()+"."+this.nom+".unranked.wins");
    }

    private void save(){
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setAllStats(String nom){
        config.set("players."+player.getUniqueId().toString()+"."+nom+".points",0);
        config.set("players."+player.getUniqueId().toString()+"."+nom+".ranked.party",0d);
        config.set("players."+player.getUniqueId().toString()+"."+nom+".ranked.wins", 0d);
        config.set("players."+player.getUniqueId().toString()+"."+nom+".unranked.party",0d);
        config.set("players."+player.getUniqueId().toString()+"."+nom+".unranked.wins", 0d);
    }

    public ArrayList<PracticeList> getClassement(){
        classementNonTrié = new HashMap<>();
        playersClassement = new PracticeList<>("playersClassement");
        pointsClassement = new PracticeList<>("pointsClassement");
        classement = new ArrayList<>();

        for(OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()){
            classementNonTrié.put(offlinePlayer,config.getInt("players."+offlinePlayer.getUniqueId().toString()+"."+this.nom+".points"));
        }

        classementTrié = triAvecValeur(classementNonTrié);
        // Obtenir l'ensemble des entrées
        Set set = classementTrié.entrySet();

        // Obtenir l'iterator pour parcourir la liste
        Iterator itr = set.iterator();

        // Afficher les pairs clé-valeur
        while(itr.hasNext()) {
            Map.Entry mentry = (Map.Entry)itr.next();
            playersClassement.add(mentry.getKey());
            pointsClassement.add(mentry.getValue());

        }
        classement.add(playersClassement);
        classement.add(pointsClassement);
        return classement;
    }

    public static HashMap<OfflinePlayer, Integer> triAvecValeur( HashMap<OfflinePlayer, Integer> map ){
        List<Map.Entry<OfflinePlayer, Integer>> list =
                new LinkedList<Map.Entry<OfflinePlayer, Integer>>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<OfflinePlayer, Integer>>(){
            public int compare( Map.Entry<OfflinePlayer, Integer> o1, Map.Entry<OfflinePlayer, Integer> o2 ){
                return (o2.getValue()).compareTo( o1.getValue());
            }
        });

        HashMap<OfflinePlayer, Integer> map_apres = new LinkedHashMap<OfflinePlayer, Integer>();
        for(Map.Entry<OfflinePlayer, Integer> entry : list)
            map_apres.put( entry.getKey(), entry.getValue() );
        return map_apres;
    }
}
