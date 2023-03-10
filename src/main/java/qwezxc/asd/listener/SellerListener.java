package qwezxc.asd.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import qwezxc.asd.Asd;
import qwezxc.asd.Items.ItemInfo;
import qwezxc.asd.listener.InventoryHolders.SellerInventoryHolder;

import java.util.Map;

import static qwezxc.asd.Items.ItemInfo.ITEM_INFOS;

public class SellerListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof SellerInventoryHolder)) return;

        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        event.setCancelled(true);

        if (item == null) return;

        for (ItemInfo itemInfo : ITEM_INFOS) {
            if (item.getType() == itemInfo.getType() && item.getItemMeta().hasLore()) {
                int count = player.getInventory().all(itemInfo.getType()).values().stream().mapToInt(ItemStack::getAmount).sum();
                int addBalance = 0;
                if (event.getClick() == ClickType.RIGHT) {
                    if (count > 0) {
                        addBalance = count * itemInfo.getValue();
                        removeItemFromInventory(player.getInventory(), itemInfo.getType(), count);
                    }
                } else {
                    if (count > 0) {
                        addBalance = itemInfo.getSingleValue();
                        removeItemFromInventory(player.getInventory(), itemInfo.getType(), 1);
                    }
                }
                if (addBalance > 0) {
                    Asd.getInstance().getPluginManager().getEconomy().addBalance(player, addBalance);
                }
                break;
            }
        }

    }

    private void removeItemFromInventory(Inventory inventory, Material material, int amount) {
        Map<Integer, ? extends ItemStack> items = inventory.all(material);
        for (Map.Entry<Integer, ? extends ItemStack> entry : items.entrySet()) {
            int amountToRemove = Math.min(amount, entry.getValue().getAmount());
            entry.getValue().setAmount(entry.getValue().getAmount() - amountToRemove);
            amount -= amountToRemove;
            if (amount <= 0) {
                break;
            }
        }
    }

}

