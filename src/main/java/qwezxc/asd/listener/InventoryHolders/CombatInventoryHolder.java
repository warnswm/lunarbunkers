package qwezxc.asd.listener.InventoryHolders;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import qwezxc.asd.Items.RegisterItems;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CombatInventoryHolder implements InventoryHolder {
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, "Combat Shop");

        ItemStack invisibility = new ItemStack(Material.SPLASH_POTION);
        PotionMeta invisibilitymeta = (PotionMeta) invisibility.getItemMeta();
        invisibilitymeta.setDisplayName(ChatColor.RED + "Invisibility Splash Potion (1:30)");
        invisibilitymeta.addCustomEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 90 * 20, 0), false);
        invisibilitymeta.setColor(Color.GRAY);
        invisibilitymeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        List<String> invisibilitylore = new ArrayList<>();
        invisibilitylore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
        invisibilitylore.add(ChatColor.GRAY + "x1 Invisibility Splash Potion");
        invisibilitylore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
        invisibilitylore.add(ChatColor.YELLOW + "Price: " + ChatColor.RED + "$1250");
        invisibilitymeta.setLore(invisibilitylore);
        invisibility.setItemMeta(invisibilitymeta);

        ItemStack antidote = new ItemStack(Material.POTION);
        PotionMeta antidotemeta = (PotionMeta) antidote.getItemMeta();
        antidotemeta.setDisplayName(ChatColor.GREEN + "Antidote");
        antidotemeta.setColor(Color.GREEN);
        antidotemeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        List<String> antidotelore = new ArrayList<>();
        antidotelore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
        antidotelore.add(ChatColor.GRAY + "x1 Antidote Potion");
        antidotelore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
        antidotelore.add(ChatColor.YELLOW + "Price: " + ChatColor.RED + "150");
        antidotemeta.setLore(antidotelore);
        antidote.setItemMeta(antidotemeta);

        inventory.setItem(18, RegisterItems.sword);
        inventory.setItem(33, invisibility);
        inventory.setItem(14, RegisterItems.speedPotion);
        inventory.setItem(15, antidote);
        inventory.setItem(16, RegisterItems.firePotion);
        inventory.setItem(23, RegisterItems.healPotion);
        inventory.setItem(24, RegisterItems.steak);
        inventory.setItem(34, RegisterItems.slownesspotion);
        inventory.setItem(37, RegisterItems.boots);
        inventory.setItem(28, RegisterItems.leggings);
        inventory.setItem(19, RegisterItems.chestplate);
        inventory.setItem(10, RegisterItems.helmet);
        inventory.setItem(20, RegisterItems.fullset);
        inventory.setItem(21, RegisterItems.enderperl);


        return inventory;
    }

}
