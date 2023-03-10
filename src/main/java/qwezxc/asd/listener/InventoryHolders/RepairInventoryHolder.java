package qwezxc.asd.listener.InventoryHolders;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RepairInventoryHolder implements InventoryHolder {
    Player player;

    @Override
    public Inventory getInventory() {
        Inventory chestInventory = Bukkit.createInventory(this, 54, ChatColor.GREEN + "Repair");

        ItemStack[] armorContents = player.getInventory().getArmorContents();
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
        return chestInventory;
    }
}
