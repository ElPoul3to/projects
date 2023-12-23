package com.elenox.pvpbox.practice.save;

import com.elenox.pvpbox.practice.PvpBoxPractice;
import com.elenox.pvpbox.practice.manager.ItemManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Kits {
    private Player player;
    private File file = new File(PvpBoxPractice.INSTANCE.getDataFolder(),"items_kits.yml");
    private YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
    private ConfigurationSection configSection;

    public Kits(Player player){
        this.player = player;
        configSection = config.getConfigurationSection("players."+player.getUniqueId().toString());

        if(configSection == null){
            config.set("players."+player.getUniqueId().toString()+".nodebuff", ItemManager.getNodebuffItems());
            config.set("players."+player.getUniqueId().toString()+".debuff",ItemManager.getDebuffItems());
            config.set("players."+player.getUniqueId().toString()+".combo",ItemManager.getComboItems());
            config.set("players."+player.getUniqueId().toString()+".builduhc",ItemManager.getBuildUhcItems());
            config.set("players."+player.getUniqueId().toString()+".spleef",ItemManager.getSpleefItems());
            config.set("players."+player.getUniqueId().toString()+".archer",ItemManager.getArcherItems());
            config.set("players."+player.getUniqueId().toString()+".elenox",ItemManager.getElenoxItems());
            config.set("players."+player.getUniqueId().toString()+".hache",ItemManager.getHacheItems());
            config.set("players."+player.getUniqueId().toString()+".soupe",ItemManager.getSoupeItems());
            this.saveFile();
        }
    }

    public void getKit(String finalPath){
        ItemStack[] content = ((List<ItemStack>) configSection.get(finalPath)).toArray(new ItemStack[0]);
        player.getInventory().setContents(content);
    }

    public void setKit(String finalPath){
        configSection.set(finalPath, player.getInventory().getContents());
        this.saveFile();
    }

    private void saveFile(){
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
