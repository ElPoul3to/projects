package com.elenox.pvpbox.practice.commands.player;

import com.elenox.pvpbox.practice.PvpBoxPractice;
import com.elenox.pvpbox.practice.list.ListManager;
import com.elenox.pvpbox.practice.manager.ZoneManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        if(ListManager.allPlayerMatch.contains(player)){
            player.sendMessage(PvpBoxPractice.getPrefix()+"§cvous ne pouvez pas effectuer cette cmmande !");
            return false;
        }

        if(label.equalsIgnoreCase("spawn")){
            player.teleport(ZoneManager.getSpawn());
            player.sendMessage(PvpBoxPractice.getPrefix()+"§7Téléportation au Spawn.");
            return true;
        }
        return false;
    }
}
