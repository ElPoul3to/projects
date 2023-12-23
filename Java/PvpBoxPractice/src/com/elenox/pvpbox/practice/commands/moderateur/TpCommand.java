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

public class TpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            ElPlayer ep = ElenoxAPI.getElenoxAPI().getElPlayer(player);

            if(ep.hasAccess(ElAccesLevel.MODERATRICE)) {
                if (ListManager.moderatorInVanish.contains(player.getUniqueId())){
                    if (label.equalsIgnoreCase("tp")) {
                        if (args.length == 0) {
                            player.sendMessage("§c/tp <joueur>");
                            return true;
                        } else {
                            Player requester = Bukkit.getPlayer(args[0]);
                            if (requester == null) {
                                player.sendMessage("§cLe joueur n'est pas connecté");
                                return true;
                            } else {
                                player.teleport(requester);
                                player.sendMessage(PvpBoxPractice.getModerationPrefix() + "§7Téléportation vers §3" + requester.getDisplayName() + " §7en cours...");
                                return true;
                            }
                        }
                    }
                }else{
                    player.sendMessage(PvpBoxPractice.getModerationPrefix()+"§cVous devez être en vanish pour effectuer cette commande.");
                    return true;
                }
            }else{
                player.sendMessage(PvpBoxPractice.getNoModeratorError());
                return true;
            }
        }
        return false;
    }
}
