package qwezxc.asd.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import qwezxc.asd.core.Economy;
import qwezxc.asd.listener.InventoryHolders.EnchanterInventoryHolder;
import qwezxc.asd.listener.enums.ArmorPrice;
import qwezxc.asd.listener.enums.SwordPrice;
import qwezxc.asd.util.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EnchantShopListener implements Listener {

    private final Economy economy;
    private static final int[] DURABILITY_SLOTS = {43, 34, 25, 16};

    public EnchantShopListener(Economy economy) {
        this.economy = economy;
    }

    @EventHandler
    public void onInventoryClick(@NotNull InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof EnchanterInventoryHolder)) {
            return;
        }

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();
        ItemStack item = event.getCurrentItem();

        switch (item.getType()) {
            case DIAMOND_HELMET:
            case DIAMOND_CHESTPLATE:
            case DIAMOND_LEGGINGS:
            case DIAMOND_BOOTS:
                handleArmorEnchantmentClick(player, inventory, item,event.getSlot());
                break;
            case GOLD_BOOTS:
            case GOLD_LEGGINGS:
            case GOLD_CHESTPLATE:
            case GOLD_HELMET:
                handleDurabilityClick(player, item, event.getSlot());
                inventory.setItem(event.getSlot(), createRedPane());
                break;
            case DIAMOND_SWORD:
                handleSwordEnchantmentClick(player, item);
                break;
            case FEATHER:
                handleFeatherEnchantmentClick(player);
                inventory.setItem(13, createRedPane());
                break;
            case DIAMOND_PICKAXE:
                handleDiamondPickaxeEnchantmentClick(player);
                inventory.setItem(14, createRedPane());
                break;
            default:break;
        }
    }

    private void handleArmorEnchantmentClick(Player player, Inventory inventory, ItemStack item,int slot) {
        int levelEnchant = Integer.parseInt(item.getItemMeta().getDisplayName().split(" ")[2]);

        if (economy.getBalance(player) < ArmorPrice.getArmorEnchantByInt(levelEnchant)) {
            player.sendMessage(Utils.color("&4&lНе досточно средств"));
            return;
        }

        economy.removeBalance(player, ArmorPrice.getArmorEnchantByInt(levelEnchant));

        if (item.getType() == Material.DIAMOND_HELMET) {
            addEnchantment(player.getInventory().getHelmet(), Enchantment.PROTECTION_ENVIRONMENTAL, levelEnchant);
        } else if (item.getType() == Material.DIAMOND_CHESTPLATE) {
            addEnchantment(player.getInventory().getChestplate(), Enchantment.PROTECTION_ENVIRONMENTAL, levelEnchant);
        } else if (item.getType() == Material.DIAMOND_LEGGINGS) {
            addEnchantment(player.getInventory().getLeggings(), Enchantment.PROTECTION_ENVIRONMENTAL, levelEnchant);
        } else if (item.getType() == Material.DIAMOND_BOOTS) {
            addEnchantment(player.getInventory().getBoots(), Enchantment.PROTECTION_ENVIRONMENTAL, levelEnchant);
        }

        if (levelEnchant == 4) {
            player.closeInventory();
        }

        inventory.setItem(slot, getItem(item, ChatColor.GREEN + "Buy Protection " + (levelEnchant + 1), Arrays.asList(
                ChatColor.GRAY + "―――――――――――――――――――――――――――",
                ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + ArmorPrice.getArmorEnchantByInt(levelEnchant + 1),
                ChatColor.GRAY + "―――――――――――――――――――――――――――"
        )));
    }

    private void handleDurabilityClick(Player player, ItemStack item,int slot) {
        int index = Arrays.binarySearch(DURABILITY_SLOTS, slot);
        ItemStack armor;
        Enchantment enchantment;

        switch (item.getType()) {
            case GOLD_HELMET:
                armor = player.getInventory().getHelmet();
                enchantment = Enchantment.DURABILITY;
                break;
            case GOLD_CHESTPLATE:
                armor = player.getInventory().getChestplate();
                enchantment = Enchantment.DURABILITY;
                break;
            case GOLD_LEGGINGS:
                armor = player.getInventory().getLeggings();
                enchantment = Enchantment.DURABILITY;
                break;
            case GOLD_BOOTS:
                armor = player.getInventory().getBoots();
                enchantment = Enchantment.DURABILITY;
                break;
            default:
                return;
        }

        if (economy.getBalance(player) < 300) {
            player.sendMessage(Utils.color("&4&lНе досточно средств"));
            return;
        }

        economy.removeBalance(player, 300);

        addEnchantment(armor, enchantment, 3);

        if (index >= 0) {
            item.setDurability((short) (item.getDurability() + 1));
        }
    }

    private void handleSwordEnchantmentClick(Player player, ItemStack item) {
        int levelEnchant = Integer.parseInt(item.getItemMeta().getDisplayName().split(" ")[2]);

        if (economy.getBalance(player) < SwordPrice.getSwordEnchantByInt(levelEnchant)) {
            player.sendMessage(Utils.color("&4&lНе досточно средств"));
            return;
        }

        economy.removeBalance(player, SwordPrice.getSwordEnchantByInt(levelEnchant));

        Optional<ItemStack> optionalSword = Arrays.stream(player.getInventory().getContents())
                .filter(sword -> sword != null && sword.getType() == Material.DIAMOND_SWORD)
                .findFirst();

        if (optionalSword.isPresent()) {
            ItemStack sword = optionalSword.get();
            Map<Enchantment, Integer> enchantments = sword.getEnchantments();

            if (levelEnchant == 1) {
                addEnchantment(sword, Enchantment.DAMAGE_ALL, levelEnchant);
            } else {
                int previousLevel = levelEnchant - 1;

                if (enchantments.containsKey(Enchantment.DAMAGE_ALL) && enchantments.get(Enchantment.DAMAGE_ALL) == previousLevel) {
                    addEnchantment(sword, Enchantment.DAMAGE_ALL, levelEnchant);
                }
            }

            if (levelEnchant == 5) {
                player.closeInventory();
            }

            item.setItemMeta(sword.getItemMeta());
            player.getInventory().remove(sword);
            player.getInventory().addItem(item);
        }
    }

    private void handleFeatherEnchantmentClick(Player player) {
        if (economy.getBalance(player) < 500) {
            player.sendMessage(Utils.color("&4&lНе досточно средств"));
            return;
        }

        economy.removeBalance(player, 500);

        ItemStack boots = player.getInventory().getBoots();
        boots.addEnchantment(Enchantment.PROTECTION_FALL, 4);
        player.getInventory().setBoots(boots);

        player.updateInventory();
    }

    private void handleDiamondPickaxeEnchantmentClick(Player player) {
        if (economy.getBalance(player) < 500) {
            player.sendMessage(Utils.color("&4&lНе досточно средств"));
            return;
        }

        economy.removeBalance(player, 500);

        ItemStack diamondPickaxe = null;

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == Material.DIAMOND_PICKAXE) {
                diamondPickaxe = item;
                break;
            }
        }

        if (diamondPickaxe != null) {
            addEnchantment(diamondPickaxe, Enchantment.DIG_SPEED, 3);

            player.updateInventory();
        }
    }

    private void addEnchantment(ItemStack item, Enchantment enchantment, int level) {
        if (item != null) {
            item.addEnchantment(enchantment, level);
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


/*
* bay me optimized code:
public class EnchantShopListener implements Listener {

    private final Economy economy;
    private static final int[] DURABILITY_SLOTS = {43, 34, 25, 16};
    private static final Map<Material, BiConsumer<EnchantShopListener, InventoryClickEvent>> ITEM_CLICK_HANDLERS = new HashMap<>();

    static {
        ITEM_CLICK_HANDLERS.put(Material.DIAMOND_HELMET, EnchantShopListener::handleArmorEnchantmentClick);
        ITEM_CLICK_HANDLERS.put(Material.DIAMOND_CHESTPLATE, EnchantShopListener::handleArmorEnchantmentClick);
        ITEM_CLICK_HANDLERS.put(Material.DIAMOND_LEGGINGS, EnchantShopListener::handleArmorEnchantmentClick);
        ITEM_CLICK_HANDLERS.put(Material.DIAMOND_BOOTS, EnchantShopListener::handleArmorEnchantmentClick);
        ITEM_CLICK_HANDLERS.put(Material.GOLD_HELMET, EnchantShopListener::handleDurabilityClick);
        ITEM_CLICK_HANDLERS.put(Material.GOLD_CHESTPLATE, EnchantShopListener::handleDurabilityClick);
        ITEM_CLICK_HANDLERS.put(Material.GOLD_LEGGINGS, EnchantShopListener::handleDurabilityClick);
        ITEM_CLICK_HANDLERS.put(Material.GOLD_BOOTS, EnchantShopListener::handleDurabilityClick);
        ITEM_CLICK_HANDLERS.put(Material.DIAMOND_SWORD, EnchantShopListener::handleSwordEnchantmentClick);
        ITEM_CLICK_HANDLERS.put(Material.FEATHER, EnchantShopListener::handleFeatherEnchantmentClick);
        ITEM_CLICK_HANDLERS.put(Material.DIAMOND_PICKAXE, EnchantShopListener::handleDiamondPickaxeEnchantmentClick);
    }

    public EnchantShopListener(Economy economy) {
        this.economy = economy;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof EnchanterInventoryHolder)) {
            return;
        }

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();
        ItemStack item = event.getCurrentItem();

        ITEM_CLICK_HANDLERS.getOrDefault(item.getType(), (l, e) -> {}).accept(this, event);
    }

    private void handleArmorEnchantmentClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();
        ItemStack item = event.getCurrentItem();
        int slot = event.getSlot();
        int levelEnchant = Integer.parseInt(item.getItemMeta().getDisplayName().split(" ")[2]);

        if (economy.getBalance(player) < ArmorPrice.getArmorEnchantByInt(levelEnchant)) {
            player.sendMessage(Utils.color("&4&lНе досточно средств"));
            return;
        }

        economy.removeBalance(player, ArmorPrice.getArmorEnchantByInt(levelEnchant));
        ItemStack armor = null;
        Enchantment enchantment = Enchantment.PROTECTION_ENVIRONMENTAL;
        if (item.getType() == Material.DIAMOND_HELMET) {
            armor = player.getInventory().getHelmet();
        } else if (item.getType() == Material.DIAMOND_CHESTPLATE) {
            armor = player.getInventory().getChestplate();
        } else if (item.getType() == Material.DIAMOND_LEGGINGS) {
            armor = player.getInventory().getLeggings();
        } else if (item.getType() == Material.DIAMOND_BOOTS) {
            armor = player.getInventory().getBoots();
        }

        if (armor != null) {
            addEnchantment(armor, enchantment, levelEnchant);
        }

        if (levelEnchant == 4) {
            player.closeInventory();
        }

        updateItem(item, name, Arrays.asList(
                ChatColor.GRAY + "―――――――――――――――――――――――――――",
                ChatColor.YELLOW + "Цена: " + ChatColor.GREEN + ArmorPrice.ArmorEnchant.getArmorEnchantByInt(levelEnchant + 1),
                ChatColor.GRAY + "―――――――――――――――――――――――――――"
        ));
        inventory.setItem(slot, item);
    }

    private void handleDurabilityClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        int slot = event.getSlot();
        int index = Arrays.binarySearch(DURABILITY_SLOTS, slot);
        Enchantment enchantment = Enchantment.DURABILITY;
        ItemStack armor = null;

        if (item.getType() == Material.GOLD_HELMET) {
            armor = player.getInventory().getHelmet();
        } else if (item.getType() == Material.GOLD_CHESTPLATE) {
            armor = player.getInventory().getChestplate();
        } else if (item.getType() == Material.GOLD_LEGGINGS) {
            armor = player.getInventory().getLeggings();
        } else if (item.getType() == Material.GOLD_BOOTS) {
            armor = player.getInventory().getBoots();
        }

        if (economy.getBalance(player) < 300) {
            player.sendMessage(Utils.color("&4&lНе досточно средств"));
            return;
        }

        economy.removeBalance(player, 300);

        if (armor != null) {
            addEnchantment(armor, enchantment, 3);
        }

        if (index >= 0) {
            item.setDurability((short) (item.getDurability() + 1));
        }

        updateItem(item, ChatColor.GREEN + "Buy Durability", null);
        event.getInventory().setItem(slot, item);
    }

    private void handleSwordEnchantmentClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        int levelEnchant = Integer.parseInt(item.getItemMeta().getDisplayName().split(" ")[2]);

        if (economy.getBalance(player) < SwordPrice.getSwordEnchantByInt(levelEnchant)) {
            player.sendMessage(Utils.color("&4&lНе досточно средств"));
            return;
        }

        economy.removeBalance(player, SwordPrice.getSwordEnchantByInt(levelEnchant));

        Optional<ItemStack> optionalSword = Arrays.stream(player.getInventory().getContents())
                .filter(sword -> sword != null && sword.getType() == Material.DIAMOND_SWORD)
                .findFirst();

        if (optionalSword.isPresent()) {
            ItemStack sword = optionalSword.get();
            Map<Enchantment, Integer> enchantments = sword.getEnchantments();

            if (levelEnchant == 1) {
                addEnchantment(sword, Enchantment.DAMAGE_ALL, levelEnchant);
            } else {
                int previousLevel = levelEnchant - 1;

                if (enchantments.containsKey(Enchantment.DAMAGE_ALL) && enchantments.get(Enchantment.DAMAGE_ALL) == previousLevel) {
                    addEnchantment(sword, Enchantment.DAMAGE_ALL, levelEnchant);
                }
            }

            if (levelEnchant == 5) {
                player.closeInventory();
            }

            item.setItemMeta(sword.getItemMeta());
            player.getInventory().remove(sword);
            player.getInventory().addItem(item);
        }
    }

    private void handleFeatherEnchantmentClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (economy.getBalance(player) < 500) {
            player.sendMessage(Utils.color("&4&lНе досточно средств"));
            return;
        }

        economy.removeBalance(player, 500);

        ItemStack boots = player.getInventory().getBoots();
        boots.addEnchantment(Enchantment.PROTECTION_FALL, 4);
        player.getInventory().setBoots(boots);

        player.updateInventory();
    }

    private void handleDiamondPickaxeEnchantmentClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (economy.getBalance(player) < 500) {
            player.sendMessage(Utils.color("&4&lНе досточно средств"));
            return;
        }

        economy.removeBalance(player, 500);

        ItemStack diamondPickaxe = null;

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == Material.DIAMOND_PICKAXE) {
                diamondPickaxe = item;
                break;
            }
        }

        if (diamondPickaxe != null) {
            addEnchantment(diamondPickaxe, Enchantment.DIG_SPEED, 3);
            player.updateInventory();
        }
    }

    private void addEnchantment(ItemStack item, Enchantment enchantment, int level) {
        item.addEnchantment(enchantment, level);
    }

    private void updateItem(ItemStack item, String name, List<String> lore) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
    }
}*/