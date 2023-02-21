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

    public NPCInteract(Asd main) {
        this.main = main;
    }

    @EventHandler
    public void onNPCInteract(NPCRightClickEvent event) {
        NPC npc = event.getNPC();
        Player player = event.getClicker();
        Inventory plinv = player.getInventory();
        if (npc.getName().equals("Combat Shop")) {
            Inventory inventory = Bukkit.createInventory(null, 54, "Trader Menu");

            ItemStack speedPotion = new ItemStack(Material.POTION);
            PotionMeta speedPotionItemMeta = (PotionMeta) speedPotion.getItemMeta();
            speedPotionItemMeta.setDisplayName(ChatColor.AQUA + "Speed Potion II");
            speedPotionItemMeta.setBasePotionData(new PotionData(PotionType.SPEED, false, true));
            speedPotionItemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            List<String> speedlore = new ArrayList<>();
            speedlore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            speedlore.add(ChatColor.GRAY + "x1 Speed II Potion");
            speedlore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            speedlore.add(ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$10");
            speedPotionItemMeta.setLore(speedlore);
            speedPotion.setItemMeta(speedPotionItemMeta);

            ItemStack healPotion = new ItemStack(Material.SPLASH_POTION);
            PotionMeta healPotionItemMeta = (PotionMeta) healPotion.getItemMeta();
            healPotionItemMeta.setDisplayName(ChatColor.GREEN + "Health Potion II");
            healPotionItemMeta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL, false, true));
            healPotionItemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            List<String> heallore = new ArrayList<>();
            heallore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            heallore.add(ChatColor.GRAY + "x1 Health Splash Potion");
            heallore.add(ChatColor.YELLOW + "- " + ChatColor.GREEN + "Right-click to fill your inventory");
            heallore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            heallore.add(ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$5");
            healPotionItemMeta.setLore(heallore);
            healPotion.setItemMeta(healPotionItemMeta);

            ItemStack firePotion = new ItemStack(Material.POTION);
            PotionMeta firePotionItemMeta = (PotionMeta) firePotion.getItemMeta();
            firePotionItemMeta.setDisplayName(ChatColor.GOLD + "Fire Resistance Potion (3:00)");
            firePotionItemMeta.setBasePotionData(new PotionData(PotionType.FIRE_RESISTANCE, false, false));
            firePotionItemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            List<String> firelore = new ArrayList<>();
            firelore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            firelore.add(ChatColor.GRAY + "x1 Fire Resistance Potion");
            firelore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            firelore.add(ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$25");
            firePotionItemMeta.setLore(firelore);
            firePotion.setItemMeta(firePotionItemMeta);

            ItemStack slownesspotion = new ItemStack(Material.SPLASH_POTION);
            PotionMeta slownesspotionItemMeta = (PotionMeta) slownesspotion.getItemMeta();
            slownesspotionItemMeta.setDisplayName(ChatColor.DARK_GRAY + "Slowness Potion (1:07)");
            slownesspotionItemMeta.setBasePotionData(new PotionData(PotionType.SLOWNESS, false, false));
            slownesspotionItemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            List<String> slownesslore = new ArrayList<>();
            slownesslore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            slownesslore.add(ChatColor.GRAY + "x1 Slowness Splash Potion");
            slownesslore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            slownesslore.add(ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$50");

            slownesspotionItemMeta.setLore(slownesslore);
            slownesspotion.setItemMeta(slownesspotionItemMeta);

            ItemStack fullset = new ItemStack(Material.DIAMOND);
            ItemMeta fullsetmeta = fullset.getItemMeta();
            List<String> fullsetlore = new ArrayList<>();
            fullsetlore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            fullsetlore.add(ChatColor.GRAY + "1x Diamond Sword");
            fullsetlore.add(ChatColor.GRAY + "1x Diamond Helmet");
            fullsetlore.add(ChatColor.GRAY + "1x Diamond Chestplate");
            fullsetlore.add(ChatColor.GRAY + "1x Diamond Leggings");
            fullsetlore.add(ChatColor.GRAY + "1x Diamond Boots");
            fullsetlore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            fullsetlore.add(ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$850");
            fullsetmeta.setLore(fullsetlore);
            fullset.setItemMeta(fullsetmeta);


            ItemStack sword = getItem(Material.DIAMOND_SWORD, Arrays.asList(
                    ChatColor.GRAY + "1x Diamond Sword",
                    ChatColor.GRAY + "―――――――――――――――――――――――――――",
                    ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$100"
            ));


            ItemStack chestplate = getItem(Material.DIAMOND_CHESTPLATE, Arrays.asList(
                    ChatColor.GRAY + "―――――――――――――――――――――――――――",
                    ChatColor.GRAY + "1x Diamond Chestplate",
                    ChatColor.GRAY + "―――――――――――――――――――――――――――",
                    ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$275"
            ));


            ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
            ItemMeta leggingsmeta = leggings.getItemMeta();
            List<String> leggingslore = new ArrayList<>();
            leggingslore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            leggingslore.add(ChatColor.GRAY + "1x Diamond Leggings");
            leggingslore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            leggingslore.add(ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$250");
            leggingsmeta.setLore(leggingslore);
            leggings.setItemMeta(leggingsmeta);

            ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
            ItemMeta bootsmeta = boots.getItemMeta();
            bootsmeta.setDisplayName(ChatColor.DARK_RED + "Diamond Boots");
            List<String> bootslore = new ArrayList<>();
            bootslore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            bootslore.add(ChatColor.GRAY + "1x Diamond Boots");
            bootslore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            bootslore.add(ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$125");
            bootsmeta.setLore(bootslore);
            boots.setItemMeta(bootsmeta);

            ItemStack enderperl = new ItemStack(Material.ENDER_PEARL);
            ItemMeta enderperlmeta = enderperl.getItemMeta();
            List<String> enderperllore = new ArrayList<>();
            enderperllore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            enderperllore.add(ChatColor.GRAY + "x1 Ender Pearl");
            enderperllore.add(ChatColor.YELLOW + "- " + ChatColor.GREEN + "Right-click to buy x16 Ender Pearl");
            enderperllore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            enderperllore.add(ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "$25");
            enderperlmeta.setLore(enderperllore);
            enderperl.setItemMeta(enderperlmeta);

            ItemStack steak = new ItemStack(Material.COOKED_BEEF);
            ItemMeta steakmeta = steak.getItemMeta();
            List<String> steaklore = new ArrayList<>();
            steaklore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            steaklore.add(ChatColor.GRAY + "x16 Steak");
            steaklore.add(ChatColor.YELLOW + "- " + ChatColor.GREEN + "Right-click to buy x64 Steak");
            steaklore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            steaklore.add(ChatColor.YELLOW + "Price: " + ChatColor.GREEN + "75");
            steakmeta.setLore(steaklore);
            steak.setItemMeta(steakmeta);

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
            Inventory inventory = Bukkit.createInventory(null, 9, "Seller Menu");
            ItemStack coal = new ItemStack(Material.COAL, 1);
            ItemMeta coalmeta = coal.getItemMeta();
            coalmeta.setDisplayName(ChatColor.BLUE + "Sell Coal");
            int coalcount = 0;
            for (ItemStack itemStack : player.getInventory().getContents()) {
                if (itemStack != null && itemStack.getType() == Material.COAL) {
                    coalcount += itemStack.getAmount();
                }
            }
            List<String> coaltext = new ArrayList<>();
            coaltext.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            coaltext.add(ChatColor.GRAY + "Left click to sell 1 x Coal for 10$");
            int coalcena = 10 * coalcount;
            coaltext.add(ChatColor.GRAY + "Right click to sell " + coalcount + "x Coal for " + coalcena + "" + "$ ");
            coaltext.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            coalmeta.setLore(coaltext);
            coal.setItemMeta(coalmeta);
            ItemStack iron = new ItemStack(Material.IRON_INGOT, 1);
            ItemMeta ironmeta = iron.getItemMeta();
            ironmeta.setDisplayName(ChatColor.BLUE + "Sell Iron Ingot");
            int ironcount = 0;
            for (ItemStack itemStack : player.getInventory().getContents()) {
                if (itemStack != null && itemStack.getType() == Material.IRON_INGOT) {
                    ironcount += itemStack.getAmount();
                }
            }
            List<String> irontext = new ArrayList<>();
            irontext.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            irontext.add(ChatColor.GRAY + "Left click to sell 1 x Iron Ingot for 15$");
            int ironcena = 15 * ironcount;
            irontext.add(ChatColor.GRAY + "Right click to sell " + ironcount + "x Iron Ingot for " + ironcena + "$");
            irontext.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            ironmeta.setLore(irontext);
            iron.setItemMeta(ironmeta);
            ItemStack diamond = new ItemStack(Material.DIAMOND, 1);
            ItemMeta dimondmeta = diamond.getItemMeta();
            dimondmeta.setDisplayName(ChatColor.BLUE + "Sell Diamond");
            int diamondcount = 0;
            for (ItemStack itemStack : player.getInventory().getContents()) {
                if (itemStack != null && itemStack.getType() == Material.DIAMOND) {
                    diamondcount += itemStack.getAmount();
                }
            }
            List<String> diamonttext = new ArrayList<>();
            diamonttext.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            diamonttext.add(ChatColor.GRAY + "Left click to sell 1 x Diamond for 15$");
            int diamondcena = 10 * diamondcount;
            diamonttext.add(ChatColor.GRAY + "Right click to sell " + ironcount + "x Diamond for " + diamondcena + "$");
            diamonttext.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            diamonttext.add(ChatColor.GRAY + "ТУТ НЕ ВЕРНАЯ ЦЕНА НАДО ИЗМЕНИТЬ");
            dimondmeta.setLore(diamonttext);
            diamond.setItemMeta(dimondmeta);
            ItemStack gold = new ItemStack(Material.GOLD_INGOT, 1);
            ItemMeta goldmeta = gold.getItemMeta();
            goldmeta.setDisplayName(ChatColor.BLUE + "Sell Gold Ingot");
            int goldcount = 0;
            for (ItemStack itemStack : player.getInventory().getContents()) {
                if (itemStack != null && itemStack.getType() == Material.GOLD_INGOT) {
                    goldcount += itemStack.getAmount();
                }
            }
            List<String> goldtext = new ArrayList<>();
            goldtext.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            goldtext.add(ChatColor.GRAY + "Left click to sell 1 x Diamond for 15$");
            int goldcena = 10 * goldcount;
            goldtext.add(ChatColor.GRAY + "Right click to sell " + goldcount + "x Diamond for " + goldcena + "$");
            goldtext.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            goldtext.add(ChatColor.GRAY + "ТУТ НЕ ВЕРНАЯ ЦЕНА НАДО ИЗМЕНИТЬ");
            goldmeta.setLore(goldtext);
            gold.setItemMeta(goldmeta);
            inventory.setItem(1, coal);
            inventory.setItem(3, iron);
            inventory.setItem(5, diamond);
            inventory.setItem(7, gold);
            player.openInventory(inventory);
        }
    }

    public ItemStack getItem(Material material, List<String> lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
