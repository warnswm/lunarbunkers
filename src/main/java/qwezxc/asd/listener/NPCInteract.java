package qwezxc.asd.listener;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import qwezxc.asd.listener.InventoryHolders.BuilderInventoryHolder;
import qwezxc.asd.listener.InventoryHolders.CombatInventoryHolder;
import qwezxc.asd.listener.InventoryHolders.EnchanterInventoryHolder;
import qwezxc.asd.listener.InventoryHolders.SellerInventoryHolder;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NPCInteract implements Listener {
    @EventHandler
    public void onNPCInteract(NPCRightClickEvent event) {
        NPC npc = event.getNPC();
        Player player = event.getClicker();

        if(npc.getName().equals("Es")){
            InventoryHolder inventoryHolder = new EnchanterInventoryHolder(player);
            Inventory inventory = inventoryHolder.getInventory();

            player.openInventory(inventory);
        }

        if (npc.data() == null) return;


        if (npc.data().has("Combat")) {
            InventoryHolder inventoryHolder = new CombatInventoryHolder();
            Inventory inventory = inventoryHolder.getInventory();

            player.openInventory(inventory);
        } else if (npc.data().has("Builder")) {
            InventoryHolder inventoryHolder = new BuilderInventoryHolder(player);
            Inventory inventory = inventoryHolder.getInventory();

            player.openInventory(inventory);
        } else if (npc.data().has("Seller")) {
            InventoryHolder inventoryHolder = new SellerInventoryHolder(player);
            Inventory inventory = inventoryHolder.getInventory();

            player.openInventory(inventory);
        }
    }


}
