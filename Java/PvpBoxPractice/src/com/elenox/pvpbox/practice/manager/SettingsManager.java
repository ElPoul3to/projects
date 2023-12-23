package com.elenox.pvpbox.practice.manager;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class SettingsManager {
    public static void setSpawnSettings(Player player){
        player.setGameMode(GameMode.SURVIVAL);
        ItemManager.setItemSpawn(player);
        player.setFoodLevel(20);
        player.setHealth(20);
        player.setLevel(0);
        player.getActivePotionEffects().clear();
        player.teleport(ZoneManager.getSpawn());
    }

    public static void setModeratorSettings(Player player){
        player.getInventory().clear();
        player.setGameMode(GameMode.SPECTATOR);
    }

    public static void setEditKitSettings(Player player){
        player.getInventory().clear();
        player.teleport(ZoneManager.getEditKit());
        player.updateInventory();
    }
}
