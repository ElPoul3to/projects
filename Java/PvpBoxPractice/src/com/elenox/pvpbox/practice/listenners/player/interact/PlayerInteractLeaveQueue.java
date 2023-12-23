package com.elenox.pvpbox.practice.listenners.player.interact;

import com.elenox.pvpbox.practice.list.ListManager;
import com.elenox.pvpbox.practice.list.PracticeList;
import com.elenox.pvpbox.practice.manager.SettingsManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractLeaveQueue implements Listener {
    @EventHandler
    public void onPlayerLeaveQueue(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack is = event.getItem();
        Action act = event.getAction();

        if (is == null) return;

        if (is.getType() == Material.REDSTONE_TORCH && is.getItemMeta().getDisplayName().contains("§cQuitter la queue §6§l")) {
            if (act == Action.RIGHT_CLICK_AIR || act == Action.RIGHT_CLICK_BLOCK) {
                for (PracticeList list : ListManager.allQueue) {
                    if (list.contains(player)) {
                        list.remove(player);
                        SettingsManager.setSpawnSettings(player);
                        break;
                    }
                }
            }
        }
    }
}
