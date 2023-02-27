package qwezxc.asd.listener;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import qwezxc.asd.Asd;
import qwezxc.asd.Items.RegisterItems;
import qwezxc.asd.util.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
enum ArmorPrice {
    ProtectionI(1, 125),
    ProtectionII(2, 375),
    ProtectionIII(3, 415),
    ProtectionIV(4, 475);
    int enchantlevel;
    int price;

    public static int getArmorEnchantByInt(int enchantlevel) {

        for (ArmorPrice armorPrice : values())
            if (armorPrice.getEnchantlevel() == enchantlevel)
                return armorPrice.getPrice();

        return 0;

    }


}

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
enum SwordPrice {
    ShapnessI(1, 125),
    ShapnessII(2, 175),
    ShapnessIII(3, 235),
    ShapnessIV(4, 310),
    ShapnessV(5, 435);
    int enchantlevel;
    int price;

    public static int getSwordEnchantByInt(int enchantlevel) {

        for (SwordPrice swordPrice : values())
            if (swordPrice.getEnchantlevel() == enchantlevel)
                return swordPrice.getPrice();

        return 0;

    }

}

public class EnchantShopListener implements Listener {

    ItemStack[] enchantItems = {
            RegisterItems.enchantboots,
            RegisterItems.enchantleggings,
            RegisterItems.enchantchestplate,
            RegisterItems.enchanthelmet
    };

    int[] enchantSlots = {43, 34, 25, 16};

