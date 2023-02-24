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
import org.bukkit.inventory.ItemStack;
import qwezxc.asd.Asd;

public class RepairListener implements Listener {
//    @EventHandler
//    public void onAnvil(PlayerInteractEvent event) {
//        Action action = event.getAction();
//        Player player = event.getPlayer();
//        Block block = event.getClickedBlock();
//
//        if (action != Action.RIGHT_CLICK_BLOCK || block == null || block.getType() != Material.ANVIL) {
//            return;
//        }
//
//        event.setCancelled(true);
//
//        ItemStack[] armorContents = player.getInventory().getArmorContents();
//        Inventory chestInventory = Bukkit.createInventory(null, 54, ChatColor.GREEN + "Repair");
//
//        boolean hasBrokenArmor = false;
//
//        // Check armor slots for broken armor
//        for (int i = 0; i < armorContents.length; i++) {
//            ItemStack armor = armorContents[i];
//
//            if (armor == null || armor.getType().getMaxDurability() <= 0 || armor.getDurability() == 0) {
//                // Armor is not broken, place a stained glass pane in the chest inventory
//                chestInventory.setItem(i == 0 ? 38 : i == 1 ? 29 : i == 2 ? 20 : 11, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5));
//                continue;
//            }
//
//            // Found a piece of broken armor
//            hasBrokenArmor = true;
//
//            double durabilityPercentage = (double) (armor.getType().getMaxDurability() - armor.getDurability()) / armor.getType().getMaxDurability();
//            ItemStack repairedArmor = new ItemStack(armor.getType(), 1);
//            repairedArmor.setDurability((short) (repairedArmor.getType().getMaxDurability() - (int) Math.ceil(durabilityPercentage * repairedArmor.getType().getMaxDurability())));
//
//            // Place repaired armor in chest inventory
//            chestInventory.setItem(i == 0 ? 38 : i == 1 ? 29 : i == 2 ? 20 : 11, repairedArmor);
//        }
//
//        if (!hasBrokenArmor) {
//            // No broken armor found, place a green glass pane in the chest inventory
//            chestInventory.setItem(11, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5));
//            chestInventory.setItem(20, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5));
//            chestInventory.setItem(29, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5));
//            chestInventory.setItem(38, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5));
//        }
//
//        // Open the chest inventory for the player
//        player.openInventory(chestInventory);
//    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory == null || !clickedInventory.getName().equals(ChatColor.GREEN + "Repair"))  return;
        event.setCancelled(true);
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType().getMaxDurability() <= 0 || clickedItem.getDurability() == 0) {
            return;
        }
        ItemStack greenPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)5);
        event.setCurrentItem(greenPane);

        int repairCost = (int) (clickedItem.getDurability() * 1.5);
        if (!Asd.getInstance().getPluginManager().getEconomy().hasEnoughMoney(player, repairCost)) {
            player.sendMessage(ChatColor.RED + "You don't have enough money to repair your armor.");
            return;
        }
        Asd.getInstance().getPluginManager().getEconomy().removeBalance(player, repairCost);
        int slot = -1;
        ItemStack[] armorContents = player.getInventory().getArmorContents();
        for (int i = 0; i < armorContents.length; i++) {
            if (clickedItem.equals(armorContents[i])) {
                slot = i;
                break;
            }
        }
        if (slot == -1) {
            return;
        }
        ItemStack repairedArmor = new ItemStack(clickedItem.getType(), 1);
        repairedArmor.setDurability((short) 0);
        armorContents[slot] = repairedArmor;
        switch (repairedArmor.getType()) {
            case DIAMOND_HELMET:
                player.getInventory().setHelmet(repairedArmor);
                break;
            case DIAMOND_CHESTPLATE:
                player.getInventory().setChestplate(repairedArmor);
                break;
            case DIAMOND_LEGGINGS:
                player.getInventory().setLeggings(repairedArmor);
                break;
            case DIAMOND_BOOTS:
                player.getInventory().setBoots(repairedArmor);
                break;
        }

        player.sendMessage(ChatColor.GREEN + "Your " + clickedItem.getType().name().toLowerCase() + " has been repaired and equipped for " + repairCost + " dollars.");
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
        ItemStack[] armorContents = player.getInventory().getArmorContents();
        Inventory chestInventory = Bukkit.createInventory(null, 54, ChatColor.GREEN + "Repair");

        boolean hasBrokenArmor = false;
        for (int i = 0; i < armorContents.length; i++) {
            ItemStack armor = armorContents[i];
            if (armor == null || armor.getType().getMaxDurability() <= 0 || armor.getDurability() == 0) {
                // Armor is not broken, place a stained glass pane in the chest inventory
                chestInventory.setItem(i == 0 ? 38 : i == 1 ? 29 : i == 2 ? 20 : 11, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5));
                continue;
            }

            // Found a piece of broken armor
            hasBrokenArmor = true;

            double durabilityPercentage = (double) (armor.getType().getMaxDurability() - armor.getDurability()) / armor.getType().getMaxDurability();
            ItemStack repairedArmor = new ItemStack(armor.getType(), 1);
            repairedArmor.setDurability((short) (repairedArmor.getType().getMaxDurability() - (int) Math.ceil(durabilityPercentage * repairedArmor.getType().getMaxDurability())));

            // Place repaired armor in chest inventory
            chestInventory.setItem(i == 0 ? 38 : i == 1 ? 29 : i == 2 ? 20 : 11, repairedArmor);
        }

        if (!hasBrokenArmor) {
            // No broken armor found, place a green glass pane in the chest inventory
            ItemStack greenPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
            chestInventory.setItem(11, greenPane);
            chestInventory.setItem(20, greenPane);
            chestInventory.setItem(29, greenPane);
            chestInventory.setItem(38, greenPane);
        }

        player.openInventory(chestInventory);
    }


}
