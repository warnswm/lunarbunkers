package qwezxc.asd.listener.InventoryHolders;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import qwezxc.asd.Items.RegisterItems;
import qwezxc.asd.listener.enums.ArmorPrice;
import qwezxc.asd.listener.enums.SwordPrice;
import qwezxc.asd.util.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EnchanterInventoryHolder implements InventoryHolder {

    Player player; //test
    String ENCHANT_SHOP_TITLE = ChatColor.GREEN + "Enchant Shop";
    ItemStack RED_PANE = createRedPane();
    ItemStack FEATHER_FALLING_4 = new ItemStack(Material.FEATHER);
    int[] DURABILITY_SLOTS = { 43, 34, 25, 16 };
    ItemStack[] DURABILITY_ITEMS = {
            RegisterItems.enchantboots,
            RegisterItems.enchantleggings,
            RegisterItems.enchantchestplate,
            RegisterItems.enchanthelmet
    };
    int[] ARMOR_SLOTS = { 37, 28, 19, 10 };

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, ENCHANT_SHOP_TITLE);

        showArmorContents(inventory);

        showPickaxe(inventory);

        showSword(inventory);

        return inventory;
    }

    private void showArmorContents(Inventory inventory) {
        ItemStack[] armorContents = player.getInventory().getArmorContents();
        for (int i = 0; i < armorContents.length; i++) {
            ItemStack armor = armorContents[i];

            if (armor == null) {
                inventory.setItem(ARMOR_SLOTS[i], RED_PANE);
                inventory.setItem(DURABILITY_SLOTS[i], RED_PANE);
                inventory.setItem(13, RED_PANE);
                continue;
            }

            ItemStack playerArmor = new ItemStack(armor.getType(), 1);
            Map<Enchantment, Integer> enchantments = armor.getEnchantments();
            int protectionLevel = enchantments.getOrDefault(Enchantment.PROTECTION_ENVIRONMENTAL, 0);
            setArmorEnchantItem(playerArmor, protectionLevel);

            if (enchantments.containsKey(Enchantment.DURABILITY)) {
                inventory.setItem(DURABILITY_SLOTS[i], RED_PANE);
            } else {
                inventory.setItem(DURABILITY_SLOTS[i], DURABILITY_ITEMS[i]);
            }

            if (armor.getType() == Material.DIAMOND_BOOTS && enchantments.containsKey(Enchantment.PROTECTION_FALL) && enchantments.get(Enchantment.PROTECTION_FALL) == 4) {
                inventory.setItem(13, RED_PANE);
            } else if (armor.getType() == Material.DIAMOND_BOOTS) {
                getItem(FEATHER_FALLING_4, ChatColor.GREEN + "Buy Feather Falling 4", Arrays.asList(
                        ChatColor.GRAY + "―――――――――――――――――――――――――――",
                        ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + "$500",
                        ChatColor.GRAY + "―――――――――――――――――――――――――――"
                ));
                inventory.setItem(13, FEATHER_FALLING_4);
            }

            inventory.setItem(ARMOR_SLOTS[i], playerArmor);
        }
    }
    private void showPickaxe(Inventory inventory) {
        ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);

        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null) continue;
            if (item.getType() == Material.DIAMOND_PICKAXE) {
                Map<Enchantment, Integer> enchantments = item.getEnchantments();
                if (enchantments.containsKey(Enchantment.DIG_SPEED)) {
                    inventory.setItem(14, createRedPane());
                } else {
                    getItem(pickaxe, ChatColor.GREEN + "Buy Efficiency 3", Arrays.asList(
                            ChatColor.GRAY + "―――――――――――――――――――――――――――",
                            ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + "$500",
                            ChatColor.GRAY + "―――――――――――――――――――――――――――"
                    ));
                    inventory.setItem(14, pickaxe);
                    break;
                }
            }
        }
    }

    private void showSword(Inventory inventory) {
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);

        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null) continue;
            if (item.getType() == Material.DIAMOND_SWORD) {
                Map<Enchantment, Integer> enchantments = item.getEnchantments();
                int sharpnessLevel = enchantments.getOrDefault(Enchantment.DAMAGE_ALL, 0);
                if (sharpnessLevel == 5) {
                    getItem(sword, ChatColor.GREEN + "Max Sharpness ", Arrays.asList(
                            ChatColor.GRAY + "―――――――――――――――――――――――――――",
                            ChatColor.RED + Utils.color("У вас максимальный лвл зачарования"),
                            ChatColor.GRAY + "―――――――――――――――――――――――――――"
                    ));
                } else {
                    getItem(sword, ChatColor.GREEN + "Buy Sharpness " + (sharpnessLevel + 1), Arrays.asList(
                            ChatColor.GRAY + "―――――――――――――――――――――――――――",
                            ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + "$" + SwordPrice.getSwordEnchantByInt(sharpnessLevel + 1),
                            ChatColor.GRAY + "―――――――――――――――――――――――――――"
                    ));
                }
                inventory.setItem(12, sword);
                break;
            }
        }
    }




    private void setArmorEnchantItem(ItemStack playerArmor, int protectionLevel) {
        String name = ChatColor.GREEN + "Max Protection ";
        List<String> lore = Arrays.asList(
                ChatColor.GRAY + "―――――――――――――――――――――――――――",
                ChatColor.RED + Utils.color("У вас максимальный лвл зачарования"),
                ChatColor.GRAY + "―――――――――――――――――――――――――――"
        );
        if (protectionLevel != 4) {
            name = ChatColor.GREEN + "Buy Protection " + (protectionLevel + 1);
            lore = Arrays.asList(
                    ChatColor.GRAY + "―――――――――――――――――――――――――――",
                    ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + ArmorPrice.getArmorEnchantByInt(protectionLevel + 1),
                    ChatColor.GRAY + "―――――――――――――――――――――――――――"
            );
        }
        getItem(playerArmor, name, lore);
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
