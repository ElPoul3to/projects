package com.elenox.pvpbox.practice.manager;

import com.elenox.api.ElenoxAPI;
import com.elenox.api.item.HeadBuilder;
import com.elenox.api.item.ItemBuilder;
import com.elenox.api.item.PotionBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;

public class ItemManager {
    public static void setItemSpawn(Player player){
        player.getInventory().clear();
        player.getInventory().setItem(0,new ItemBuilder(Material.IRON_SWORD, 1, "§9Unranked").toItemStack());
        player.getInventory().setItem(1,new ItemBuilder(Material.DIAMOND_SWORD, 1, "§5Ranked").toItemStack());
        player.getInventory().setItem(4,new HeadBuilder(player.getName(),"§eVos Statistiques", Arrays.asList()).toItemStack());
        player.getInventory().setItem(7,new ItemBuilder(Material.EMERALD, 1, "§aClassement").toItemStack());
        player.getInventory().setItem(8,new ItemBuilder(Material.ANVIL, 1, "§8Editeur des Kits").toItemStack());
        player.updateInventory();
    }

    public static void setItemQueue(Player player,String nom){
        player.getInventory().clear();
        player.getInventory().setItem(4,new ItemBuilder(Material.REDSTONE_TORCH,1,"§cQuitter la queue §6§l"+nom).toItemStack());
        player.updateInventory();
    }

    public static void setItemMatchTest(Player player){
        player.getInventory().clear();
        player.getInventory().setItem(0,new ItemBuilder(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL,10).toItemStack());
        player.updateInventory();
    }

    public static ArrayList<ItemStack> getNodebuffItems(){
        ArrayList<ItemStack> itemNodebuff = new ArrayList<>();

        itemNodebuff.add(new ItemBuilder(Material.DIAMOND_SWORD,1,"")
                .addEnchant(Enchantment.DAMAGE_ALL, 3).addEnchant(Enchantment.DURABILITY,3).addEnchant(Enchantment.FIRE_ASPECT,2).toItemStack());
        itemNodebuff.add(new ItemBuilder(Material.ENDER_PEARL,16,"").toItemStack());

        itemNodebuff.add(ElenoxAPI.getElenoxAPI().createPotionItem(PotionBuilder.PotionType.FIRE_RESISTANCE).extend().toItemStack(1));
        itemNodebuff.add(new ItemBuilder(Material.GOLDEN_CARROT,64,"").toItemStack());

        for(int i = 0; i<=26; i++){
            itemNodebuff.add(ElenoxAPI.getElenoxAPI().createPotionItem(PotionBuilder.PotionType.INSTANT_HEAL).splash().strong().toItemStack(1));
        }
        for(int i = 0; i<=4; i++){
            itemNodebuff.add(ElenoxAPI.getElenoxAPI().createPotionItem(PotionBuilder.PotionType.SPEED).strong().toItemStack(1));
        }
        return itemNodebuff;
    }

