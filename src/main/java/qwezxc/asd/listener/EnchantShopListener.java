package qwezxc.asd.listener;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
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
import qwezxc.asd.Asd;
import qwezxc.asd.Items.RegisterItems;
import qwezxc.asd.listener.InventoryHolders.CombatInventoryHolder;
import qwezxc.asd.listener.InventoryHolders.EnchanterInventoryHolder;
import qwezxc.asd.listener.enums.ArmorPrice;
import qwezxc.asd.listener.enums.SwordPrice;
import qwezxc.asd.util.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EnchantShopListener implements Listener {

    int[] DURABILITY_SLOTS = { 43, 34, 25, 16 };
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof EnchanterInventoryHolder)) return;

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();
        ItemStack item = event.getCurrentItem();

        if (item == null || item.getItemMeta().getDisplayName() == null || item.getType() == null || item.getItemMeta() == null) return;

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
            inventory.setItem(i, createRedPane());
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

    private void onDurabilityClick(Player player, ItemStack item){
        switch (item.getType()) {
            case GOLD_HELMET:
                ItemStack playerhelmet = player.getInventory().getHelmet();
                playerhelmet.addEnchantment(Enchantment.DURABILITY, 3);
                player.getInventory().setHelmet(playerhelmet);
                break;
            case GOLD_CHESTPLATE:
                ItemStack playerchest = player.getInventory().getChestplate();
                playerchest.addEnchantment(Enchantment.DURABILITY, 3);
                player.getInventory().setChestplate(playerchest);
                break;
            case GOLD_LEGGINGS:
                ItemStack playerleg = player.getInventory().getLeggings();
                playerleg.addEnchantment(Enchantment.DURABILITY, 3);
                player.getInventory().setLeggings(playerleg);
                break;
            case GOLD_BOOTS:
                ItemStack playerboots = player.getInventory().getBoots();
                playerboots.addEnchantment(Enchantment.DURABILITY, 3);
                player.getInventory().setBoots(playerboots);
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

                if (levelenchant == 4) {
                    player.closeInventory();
                }
                break;
        }
    }

    private void onSwordClick(Player player, int levelEnchant) {
        int swordPrice = SwordPrice.getSwordEnchantByInt(levelEnchant);
        Asd.getInstance().getPluginManager().getEconomy().removeBalance(player, swordPrice);

        Optional<ItemStack> diamondSword = Arrays.stream(player.getInventory().getContents())
                .filter(item -> item != null && item.getType() == Material.DIAMOND_SWORD)
                .findFirst();

        if (diamondSword.isPresent()) {
            ItemStack sword = diamondSword.get();
            if (levelEnchant == 1) {
                addEnchantment(sword, Enchantment.DAMAGE_ALL, levelEnchant);
            } else {
                Map<Enchantment, Integer> enchantments = sword.getEnchantments();
                if (enchantments.containsKey(Enchantment.DAMAGE_ALL) && enchantments.get(Enchantment.DAMAGE_ALL) == levelEnchant - 1) {
                    addEnchantment(sword, Enchantment.DAMAGE_ALL, levelEnchant);
                }
                if( levelEnchant == 5){
                    player.closeInventory();
                }
            }
        }
    }


    private void addEnchantment(ItemStack item, Enchantment enchantment, int level) {
        item.addEnchantment(enchantment, level);
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