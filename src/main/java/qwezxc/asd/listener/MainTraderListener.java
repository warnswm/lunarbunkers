package qwezxc.asd.listener;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import qwezxc.asd.Asd;
import qwezxc.asd.listener.InventoryHolders.CombatInventoryHolder;

public class MainTraderListener implements Listener {

    @EventHandler
    public void onInventoryClick(@NotNull InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof CombatInventoryHolder)) return;

        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        int availableSlots = player.getInventory().firstEmpty();
        if (availableSlots == -1) {
            player.sendMessage("Inventory is full");
            return;
        }

        net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = nmsItem.getTag();

        switch (item.getType()) {
            case POTION:
            case SPLASH_POTION:
                handlePotionPurchase(player, item, tag,event.getClick());
                break;
            case DIAMOND_SWORD:
                buy(player, new ItemStack(Material.DIAMOND_SWORD, 1), 100);
                break;
            case DIAMOND_BOOTS:
                purchaseArmorItem(player, item, Material.DIAMOND_BOOTS, 125, "алмазные ботинки");
                break;
            case DIAMOND_LEGGINGS:
                purchaseArmorItem(player, item, Material.DIAMOND_LEGGINGS, 250, "алмазные поножи");
                break;
            case DIAMOND_CHESTPLATE:
                purchaseArmorItem(player, item, Material.DIAMOND_CHESTPLATE, 275, "алмазный нагрудник");
                break;
            case DIAMOND_HELMET:
                purchaseArmorItem(player, item, Material.DIAMOND_HELMET, 275, "алмазный шлем");
                break;
            case DIAMOND:
                purchaseFullArmorSet(player, item, 850, "фулл сет и меч");
                break;
            case ENDER_PEARL:
                handleEnderPearlPurchase(player, item,event.getClick());
                break;
            case COOKED_BEEF:
                handleFoodPurchase(player, item,event.getClick());
                break;
        }
    }

    private void handlePotionPurchase(Player player, ItemStack item, NBTTagCompound tag, ClickType event) {
        if (tag.hasKey(ChatColor.AQUA + "Speed Potion II (1:30)")) {
            buy(player, new ItemStack(Material.POTION, 1, (short) 8226), 10);
        } else if (tag.hasKey(ChatColor.GREEN + "Health Potion II")) {
            handleHealthPotionPurchase(player, item,event);
        } else if (tag.hasKey(ChatColor.GOLD + "Fire Resistance Potion (3:00)")) {
            buy(player, new ItemStack(Material.POTION, 1, (short) 8195), 25);
        } else if (tag.hasKey(ChatColor.DARK_GRAY + "Slowness Potion (1:07)")) {
            buy(player, new ItemStack(Material.POTION, 1, (short) 16394), 25);
        }
    }

    private void handleHealthPotionPurchase(Player player, ItemStack item,ClickType event) {
        if (event == ClickType.RIGHT) {
            int count = 0;
            for (ItemStack i : player.getInventory().getStorageContents()) {
                if (i == null || i.getType() == Material.AIR) {
                    count++;
                }
            }
            if (!Asd.getInstance().getPluginManager().getEconomy().hasEnoughMoney(player, 5 * count)) {
                player.sendMessage(ChatColor.RED + "У вас не дстаточно денег чтобы заполнить инвентарь зельем");
                return;
            }
            for (int i = count; i > 0; i--) {
                buy(player, new ItemStack(Material.POTION, 1, (short) 16421), 5);
            }
        } else {
            buy(player, new ItemStack(Material.POTION, 1, (short) 16421), 5);
        }
    }

    private void handleEnderPearlPurchase(Player player, ItemStack item,ClickType event) {
        if (event == ClickType.RIGHT) {
            if (!Asd.getInstance().getPluginManager().getEconomy().hasEnoughMoney(player, 25 * 16)) {
                player.sendMessage(ChatColor.RED + "У вас не дстаточно денег чтобы купить это");
                return;
            }
            Asd.getInstance().getPluginManager().getEconomy().removeBalance(player, 25 * 16);
            player.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 16));
        } else {
            buy(player, new ItemStack(Material.ENDER_PEARL, 1), 25);
        }
    }

    private void handleFoodPurchase(Player player, ItemStack item,ClickType event) {
        if (event == ClickType.RIGHT) {
            if (!Asd.getInstance().getPluginManager().getEconomy().hasEnoughMoney(player, 75 * 4)) {
                player.sendMessage(ChatColor.RED + "У вас не дстаточно денег чтобы купить это");
                return;
            }
            Asd.getInstance().getPluginManager().getEconomy().removeBalance(player, 75 * 4);
            player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 64));
        } else {
            buy(player, new ItemStack(Material.COOKED_BEEF, 16), 75);
        }
    }

    private void purchaseArmorItem(Player player, ItemStack item, Material itemType, int cost, String displayName) {
        if (!Asd.getInstance().getPluginManager().getEconomy().hasEnoughMoney(player, cost)) {
            player.sendMessage(ChatColor.RED + "У вас не дстаточно денег чтобы купить " + displayName);
            return;
        }
        Asd.getInstance().getPluginManager().getEconomy().removeBalance(player, cost);

        ItemStack oldItem = null;
        switch (itemType) {
            case DIAMOND_BOOTS:
                oldItem = player.getInventory().getBoots();
                player.getInventory().setBoots(item);
                break;
            case DIAMOND_LEGGINGS:
                oldItem = player.getInventory().getLeggings();
                player.getInventory().setLeggings(item);
                break;
            case DIAMOND_CHESTPLATE:
                oldItem = player.getInventory().getChestplate();
                player.getInventory().setChestplate(item);
                break;
            case DIAMOND_HELMET:
                oldItem = player.getInventory().getHelmet();
                player.getInventory().setHelmet(item);
                break;
        }

        if (oldItem != null && oldItem.getType() != Material.AIR) {
            player.getInventory().addItem(oldItem);
        }
    }

    private void purchaseFullArmorSet(Player player, ItemStack item, int cost, String displayName) {
        if (!Asd.getInstance().getPluginManager().getEconomy().hasEnoughMoney(player, cost)) {
            player.sendMessage(ChatColor.RED + "У вас не дстаточно денег чтобы купить " + displayName);
            return;
        }
        Asd.getInstance().getPluginManager().getEconomy().removeBalance(player, cost);

        player.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
        purchaseArmorItem(player, new ItemStack(Material.DIAMOND_BOOTS), Material.DIAMOND_BOOTS, 0, "");
        purchaseArmorItem(player, new ItemStack(Material.DIAMOND_LEGGINGS), Material.DIAMOND_LEGGINGS, 0, "");
        purchaseArmorItem(player, new ItemStack(Material.DIAMOND_CHESTPLATE), Material.DIAMOND_CHESTPLATE, 0, "");
        purchaseArmorItem(player, new ItemStack(Material.DIAMOND_HELMET), Material.DIAMOND_HELMET, 0, "");
    }

    private void buy(Player player, ItemStack item, int price) {
        if (!Asd.getInstance().getPluginManager().getEconomy().hasEnoughMoney(player, price)) {
            player.sendMessage(ChatColor.RED + "У вас не дстаточно денег чтобы купить это зелье");
            return;
        }
        Asd.getInstance().getPluginManager().getEconomy().removeBalance(player, price);
        player.getInventory().addItem(item);
    }
}

