package com.elenox.pvpbox.practice.menu;

import com.elenox.api.ElenoxAPI;
import com.elenox.api.gui.AbstractGui;
import com.elenox.api.item.HeadBuilder;
import com.elenox.api.item.ItemBuilder;
import com.elenox.pvpbox.practice.save.Statistiques;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class StatistiquesView extends AbstractGui {
    private int[] bords = new int[]{0, 1, 2, 3, 5, 6, 7,8, 9, 17, 18, 26,27,28,29,30,31,32,33,34,35};
    private String nom;

    public StatistiquesView(String nomMatch){
        this.nom = nomMatch;
    }

    @Override
    public void display(Player player) {
        String nomMin = nom.toLowerCase();
        Statistiques stats = new Statistiques(player,nomMin);
        this.inventory = Bukkit.createInventory(player,36,"§8Statistiques "+nom);

        for(int s : this.bords){
            this.setSlotData( new ItemStack(Material.BLUE_STAINED_GLASS_PANE,1),s,"");
        }

        this.setSlotData(new HeadBuilder(player.getName(),"§7» §e"+player.getDisplayName(), Arrays.asList("§9Statistiques en §6"+nom)).toItemStack(),4,"");

        this.setSlotData(new ItemBuilder(Material.IRON_SWORD,1,"§6Statistiques §9§lUnranked")
                .setLore(Arrays.asList("§3Parties§7: §e"+(int)stats.getUnrankedParty(),
                "§aVictoires§7: §e"+(int)stats.getUnrankedWins(),"§cDéfaites§7: §e"+
                                (int)(stats.getUnrankedParty()-stats.getUnrankedWins()))).toItemStack(),11,"");

        this.setSlotData(new ItemBuilder(Material.NETHER_STAR,1,"§6§lRatio §aVictoire§7/§cDéfaite")
                .setLore(this.tryRatio(stats)).toItemStack(),13,"");

        this.setSlotData(new ItemBuilder(Material.DIAMOND_SWORD,1,"§6Statistiques §5§lRanked")
                .setLore(Arrays.asList("§3Parties: §e"+(int)stats.getRankedParty(),
                "§aVictoires: §e"+(int)stats.getRankedWins(),"§cDéfaites§7: §e"+
                                (int)(stats.getRankedParty()-stats.getRankedWins()))).toItemStack(),15,"");

        this.setSlotData(new ItemBuilder(Material.EXPERIENCE_BOTTLE,1,"§ePoints").setLore(Arrays.asList("§7Nombre de §e§lPoints§7: §e"+
                stats.getPoints())).toItemStack(),21,"");

        this.setSlotData(new ItemBuilder(Material.ENDER_PEARL,1,"§6Statistiques §3§lGlobales")
                .setLore(Arrays.asList("§3Parties§7: §e"+ (int)(stats.getRankedParty()+stats.getUnrankedParty()),
                        "§aVictoires§7: §e"+(int)(stats.getRankedWins()+stats.getUnrankedWins()),
                "§cDéfaites§7: §e"+ (int)((stats.getRankedParty()+stats.getUnrankedParty() - (stats.getRankedWins()+stats.getUnrankedWins())))))
                .toItemStack(),23,"");

        this.setSlotData(new ItemBuilder(Material.BARRIER,1,"§c» Retour").toItemStack(),27,"retourStatistiquesKit");

        player.openInventory(this.inventory);
    }


    private ArrayList<String> tryRatio(Statistiques stats){
        ArrayList<String> ratioList = new ArrayList<>();
        if((stats.getRankedWins()+stats.getUnrankedWins()) /
                ((stats.getRankedParty() - stats.getRankedWins()) + (stats.getUnrankedParty()- stats.getUnrankedWins())) == 0.0d) {
            ratioList.add("§3Global §7: §4Manque de donnée");
        }else{
            ratioList.add("§3Global §7: §e"+ (stats.getRankedWins()+stats.getUnrankedWins()) /
                    ((stats.getRankedParty() - stats.getRankedWins()) + (stats.getUnrankedParty()- stats.getUnrankedWins())));
        }

        if((stats.getUnrankedWins() / (stats.getUnrankedParty() - stats.getUnrankedWins())) == 0.0d){
            ratioList.add("§9§lUnranked §7: §4Manque de donnée");
        }else{
            ratioList.add("§9§lUnranked §7: §e"+ stats.getUnrankedWins() / (stats.getUnrankedParty() - stats.getUnrankedWins()));
        }

        if((stats.getRankedWins() / (stats.getRankedParty() - stats.getRankedWins())) == 0.0d) {
            ratioList.add("§5Ranked §7: §4Manque de donnée");
        }else{
            ratioList.add("§5Ranked §7: §e"+ stats.getRankedWins() / (stats.getRankedParty() - stats.getRankedWins()));
        }

        return ratioList;
    }

    @Override
    public void onClick(Player player, ItemStack stack, String action){
        if(action.isEmpty()) return;

        if(action.equalsIgnoreCase("retourStatistiquesKit")){
            ElenoxAPI.getElenoxAPI().getGuiManager().closeGui(player);
            ElenoxAPI.getElenoxAPI().getGuiManager().openGui(player,new StatistiquesKit());
        }
    }
}
