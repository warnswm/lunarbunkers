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
import qwezxc.asd.util.Utils;

public class RepairListener implements Listener {

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
        switch (repairedItem.getType()) {
            case DIAMOND_HELMET:
                player.getInventory().setHelmet(repairedItem);
                break;
            case DIAMOND_CHESTPLATE:
                player.getInventory().setChestplate(repairedItem);
                break;
            case DIAMOND_LEGGINGS:
                player.getInventory().setLeggings(repairedItem);
                break;
            case DIAMOND_BOOTS:
                player.getInventory().setBoots(repairedItem);
                break;
            case DIAMOND_SWORD:
                player.getInventory().setItem(slot, repairedItem);
                break;
            case DIAMOND_PICKAXE:
                player.getInventory().setItem(slot, repairedItem);
                break;
            case DIAMOND_AXE:
                player.getInventory().setItem(slot, repairedItem);
                break;
        }

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
        ItemStack[] armorContents = player.getInventory().getArmorContents();
        Inventory chestInventory = Bukkit.createInventory(null, 54, ChatColor.GREEN + "Repair");
        ItemStack greenPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
        boolean hasBrokenArmor = false;
        boolean hasBrokenItem = false;
        for (int i = 0; i < armorContents.length; i++) {
            ItemStack armor = armorContents[i];
            if (armor == null || armor.getType().getMaxDurability() <= 0 || armor.getDurability() == 0) {
                // Armor is not broken, place a stained glass pane in the chest inventory
                chestInventory.setItem(i == 0 ? 38 : i == 1 ? 29 : i == 2 ? 20 : 11, greenPane);
                continue;
            }

            // Found a piece of broken armor
            hasBrokenArmor = true;

            double durabilityPercentage = (double) (armor.getType().getMaxDurability() - armor.getDurability()) / armor.getType().getMaxDurability();
            ItemStack repairedArmor = new ItemStack(armor.getType(), 1);
            repairedArmor.setDurability((short) (repairedArmor.getType().getMaxDurability() - (int) Math.ceil(durabilityPercentage * repairedArmor.getType().getMaxDurability())));

            // Set repaired armor's enchantments
            repairedArmor.addUnsafeEnchantments(armor.getEnchantments());

            // Place repaired armor in chest inventory
            chestInventory.setItem(i == 0 ? 38 : i == 1 ? 29 : i == 2 ? 20 : 11, repairedArmor);
        }
        ItemStack mainhand = player.getInventory().getItemInMainHand();
        if (mainhand == null) {
            // Main hand is empty, skip repairing
            chestInventory.setItem(43, greenPane);
        } else if (mainhand.getType().getMaxDurability() > 0 && mainhand.getDurability() > 0 && (mainhand.getType().toString().endsWith("_SWORD") || mainhand.getType().toString().endsWith("_AXE") || mainhand.getType().toString().endsWith("_PICKAXE"))) {
            // Found a broken tool or weapon
            hasBrokenItem = true;

            double durabilityPercentage = (double) (mainhand.getType().getMaxDurability() - mainhand.getDurability()) / mainhand.getType().getMaxDurability();
            ItemStack repairedItem = new ItemStack(mainhand.getType(), 1);
            repairedItem.setDurability((short) (repairedItem.getType().getMaxDurability() - (int) Math.ceil(durabilityPercentage * repairedItem.getType().getMaxDurability())));

            repairedItem.addUnsafeEnchantments(mainhand.getEnchantments());

            // Place repaired item in chest inventory
            chestInventory.setItem(43, repairedItem);
        } else {
            // Item is not a tool or weapon, skip repairing
            chestInventory.setItem(43, greenPane);
        }

        if (!hasBrokenArmor) {
            // No broken armor found, place a green glass pane in the chest inventory
            chestInventory.setItem(11, greenPane);
            chestInventory.setItem(20, greenPane);
            chestInventory.setItem(29, greenPane);
            chestInventory.setItem(38, greenPane);
        }
        if (!hasBrokenItem) {
            chestInventory.setItem(43, greenPane);
        }

        player.openInventory(chestInventory);
    }


}
