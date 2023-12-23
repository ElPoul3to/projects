package com.elenox.pvpbox.practice.commands.moderateur;

import com.elenox.api.ElenoxAPI;
import com.elenox.api.player.ElAccesLevel;
import com.elenox.api.player.ElPlayer;
import com.elenox.pvpbox.practice.PvpBoxPractice;
import com.elenox.pvpbox.practice.list.ListManager;
import com.elenox.pvpbox.practice.manager.SettingsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Vanish implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            ElPlayer ep = ElenoxAPI.getElenoxAPI().getElPlayer(player);

            if(ep.hasAccess(ElAccesLevel.MODERATRICE)){
                if(label.equalsIgnoreCase("vanish") || label.equalsIgnoreCase("v")){
                    if (!(ListManager.moderatorInVanish.contains(player.getUniqueId()))){
                        if(!(ListManager.allPlayerMatch.contains(player) || ListManager.allPlayerQueue.contains(player))) {
                            ListManager.moderatorInVanish.add(player.getUniqueId());
                            SettingsManager.setModeratorSettings(player);
                            player.sendMessage(PvpBoxPractice.getModerationPrefix() + "§7Vanish §aactivé");
                            return true;
                        }

                    }else if(ListManager.moderatorInVanish.contains(player.getUniqueId())){
                        ListManager.moderatorInVanish.remove(player.getUniqueId());
                        SettingsManager.setSpawnSettings(player);
                        player.sendMessage(PvpBoxPractice.getModerationPrefix()+"§7Vanish §cdésactivé");
                        return true;
                    }
                }
            }else {
                player.sendMessage(PvpBoxPractice.getNoModeratorError());
            }
        }
        return false;
    }
}
