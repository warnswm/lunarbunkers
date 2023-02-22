package qwezxc.asd.Items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
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

        combatShop.setItem(18, RegisterItems.sword);
        combatShop.setItem(14, invisibility);
        combatShop.setItem(15, RegisterItems.speedPotion);
        combatShop.setItem(16, RegisterItems.firePotion);
        combatShop.setItem(23, RegisterItems.healPotion);
        combatShop.setItem(34, RegisterItems.slownesspotion);
        combatShop.setItem(37, RegisterItems.boots);
        combatShop.setItem(28, RegisterItems.leggings);
        combatShop.setItem(19, RegisterItems.chestplate);
        combatShop.setItem(20, RegisterItems.fullset);
        combatShop.setItem(21, RegisterItems.enderperl);
    }

}
