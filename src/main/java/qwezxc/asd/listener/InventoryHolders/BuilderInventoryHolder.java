package qwezxc.asd.listener.InventoryHolders;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import qwezxc.asd.Items.RegisterItems;

import static qwezxc.asd.util.Utils.createBuyItem;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BuilderInventoryHolder implements InventoryHolder {
    Player player;
    int FENCE_GATE_PRICE = 3;
    int CHEST_PRICE = 3;
    int LADDER_PRICE = 3;
    int COBBLESTONE_PRICE = 1;

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 45, "Builder Shop");

        inventory.setItem(1, createBuyItem(Material.FENCE_GATE, "Дубовая калитка", "Дубовая калитка", FENCE_GATE_PRICE,player));
        inventory.setItem(2, createBuyItem(Material.CHEST, "Сундук", "Сундук", CHEST_PRICE,player));
        inventory.setItem(3, createBuyItem(Material.LADDER, "Лестница", "Лестница", LADDER_PRICE,player));
        inventory.setItem(5, createBuyItem(Material.COBBLESTONE, "Булыжник", "Булыжник", COBBLESTONE_PRICE,player));
        inventory.setItem(30, RegisterItems.diamondpickaxe);
        inventory.setItem(31, RegisterItems.diamondaxe);

        return inventory;
    }

}
