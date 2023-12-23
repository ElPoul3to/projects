package com.elenox.pvpbox.practice.menu;

import com.elenox.api.ElenoxAPI;
import com.elenox.api.gui.AbstractGui;
import com.elenox.api.item.ItemBuilder;
import com.elenox.api.item.PotionBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StatistiquesKit extends AbstractGui {
    private int[] borre = new int[]{0, 1, 2, 3,4, 5, 6, 7,8, 9, 17, 18, 26,27,28,29,30,31,32,33,34,35};

    @Override
    public void display(Player player) {
        this.inventory = Bukkit.createInventory(player,36,"§8Choisissez votre Kit");

        for(int s : borre){
            this.setSlotData( new ItemStack(Material.CYAN_STAINED_GLASS_PANE,1),s,"");
        }

        ItemStack potNodebuff = ElenoxAPI.getElenoxAPI().createPotionItem(PotionBuilder.PotionType.INSTANT_HEAL).splash().strong().toItemStack(1);
        ItemMeta potNodebuffMeta = potNodebuff.getItemMeta();
        potNodebuffMeta.setDisplayName("§6Nodebuff Statistiques");
        potNodebuff.setItemMeta(potNodebuffMeta);

        ItemStack potDebuff = ElenoxAPI.getElenoxAPI().createPotionItem(PotionBuilder.PotionType.POISON).splash().toItemStack(1);
        ItemMeta potDebuffItemMeta = potDebuff.getItemMeta();
        potDebuffItemMeta.setDisplayName("§6Debuff Statistiques");
        potDebuff.setItemMeta(potDebuffItemMeta);

        this.setSlotData(potNodebuff,10, "nodebuffStats");
        this.setSlotData(potDebuff,11, "debuffStats");
        this.setSlotData(new ItemBuilder(Material.PUFFERFISH, 1, "§6Combo Statistiques").toItemStack(),12, "comboStats");
        this.setSlotData(new ItemBuilder(Material.LAVA_BUCKET, 1, "§6BuildUHC Statistiques").toItemStack(),13, "buildUHCStats");
        this.setSlotData(new ItemBuilder(Material.LEAD, 1, "§6Sumo Statistiques").toItemStack(),14, "sumoStats");
        this.setSlotData(new ItemBuilder(Material.DIAMOND_SHOVEL, 1, "§6Spleef Statistiques").toItemStack(),15, "spleefStats");
        this.setSlotData(new ItemBuilder(Material.BOW, 1, "§6Archer Statistiques").toItemStack(),16, "archerStats");
        this.setSlotData(new ItemBuilder(Material.CREEPER_SPAWN_EGG, 1, "§6ElenoxKit Statistiques").toItemStack(),21, "elenoxStats");
        this.setSlotData(new ItemBuilder(Material.IRON_AXE, 1, "§6Hache Statistiques").toItemStack(),22, "hacheStats");
        this.setSlotData(new ItemBuilder(Material.MUSHROOM_STEW, 1, "§6Soupe Statistiques").toItemStack(),23, "soupeStats");
        player.openInventory(this.inventory);
    }

    @Override
    public void onClick(Player player, ItemStack stack, String action){
        if(action.isEmpty())return;

        if(action.equalsIgnoreCase("nodebuffStats")){
            ElenoxAPI.getElenoxAPI().getGuiManager().closeGui(player);
            ElenoxAPI.getElenoxAPI().getGuiManager().openGui(player,new StatistiquesView("Nodebuff"));

        }else if(action.equalsIgnoreCase("debuffStats")){
            ElenoxAPI.getElenoxAPI().getGuiManager().openGui(player,new StatistiquesView("Debuff"));

        }else if(action.equalsIgnoreCase("comboStats")){
            ElenoxAPI.getElenoxAPI().getGuiManager().openGui(player,new StatistiquesView("Combo"));

        }else if(action.equalsIgnoreCase("buildUHCStats")) {
            ElenoxAPI.getElenoxAPI().getGuiManager().openGui(player,new StatistiquesView("BuildUHC"));

        }else if(action.equalsIgnoreCase("sumoStats")){
            ElenoxAPI.getElenoxAPI().getGuiManager().openGui(player,new StatistiquesView("Sumo"));

        }else if(action.equalsIgnoreCase("spleefStats")){
            ElenoxAPI.getElenoxAPI().getGuiManager().openGui(player,new StatistiquesView("Spleef"));

        }else if(action.equalsIgnoreCase("archerStats")){
            ElenoxAPI.getElenoxAPI().getGuiManager().openGui(player,new StatistiquesView("Archer"));

        }else if(action.equalsIgnoreCase("elenoxStats")){
            ElenoxAPI.getElenoxAPI().getGuiManager().openGui(player,new StatistiquesView("ElenoxKit"));

        }else if(action.equalsIgnoreCase("hacheStats")){
            ElenoxAPI.getElenoxAPI().getGuiManager().openGui(player,new StatistiquesView("Hache"));

        }else if(action.equalsIgnoreCase("soupeStats")){
            ElenoxAPI.getElenoxAPI().getGuiManager().openGui(player,new StatistiquesView("Soupe"));
        }
    }
}
