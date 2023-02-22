package qwezxc.asd.Items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.Arrays;
import java.util.List;

public class RegisterItems {

    public static ItemStack speedPotion = getPotion(Material.POTION, ChatColor.AQUA + "Speed Potion II", PotionType.SPEED, false, true, Arrays.asList(
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.GRAY + "x1 Speed II Potion",
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$10"
    ));

    public static ItemStack healPotion = getPotion(Material.SPLASH_POTION,ChatColor.GREEN + "Health Potion II",PotionType.INSTANT_HEAL, false, true,Arrays.asList(
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.GRAY + "x1 Health Splash Potion",
            ChatColor.YELLOW + "- " + ChatColor.GREEN + "Right-click to fill your inventory",
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$5"
    ));


    public static ItemStack firePotion = getPotion(Material.POTION,ChatColor.GOLD + "Fire Resistance Potion (3:00)",PotionType.FIRE_RESISTANCE,false,false, Arrays.asList(
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.GRAY + "x1 Fire Resistance Potion",
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$25"
    ));


    public static ItemStack slownesspotion = getPotion(Material.SPLASH_POTION,ChatColor.DARK_GRAY + "Slowness Potion (1:07)",PotionType.SLOWNESS,false,false,Arrays.asList(
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.GRAY + "x1 Slowness Splash Potion",
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$50"
    ));


    public static ItemStack fullset = getItem(Material.DIAMOND, ChatColor.RED + "Full set", Arrays.asList(
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.GRAY + "1x Diamond Sword",
            ChatColor.GRAY + "1x Diamond Helmet",
            ChatColor.GRAY + "1x Diamond Chestplate",
            ChatColor.GRAY + "1x Diamond Leggings",
            ChatColor.GRAY + "1x Diamond Boots",
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$850"
    ));


    public static ItemStack sword = getItem(Material.DIAMOND_SWORD, ChatColor.GREEN + "Diamond Sword", Arrays.asList(
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.GRAY + "1x Diamond Sword",
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$100"
    ));

    public static ItemStack chestplate = getItem(Material.DIAMOND_CHESTPLATE, ChatColor.RED + "Diamond Chestplate", Arrays.asList(
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.GRAY + "1x Diamond Chestplate",
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$275"
    ));


    public static ItemStack leggings = getItem(Material.DIAMOND_LEGGINGS, ChatColor.RED + "Diamond Leggings", Arrays.asList(
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.GRAY + "1x Diamond Leggings",
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$250"
    ));


    public static ItemStack boots = getItem(Material.DIAMOND_BOOTS, ChatColor.RED + "Diamond Boots", Arrays.asList(
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.GRAY + "1x Diamond Boots",
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$125"
    ));


    public static ItemStack enderperl = getItem(Material.ENDER_PEARL,ChatColor.GREEN + "Ender Pearl", Arrays.asList(
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.GRAY + "x1 Ender Pearl",
            ChatColor.YELLOW + "- " + ChatColor.GREEN + "Right-click to buy x16 Ender Pearl",
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$25"
    ));



    public static ItemStack steak = getItem(Material.COOKED_BEEF,ChatColor.GREEN + "Steak", Arrays.asList(
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.GRAY + "x16 Steak",
            ChatColor.YELLOW + "- " + ChatColor.GREEN + "Right-click to buy x64 Steak",
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "75"
    ));

    public static ItemStack getItem(Material material, String name, List<String> lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
    public static ItemStack getPotion(Material material,String name,PotionType potionType,boolean extander,boolean upgraded, List<String> lore){
        ItemStack itemStack = new ItemStack(material);
        PotionMeta itemMeta = (PotionMeta) itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setBasePotionData(new PotionData(potionType,extander,upgraded));
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
