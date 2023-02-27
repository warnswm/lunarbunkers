package qwezxc.asd.listener;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import qwezxc.asd.Asd;
import qwezxc.asd.Items.DiamodPick;
import qwezxc.asd.Items.RegisterInventory;
import qwezxc.asd.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

// ToDo: Какого хера, ты каждый раз создаешь инвентари? Если можешь просто открыть нужны? У тебя контент в них не меняется.
// ToDo: Создай два инвентаря и открывай их, когда тебе нужно.
public class NPCInteract implements Listener {
    final int COAL_PRICE = 10;
    final int IRON_INGOT_PRICE = 15;
    final int DIAMOND_PRICE = 30;
    final int GOLD_INGOT_PRICE = 20;
    final int FENCE_GATE_PRICE = 3;
    final int CHEST_PRICE = 3;
    final int LADDER_PRICE = 3;
    final int COBBLESTONE_PRICE = 1;


    public NPCInteract() {

    }


    @EventHandler
    public void onNPCInteract(NPCRightClickEvent event) {
        NPC npc = event.getNPC();
        Player player = event.getClicker();
        if (player.getGameMode() != GameMode.SURVIVAL) return;
        if (npc.getName().equals("Combat Shop")) {
            player.openInventory(RegisterInventory.combatShop);
        } else if (npc.getName().equals("Builder Shop")) {
            Inventory inventory = Bukkit.createInventory(null, 45, "Builder Shop");

            inventory.setItem(1, createBuyItem(Material.FENCE_GATE, "Дубовая калитка", "Дубовая калитка", FENCE_GATE_PRICE, player));
            inventory.setItem(2, createBuyItem(Material.CHEST, "Сундук", "Сундук", CHEST_PRICE, player));
            inventory.setItem(3, createBuyItem(Material.LADDER, "Лестница", "Лестница", LADDER_PRICE, player));
            inventory.setItem(5, createBuyItem(Material.COBBLESTONE, "Булыжник", "Булыжник", COBBLESTONE_PRICE, player));
            inventory.setItem(30, DiamodPick.createDiamondPickaxe());
            inventory.setItem(31, DiamodPick.createDiamondAxe());

            player.openInventory(inventory);
        } else if (npc.getName().equals("Seller Shop")) {
            Inventory inventory = Bukkit.createInventory(null, 9, "Seller Shop");

            ItemStack coal = createSellItem(Material.COAL, "Продать уголь", COAL_PRICE, player);
            ItemStack iron = createSellItem(Material.IRON_INGOT, "Продать железный слиток", IRON_INGOT_PRICE, player);
            ItemStack diamond = createSellItem(Material.DIAMOND, "Продать алмаз", DIAMOND_PRICE, player);
            ItemStack gold = createSellItem(Material.GOLD_INGOT, "Продать золотой слиток", GOLD_INGOT_PRICE, player);
            // ToDo: Add Emerald price 40$

            inventory.setItem(7, coal);
            inventory.setItem(5, iron);
            inventory.setItem(1, diamond);
            inventory.setItem(3, gold);

            player.openInventory(inventory);
        }
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
        if (itemCount != 0) {
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            lore.add(ChatColor.GRAY + "Щелкните левой кнопкой мыши, чтобы продать 1x " + StringUtils.declineNoun(displayName.replaceFirst("Продать ", ""), 1) + " за " + price + "$");
            int totalPrice = price * itemCount;
            lore.add(ChatColor.GRAY + "Щелкните правой кнопкой мыши, чтобы продать " + itemCount + "x " + StringUtils.declineNoun(displayName.replaceFirst("Продать ", ""), itemCount) + " за " + totalPrice + "$ ");
            lore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RED + "У вас недостаточно " + displayName.replaceFirst("Продать ", "") + " для продажи ");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createBuyItem(Material material, String displayName, String name, int price, Player player) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + displayName);
        int itemCount;
        int playerMoney = Asd.getInstance().getPluginManager().getEconomy().getBalance(player);
        if (playerMoney >= price) {
            itemCount = playerMoney / price;
            itemCount = Math.min(itemCount, item.getMaxStackSize());

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            lore.add(ChatColor.GRAY + "Щелкните левой кнопкой мыши, чтобы купить 1 " + StringUtils.declineNoun(displayName, 1) + " за " + price + "$");

            int totalPrice = price * itemCount;
            lore.add(ChatColor.GRAY + "Щелкните правой кнопкой мыши, чтобы купить " + itemCount + " " + StringUtils.declineNoun(displayName, itemCount) + " за " + totalPrice + "$ ");
            lore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        } else {
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.RED + "У вас недостаточно денег для покупки " + displayName);
            meta.setLore(lore);
        }

        item.setItemMeta(meta);
        return item;
    }

}
