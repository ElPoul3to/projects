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


public class RankedKits extends AbstractGui {
    int[] borre = new int[]{ 1, 2, 3, 4, 5, 6, 7, 9, 17, 18, 26,28,29,30,31,32,33,34};
    int[] coins = new int[]{0,8,27,35};

    @Override
    public void display(Player player) {
        this.inventory = Bukkit.createInventory(player,36,"§8Ranked");
        for(int s : borre){
            this.setSlotData( new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE,1),s,"");
        }
        for(int s : coins){
            this.setSlotData( new ItemStack(Material.PURPLE_STAINED_GLASS_PANE,1),s,"");
        }

        ItemStack potNodebuff = ElenoxAPI.getElenoxAPI().createPotionItem(PotionBuilder.PotionType.INSTANT_HEAL).splash().strong().toItemStack(1);
        ItemMeta potNodebuffMeta = potNodebuff.getItemMeta();
        potNodebuffMeta.setDisplayName("§6Nodebuff");
        potNodebuffMeta.removeItemFlags();
        potNodebuff.setItemMeta(potNodebuffMeta);

        ItemStack potDebuff = ElenoxAPI.getElenoxAPI().createPotionItem(PotionBuilder.PotionType.POISON).splash().toItemStack(1);
        ItemMeta potDebuffItemMeta = potDebuff.getItemMeta();
        potDebuffItemMeta.setDisplayName("§6Debuff");
        potDebuffItemMeta.removeItemFlags();
        potDebuff.setItemMeta(potDebuffItemMeta);

        this.setSlotData(potNodebuff,10, "nodebuffRanked");
        this.setSlotData(potDebuff,11, "debuffRanked");
        this.setSlotData(new ItemBuilder(Material.PUFFERFISH, 1, "§6Combo").toItemStack(),12, "comboRanked");
        this.setSlotData(new ItemBuilder(Material.LAVA_BUCKET, 1, "§6BuildUHC").toItemStack(),13, "buildUHCRanked");
        this.setSlotData(new ItemBuilder(Material.LEAD, 1, "§6Sumo").toItemStack(),14, "sumoRanked");
        this.setSlotData(new ItemBuilder(Material.DIAMOND_SHOVEL, 1, "§6Spleef").toItemStack(),15, "spleefRanked");
        this.setSlotData(new ItemBuilder(Material.BOW, 1, "§6Archer").toItemStack(),16, "archerRanked");
        this.setSlotData(new ItemBuilder(Material.CREEPER_SPAWN_EGG, 1, "§6ElenoxKit").toItemStack(),21, "elenoxRanked");
        this.setSlotData(new ItemBuilder(Material.IRON_AXE, 1, "§6Hache").toItemStack(),22, "hacheRanked");
        this.setSlotData(new ItemBuilder(Material.MUSHROOM_STEW, 1, "§6Soupe").toItemStack(),23, "soupeRanked");
        player.openInventory(this.inventory);
    }

    @Override
    public void onClick(Player player, ItemStack stack, String action){
        if(action.isEmpty())return;

        if(action.equalsIgnoreCase("nodebuffRanked")){
            MatchManager.systemQueue(player, ListManager.nodebuffRankedQueue,ListManager.nodebuffRanked,"§6§lNodebuff §5§lRanked","nodebuffRanked");
        }else if(action.equalsIgnoreCase("debuffRanked")){
            MatchManager.systemQueue(player, ListManager.debuffRankedQueue,ListManager.debuffRanked,"§6§lDebuff §5§lRanked","debuffRanked");
        }else if(action.equalsIgnoreCase("comboRanked")){
            MatchManager.systemQueue(player, ListManager.comboRankedQueue,ListManager.comboRanked,"§6§lCombo §5§lRanked","comboRanked");
        }else if(action.equalsIgnoreCase("buildUHCRanked")){
            MatchManager.systemQueue(player, ListManager.builduhcRankedQueue,ListManager.builduhcRanked,"§6§lBuildUHC §5§lRanked","builduhcRanked");
        }else if(action.equalsIgnoreCase("sumoRanked")){
            MatchManager.systemQueue(player, ListManager.sumoRankedQueue,ListManager.sumoRanked,"§6§lSumo §5§lRanked","sumoRanked");
        }else if(action.equalsIgnoreCase("spleefRanked")){
            MatchManager.systemQueue(player, ListManager.spleefRankedQueue,ListManager.spleefRanked,"§6§lSpleef §5§lRanked","spleefRanked");
        }else if(action.equalsIgnoreCase("archerRanked")){
            MatchManager.systemQueue(player, ListManager.archerRankedQueue,ListManager.archerRanked,"§6§lArcher §5§lRanked","archerRanked");
        }else if(action.equalsIgnoreCase("elenoxRanked")){
            MatchManager.systemQueue(player, ListManager.elenoxRankedQueue,ListManager.elenoxRanked,"§6§lElenoxKit §5§lRanked","elenoxRanked");
        }else if(action.equalsIgnoreCase("hacheRanked")){
            MatchManager.systemQueue(player, ListManager.hacheRankedQueue,ListManager.hacheRanked,"§6§lHache §5§lRanked","hacheRanked");
        }else if(action.equalsIgnoreCase("soupeRanked")){
            MatchManager.systemQueue(player, ListManager.soupeRankedQueue,ListManager.soupeRanked,"§6§lSoupe §5§lRanked","soupeRanked");
        }
        player.closeInventory();
        player.updateInventory();
    }
}
