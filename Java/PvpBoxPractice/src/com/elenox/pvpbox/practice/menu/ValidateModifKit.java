package com.elenox.pvpbox.practice.menu;

import com.elenox.api.gui.AbstractGui;
import com.elenox.api.item.ItemBuilder;
import com.elenox.pvpbox.practice.PvpBoxPractice;
import com.elenox.pvpbox.practice.list.ListManager;
import com.elenox.pvpbox.practice.list.PracticeList;
import com.elenox.pvpbox.practice.manager.SettingsManager;
import com.elenox.pvpbox.practice.save.Kits;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ValidateModifKit extends AbstractGui {
    @Override
    public void display(Player player) {
        this.inventory = Bukkit.createInventory(player, 27, "§8EditKit Validation");
        this.setSlotData(new ItemBuilder(Material.GREEN_WOOL, 1, "§aValider").toItemStack(), 11, "valider");
        this.setSlotData(new ItemBuilder(Material.RED_WOOL, 1, "§cAnnuler").toItemStack(), 15, "annuler");
        this.setSlotData(new ItemBuilder(Material.BARRIER,1,"§cRetour").toItemStack(),22,"retour");
        player.openInventory(this.inventory);
    }

    @Override
    public void onClick(Player player, ItemStack stack, String action) {
        if (action.isEmpty()) return;

        if (action.equalsIgnoreCase("valider")) {
            String nom = "";
            if (ListManager.nodebuffEditKit.contains(player)) {
                ListManager.nodebuffEditKit.remove(player);
                nom = "Nodebuff";

            } else if (ListManager.debuffEditKit.contains(player)) {
                ListManager.debuffEditKit.remove(player);
                nom = "Debuff";

            } else if (ListManager.comboEditKit.contains(player)) {
                ListManager.comboEditKit.remove(player);
                nom = "Combo";

            } else if (ListManager.buildUhcEditKit.contains(player)) {
                ListManager.buildUhcEditKit.remove(player);
                nom = "BuildUHC";

            } else if (ListManager.spleefEditKit.contains(player)) {
                ListManager.spleefEditKit.remove(player);
                nom = "Spleef";

            } else if (ListManager.archerEditKit.contains(player)) {
                ListManager.archerEditKit.remove(player);
                nom = "Archer";

            } else if (ListManager.elenoxEditKit.contains(player)) {
                ListManager.elenoxEditKit.remove(player);
                nom = "ElenoxKit";

            } else if (ListManager.hacheEditKit.contains(player)) {
                ListManager.hacheEditKit.remove(player);
                nom = "Hache";

            } else if (ListManager.soupeEditKit.contains(player)) {
                ListManager.soupeEditKit.remove(player);
                nom = "Soupe";
            }
            new Kits(player).setKit(nom.toLowerCase());
            ListManager.playerWhotEditKit.remove(player);
            player.sendMessage(PvpBoxPractice.getPrefix() + "§7Le kit §6§L" + nom + " §7est à jour.");
            SettingsManager.setSpawnSettings(player);

        } else if (action.equalsIgnoreCase("annuler")) {
            for(PracticeList list : ListManager.allListEditKit){
                if(list.contains(player)){
                    list.remove(player);
                    ListManager.playerWhotEditKit.remove(player);
                    SettingsManager.setSpawnSettings(player);
                    player.closeInventory();
                    player.sendMessage(PvpBoxPractice.getPrefix()+"§cRetour au spawn.");
                    break;
                }
            }

        }else if (action.equalsIgnoreCase("retour")){
            player.closeInventory();
        }
    }
}
