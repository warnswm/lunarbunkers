package qwezxc.asd.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainTrader implements Listener {
    private Asd main;



    public MainTrader(final Asd main) {
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
        if (inventory.getTitle().equals("Trader Menu")) {

            event.setCancelled(true);


            if (item == null) return;

            if (item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Speed Potion II")) {
                if (player.isOnline()) {
                    if (emptySlots == -1) {
                        player.sendMessage("Inventory is full");
                    } else {
                        Scoreboard scoreboard = player.getScoreboard();
                        Objective objective = scoreboard.getObjective("Bunkers");
                        scoreboard.resetScores(Bukkit.getOfflinePlayer("Balance: " + Asd.getInstance().getPluginManager().getnewEconomy().getBalance(player)));
                        Asd.getInstance().getPluginManager().getnewEconomy().removeBalance(player, 10);
                        player.getInventory().addItem(new ItemStack(Material.POTION, 1, (short) 8226));
                        Score score1 = objective.getScore("Balance: " + Asd.getInstance().getPluginManager().getnewEconomy().getBalance(player));
                        score1.setScore(1);
                        player.setScoreboard(scoreboard);
                    }
                }
            } else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Health Potion II")) {
                if (player.isOnline()) {
                    if (emptySlots == -1) {
                        player.sendMessage("Inventory is full");
                    } else if (event.getClick() == ClickType.RIGHT) {
                        int rmbhill = 0;
                        for (ItemStack i : player.getInventory().getStorageContents()) {
                            if (i == null || i.getType() == Material.AIR) {
                                player.getInventory().addItem(new ItemStack(Material.POTION, 1, (short) 16421));
                                player.sendMessage(String.valueOf(emptySlots));
                                rmbhill++;
                            }
                        }
                        Scoreboard scoreboard = player.getScoreboard();
                        Objective objective = scoreboard.getObjective("Bunkers");
                        scoreboard.resetScores(Bukkit.getOfflinePlayer("Balance: " + Asd.getInstance().getPluginManager().getnewEconomy().getBalance(player)));
                        Asd.getInstance().getPluginManager().getnewEconomy().removeBalance(player,5 * rmbhill);
                        Score score1 = objective.getScore("Balance: " + Asd.getInstance().getPluginManager().getnewEconomy().getBalance(player));
                        score1.setScore(1);
                        player.setScoreboard(scoreboard);
                    } else {
                        Scoreboard scoreboard = player.getScoreboard();
                        Objective objective = scoreboard.getObjective("Bunkers");
                        scoreboard.resetScores(Bukkit.getOfflinePlayer("Balance: " + Asd.getInstance().getPluginManager().getnewEconomy().getBalance(player)));
                        Asd.getInstance().getPluginManager().getnewEconomy().removeBalance(player, 5);
                        player.getInventory().addItem(new ItemStack(Material.POTION, 1, (short) 16421));
                        Score score1 = objective.getScore("Balance: " + Asd.getInstance().getPluginManager().getnewEconomy().getBalance(player));
                        score1.setScore(1);
                        player.setScoreboard(scoreboard);
                    }

                }
            } else if (item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Fire Resistance Potion (3:00)")) {
                if (player.isOnline()) {
                    if (emptySlots == -1) {
                        player.sendMessage("Inventory is full");
                    } else {
                        Scoreboard scoreboard = player.getScoreboard();
                        Objective objective = scoreboard.getObjective("Bunkers");
                        scoreboard.resetScores(Bukkit.getOfflinePlayer("Balance: " + Asd.getInstance().getPluginManager().getnewEconomy().getBalance(player)));
                        Asd.getInstance().getPluginManager().getnewEconomy().removeBalance(player, 25);
                        player.getInventory().addItem(new ItemStack(Material.POTION, 1, (short) 8195));
                        Score score1 = objective.getScore("Balance: " + Asd.getInstance().getPluginManager().getnewEconomy().getBalance(player));
                        score1.setScore(1);
                        player.setScoreboard(scoreboard);
                    }
                }
            } else if (item.getItemMeta().getDisplayName().equals(ChatColor.DARK_GRAY + "Slowness Potion (1:07)")) {
                if (player.isOnline()) {
                    if (emptySlots == -1) {
                        player.sendMessage("Inventory is full");
                    } else {
                        Scoreboard scoreboard = player.getScoreboard();
                        Objective objective = scoreboard.getObjective("Bunkers");
                        scoreboard.resetScores(Bukkit.getOfflinePlayer("Balance: " + Asd.getInstance().getPluginManager().getnewEconomy().getBalance(player)));
                        Asd.getInstance().getPluginManager().getnewEconomy().removeBalance(player, 50);
                        player.getInventory().addItem(new ItemStack(Material.POTION, 1, (short) 16394));
                        Score score1 = objective.getScore("Balance: " + Asd.getInstance().getPluginManager().getnewEconomy().getBalance(player));
                        score1.setScore(1);
                        player.setScoreboard(scoreboard);
                    }
                }
            } else if (item.getItemMeta().getDisplayName() == null) {
                return;
            } else {
                return;
            }
        }
    }
}
