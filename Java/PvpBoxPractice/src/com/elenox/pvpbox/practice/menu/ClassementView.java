package com.elenox.pvpbox.practice.menu;

import com.elenox.api.ElenoxAPI;
import com.elenox.api.gui.AbstractGui;
import com.elenox.api.item.HeadBuilder;
import com.elenox.api.item.ItemBuilder;
import com.elenox.pvpbox.practice.list.PracticeList;
import com.elenox.pvpbox.practice.save.Statistiques;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class ClassementView extends AbstractGui {
    private String nom;
    private int[] bords = new int[]{0,1,2,3,5,6,7,8,9,17,18,26,27,35,36,37,38,39,40,41,42,43,44};

    public ClassementView(String nom){
        this.nom = nom;
    }

    @Override
    public void display(Player player) {
        this.inventory = Bukkit.createInventory(player,45,"§8Classement "+nom);

        for(int s : this.bords){
            this.setSlotData(new ItemStack(Material.YELLOW_STAINED_GLASS_PANE,1),s,"");
        }

        this.setSlotData(new ItemBuilder(Material.EMERALD,1,"§aClassement §6§l"+nom).toItemStack(),4,"");

         ArrayList<PracticeList> classement = new Statistiques(player,nom.toLowerCase()).getClassement();
         PracticeList<OfflinePlayer> players = classement.get(0);
         PracticeList<Integer> points = classement.get(1);

         OfflinePlayer player1 = (OfflinePlayer) players.get(0);
         this.setSlotData(new HeadBuilder( player1.getName(),"§e§l1er §3"+player1.getName(),
         Arrays.asList("§7Nombre de points: §e"+points.get(0))).toItemStack(),13,"");

         if(players.size() >= 2){
             OfflinePlayer player2 = (OfflinePlayer) players.get(1);
             this.setSlotData(new HeadBuilder( player2.getName(),"§e§l2ème §3"+player2.getName(),
             Arrays.asList("§7Nombre de points: §e"+points.get(1))).toItemStack(),21,"");
         }

         if(players.size() >= 3){
             OfflinePlayer player3 = (OfflinePlayer) players.get(2);
             this.setSlotData(new HeadBuilder( player3.getName(),"§e§l3ème §3"+player3.getName(),
             Arrays.asList("§7Nombre de points: §e"+points.get(2))).toItemStack(),23,"");;
         }
         if(players.size() >= 4){
             OfflinePlayer player4 = (OfflinePlayer) players.get(3);
             this.setSlotData(new HeadBuilder( player4.getName(),"§e§l4ème §3"+player4.getName(),
             Arrays.asList("§7Nombre de points: §e"+points.get(3))).toItemStack(),29,"");
         }

         if(players.size() >= 5){
             OfflinePlayer player5 = (OfflinePlayer) players.get(4);
             this.setSlotData(new HeadBuilder( player5.getName(),"§e§l5ème §3"+player5.getName(),
             Arrays.asList("§7Nombre de points: §e"+points.get(4))).toItemStack(),30,"");
         }
         if(players.size() >= 6){
             OfflinePlayer player6 = (OfflinePlayer) players.get(5);
             this.setSlotData(new HeadBuilder( player6.getName(),"§e§l6ème §3"+player6.getName(),
             Arrays.asList("§7Nombre de points: §e"+points.get(5))).toItemStack(),31,"");
         }
         if(players.size() >= 7){
             OfflinePlayer player7 = (OfflinePlayer) players.get(6);
             this.setSlotData(new HeadBuilder( player7.getName(),"§e§l7ème §3"+player7.getName(),
             Arrays.asList("§7Nombre de points: §e"+points.get(6))).toItemStack(),32,"");
         }

         if(players.size() >= 8){
             OfflinePlayer player8 = (OfflinePlayer) players.get(7);
             this.setSlotData(new HeadBuilder( player8.getName(),"§e§l8ème §3"+player8.getName(),
             Arrays.asList("§7Nombre de points: §e"+points.get(7))).toItemStack(),33,"");
         }


        this.setSlotData(new ItemBuilder(Material.BARRIER,1,"§c» Retour").toItemStack(),36,"retourClassementKit");

        player.openInventory(this.inventory);
    }

    @Override
    public void onClick(Player player, ItemStack stack, String action){
        if(action.isEmpty()) return;

        if(action.equalsIgnoreCase("retourClassementKit")){
            ElenoxAPI.getElenoxAPI().getGuiManager().closeGui(player);
            ElenoxAPI.getElenoxAPI().getGuiManager().openGui(player,new ClassementKit());
        }
    }

}