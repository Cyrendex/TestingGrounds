package me.Cyrendex.testingGrounds.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CursorListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        ItemStack item = e.getItem();
        if (item == null || item.getType() != Material.TRIPWIRE_HOOK) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasCustomModelDataComponent()) return;

        if (meta.getCustomModelDataComponent().getStrings().contains("cursor")) {
            e.setCancelled(true);


            switch (e.getAction()) {
                case RIGHT_CLICK_AIR -> {
                    // TODO
                }
                case RIGHT_CLICK_BLOCK -> {
                    // TODO
                }
                case LEFT_CLICK_AIR -> {
                    // TODO
                }
                case LEFT_CLICK_BLOCK -> {
                    // TODO
                }
                default -> {
                    // Probably nothing but I'll consider
                }
            }
        }
    }
}
