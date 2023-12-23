package com.elenox.pvpbox.practice.commands.moderateur;

import com.elenox.api.ElenoxAPI;
import com.elenox.api.player.ElAccesLevel;
import com.elenox.api.player.ElPlayer;
import com.elenox.pvpbox.practice.PvpBoxPractice;
import com.elenox.pvpbox.practice.list.ListManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Freeze implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player =(Player) sender;
        ElPlayer ep = ElenoxAPI.getElenoxAPI().getElPlayer(player);

        if(ep.hasAccess(ElAccesLevel.MODERATRICE)){
            if(label.equalsIgnoreCase("freeze")){
                if(args.length == 0){
                    player.sendMessage("§c/freeze <joueur>");
                    return true;
                }else{
                    Player requester = Bukkit.getPlayer(args[0]);
                    if(requester == null){
                        player.sendMessage("§cLe joueur n'est pas connecté");
                        return true;
                    }else{
                        ListManager.playerFrozen.add(requester);
                        player.sendMessage(PvpBoxPractice.getModerationPrefix()+"§7Vous avez §c§lFreeze §3"+requester.getDisplayName());
                        requester.sendMessage(PvpBoxPractice.getPrefix()+"§7Vous êtes §c§lFreeze§7, vous ne pouvez plus bouger.");
                        return true;
                    }
                }
            }

        }else{
            player.sendMessage(PvpBoxPractice.getNoModeratorError());
            return true;
        }

        return false;
    }
}
