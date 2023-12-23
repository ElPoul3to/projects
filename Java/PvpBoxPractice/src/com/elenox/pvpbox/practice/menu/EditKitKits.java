package com.elenox.pvpbox.practice.menu;

import com.elenox.api.ElenoxAPI;
import com.elenox.api.gui.AbstractGui;
import com.elenox.api.item.ItemBuilder;
import com.elenox.api.item.PotionBuilder;
import com.elenox.pvpbox.practice.list.ListManager;
import com.elenox.pvpbox.practice.manager.SettingsManager;
import com.elenox.pvpbox.practice.save.Kits;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EditKitKits extends AbstractGui {
    int[] borre = new int[]{ 1, 2, 3, 4, 5, 6, 7, 9, 17, 18, 26,28,29,30,31,32,33,34};
    int[] coins = new int[]{0,8,27,35};

    @Override
    public void display(Player player) {
        this.inventory = Bukkit.createInventory(player,36,"§8Editeur des Kits");

        for(int s : borre){
            this.setSlotData( new ItemStack(Material.GRAY_STAINED_GLASS_PANE,1),s,"");
        }
        for(int s : coins){
            this.setSlotData( new ItemStack(Material.BLACK_STAINED_GLASS_PANE,1),s,"");
        }

        ItemStack potNodebuff = ElenoxAPI.getElenoxAPI().createPotionItem(PotionBuilder.PotionType.INSTANT_HEAL).splash().strong().toItemStack(1);
        ItemMeta potNodebuffMeta = potNodebuff.getItemMeta();
        potNodebuffMeta.setDisplayName("§6Nodebuff");
        potNodebuff.setItemMeta(potNodebuffMeta);

        ItemStack potDebuff = ElenoxAPI.getElenoxAPI().createPotionItem(PotionBuilder.PotionType.POISON).splash().toItemStack(1);
        ItemMeta potDebuffItemMeta = potDebuff.getItemMeta();
        potDebuffItemMeta.setDisplayName("§6Debuff");
        potDebuff.setItemMeta(potDebuffItemMeta);

        this.setSlotData(potNodebuff,10, "nodebuffEdit");
        this.setSlotData(potDebuff,11, "debuffEdit");
        this.setSlotData(new ItemBuilder(Material.PUFFERFISH, 1, "§6Combo").toItemStack(),12, "comboEdit");
        this.setSlotData(new ItemBuilder(Material.LAVA_BUCKET, 1, "§6BuildUHC").toItemStack(),13, "buildUHCEdit");
        this.setSlotData(new ItemBuilder(Material.DIAMOND_SHOVEL, 1, "§6Spleef").toItemStack(),14, "spleefEdit");
        this.setSlotData(new ItemBuilder(Material.BOW, 1, "§6Archer").toItemStack(),15, "archerEdit");
        this.setSlotData(new ItemBuilder(Material.CREEPER_SPAWN_EGG, 1, "§6ElenoxKit").toItemStack(),21, "elenoxEdit");
        this.setSlotData(new ItemBuilder(Material.IRON_AXE, 1, "§6Hache").toItemStack(),22, "hacheEdit");
        this.setSlotData(new ItemBuilder(Material.MUSHROOM_STEW, 1, "§6Soupe").toItemStack(),23, "soupeEdit");
        player.openInventory(this.inventory);
    }

    @Override
    public void onClick(Player player, ItemStack stack, String action){
        String nomPath = "";
        if(action.isEmpty())return;

        if(action.equalsIgnoreCase("nodebuffEdit")){
            nomPath = "nodebuff";
            ListManager.nodebuffEditKit.add(player);

        }else if(action.equalsIgnoreCase("debuffEdit")){
            nomPath = "debuff";
            ListManager.debuffEditKit.add(player);

        }else if(action.equalsIgnoreCase("comboEdit")){
            nomPath = "combo";
            ListManager.comboEditKit.add(player);

        }else if(action.equalsIgnoreCase("buildUHCEdit")) {
            nomPath = "builduhc";
            ListManager.buildUhcEditKit.add(player);

        }else if(action.equalsIgnoreCase("spleefEdit")){
            nomPath = "spleef";
            ListManager.spleefEditKit.add(player);

        }else if(action.equalsIgnoreCase("archerEdit")){
            nomPath = "archer";
            ListManager.archerEditKit.add(player);

        }else if(action.equalsIgnoreCase("elenoxEdit")){
            nomPath = "elenox";
            ListManager.elenoxEditKit.add(player);

        }else if(action.equalsIgnoreCase("hacheEdit")){
            nomPath = "hache";
            ListManager.hacheEditKit.add(player);

        }else if(action.equalsIgnoreCase("soupeEdit")){
            nomPath = "soupe";
            ListManager.soupeEditKit.add(player);
        }

        SettingsManager.setEditKitSettings(player);
        new Kits(player).getKit(nomPath);
        ListManager.playerWhotEditKit.add(player);
        player.closeInventory();
        player.updateInventory();
    }
}
