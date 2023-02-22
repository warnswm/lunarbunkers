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
        int availableSlots = -1;
        availableSlots = player.getInventory().firstEmpty();
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("Bunkers");
        if (inventory.getTitle().equals("Combat Shop")) {

            event.setCancelled(true);


            if (item == null) return;
            if (item.getItemMeta().getDisplayName() != null) {
                if (item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Speed Potion II")) {
                    buyPotion(player, new ItemStack(Material.POTION, 1, (short) 8226), 10);
                } else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Health Potion II")) {
                    if (event.getClick() == ClickType.RIGHT) {
                        int count = 0;
                        for (ItemStack i : player.getInventory().getStorageContents()) {
                            if (i == null || i.getType() == Material.AIR) {
                                count++;
                            }
                        }
                        buyPotion(player, new ItemStack(Material.POTION, count, (short) 16421), 5 * count);
                    } else {
                        buyPotion(player, new ItemStack(Material.POTION, 1, (short) 16421), 5);
                    }
                } else if (item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Fire Resistance Potion (3:00)")) {
                    buyPotion(player, new ItemStack(Material.POTION, 1, (short) 8195), 25);
                }else if (item.getItemMeta().getDisplayName().equals(ChatColor.DARK_GRAY + "Slowness Potion (1:07)")) {
                    buyPotion(player, new ItemStack(Material.POTION, 1, (short) 16394), 25);
                } else if (item.getItemMeta().getDisplayName().equals(ChatColor.RED + "Diamond Boots")) {
                    purchaseItem(player,new ItemStack(Material.DIAMOND_BOOTS), 125, "алмазные ботинки");
                } else if (item.getItemMeta().getDisplayName().equals(ChatColor.RED + "Diamond Leggings")) {
                    purchaseItem(player,new ItemStack(Material.DIAMOND_LEGGINGS), 250, "алмазные поножи");
                }else if (item.getItemMeta().getDisplayName().equals(ChatColor.RED + "Diamond Chestplate")) {
                    purchaseItem(player,new ItemStack(Material.DIAMOND_CHESTPLATE), 275, "алмазный нагрудник");
                }else if (item.getItemMeta().getDisplayName().equals(ChatColor.RED + "Diamond Helmet")) {
                    purchaseItem(player,new ItemStack(Material.DIAMOND_HELMET), 275, "алмазный шлем");
                }else if (item.getItemMeta().getDisplayName().equals(ChatColor.RED + "Full set")) {
                    purchaseItem(player,new ItemStack(Material.DIAMOND), 850, "фулл сет и меч");
                }
                else if (item.getItemMeta().getDisplayName() == null) {
                    return;
                } else {
                    return;
                }
            }
        }
    }


    private void buyPotion(Player player, ItemStack potion, int price) {
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("Bunkers");
        int availableSlots = player.getInventory().firstEmpty();
        if (availableSlots == -1) {
            player.sendMessage("Inventory is full");
            return;
        }
        if (!Asd.getInstance().getPluginManager().getEconomy().hasEnoughMoney(player, price)) {
            player.sendMessage(ChatColor.RED + "У вас не дстаточно денег чтобы купить это зелье");
            return;
        }
        scoreboard.resetScores(Bukkit.getOfflinePlayer("Balance: " + Asd.getInstance().getPluginManager().getEconomy().getBalance(player)));
        Asd.getInstance().getPluginManager().getEconomy().removeBalance(player, price);
        player.getInventory().addItem(potion);
        Score score1 = objective.getScore("Balance: " + Asd.getInstance().getPluginManager().getEconomy().getBalance(player));
        score1.setScore(1);
        player.setScoreboard(scoreboard);
    }

    public void purchaseItem(Player player, ItemStack itemStack, int cost, String displayName) {
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("Bunkers");
        int availableSlots = player.getInventory().firstEmpty();
        if (availableSlots == -1) {
            player.sendMessage("Inventory is full");
            return;
        }
        if (!Asd.getInstance().getPluginManager().getEconomy().hasEnoughMoney(player, cost)) {
            player.sendMessage(ChatColor.RED + "У вас не дстаточно денег чтобы купить " + displayName);
            return;
        }
        scoreboard.resetScores(Bukkit.getOfflinePlayer("Balance: " + Asd.getInstance().getPluginManager().getEconomy().getBalance(player)));
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
        Score score1 = objective.getScore("Balance: " + Asd.getInstance().getPluginManager().getEconomy().getBalance(player));
        score1.setScore(1);
        player.setScoreboard(scoreboard);
    }

}

