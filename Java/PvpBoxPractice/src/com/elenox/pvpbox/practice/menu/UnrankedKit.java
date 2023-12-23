package com.elenox.pvpbox.practice.menu;

import com.elenox.api.ElenoxAPI;
import com.elenox.api.gui.AbstractGui;
import com.elenox.api.item.ItemBuilder;
import com.elenox.api.item.PotionBuilder;
import com.elenox.pvpbox.practice.list.ListManager;
import com.elenox.pvpbox.practice.manager.MatchManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class UnrankedKit extends AbstractGui {
    int[] borre = new int[]{ 1, 2, 3, 4, 5, 6, 7, 9, 17, 18, 26,28,29,30,31,32,33,34};
    int[] coins = new int[]{0,8,27,35};

    @Override
    public void display(Player player) {
        this.inventory = Bukkit.createInventory(player,36,"§8Unranked");
        for(int s : borre){
            this.setSlotData( new ItemStack(Material.WHITE_STAINED_GLASS_PANE,1),s,"");
        }
        for(int s : coins){
            this.setSlotData( new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE,1),s,"");
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

        this.setSlotData(potNodebuff,10, "nodebuffUnranked");
        this.setSlotData(potDebuff,11, "debuffUnranked");
        this.setSlotData(new ItemBuilder(Material.PUFFERFISH, 1, "§6Combo").toItemStack(),12, "comboUnranked");
        this.setSlotData(new ItemBuilder(Material.LAVA_BUCKET, 1, "§6BuildUHC").toItemStack(),13, "buildUHCUnranked");
        this.setSlotData(new ItemBuilder(Material.LEAD, 1, "§6Sumo").toItemStack(),14, "sumoUnranked");
        this.setSlotData(new ItemBuilder(Material.DIAMOND_SHOVEL, 1, "§6Spleef").toItemStack(),15, "spleefUnranked");
        this.setSlotData(new ItemBuilder(Material.BOW, 1, "§6Archer").toItemStack(),16, "archerUnranked");
        this.setSlotData(new ItemBuilder(Material.CREEPER_SPAWN_EGG, 1, "§6ElenoxKit").toItemStack(),21, "elenoxUnranked");
        this.setSlotData(new ItemBuilder(Material.IRON_AXE, 1, "§6Hache").toItemStack(),22, "hacheUnranked");
        this.setSlotData(new ItemBuilder(Material.MUSHROOM_STEW, 1, "§6Soupe").toItemStack(),23, "soupeUnranked");
        player.openInventory(this.inventory);
    }

    @Override
    public void onClick(Player player, ItemStack stack, String action){
        if(action.isEmpty())return;

        if(action.equalsIgnoreCase("nodebuffUnranked")){
            MatchManager.systemQueue(player, ListManager.nodebuffUnrankedQueue,ListManager.nodebuffUnranked,"§6§lNodebuff §9§lUnranked","nodebuffUnranked");
        }else if(action.equalsIgnoreCase("debuffUnranked")){
            MatchManager.systemQueue(player, ListManager.debuffUnrankedQueue,ListManager.debuffUnranked,"§6§lDebuff §9§lUnranked","debuffUnranked");
        }else if(action.equalsIgnoreCase("comboUnranked")){
            MatchManager.systemQueue(player, ListManager.comboUnrankedQueue,ListManager.comboUnranked,"§6§lCombo §9§lUnranked","comboUnranked");
        }else if(action.equalsIgnoreCase("buildUHCUnranked")){
            MatchManager.systemQueue(player, ListManager.builduhcUnrankedQueue,ListManager.builduhcUnranked,"§6§lBuildUHC §9§lUnranked","builduhcUnranked");
        }else if(action.equalsIgnoreCase("sumoUnranked")){
            MatchManager.systemQueue(player, ListManager.sumoUnrankedQueue,ListManager.sumoUnranked,"§6§lSumo §9§lUnranked","sumoUnranked");
        }else if(action.equalsIgnoreCase("spleefUnranked")){
            MatchManager.systemQueue(player, ListManager.spleefUnrankedQueue,ListManager.spleefUnranked,"§6§lSpleef §9§lUnranked","spleefUnranked");
        }else if(action.equalsIgnoreCase("archerUnranked")){
            MatchManager.systemQueue(player, ListManager.archerUnrankedQueue,ListManager.archerUnranked,"§6§lArcher §9§lUnranked","archerUnranked");
        }else if(action.equalsIgnoreCase("elenoxUnranked")){
            MatchManager.systemQueue(player, ListManager.elenoxUnrankedQueue,ListManager.elenoxUnranked,"§6§lElenoxKit §9§lUnranked","elenoxUnranked");
        }else if(action.equalsIgnoreCase("hacheUnranked")){
            MatchManager.systemQueue(player, ListManager.hacheUnrankedQueue,ListManager.hacheUnranked,"§6§lHache §9§lUnranked","hacheUnranked");
        }else if(action.equalsIgnoreCase("soupeUnranked")){
            MatchManager.systemQueue(player, ListManager.soupeUnrankedQueue,ListManager.soupeUnranked,"§6§lSoupe §9§lUnranked","soupeUnranked");
        }
        player.closeInventory();
        player.updateInventory();
    }
}
