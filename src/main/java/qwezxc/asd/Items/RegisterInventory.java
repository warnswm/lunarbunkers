package qwezxc.asd.Items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class RegisterInventory {

    public static Inventory combatShop = Bukkit.createInventory(null, 54, "Combat Shop");

    static {
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

        combatShop.setItem(18, RegisterItems.sword);
        combatShop.setItem(33, invisibility);
        combatShop.setItem(14, RegisterItems.speedPotion);
        combatShop.setItem(15, antidote);
        combatShop.setItem(16, RegisterItems.firePotion);
        combatShop.setItem(23, RegisterItems.healPotion);
        combatShop.setItem(24, RegisterItems.steak);
        combatShop.setItem(34, RegisterItems.slownesspotion);
        combatShop.setItem(37, RegisterItems.boots);
        combatShop.setItem(28, RegisterItems.leggings);
        combatShop.setItem(19, RegisterItems.chestplate);
        combatShop.setItem(10, RegisterItems.helmet);
        combatShop.setItem(20, RegisterItems.fullset);
        combatShop.setItem(21, RegisterItems.enderperl);
    }
}
