package qwezxc.asd.Items;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@Getter
@Setter
public class EnchantmentShop {

    public EnchantmentShop(Player player) {
        Inventory chestInventory = Bukkit.createInventory(null, 54, ChatColor.GREEN + "Enchant Shop");
    }
}
