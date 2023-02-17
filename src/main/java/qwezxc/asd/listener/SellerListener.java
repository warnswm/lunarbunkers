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

import java.util.UUID;

public class SellerListener implements Listener {
    private Asd main;

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
        if (inventory.getTitle().equals("Seller Menu")) {
            event.setCancelled(true);
            if (item == null) return;
            if (player.isOnline()) {
                if (item.getType() == Material.COAL) {
                    if(item.getItemMeta().hasLore()) {
                        if (player.getInventory().contains(Material.COAL)) {
                            if (event.getClick() == ClickType.RIGHT) {
                                int coalcountinsellerforrightclick = 0;
                                for (ItemStack itemStack : player.getInventory().getContents()) {
                                    if (itemStack != null && itemStack.getType() == Material.COAL) {
                                        coalcountinsellerforrightclick += itemStack.getAmount();
                                    }
                                }
                                player.getInventory().removeItem(new ItemStack(Material.COAL, coalcountinsellerforrightclick));
                                int addcoalbalance = coalcountinsellerforrightclick * 10;
                                Scoreboard scoreboard = player.getScoreboard();
                                Objective objective = scoreboard.getObjective("Bunkers");
                                scoreboard.resetScores(Bukkit.getOfflinePlayer("Balance: " + Asd.getInstance().getPluginManager().getEconomy().getBalance(uuid)));
                                Asd.getInstance().getPluginManager().getEconomy().addBalance(uuid, addcoalbalance);
                                Score score2 = objective.getScore("Balance: " + Asd.getInstance().getPluginManager().getEconomy().getBalance(uuid));
                                score2.setScore(2);
                                player.setScoreboard(scoreboard);
                            } else if (event.getClick() == ClickType.LEFT) {
                                player.getInventory().removeItem(new ItemStack(Material.COAL, 1));
                                int addcoalbalance = 10;
                                Scoreboard scoreboard = player.getScoreboard();
                                Objective objective = scoreboard.getObjective("Bunkers");
                                scoreboard.resetScores(Bukkit.getOfflinePlayer("Balance: " + Asd.getInstance().getPluginManager().getEconomy().getBalance(uuid)));
                                Asd.getInstance().getPluginManager().getEconomy().addBalance(uuid, addcoalbalance);
                                Score score2 = objective.getScore("Balance: " + Asd.getInstance().getPluginManager().getEconomy().getBalance(uuid));
                                score2.setScore(2);
                                player.setScoreboard(scoreboard);
                            }
                        }
                    }
                }
                if (item.getType() == Material.IRON_INGOT) {
                    if(item.getItemMeta().hasLore()) {
                        if (player.getInventory().contains(Material.IRON_INGOT)) {
                            if (event.getClick() == ClickType.RIGHT) {
                                int ironcount = 0;
                                for (ItemStack itemStack : player.getInventory().getContents()) {
                                    if (itemStack != null && itemStack.getType() == Material.IRON_INGOT) {
                                        ironcount += itemStack.getAmount();
                                    }
                                }
                                player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT, ironcount));
                                int addironbalance = ironcount * 15;
                                Scoreboard scoreboard = player.getScoreboard();
                                Objective objective = scoreboard.getObjective("Bunkers");
                                scoreboard.resetScores(Bukkit.getOfflinePlayer("Balance: " + Asd.getInstance().getPluginManager().getEconomy().getBalance(uuid)));
                                Asd.getInstance().getPluginManager().getEconomy().addBalance(uuid, addironbalance);
                                Score score2 = objective.getScore("Balance: " + Asd.getInstance().getPluginManager().getEconomy().getBalance(uuid));
                                score2.setScore(2);
                                player.setScoreboard(scoreboard);
                            } else if (event.getClick() == ClickType.LEFT) {
                                player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT, 1));
                                int addironbalance = 15;
                                Scoreboard scoreboard = player.getScoreboard();
                                Objective objective = scoreboard.getObjective("Bunkers");
                                scoreboard.resetScores(Bukkit.getOfflinePlayer("Balance: " + Asd.getInstance().getPluginManager().getEconomy().getBalance(uuid)));
                                Asd.getInstance().getPluginManager().getEconomy().addBalance(uuid, addironbalance);
                                Score score2 = objective.getScore("Balance: " + Asd.getInstance().getPluginManager().getEconomy().getBalance(uuid));
                                score2.setScore(2);
                                player.setScoreboard(scoreboard);
                                player.sendMessage(String.valueOf(addironbalance));
                            }
                        }
                    }
                }
            }
        }

    }
}
