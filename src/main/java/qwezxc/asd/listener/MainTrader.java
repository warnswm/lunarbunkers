package qwezxc.asd.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class MainTrader implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();
        ItemStack item = event.getCurrentItem();
        Inventory inventory = event.getInventory();
        if (inventory.getTitle().equals("Trader")) {

            event.setCancelled(true);

            if (item == null) return;

            if (item.getType() == Material.GOLD_INGOT && item.getItemMeta().getDisplayName().equals("Exchange 1 Gold for 1 Diamond")) {
                if (player.getInventory().contains(Material.GOLD_INGOT)) {

                }
            }
        }
    }
}
