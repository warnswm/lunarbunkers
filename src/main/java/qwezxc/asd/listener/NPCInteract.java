package qwezxc.asd.listener;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import qwezxc.asd.Items.RegisterInventory;
import qwezxc.asd.listener.InventoryHolders.BuilderInventoryHolder;
import qwezxc.asd.listener.InventoryHolders.SellerInventoryHolder;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NPCInteract implements Listener {

    @EventHandler
    public void onNPCInteract(NPCRightClickEvent event) {
        NPC npc = event.getNPC();
        Player player = event.getClicker();
        if (player.getGameMode() != GameMode.SURVIVAL) return;
        if (npc.getName().equals("Combat Shop")) {
            player.openInventory(RegisterInventory.combatShop);
        } else if (npc.getName().equals("Builder Shop")) {
            InventoryHolder inventoryHolder = new BuilderInventoryHolder(player);
            Inventory inventory = inventoryHolder.getInventory();

            player.openInventory(inventory);
        } else if (npc.getName().equals("Seller Shop")) {
            InventoryHolder inventoryHolder = new SellerInventoryHolder(player);
            Inventory inventory = inventoryHolder.getInventory();

            player.openInventory(inventory);
        }
    }

}
