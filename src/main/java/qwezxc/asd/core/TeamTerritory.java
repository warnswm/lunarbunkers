package qwezxc.asd.core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Gate;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import qwezxc.asd.Asd;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeamTerritory implements Listener {
    private Asd main;
    private Teams teams;

    private Map<String, Location> baseTerritories = new HashMap<>();
    private double baseRadius = 14.5;
    public TeamTerritory(Asd main,Teams teams){
        this.teams = teams;
        this.main = main;
        baseTerritories.put("Red", new Location(Bukkit.getWorld("world"), 1.5, 64.5, 85.5));
        baseTerritories.put("Blue", new Location(Bukkit.getWorld("world"), 1.5,64.5,-85.5));
        baseTerritories.put("Green", new Location(Bukkit.getWorld("world"), -85.5, 64.5, 0.5));
        baseTerritories.put("Yellow", new Location(Bukkit.getWorld("world"), 85.5, 64.5, 0.5));
    }



    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (block != null && block.getType() == Material.FENCE_GATE) {

            // Get the player's team
            Team playerTeam = teams.getTeam(player);
            if(playerTeam==null){
                return;
            }
            // Check if the player is within any base territory
            boolean isInBaseTerritory = false;
            for (Map.Entry<String, Location> entry : baseTerritories.entrySet()) {
                String teamName = entry.getKey();
                Location baseLocation = entry.getValue();
                if (Math.abs(player.getLocation().getX() - baseLocation.getX()) <= baseRadius &&
                        Math.abs(player.getLocation().getY() - baseLocation.getY()) <= baseRadius &&
                        Math.abs(player.getLocation().getZ() - baseLocation.getZ()) <= baseRadius) {

                    // The player is within a base territory
                    isInBaseTerritory = true;

                    // Check if the player is in their own base territory
                    if (playerTeam.getName().equals(teamName)) {
                        // Allow the player to open the gate
                        event.setCancelled(false);
                    } else {
                        // Prevent the player from opening the gate
                        event.setCancelled(true);
                        player.sendMessage(ChatColor.RED + "You cannot open gates in the base territory of other teams!");
                    }
                    break;
                }
            }

            // If the player is not within any base territory, allow them to open the gate
            if (!isInBaseTerritory) {
                event.setCancelled(false);
            }
        }else if(block != null && block.getType() == Material.CHEST){
            Team playerTeam = teams.getTeam(player);
            if(playerTeam==null){
                return;
            }
            // Check if the player is within any base territory
            boolean isInBaseTerritory = false;
            for (Map.Entry<String, Location> entry : baseTerritories.entrySet()) {
                String teamName = entry.getKey();
                Location baseLocation = entry.getValue();
                if (Math.abs(player.getLocation().getX() - baseLocation.getX()) <= baseRadius &&
                        Math.abs(player.getLocation().getY() - baseLocation.getY()) <= baseRadius &&
                        Math.abs(player.getLocation().getZ() - baseLocation.getZ()) <= baseRadius) {
                    isInBaseTerritory = true;

                    if (playerTeam.getName().equals(teamName)) {
                        event.setCancelled(false);
                    } else {
                        event.setCancelled(true);
                        player.sendMessage(ChatColor.RED + "You cannot open gates in the base territory of other teams!");
                    }
                    break;
                }
            }

            if (!isInBaseTerritory) {
                event.setCancelled(false);
            }
        }
    }

}