    @EventHandler
    public void onNPCInteract(NPCRightClickEvent event) {
        NPC npc = event.getNPC();
        Player player = event.getClicker();
        if (player.getGameMode() != GameMode.SURVIVAL) return;
        if (!npc.getName().equals("Es")) return;
        Inventory chestInventory = Bukkit.createInventory(null, 54, ChatColor.GREEN + "Enchant Shop");
        ItemStack[] armorContents = player.getInventory().getArmorContents();
        boolean hasSword = false;
        boolean hasPickaxe = false;
        for (int i = 0; i < armorContents.length; i++) {
            ItemStack armor = armorContents[i];
            if (armor == null) {
                chestInventory.setItem(i == 0 ? 37 : i == 1 ? 28 : i == 2 ? 19 : 10, createRedPane());
                chestInventory.setItem(i == 0 ? 43 : i == 1 ? 34 : i == 2 ? 25 : 16, createRedPane());
                chestInventory.setItem(13, createRedPane());
                continue;
            }
            ItemStack playerArmor = new ItemStack(armor.getType(), 1);
            ItemStack playerboots = new ItemStack(Material.FEATHER);
            Map<Enchantment, Integer> enchantments = armor.getEnchantments();
            int protectionLevel = enchantments.getOrDefault(Enchantment.PROTECTION_ENVIRONMENTAL, 0);
            if (protectionLevel == 4) {
                getItem(playerArmor, ChatColor.GREEN + "Max Protection ", Arrays.asList(
                        ChatColor.GRAY + "―――――――――――――――――――――――――――",
                        ChatColor.RED + Utils.color("У вас максимальный лвл зачарования"),
                        ChatColor.GRAY + "―――――――――――――――――――――――――――"
                ));
            } else {
                getItem(playerArmor, ChatColor.GREEN + "Buy Protection " + (protectionLevel + 1), Arrays.asList(
                        ChatColor.GRAY + "―――――――――――――――――――――――――――",
                        ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + ArmorPrice.getArmorEnchantByInt(protectionLevel + 1),
                        ChatColor.GRAY + "―――――――――――――――――――――――――――"
                ));
            }
            if (enchantments.containsKey(Enchantment.DURABILITY)) {
                chestInventory.setItem(i == 0 ? 43 : i == 1 ? 34 : i == 2 ? 25 : 16, createRedPane());
            } else {
                chestInventory.setItem(enchantSlots[i], enchantItems[i]);
            }
            if (armor.getType() == Material.DIAMOND_BOOTS && enchantments.containsKey(Enchantment.PROTECTION_FALL) && enchantments.get(Enchantment.PROTECTION_FALL) == 4) {
                chestInventory.setItem(13, createRedPane());
            } else if (armor.getType() == Material.DIAMOND_BOOTS) {
                // Boots without Feather Falling 4 enchantment
                getItem(playerboots, ChatColor.GREEN + "Buy Feather Falling 4", Arrays.asList(
                        ChatColor.GRAY + "―――――――――――――――――――――――――――",
                        ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + "$500",
                        ChatColor.GRAY + "―――――――――――――――――――――――――――"
                ));
                chestInventory.setItem(13, playerboots);
            }

            chestInventory.setItem(i == 0 ? 37 : i == 1 ? 28 : i == 2 ? 19 : 10, playerArmor);
        }
        ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null) continue;
            if (item.getType() == Material.DIAMOND_PICKAXE) {
                Map<Enchantment, Integer> enchantments = item.getEnchantments();
                if (enchantments.containsKey(Enchantment.DIG_SPEED)) {
                    chestInventory.setItem(14, createRedPane());
                } else {
                    getItem(pickaxe, ChatColor.GREEN + "Buy Efficiency 3", Arrays.asList(
                            ChatColor.GRAY + "―――――――――――――――――――――――――――",
                            ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + "$500",
                            ChatColor.GRAY + "―――――――――――――――――――――――――――"
                    ));
                    hasPickaxe = true;
                    chestInventory.setItem(14, pickaxe);
                }
            }
            else if (item.getType() == Material.DIAMOND_SWORD) {
            Map<Enchantment, Integer> enchantments = item.getEnchantments();
            int sharpnessLevel = enchantments.getOrDefault(Enchantment.DAMAGE_ALL, 0);
                if (sharpnessLevel == 5) {
                    getItem(sword, ChatColor.GREEN + "Max Sharpness ", Arrays.asList(
                            ChatColor.GRAY + "―――――――――――――――――――――――――――",
                            ChatColor.RED + Utils.color("У вас максимальный лвл зачарования"),
                            ChatColor.GRAY + "―――――――――――――――――――――――――――"
                    ));
                } else
                    getItem(sword, ChatColor.GREEN + "Buy Sharpness " + (sharpnessLevel + 1), Arrays.asList(
                            ChatColor.GRAY + "―――――――――――――――――――――――――――",
                            ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + "$" + SwordPrice.getSwordEnchantByInt(sharpnessLevel + 1),
                            ChatColor.GRAY + "―――――――――――――――――――――――――――"
                    ));

                hasSword = true;
                chestInventory.setItem(12, sword);
            }
        }
        if (!hasSword)
            chestInventory.setItem(12, createRedPane());
        if (!hasPickaxe)
            chestInventory.setItem(14, createRedPane());

        player.openInventory(chestInventory);
    }



    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        if (!inventory.getTitle().equals(ChatColor.GREEN + "Enchant Shop")) return;

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if (item == null || item.getItemMeta().getDisplayName() == null || item.getType() == null) return;

        if (item.getType() == Material.DIAMOND_BOOTS || item.getType() == Material.DIAMOND_HELMET || item.getType() == Material.DIAMOND_CHESTPLATE || item.getType() == Material.DIAMOND_LEGGINGS) {
            int levelenchant = Integer.parseInt(item.getItemMeta().getDisplayName().split(" ")[2]);
            if (Asd.getInstance().getPluginManager().getEconomy().getBalance(player) < ArmorPrice.getArmorEnchantByInt(levelenchant)) {
                player.sendMessage(Utils.color("&4&lНе досточно средств"));
                return;
            }

            onProtectionClick(player,item,levelenchant);
            event.setCurrentItem(getItem(item, ChatColor.GREEN + "Buy Protection " + (levelenchant + 1), Arrays.asList(
                    ChatColor.GRAY + "―――――――――――――――――――――――――――",
                    ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + ArmorPrice.getArmorEnchantByInt(levelenchant + 1),
                    ChatColor.GRAY + "―――――――――――――――――――――――――――"
            )));
        }
        else if (item.getType() == Material.GOLD_BOOTS || item.getType() == Material.GOLD_HELMET || item.getType() == Material.GOLD_CHESTPLATE || item.getType() == Material.GOLD_LEGGINGS) {
            if(Asd.getInstance().getPluginManager().getEconomy().getBalance(player) < 300) {
                player.sendMessage(Utils.color("&4&lНе досточно средств"));
                return;
            }

            onDurabilityClick(player,item);
            int i = event.getSlot();
            inventory.setItem(i == 0 ? 43 : i == 1 ? 34 : i == 2 ? 25 : 16, createRedPane());
        }
        else if(item.getType() == Material.DIAMOND_SWORD){
            int levelenchant = Integer.parseInt(item.getItemMeta().getDisplayName().split(" ")[2]);
            if (Asd.getInstance().getPluginManager().getEconomy().getBalance(player) < SwordPrice.getSwordEnchantByInt(levelenchant)) {
                player.sendMessage(Utils.color("&4&lНе досточно средств"));
                return;
            }
            onSwordClick(player,levelenchant);
            event.setCurrentItem(getItem(item, ChatColor.GREEN + "Buy Sharpness " + (levelenchant + 1), Arrays.asList(
                    ChatColor.GRAY + "―――――――――――――――――――――――――――",
                    ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + SwordPrice.getSwordEnchantByInt(levelenchant + 1),
                    ChatColor.GRAY + "―――――――――――――――――――――――――――"
            )));
        }
        else if (item.getType() == Material.FEATHER){
            if (Asd.getInstance().getPluginManager().getEconomy().getBalance(player) < 500) {
                player.sendMessage(Utils.color("&4&lНе досточно средств"));
                return;
            }
            ItemStack playerboots = player.getInventory().getBoots();
            playerboots.addEnchantment(Enchantment.PROTECTION_FALL,4);
            inventory.setItem(13,createRedPane());
        }
        else if (item.getType() == Material.DIAMOND_PICKAXE) {
            if (Asd.getInstance().getPluginManager().getEconomy().getBalance(player) < 500) {
                player.sendMessage(Utils.color("&4&lНе досточно средств"));
                return;
            }
            for (ItemStack playeritem : player.getInventory().getContents()) {
                if (playeritem != null && playeritem.getType() == Material.DIAMOND_PICKAXE) {
                    playeritem.addEnchantment(Enchantment.DIG_SPEED, 3);
                    inventory.setItem(14,createRedPane());
                    break;
                }
            }
        }

    }

    private void onSwordClick(Player player,int levelenchant){
        Asd.getInstance().getPluginManager().getEconomy().removeBalance(player, SwordPrice.getSwordEnchantByInt(levelenchant));
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == Material.DIAMOND_SWORD) {
                if (levelenchant == 1){
                    item.addEnchantment(Enchantment.DAMAGE_ALL,levelenchant);
                    break;
                }
                Map<Enchantment, Integer> enchantments = item.getEnchantments();
                if (enchantments.containsKey(Enchantment.DAMAGE_ALL) && enchantments.get(Enchantment.DAMAGE_ALL) == levelenchant-1) {
                    item.addEnchantment(Enchantment.DAMAGE_ALL,levelenchant);
                    break;
                }
            }
        }

    }

    private void onDurabilityClick(Player player, ItemStack item){
        switch (item.getType()) {
            case GOLD_HELMET:
                ItemStack playerhelmet = player.getInventory().getHelmet();
                playerhelmet.addEnchantment(Enchantment.DURABILITY, 3);
                player.getInventory().setHelmet(playerhelmet);
                player.closeInventory();
                break;
            case GOLD_CHESTPLATE:
                ItemStack playerchest = player.getInventory().getChestplate();
                playerchest.addEnchantment(Enchantment.DURABILITY, 3);
                player.getInventory().setChestplate(playerchest);
                player.closeInventory();
                break;
            case GOLD_LEGGINGS:
                ItemStack playerleg = player.getInventory().getLeggings();
                playerleg.addEnchantment(Enchantment.DURABILITY, 3);
                player.getInventory().setLeggings(playerleg);
                player.closeInventory();
                break;
            case GOLD_BOOTS:
                ItemStack playerboots = player.getInventory().getBoots();
                playerboots.addEnchantment(Enchantment.DURABILITY, 3);
                player.getInventory().setBoots(playerboots);
                player.closeInventory();
                break;
        }
    }
    private void onProtectionClick(Player player, ItemStack item,int levelenchant){
        switch (item.getType()) {
            case DIAMOND_HELMET:
                Asd.getInstance().getPluginManager().getEconomy().removeBalance(player, ArmorPrice.getArmorEnchantByInt(levelenchant));
                ItemStack playerhelmet = player.getInventory().getHelmet();
                playerhelmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, levelenchant);
                player.getInventory().setHelmet(playerhelmet);
                if(levelenchant == 4){
                    player.closeInventory();
                }
                break;
            case DIAMOND_CHESTPLATE:
                Asd.getInstance().getPluginManager().getEconomy().removeBalance(player, ArmorPrice.getArmorEnchantByInt(levelenchant));
                ItemStack playerchest = player.getInventory().getChestplate();
                playerchest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, levelenchant);
                player.getInventory().setChestplate(playerchest);
                if(levelenchant == 4){
                    player.closeInventory();
                }
                break;
            case DIAMOND_LEGGINGS:
                Asd.getInstance().getPluginManager().getEconomy().removeBalance(player, ArmorPrice.getArmorEnchantByInt(levelenchant));
                ItemStack playerleg = player.getInventory().getLeggings();
                playerleg.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, levelenchant);
                player.getInventory().setLeggings(playerleg);

                if(levelenchant == 4){
                    player.closeInventory();
                }
                break;
            case DIAMOND_BOOTS:
                Asd.getInstance().getPluginManager().getEconomy().removeBalance(player, ArmorPrice.getArmorEnchantByInt(levelenchant));
                ItemStack playerboots = player.getInventory().getBoots();
                playerboots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, levelenchant);
                player.getInventory().setBoots(playerboots);

                if(levelenchant == 4){
                    player.closeInventory();
                }
                break;
        }
    }

    private ItemStack getItem(ItemStack itemStack, String name, List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    private ItemStack createRedPane() {
        ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
        ItemMeta meta = pane.getItemMeta();
        meta.setDisplayName(Utils.color("&4&lНедоступно"));
        pane.setItemMeta(meta);
        return pane;
    }


}