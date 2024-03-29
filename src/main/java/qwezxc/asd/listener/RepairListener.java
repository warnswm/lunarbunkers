package qwezxc.asd.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Consumer;
import org.jetbrains.annotations.NotNull;
import qwezxc.asd.Asd;
import qwezxc.asd.listener.InventoryHolders.RepairInventoryHolder;
import qwezxc.asd.util.Utils;

import java.util.EnumMap;

public class RepairListener implements Listener {

    @EventHandler
    public void onInventoryClick(@NotNull InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof RepairInventoryHolder)) return;

        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem.getType().getMaxDurability() <= 0 || clickedItem.getDurability() == 0) return;

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
    public void onAnvil(@NotNull InventoryOpenEvent event) {
        Inventory inventory = event.getInventory();
        if (inventory.getType() != InventoryType.ANVIL) return;

        Player player = (Player) event.getPlayer();
        player.closeInventory();
        player.openInventory(new RepairInventoryHolder(player).getInventory());
    }


    private void setRepairedItem(Player player, int slot, ItemStack repairedItem) {
        EnumMap<Material, Consumer<ItemStack>> setInventoryItem = new EnumMap<>(Material.class);
        setInventoryItem.put(Material.DIAMOND_HELMET, player.getInventory()::setHelmet);
        setInventoryItem.put(Material.DIAMOND_CHESTPLATE, player.getInventory()::setChestplate);
        setInventoryItem.put(Material.DIAMOND_LEGGINGS, player.getInventory()::setLeggings);
        setInventoryItem.put(Material.DIAMOND_BOOTS, player.getInventory()::setBoots);
        setInventoryItem.put(Material.DIAMOND_SWORD, item -> player.getInventory().setItem(slot, item));
        setInventoryItem.put(Material.DIAMOND_PICKAXE, item -> player.getInventory().setItem(slot, item));
        setInventoryItem.put(Material.DIAMOND_AXE, item -> player.getInventory().setItem(slot, item));

        Material itemMaterial = repairedItem.getType();
        setInventoryItem.getOrDefault(itemMaterial,
                        item -> player.getInventory().setItem(slot, item))
                .accept(repairedItem);
    }

}