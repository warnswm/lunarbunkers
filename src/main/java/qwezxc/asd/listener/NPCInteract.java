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
import qwezxc.asd.Items.RegisterItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// ToDo: Какого хера, ты каждый раз создаешь инвентари? Если можешь просто открыть нужны? У тебя контент в них не меняется.
// ToDo: Создай два инвентаря и открывай их, когда тебе нужно.
public class NPCInteract implements Listener {
    final int COAL_PRICE = 10;
    final int IRON_INGOT_PRICE = 15;
    final int DIAMOND_PRICE = 20;
    final int GOLD_INGOT_PRICE = 20;

    public Inventory combat = Bukkit.createInventory(null, 54, "Combat Shop");

    public NPCInteract() {

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
        combat.setItem(18, RegisterItems.sword);
        combat.setItem(14, invisibility);
        combat.setItem(15, RegisterItems.speedPotion);
        combat.setItem(16, RegisterItems.firePotion);
        combat.setItem(23, RegisterItems.healPotion);
        combat.setItem(34, RegisterItems.slownesspotion);
        combat.setItem(37, RegisterItems.boots);
        combat.setItem(28, RegisterItems.leggings);
        combat.setItem(19, RegisterItems.chestplate);
        combat.setItem(20, RegisterItems.fullset);
        combat.setItem(21, RegisterItems.enderperl);
    }


    @EventHandler
    public void onNPCInteract(NPCRightClickEvent event) {
        NPC npc = event.getNPC();
        Player player = event.getClicker();
        if (npc.getName().equals("Combat Shop")) {
            player.openInventory(combat);
        }
        if (npc.getName().equals("Seller")) {
            // Create a new inventory with a size of 27 and a title
            Inventory inventory = Bukkit.createInventory(null, 9, "Seller Shop");

            ItemStack coal = createSellItem(Material.COAL, "Sell Coal", COAL_PRICE, player);
            ItemStack iron = createSellItem(Material.IRON_INGOT, "Sell Iron Ingot", IRON_INGOT_PRICE, player);
            ItemStack diamond = createSellItem(Material.DIAMOND, "Sell Diamond", DIAMOND_PRICE, player);
            ItemStack gold = createSellItem(Material.GOLD_INGOT, "Sell Gold Ingot", GOLD_INGOT_PRICE, player);

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
