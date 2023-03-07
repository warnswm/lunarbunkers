package qwezxc.asd.Items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

import java.util.Arrays;

import static qwezxc.asd.util.Utils.getItem;
import static qwezxc.asd.util.Utils.getPotion;

public class RegisterItems {

    public static final ItemStack speedPotion = getPotion(Material.POTION, ChatColor.AQUA + "Speed Potion II (1:30)", PotionType.SPEED, false, true, Arrays.asList(
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.GRAY + "x1 Speed II Potion",
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + "$10"
    ));

    public static final ItemStack healPotion = getPotion(Material.SPLASH_POTION, ChatColor.GREEN + "Health Potion II", PotionType.INSTANT_HEAL, false, true, Arrays.asList(
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.GRAY + "x1 Health Splash Potion",
            ChatColor.YELLOW + "- " + ChatColor.GREEN + "Right-click to fill your inventory",
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + "$5"
    ));


    public static final ItemStack firePotion = getPotion(Material.POTION, ChatColor.GOLD + "Fire Resistance Potion (3:00)", PotionType.FIRE_RESISTANCE, false, false, Arrays.asList(
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.GRAY + "x1 Fire Resistance Potion",
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + "$25"
    ));


    public static final ItemStack slownesspotion = getPotion(Material.SPLASH_POTION, ChatColor.DARK_GRAY + "Slowness Potion (1:07)", PotionType.SLOWNESS, false, false, Arrays.asList(
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.GRAY + "x1 Slowness Splash Potion",
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + "$50"
    ));


    public static final ItemStack fullset = getItem(Material.DIAMOND, ChatColor.RED + "Full set", Arrays.asList(
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.GRAY + "1x Diamond Sword",
            ChatColor.GRAY + "1x Diamond Helmet",
            ChatColor.GRAY + "1x Diamond Chestplate",
            ChatColor.GRAY + "1x Diamond Leggings",
            ChatColor.GRAY + "1x Diamond Boots",
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + "$850"
    ));


    public static final ItemStack sword = getItem(Material.DIAMOND_SWORD, ChatColor.GREEN + "Diamond Sword", Arrays.asList(
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.GRAY + "1x Diamond Sword",
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + "$100"
    ));

    public static final ItemStack helmet = getItem(Material.DIAMOND_HELMET, ChatColor.RED + "Diamond Helmet", Arrays.asList(
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.GRAY + "1x Diamond Helmet",
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + "$125"
    ));
    public static final ItemStack chestplate = getItem(Material.DIAMOND_CHESTPLATE, ChatColor.RED + "Diamond Chestplate", Arrays.asList(
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.GRAY + "1x Diamond Chestplate",
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + "$275"
    ));


    public static final ItemStack leggings = getItem(Material.DIAMOND_LEGGINGS, ChatColor.RED + "Diamond Leggings", Arrays.asList(
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.GRAY + "1x Diamond Leggings",
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + "$250"
    ));


    public static final ItemStack boots = getItem(Material.DIAMOND_BOOTS, ChatColor.RED + "Diamond Boots", Arrays.asList(
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.GRAY + "1x Diamond Boots",
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + "$125"
    ));


    public static final ItemStack enderperl = getItem(Material.ENDER_PEARL, ChatColor.GREEN + "Ender Pearl", Arrays.asList(
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.GRAY + "x1 Ender Pearl",
            ChatColor.YELLOW + "- " + ChatColor.GREEN + "Right-click to buy x16 Ender Pearl",
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + "$25"
    ));


    public static final ItemStack steak = getItem(Material.COOKED_BEEF, ChatColor.GREEN + "Steak", Arrays.asList(
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.GRAY + "x16 Steak",
            ChatColor.YELLOW + "- " + ChatColor.GREEN + "Right-click to buy x64 Steak",
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + "$75"
    ));

    public static final ItemStack enchanthelmet = getItem(Material.GOLD_HELMET, ChatColor.GREEN + "Прочность 3", Arrays.asList(
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + "300",
            ChatColor.GRAY + "―――――――――――――――――――――――――――"
    ));
    public static final ItemStack enchantchestplate = getItem(Material.GOLD_CHESTPLATE, ChatColor.GREEN + "Прочность 3", Arrays.asList(
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + "300",
            ChatColor.GRAY + "―――――――――――――――――――――――――――"
    ));
    public static final ItemStack enchantleggings = getItem(Material.GOLD_LEGGINGS, ChatColor.GREEN + "Прочность 3", Arrays.asList(
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + "300",
            ChatColor.GRAY + "―――――――――――――――――――――――――――"
    ));
    public static final ItemStack enchantboots = getItem(Material.GOLD_BOOTS, ChatColor.GREEN + "Прочность 3", Arrays.asList(
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + "300",
            ChatColor.GRAY + "―――――――――――――――――――――――――――"
    ));

    public static final ItemStack diamondpickaxe = getItem(Material.DIAMOND_PICKAXE, ChatColor.GREEN + "Алмазная кирка", Arrays.asList(
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + "50",
            ChatColor.GRAY + "―――――――――――――――――――――――――――"
    ));

    public static final ItemStack diamondaxe = getItem(Material.DIAMOND_AXE, ChatColor.GREEN + "Алмазный топор", Arrays.asList(
            ChatColor.GRAY + "―――――――――――――――――――――――――――",
            ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + "50",
            ChatColor.GRAY + "―――――――――――――――――――――――――――"
    ));

}
