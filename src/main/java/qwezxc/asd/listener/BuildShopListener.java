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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import qwezxc.asd.Asd;
import qwezxc.asd.listener.InventoryHolders.BuilderInventoryHolder;

public class BuildShopListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (!(event.getInventory().getHolder() instanceof BuilderInventoryHolder)) return;

        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        event.setCancelled(true);

        int availableSlots = player.getInventory().firstEmpty();
        if (availableSlots == -1) {
            player.sendMessage("Inventory is full");
            return;
        }
        if (item == null || item.getItemMeta() == null || item.getItemMeta().getDisplayName() == null) return;
        if (item.getType() == Material.FENCE_GATE) {
            net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
            NBTTagCompound nbtTagCompound = nmsItem.getTag();
            if (nbtTagCompound != null && nbtTagCompound.getBoolean("Дубовая калитка")) {
                if (event.getClick() == ClickType.RIGHT) {
                    buymulti(player, new ItemStack(Material.FENCE_GATE), 3);
                } else {
                    buysolo(player, new ItemStack(Material.FENCE_GATE), 3);
                }
            }
        } else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Сундук")) {
            if (event.getClick() == ClickType.RIGHT) {
                buymulti(player, new ItemStack(Material.CHEST), 3);
            } else {
                buysolo(player, new ItemStack(Material.CHEST), 3);
            }
        } else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Лестница")) {
            if (event.getClick() == ClickType.RIGHT) {
                buymulti(player, new ItemStack(Material.LADDER), 3);
            } else {
                buysolo(player, new ItemStack(Material.LADDER), 3);
            }
        } else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Алмазная кирка")) {
            buysolo(player, new ItemStack(Material.DIAMOND_PICKAXE), 50);
        } else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Алмазный топор")) {
            buysolo(player, new ItemStack(Material.DIAMOND_AXE), 50);
        } else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Булыжник")) {
            if (event.getClick() == ClickType.RIGHT) {
                buymulti(player, new ItemStack(Material.COBBLESTONE), 1);
            } else {
                buysolo(player, new ItemStack(Material.COBBLESTONE), 1);
            }
        }

    }

    private void buysolo(Player player, ItemStack item, int price) {
        if (!Asd.getInstance().getPluginManager().getEconomy().hasEnoughMoney(player, price)) {
            player.sendMessage(ChatColor.RED + "У вас не дстаточно денег");
            return;
        }
        Asd.getInstance().getPluginManager().getEconomy().removeBalance(player, price);
        player.getInventory().addItem(item);
    }

    private void buymulti(Player player, ItemStack item, int price) {
        if (!Asd.getInstance().getPluginManager().getEconomy().hasEnoughMoney(player, price)) {
            player.sendMessage(ChatColor.RED + "У вас не дстаточно денег");
            return;
        }
        int itemCount = 0;
        int playerMoney = Asd.getInstance().getPluginManager().getEconomy().getBalance(player);
        if (playerMoney >= price) {
            itemCount = playerMoney / price;
            itemCount = Math.min(itemCount, item.getMaxStackSize());
        }
        Asd.getInstance().getPluginManager().getEconomy().removeBalance(player, price * itemCount);
        for (int i = itemCount; i > 0; i--) {
            player.getInventory().addItem(item);
        }
    }
}
