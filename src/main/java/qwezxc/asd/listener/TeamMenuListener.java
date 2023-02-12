package qwezxc.asd.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import qwezxc.asd.Team;
import qwezxc.asd.Teams;
import java.util.UUID;
public class TeamMenuListener implements Listener {
    private Teams teams;

    public TeamMenuListener(Teams teams) {
        this.teams = teams;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();
        ItemStack item = event.getCurrentItem();
        Inventory inventory = event.getInventory();
        if (item == null || item.getType() == Material.AIR) {
            return;
        }
        if (inventory.getTitle().equals("Team Select")) {
            event.setCancelled(true);
            if (item == null) return;
            for (Team team : teams.getTeams().values()) {
                if (item.getType() == team.getWoolBlock()) {
                    teams.addPlayerToTeam(player, team);
                    player.sendMessage("You joined the " + team.getName() + " team!");
                    break;
                }
            }
        }
    }
}

