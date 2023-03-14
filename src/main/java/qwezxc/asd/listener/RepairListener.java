package qwezxc.asd.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Consumer;
import org.jetbrains.annotations.NotNull;
import qwezxc.asd.Asd;
import qwezxc.asd.listener.InventoryHolders.CombatInventoryHolder;
import qwezxc.asd.listener.InventoryHolders.RepairInventoryHolder;
import qwezxc.asd.util.Utils;

import java.util.HashMap;
import java.util.Map;

public class RepairListener implements Listener {

    @EventHandler
    public void onInventoryClick(@NotNull InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof RepairInventoryHolder)) return;

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem.getType().getMaxDurability() <= 0 || clickedItem.getDurability() == 0) {
            return;
        }

        int repairCost = (int) (clickedItem.getDurability() * 1.5);
        if (!Asd.getInstance().getPluginManager().getEconomy().hasEnoughMoney(player, repairCost)) {
            player.sendMessage(ChatColor.RED + "У вас недостаточно денег, чтобы починиться.");
            return;
        }
        Asd.getInstance().getPluginManager().getEconomy().removeBalance(player, repairCost);

        ItemStack greenPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
        event.setCurrentItem(greenPane);
        int slot = -1;
        ItemStack[] contents = player.getInventory().getContents();
        for (int i = 0; i < contents.length; i++) {
            if (clickedItem.equals(contents[i])) {
                slot = i;
                break;
            }
        }
        if (slot == -1) {
            return;
        }
        ItemStack repairedItem = new ItemStack(clickedItem.getType(), 1);
        repairedItem.setDurability((short) 0);
        repairedItem.addUnsafeEnchantments(clickedItem.getEnchantments());
        contents[slot] = repairedItem;
        setRepairedItem(player, slot, repairedItem);

        player.sendMessage(Utils.color("&a" + "Ты починнил " + clickedItem.getType().name().toLowerCase() + " за " + repairCost + "$"));

    }


    @EventHandler
    public void onAnvil(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Block block = event.getClickedBlock();
        if (block == null || block.getType() != Material.ANVIL) {
            return;
        }

        event.setCancelled(true);

        Player player = event.getPlayer();
        InventoryHolder inventoryHolder = new RepairInventoryHolder(player);
        Inventory inventory = inventoryHolder.getInventory();

        player.openInventory(inventory);
    }

    private void setRepairedItem(Player player, int slot, ItemStack repairedItem) {
        Map<Material, Consumer<ItemStack>> setInventoryItem = new HashMap<>();
        setInventoryItem.put(Material.DIAMOND_HELMET, player.getInventory()::setHelmet);
        setInventoryItem.put(Material.DIAMOND_CHESTPLATE, player.getInventory()::setChestplate);
        setInventoryItem.put(Material.DIAMOND_LEGGINGS, player.getInventory()::setLeggings);
        setInventoryItem.put(Material.DIAMOND_BOOTS, player.getInventory()::setBoots);
        setInventoryItem.put(Material.DIAMOND_SWORD, (item) -> player.getInventory().setItem(slot, item));
        setInventoryItem.put(Material.DIAMOND_PICKAXE, (item) -> player.getInventory().setItem(slot, item));
        setInventoryItem.put(Material.DIAMOND_AXE, (item) -> player.getInventory().setItem(slot, item));

        Material itemMaterial = repairedItem.getType();
        if (setInventoryItem.containsKey(itemMaterial)) {
            setInventoryItem.get(itemMaterial).accept(repairedItem);
        } else {
            player.getInventory().setItem(slot, repairedItem);
        }
    }
}