    public static void setNodebuff_Debuff_BuildUHCArmor(Player player){
        player.getInventory().setHelmet(new ItemBuilder(Material.DIAMOND_HELMET,1,"").addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,2)
                .addEnchant(Enchantment.DURABILITY,3).toItemStack());
        player.getInventory().setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE,1,"").addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,2)
                .addEnchant(Enchantment.DURABILITY,3).toItemStack());
        player.getInventory().setLeggings(new ItemBuilder(Material.DIAMOND_LEGGINGS,1,"")
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,2).addEnchant(Enchantment.DURABILITY,3).toItemStack());
        player.getInventory().setBoots(new ItemBuilder(Material.DIAMOND_BOOTS,1,"")
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,2).addEnchant(Enchantment.DURABILITY,3).addEnchant(Enchantment.PROTECTION_FALL,4).toItemStack());
    }

    public static ArrayList<ItemStack> getDebuffItems(){
        ArrayList<ItemStack> itemDebuff = new ArrayList<>();

        itemDebuff.add(new ItemBuilder(Material.DIAMOND_SWORD,1,"")
                .addEnchant(Enchantment.DAMAGE_ALL, 3).addEnchant(Enchantment.DURABILITY,3).addEnchant(Enchantment.FIRE_ASPECT,2).toItemStack());
        itemDebuff.add(new ItemBuilder(Material.ENDER_PEARL,16,"").toItemStack());
        itemDebuff.add(ElenoxAPI.getElenoxAPI().createPotionItem(PotionBuilder.PotionType.FIRE_RESISTANCE).extend().toItemStack(1));
        itemDebuff.add(new ItemBuilder(Material.GOLDEN_CARROT,64,"").toItemStack());

        for(int i = 0; i<=5; i++){
            itemDebuff.add(ElenoxAPI.getElenoxAPI().createPotionItem(PotionBuilder.PotionType.SPEED).strong().toItemStack(1));
        }
        for(int i = 0; i<3; i++){
            itemDebuff.add(ElenoxAPI.getElenoxAPI().createPotionItem(PotionBuilder.PotionType.POISON).splash().strong().toItemStack(1));
        }
        for(int i = 0; i<3; i++){
            itemDebuff.add(ElenoxAPI.getElenoxAPI().createPotionItem(PotionBuilder.PotionType.SLOWNESS).splash().toItemStack(1));
        }
        for(int i = 0; i<=21; i++){
            itemDebuff.add(ElenoxAPI.getElenoxAPI().createPotionItem(PotionBuilder.PotionType.INSTANT_HEAL).splash().strong().toItemStack(1));
        }
        return itemDebuff;
    }

    public static ArrayList<ItemStack> getComboItems(){
        ArrayList<ItemStack> itemCombo = new ArrayList<>();

        itemCombo.add(new ItemBuilder(Material.DIAMOND_SWORD,1,"")
                .addEnchant(Enchantment.DAMAGE_ALL, 5).addEnchant(Enchantment.DURABILITY,3).addEnchant(Enchantment.FIRE_ASPECT,2).toItemStack());
        itemCombo.add(new ItemBuilder(Material.ENDER_PEARL,16,"").toItemStack());
        itemCombo.add(new ItemBuilder(Material.ENCHANTED_GOLDEN_APPLE,64,"").toItemStack());


        itemCombo.add(new ItemBuilder(Material.DIAMOND_HELMET,1,"")
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).addEnchant(Enchantment.DURABILITY,3).toItemStack());
        itemCombo.add(new ItemBuilder(Material.DIAMOND_CHESTPLATE,1,"")
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).addEnchant(Enchantment.DURABILITY,3).toItemStack());
        itemCombo.add(new ItemBuilder(Material.DIAMOND_LEGGINGS,1,"")
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).addEnchant(Enchantment.DURABILITY,3).toItemStack());
        itemCombo.add(new ItemBuilder(Material.DIAMOND_BOOTS,1,"")
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).addEnchant(Enchantment.DURABILITY,3).toItemStack());

        return itemCombo;
    }

    public static void setComboArmorAndPotionEffects(Player player){
        player.getInventory().setHelmet(new ItemBuilder(Material.DIAMOND_HELMET,1,"")
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).addEnchant(Enchantment.DURABILITY,3).toItemStack());
        player.getInventory().setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE,1,"")
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).addEnchant(Enchantment.DURABILITY,3).toItemStack());
        player.getInventory().setLeggings(new ItemBuilder(Material.DIAMOND_LEGGINGS,1,"")
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).addEnchant(Enchantment.DURABILITY,3).toItemStack());
        player.getInventory().setBoots(new ItemBuilder(Material.DIAMOND_BOOTS,1,"")
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).addEnchant(Enchantment.DURABILITY,3).toItemStack());

        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,20*60*10,2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,20*60*10,2));
    }

    public static ArrayList<ItemStack> getBuildUhcItems(){
        ArrayList<ItemStack> itemsBuildUHC = new ArrayList<>();

        itemsBuildUHC.add(new ItemBuilder(Material.DIAMOND_SWORD,1,"")
                .addEnchant(Enchantment.DAMAGE_ALL, 3).addEnchant(Enchantment.DURABILITY,3).addEnchant(Enchantment.FIRE_ASPECT,2).toItemStack());
        itemsBuildUHC.add(new ItemBuilder(Material.FISHING_ROD,1,"").toItemStack());
        itemsBuildUHC.add(new ItemBuilder(Material.BOW,1,"").addEnchant(Enchantment.ARROW_DAMAGE,3)
                .addEnchant(Enchantment.DURABILITY,3).toItemStack());
        itemsBuildUHC.add(new ItemBuilder(Material.GOLDEN_CARROT,64,"").toItemStack());
        itemsBuildUHC.add(new ItemBuilder(Material.GOLDEN_APPLE,6,"").toItemStack());
        itemsBuildUHC.add(new ItemBuilder(Material.GOLDEN_APPLE,3,"§5Super Pomme").toItemStack());
        itemsBuildUHC.add(new ItemBuilder(Material.COBBLESTONE,64,"").toItemStack());
        itemsBuildUHC.add(new ItemBuilder(Material.WATER_BUCKET,1,"").toItemStack());
        itemsBuildUHC.add(new ItemBuilder(Material.LAVA_BUCKET,1,"").toItemStack());
        itemsBuildUHC.add(new ItemBuilder(Material.DIAMOND_PICKAXE,1,"").toItemStack());
        itemsBuildUHC.add(new ItemBuilder(Material.DIAMOND_AXE,1,"").toItemStack());
        itemsBuildUHC.add(new ItemBuilder(Material.OAK_PLANKS,64,"").toItemStack());
        itemsBuildUHC.add(new ItemBuilder(Material.WATER_BUCKET,1,"").toItemStack());
        itemsBuildUHC.add(new ItemBuilder(Material.LAVA_BUCKET,1,"").toItemStack());
        itemsBuildUHC.add(new ItemBuilder(Material.ARROW,32,"").toItemStack());

        return itemsBuildUHC;
    }

    public static void setSumoItems(Player player){
        player.getInventory().clear();
        player.getInventory().setItem(0,new ItemBuilder(Material.COOKED_BEEF,2,"").toItemStack());
    }

    public static ArrayList<ItemStack> getSpleefItems(){
        ArrayList<ItemStack> itemsSpleef = new ArrayList<>();

        itemsSpleef.add(new ItemBuilder(Material.DIAMOND_SHOVEL).addEnchant(Enchantment.DIG_SPEED,5).addEnchant(Enchantment.DURABILITY,3).toItemStack());
        itemsSpleef.add(new ItemBuilder(Material.GOLDEN_CARROT,64,"").toItemStack());

        return itemsSpleef;
    }

    public static ArrayList<ItemStack> getArcherItems(){
        ArrayList<ItemStack> itemsArcher = new ArrayList<>();

        itemsArcher.add(new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_INFINITE,1).addEnchant(Enchantment.ARROW_KNOCKBACK,2).toItemStack());
        itemsArcher.add(new ItemBuilder(Material.ARROW).toItemStack());
        itemsArcher.add(new ItemBuilder(Material.GOLDEN_CARROT,64,"").toItemStack());

        return itemsArcher;
    }

    public static void setArcherArmor(Player player){
        player.getInventory().setHelmet(new ItemBuilder(Material.LEATHER_HELMET).toItemStack());
        player.getInventory().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE).toItemStack());
        player.getInventory().setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS).toItemStack());
        player.getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS).toItemStack());

    }

    public static ArrayList<ItemStack> getElenoxItems(){
        ArrayList<ItemStack> itemsElenox = new ArrayList<>();

        itemsElenox.add(new ItemBuilder(Material.STICK).addEnchant(Enchantment.KNOCKBACK,10).toItemStack());
        itemsElenox.add(new ItemBuilder(Material.GOLDEN_APPLE).toItemStack());
        itemsElenox.add(new ItemBuilder(Material.CREEPER_SPAWN_EGG).toItemStack());
        itemsElenox.add(new ItemBuilder(Material.ENDER_PEARL,16,"").toItemStack());
        itemsElenox.add(new ItemBuilder(Material.GOLDEN_CARROT,64,"").toItemStack());
        itemsElenox.add(ElenoxAPI.getElenoxAPI().createPotionItem(PotionBuilder.PotionType.INSTANT_HEAL).strong().toItemStack(1));
        itemsElenox.add(ElenoxAPI.getElenoxAPI().createPotionItem(PotionBuilder.PotionType.SPEED).strong().splash().toItemStack(1));

        return itemsElenox;
    }

    public static void setElenoxArmor(Player player){
        player.getInventory().setHelmet(new ItemBuilder(Material.LEATHER_HELMET,1,"§3El'Casque").toItemStack());
        player.getInventory().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE,1,"§3El'Plastron").toItemStack());
        player.getInventory().setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS,1,"§3El'Jambière").toItemStack());
        player.getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS,1,"§3El'Botte").toItemStack());
    }

    public static ArrayList<ItemStack> getHacheItems(){
        ArrayList<ItemStack> itemsArcher = new ArrayList<>();

        itemsArcher.add(new ItemBuilder(Material.IRON_AXE).toItemStack());
        itemsArcher.add(new ItemBuilder(Material.FISHING_ROD).toItemStack());
        itemsArcher.add(new ItemBuilder(Material.GOLDEN_CARROT,64,"").toItemStack());

        return itemsArcher;
    }

    public static void setHachArmor(Player player){
        player.getInventory().setHelmet(new ItemBuilder(Material.IRON_HELMET).toItemStack());
        player.getInventory().setChestplate(new ItemBuilder(Material.IRON_CHESTPLATE).toItemStack());
        player.getInventory().setLeggings(new ItemBuilder(Material.IRON_LEGGINGS).toItemStack());
        player.getInventory().setBoots(new ItemBuilder(Material.IRON_BOOTS).toItemStack());

    }

    public static ArrayList<ItemStack> getSoupeItems(){
        ArrayList<ItemStack> itemsSoupe = new ArrayList<>();

        itemsSoupe.add(new ItemBuilder(Material.STONE_SWORD).toItemStack());
        itemsSoupe.add(new ItemBuilder(Material.GOLDEN_CARROT,64,"").toItemStack());
        for(int i = 0; i<= 33; i++){
            itemsSoupe.add(new ItemBuilder(Material.MUSHROOM_STEW).toItemStack());
        }

        return itemsSoupe;
    }
}
