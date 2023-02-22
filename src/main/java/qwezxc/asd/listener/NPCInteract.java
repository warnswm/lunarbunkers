package qwezxc.asd.listener;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import qwezxc.asd.Asd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NPCInteract implements Listener {
    private final Asd main;
    final int COAL_PRICE = 10;
    final int IRON_INGOT_PRICE = 15;
    final int DIAMOND_PRICE = 20;
    final int GOLD_INGOT_PRICE = 20;
    public NPCInteract(Asd main) {
        this.main = main;
    }

    @EventHandler
    public void onNPCInteract(NPCRightClickEvent event) {
        NPC npc = event.getNPC();
        Player player = event.getClicker();
        Inventory plinv = player.getInventory();
        if (npc.getName().equals("Combat Shop")) {
            Inventory inventory = Bukkit.createInventory(null, 54, "Combat Shop");

            ItemStack speedPotion = getPotion(Material.POTION,ChatColor.AQUA + "Speed Potion II",PotionType.SPEED, false, true,Arrays.asList(
                    ChatColor.GRAY + "―――――――――――――――――――――――――――",
                    ChatColor.GRAY + "x1 Speed II Potion",
                    ChatColor.GRAY + "―――――――――――――――――――――――――――",
                    ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$10"
            ));

            ItemStack healPotion = getPotion(Material.SPLASH_POTION,ChatColor.GREEN + "Health Potion II",PotionType.INSTANT_HEAL, false, true,Arrays.asList(
                    ChatColor.GRAY + "―――――――――――――――――――――――――――",
                    ChatColor.GRAY + "x1 Health Splash Potion",
                    ChatColor.YELLOW + "- " + ChatColor.GREEN + "Right-click to fill your inventory",
                    ChatColor.GRAY + "―――――――――――――――――――――――――――",
                    ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$5"
            ));


            ItemStack firePotion = getPotion(Material.POTION,ChatColor.GOLD + "Fire Resistance Potion (3:00)",PotionType.FIRE_RESISTANCE,false,false, Arrays.asList(
                    ChatColor.GRAY + "―――――――――――――――――――――――――――",
                    ChatColor.GRAY + "x1 Fire Resistance Potion",
                    ChatColor.GRAY + "―――――――――――――――――――――――――――",
                    ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$25"
            ));


            ItemStack slownesspotion = getPotion(Material.SPLASH_POTION,ChatColor.DARK_GRAY + "Slowness Potion (1:07)",PotionType.SLOWNESS,false,false,Arrays.asList(
                    ChatColor.GRAY + "―――――――――――――――――――――――――――",
                    ChatColor.GRAY + "x1 Slowness Splash Potion",
                    ChatColor.GRAY + "―――――――――――――――――――――――――――",
                    ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$50"
            ));


            ItemStack fullset = getItem(Material.DIAMOND, ChatColor.RED + "Full set", Arrays.asList(
                ChatColor.GRAY + "―――――――――――――――――――――――――――",
                ChatColor.GRAY + "1x Diamond Sword",
                ChatColor.GRAY + "1x Diamond Helmet",
                ChatColor.GRAY + "1x Diamond Chestplate",
                ChatColor.GRAY + "1x Diamond Leggings",
                ChatColor.GRAY + "1x Diamond Boots",
                ChatColor.GRAY + "―――――――――――――――――――――――――――",
                ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$850"
            ));


            ItemStack sword = getItem(Material.DIAMOND_SWORD, ChatColor.GREEN + "Diamond Sword", Arrays.asList(
                    ChatColor.GRAY + "―――――――――――――――――――――――――――",
                    ChatColor.GRAY + "1x Diamond Sword",
                    ChatColor.GRAY + "―――――――――――――――――――――――――――",
                    ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$100"
            ));

            ItemStack chestplate = getItem(Material.DIAMOND_CHESTPLATE, ChatColor.RED + "Diamond Chestplate", Arrays.asList(
                    ChatColor.GRAY + "―――――――――――――――――――――――――――",
                    ChatColor.GRAY + "1x Diamond Chestplate",
                    ChatColor.GRAY + "―――――――――――――――――――――――――――",
                    ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$275"
            ));


            ItemStack leggings = getItem(Material.DIAMOND_LEGGINGS, ChatColor.RED + "Diamond Leggings", Arrays.asList(
                    ChatColor.GRAY + "―――――――――――――――――――――――――――",
                    ChatColor.GRAY + "1x Diamond Leggings",
                    ChatColor.GRAY + "―――――――――――――――――――――――――――",
                    ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$250"
            ));


            ItemStack boots = getItem(Material.DIAMOND_BOOTS, ChatColor.RED + "Diamond Boots", Arrays.asList(
                    ChatColor.GRAY + "―――――――――――――――――――――――――――",
                    ChatColor.GRAY + "1x Diamond Boots",
                    ChatColor.GRAY + "―――――――――――――――――――――――――――",
                    ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$125"
            ));


            ItemStack enderperl = getItem(Material.ENDER_PEARL,ChatColor.GREEN + "Ender Pearl", Arrays.asList(
                    ChatColor.GRAY + "―――――――――――――――――――――――――――",
                    ChatColor.GRAY + "x1 Ender Pearl",
                    ChatColor.YELLOW + "- " + ChatColor.GREEN + "Right-click to buy x16 Ender Pearl",
                    ChatColor.GRAY + "―――――――――――――――――――――――――――",
                    ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$25"
            ));



            ItemStack steak = getItem(Material.COOKED_BEEF,ChatColor.GREEN + "Steak", Arrays.asList(
                    ChatColor.GRAY + "―――――――――――――――――――――――――――",
                    ChatColor.GRAY + "x16 Steak",
                    ChatColor.YELLOW + "- " + ChatColor.GREEN + "Right-click to buy x64 Steak",
                    ChatColor.GRAY + "―――――――――――――――――――――――――――",
                    ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "75"
            ));


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

            //Invisiblity  Splash при дамаге не снимать инвиз = 1250$
            //Poison 50$ 33 sec
            //Lesser Invisiblite Potion 5 00 min 100$ при дамаге снять инвиз
            //Antidote 150$ 5 sec удаляет все негативные баффы идает имунитет
            inventory.setItem(18, sword);
            inventory.setItem(14, invisibility);
            inventory.setItem(15, speedPotion);
            inventory.setItem(16, firePotion);
            inventory.setItem(23, healPotion);
            inventory.setItem(34, slownesspotion);
            inventory.setItem(37, boots);
            inventory.setItem(28, leggings);
            inventory.setItem(19, chestplate);
            inventory.setItem(20, fullset);
            inventory.setItem(21, enderperl);

            player.openInventory(inventory);
        }
        if (npc.getName().equals("Seller")) {
            // Create a new inventory with a size of 27 and a title
            Inventory inventory = Bukkit.createInventory(null, 9, "Seller Shop");

            ItemStack coal = createSellItem(Material.COAL, "Sell Coal", 10, player);
            ItemStack iron = createSellItem(Material.IRON_INGOT, "Sell Iron Ingot", 15, player);
            ItemStack diamond = createSellItem(Material.DIAMOND, "Sell Diamond", 50, player);
            ItemStack gold = createSellItem(Material.GOLD_INGOT, "Sell Gold Ingot", 20, player);

            inventory.setItem(7, coal);
            inventory.setItem(5, iron);
            inventory.setItem(1, diamond);
            inventory.setItem(3, gold);

            player.openInventory(inventory);
        }
    }

    public ItemStack getItem(Material material, String name, List<String> lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
    public ItemStack getPotion(Material material,String name,PotionType potionType,boolean extander,boolean upgraded, List<String> lore){
        ItemStack itemStack = new ItemStack(material);
        PotionMeta itemMeta = (PotionMeta) itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setBasePotionData(new PotionData(potionType,extander,upgraded));
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    private ItemStack createSellItem(Material material, String displayName, int price, Player player) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.BLUE + displayName);
        int itemCount = 0;
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack != null && itemStack.getType() == material) {
                itemCount += itemStack.getAmount();
            }
        }
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
        lore.add(ChatColor.GRAY + "Left click to sell 1 x " + displayName + " for " + price + "$");
        int totalPrice = price * itemCount;
        lore.add(ChatColor.GRAY + "Right click to sell " + itemCount + "x " + displayName + " for " + totalPrice + "$ ");
        lore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
