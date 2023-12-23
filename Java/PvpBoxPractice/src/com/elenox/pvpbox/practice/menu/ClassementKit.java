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

import java.util.Arrays;

public class ClassementKit extends AbstractGui {
    int[] borre = new int[]{ 1, 2, 3, 4, 5, 6, 7, 9, 17, 18, 26,28,29,30,31,32,33,34};
    int[] coins = new int[]{0,8,27,35};

    @Override
    public void display(Player player) {
        this.inventory = Bukkit.createInventory(player,36,"§8Classement");
        for(int s : borre){
            this.setSlotData( new ItemStack(Material.LIME_STAINED_GLASS_PANE,1),s,"");
        }
        for(int s : coins){
            this.setSlotData( new ItemStack(Material.GREEN_STAINED_GLASS_PANE,1),s,"");
        }

        ItemStack potNodebuff = ElenoxAPI.getElenoxAPI().createPotionItem(PotionBuilder.PotionType.INSTANT_HEAL).splash().strong().toItemStack(1);
        ItemMeta potNodebuffMeta = potNodebuff.getItemMeta();
        potNodebuffMeta.setDisplayName("§6Nodebuff");
        potNodebuffMeta.setLore(Arrays.asList(""));
        potNodebuff.setItemMeta(potNodebuffMeta);

        ItemStack potDebuff = ElenoxAPI.getElenoxAPI().createPotionItem(PotionBuilder.PotionType.POISON).splash().toItemStack(1);
        ItemMeta potDebuffItemMeta = potDebuff.getItemMeta();
        potDebuffItemMeta.setDisplayName("§6Debuff");
        potDebuffItemMeta.setLore(Arrays.asList(""));
        potDebuff.setItemMeta(potDebuffItemMeta);

        this.setSlotData(potNodebuff,10, "nodebuffClassement");
        this.setSlotData(potDebuff,11, "debuffClassement");
        this.setSlotData(new ItemBuilder(Material.PUFFERFISH, 1, "§6Combo").toItemStack(),12, "comboClassement");
        this.setSlotData(new ItemBuilder(Material.LAVA_BUCKET, 1, "§6BuildUHC").toItemStack(),13, "buildUHCClassement");
        this.setSlotData(new ItemBuilder(Material.LEAD, 1, "§6Sumo").toItemStack(),14, "sumoClassement");
        this.setSlotData(new ItemBuilder(Material.DIAMOND_SHOVEL, 1, "§6Spleef").toItemStack(),15, "spleefClassement");
        this.setSlotData(new ItemBuilder(Material.BOW, 1, "§6Archer").toItemStack(),16, "archerClassement");
        this.setSlotData(new ItemBuilder(Material.CREEPER_SPAWN_EGG, 1, "§6ElenoxKit").toItemStack(),21, "elenoxClassement");
        this.setSlotData(new ItemBuilder(Material.IRON_AXE, 1, "§6Hache").toItemStack(),22, "hacheClassement");
        this.setSlotData(new ItemBuilder(Material.MUSHROOM_STEW, 1, "§6Soupe").toItemStack(),23, "soupeClassement");
        player.openInventory(this.inventory);
    }

    @Override
    public void onClick(Player player, ItemStack stack, String action){
        if(action.isEmpty())return;

        if(action.equalsIgnoreCase("nodebuffClassement")){
            ElenoxAPI.getElenoxAPI().getGuiManager().closeGui(player);
            ElenoxAPI.getElenoxAPI().getGuiManager().openGui(player,new ClassementView("Nodebuff"));
        }else if(action.equalsIgnoreCase("debuffClassement")){
            ElenoxAPI.getElenoxAPI().getGuiManager().closeGui(player);
            ElenoxAPI.getElenoxAPI().getGuiManager().openGui(player,new ClassementView("Debuff"));
        }else if(action.equalsIgnoreCase("comboClassement")){
            ElenoxAPI.getElenoxAPI().getGuiManager().closeGui(player);
            ElenoxAPI.getElenoxAPI().getGuiManager().openGui(player,new ClassementView("Combo"));
        }else if(action.equalsIgnoreCase("buildUHCClassement")){
            ElenoxAPI.getElenoxAPI().getGuiManager().closeGui(player);
            ElenoxAPI.getElenoxAPI().getGuiManager().openGui(player,new ClassementView("BuildUHC"));
        }else if(action.equalsIgnoreCase("sumoClassement")){
            ElenoxAPI.getElenoxAPI().getGuiManager().closeGui(player);
            ElenoxAPI.getElenoxAPI().getGuiManager().openGui(player,new ClassementView("Sumo"));
        }else if(action.equalsIgnoreCase("spleefClassement")){
            ElenoxAPI.getElenoxAPI().getGuiManager().closeGui(player);
            ElenoxAPI.getElenoxAPI().getGuiManager().openGui(player,new ClassementView("Spleef"));
        }else if(action.equalsIgnoreCase("archerClassement")){
            ElenoxAPI.getElenoxAPI().getGuiManager().closeGui(player);
            ElenoxAPI.getElenoxAPI().getGuiManager().openGui(player,new ClassementView("Archer"));
        }else if(action.equalsIgnoreCase("elenoxClassement")){
            ElenoxAPI.getElenoxAPI().getGuiManager().closeGui(player);
            ElenoxAPI.getElenoxAPI().getGuiManager().openGui(player,new ClassementView("ElenoxKit"));
        }else if(action.equalsIgnoreCase("hacheClassement")){
            ElenoxAPI.getElenoxAPI().getGuiManager().closeGui(player);
            ElenoxAPI.getElenoxAPI().getGuiManager().openGui(player,new ClassementView("Hache"));
        }else if(action.equalsIgnoreCase("soupeClassement")){
            ElenoxAPI.getElenoxAPI().getGuiManager().closeGui(player);
            ElenoxAPI.getElenoxAPI().getGuiManager().openGui(player,new ClassementView("Soupe"));
        }
    }
}
