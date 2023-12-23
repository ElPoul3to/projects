package com.elenox.pvpbox.practice;

import com.elenox.pvpbox.practice.commands.CommandManger;
import com.elenox.pvpbox.practice.list.ListManager;
import com.elenox.pvpbox.practice.listenners.ListenerManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PvpBoxPractice extends JavaPlugin {
    public static PvpBoxPractice INSTANCE;

    @Override
    public void onEnable(){
        INSTANCE = this;
        new ListenerManager().runListenner();
        new CommandManger().runCommand();
        new ListManager().addQueueMainList();
    }

    @Override
    public void onDisable(){
        ListManager.runClearList();
    }

    public static String getPrefix(){
        return "§8[§9Practice§8] ";
    }
    public static String getModerationPrefix(){return "§8[§c§lModération§8] ";}
    public static String getNoModeratorError(){ return getPrefix()+"§cVous ne pouvez pas effectuer cette commmande.";}
}
/**
 * By Chickenox (Thomas F.)
**/