package qwezxc.asd.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import qwezxc.asd.Asd;
import qwezxc.asd.core.Economy;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SellerListener implements Listener {
    private Asd main;

    private HashMap<UUID, Double> balances;

    public SellerListener(final Asd main){
        this.main = main;
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();
        ItemStack item = event.getCurrentItem();
        Inventory inventory = event.getInventory();
        int emptySlots = -1;
        for (ItemStack i : player.getInventory().getStorageContents()) {
            if (i == null || i.getType() == Material.AIR) {
                emptySlots++;
            }
        }
        if (inventory.getName().equals("Seller Shop")) {
            event.setCancelled(true);
            if (item == null) return;

            if (item.getType() == Material.COAL && item.getItemMeta().hasLore()) {
                int coalCount = player.getInventory().all(Material.COAL).values().stream()
                        .mapToInt(ItemStack::getAmount).sum();
                int addCoalBalance = 0;
                if (event.getClick() == ClickType.RIGHT) {
                    if (coalCount > 0) {
                        addCoalBalance = coalCount * 10;
                        removeItemFromInventory(player.getInventory(), Material.COAL, coalCount);
                    }
                } else {
                    if (coalCount > 0) {
                        addCoalBalance = 10;
                        removeItemFromInventory(player.getInventory(), Material.COAL, 1);
                    }
                }
                if (addCoalBalance > 0) {
                    Asd.getInstance().getPluginManager().getEconomy().addBalance(player, addCoalBalance);
                    updateScoreboard(player, addCoalBalance);
                }
            }  else if (item.getType() == Material.IRON_INGOT && item.getItemMeta().hasLore()) {
                int ironCount = player.getInventory().all(Material.IRON_INGOT).values().stream()
                        .mapToInt(ItemStack::getAmount).sum();
                int addIronBalance = 0;
                if (event.getClick() == ClickType.RIGHT) {
                    if (ironCount > 0) {
                        addIronBalance = ironCount * 15;
                        removeItemFromInventory(player.getInventory(), Material.IRON_INGOT, ironCount);
                    }
                } else {
                    if (ironCount > 0) {
                        addIronBalance = 15;
                        removeItemFromInventory(player.getInventory(), Material.IRON_INGOT, 1);
                    }
                }
                if (addIronBalance > 0) {
                    Asd.getInstance().getPluginManager().getEconomy().addBalance(player, addIronBalance);
                    updateScoreboard(player, addIronBalance);
                }
            }else if (item.getType() == Material.GOLD_INGOT && item.getItemMeta().hasLore()) {
                int goldCount = player.getInventory().all(Material.GOLD_INGOT).values().stream()
                        .mapToInt(ItemStack::getAmount).sum();
                int addGoldBalance = 0;
                if (event.getClick() == ClickType.RIGHT) {
                    if (goldCount > 0) {
                        addGoldBalance = goldCount * 20;
                        removeItemFromInventory(player.getInventory(), Material.GOLD_INGOT, goldCount);
                    }
                } else {
                    if (goldCount > 0) {
                        addGoldBalance = 20;
                        removeItemFromInventory(player.getInventory(), Material.GOLD_INGOT, 1);
                    }
                }
                if (addGoldBalance > 0) {
                    Asd.getInstance().getPluginManager().getEconomy().addBalance(player, addGoldBalance);
                    updateScoreboard(player, addGoldBalance);
                }
            }else if (item.getType() == Material.DIAMOND && item.getItemMeta().hasLore()) {
                int diamondCount = player.getInventory().all(Material.DIAMOND).values().stream()
                        .mapToInt(ItemStack::getAmount).sum();
                int addDiamondBalance = 0;
                if (event.getClick() == ClickType.RIGHT) {
                    if (diamondCount > 0) {
                        addDiamondBalance = diamondCount * 50;
                        removeItemFromInventory(player.getInventory(), Material.DIAMOND, diamondCount);
                    }
                } else {
                    if (diamondCount > 0) {
                        addDiamondBalance = 50;
                        removeItemFromInventory(player.getInventory(), Material.DIAMOND, 1);
                    }
                }
                if (addDiamondBalance > 0) {
                    Asd.getInstance().getPluginManager().getEconomy().addBalance(player, addDiamondBalance);
                    updateScoreboard(player, addDiamondBalance);
                }
            }
        }
    }

    private void updateScoreboard(Player player, int balanceToAdd) {
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("Bunkers");
        scoreboard.resetScores(Bukkit.getOfflinePlayer("Balance: " + (Asd.getInstance().getPluginManager().getEconomy().getBalance(player)-balanceToAdd)));
        Score score = objective.getScore("Balance: " + Asd.getInstance().getPluginManager().getEconomy().getBalance(player));
        score.setScore(1);
        player.setScoreboard(scoreboard);
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

