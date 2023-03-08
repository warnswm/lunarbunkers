package qwezxc.asd.listener.InventoryHolders;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import static qwezxc.asd.util.Utils.createSellItem;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SellerInventoryHolder implements InventoryHolder {
    Player player;
    int COAL_PRICE = 10;
    int IRON_INGOT_PRICE = 15;
    int DIAMOND_PRICE = 30;
    int GOLD_INGOT_PRICE = 20;
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 9, "Seller Shop");

        ItemStack coal = createSellItem(Material.COAL, "Продать уголь", COAL_PRICE, player);
        ItemStack iron = createSellItem(Material.IRON_INGOT, "Продать железный слиток", IRON_INGOT_PRICE, player);
        ItemStack diamond = createSellItem(Material.DIAMOND, "Продать алмаз", DIAMOND_PRICE, player);
        ItemStack gold = createSellItem(Material.GOLD_INGOT, "Продать золотой слиток", GOLD_INGOT_PRICE, player);
        // ToDo: Add Emerald price 40$

        inventory.setItem(7, coal);
        inventory.setItem(5, iron);
        inventory.setItem(1, diamond);
        inventory.setItem(3, gold);


        return inventory;
    }

}
