package qwezxc.asd.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import qwezxc.asd.Asd;
import qwezxc.asd.listener.InventoryHolders.CombatInventoryHolder;

public class MainTraderListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof CombatInventoryHolder)) return;

        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        event.setCancelled(true);

        int availableSlots = player.getInventory().firstEmpty();
        if (availableSlots == -1) {
            player.sendMessage("Inventory is full");
            return;
        }

        if (item == null || item.getItemMeta().getDisplayName() == null) return;
        if (item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Speed Potion II (1:30)")) {
            buy(player, new ItemStack(Material.POTION, 1, (short) 8226), 10);
        } else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Health Potion II")) {
            if (event.getClick() == ClickType.RIGHT) {
                int count = 0;
                for (ItemStack i : player.getInventory().getStorageContents()) {
                    if (i == null || i.getType() == Material.AIR) {
                        count++;
                    }
                }
                if (!Asd.getInstance().getPluginManager().getEconomy().hasEnoughMoney(player, 5 * count)) {
                    player.sendMessage(ChatColor.RED + "У вас не дстаточно денег чтобы заполнить инвентарь зельем");
                    return;
                }
                for (int i = count; i > 0; i--) {
                    buy(player, new ItemStack(Material.POTION, 1, (short) 16421), 5);
                }
            } else {
                buy(player, new ItemStack(Material.POTION, 1, (short) 16421), 5);
            }
        } else if (item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Fire Resistance Potion (3:00)"))
            buy(player, new ItemStack(Material.POTION, 1, (short) 8195), 25);
         else if (item.getItemMeta().getDisplayName().equals(ChatColor.DARK_GRAY + "Slowness Potion (1:07)"))
            buy(player, new ItemStack(Material.POTION, 1, (short) 16394), 25);
         else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Diamond Sword"))
            buy(player, new ItemStack(Material.DIAMOND_SWORD, 1),100);
         else if (item.getType() == Material.DIAMOND_BOOTS)
            purchaseItem(player, new ItemStack(Material.DIAMOND_BOOTS), 125, "алмазные ботинки");
         else if (item.getType() == Material.DIAMOND_LEGGINGS)
            purchaseItem(player, new ItemStack(Material.DIAMOND_LEGGINGS), 250, "алмазные поножи");
         else if (item.getType() == Material.DIAMOND_CHESTPLATE)
            purchaseItem(player, new ItemStack(Material.DIAMOND_CHESTPLATE), 275, "алмазный нагрудник");
         else if (item.getType() == Material.DIAMOND_HELMET)
            purchaseItem(player, new ItemStack(Material.DIAMOND_HELMET), 275, "алмазный шлем");
         else if (item.getType() == Material.DIAMOND)
            purchaseItem(player, new ItemStack(Material.DIAMOND), 850, "фулл сет и меч");
         else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Ender Pearl")) {
            if (event.getClick() == ClickType.RIGHT) {
                if (!Asd.getInstance().getPluginManager().getEconomy().hasEnoughMoney(player, 25 * 16)) {
                    player.sendMessage(ChatColor.RED + "У вас не дстаточно денег чтобы купить это");
                    return;
                }
                Asd.getInstance().getPluginManager().getEconomy().removeBalance(player, 25 * 16);
                player.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 16));
            } else {
                buy(player, new ItemStack(Material.ENDER_PEARL, 1), 25);
            }
        } else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Steak")) {
            if (event.getClick() == ClickType.RIGHT) {
                if (!Asd.getInstance().getPluginManager().getEconomy().hasEnoughMoney(player, 75 * 4)) {
                    player.sendMessage(ChatColor.RED + "У вас не дстаточно денег чтобы купить это");
                    return;
                }
                Asd.getInstance().getPluginManager().getEconomy().removeBalance(player, 75 * 4);
                player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 64));
            } else {
                buy(player, new ItemStack(Material.COOKED_BEEF, 16), 75);
            }
        }
    }


    private void buy(Player player, ItemStack item, int price) {
        if (!Asd.getInstance().getPluginManager().getEconomy().hasEnoughMoney(player, price)) {
            player.sendMessage(ChatColor.RED + "У вас не дстаточно денег чтобы купить это зелье");
            return;
        }
        Asd.getInstance().getPluginManager().getEconomy().removeBalance(player, price);
        player.getInventory().addItem(item);
    }

    public void purchaseItem(Player player, ItemStack itemStack, int cost, String displayName) {

        if (!Asd.getInstance().getPluginManager().getEconomy().hasEnoughMoney(player, cost)) {
            player.sendMessage(ChatColor.RED + "У вас не дстаточно денег чтобы купить " + displayName);
            return;
        }
        Asd.getInstance().getPluginManager().getEconomy().removeBalance(player, cost);
        if (itemStack.getType() == Material.DIAMOND) {
            ItemStack boots = player.getInventory().getBoots();
            ItemStack leggings = player.getInventory().getLeggings();
            ItemStack chestplate = player.getInventory().getChestplate();
            ItemStack helmet = player.getInventory().getHelmet();

            // Check which armor pieces are not worn and equip them
            if (boots == null || boots.getType() == Material.AIR) {
                player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
            } else if (boots.getType() == Material.DIAMOND_BOOTS) {
                player.getInventory().addItem(new ItemStack(Material.DIAMOND_BOOTS));
            }

            if (leggings == null || leggings.getType() == Material.AIR) {
                player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
            } else if (leggings.getType() == Material.DIAMOND_LEGGINGS) {
                player.getInventory().addItem(new ItemStack(Material.DIAMOND_LEGGINGS));
            }

            if (chestplate == null || chestplate.getType() == Material.AIR) {
                player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
            } else if (chestplate.getType() == Material.DIAMOND_CHESTPLATE) {
                player.getInventory().addItem(new ItemStack(Material.DIAMOND_CHESTPLATE));
            }

            if (helmet == null || helmet.getType() == Material.AIR) {
                player.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
            } else if (helmet.getType() == Material.DIAMOND_HELMET) {
                player.getInventory().addItem(new ItemStack(Material.DIAMOND_HELMET));
            }
        }else {
            ItemStack item = null;
            if (itemStack.getType() == Material.DIAMOND_BOOTS) {
                item = player.getInventory().getBoots();
                player.getInventory().setBoots(itemStack);
            } else if (itemStack.getType() == Material.DIAMOND_LEGGINGS) {
                item = player.getInventory().getLeggings();
                player.getInventory().setLeggings(itemStack);
            } else if (itemStack.getType() == Material.DIAMOND_CHESTPLATE) {
                item = player.getInventory().getChestplate();
                player.getInventory().setChestplate(itemStack);
            } else if (itemStack.getType() == Material.DIAMOND_HELMET) {
                item = player.getInventory().getHelmet();
                player.getInventory().setHelmet(itemStack);
            }
            if (item != null && item.getType() != Material.AIR) {
                player.getInventory().addItem(item);
            }
        }
    }

}

