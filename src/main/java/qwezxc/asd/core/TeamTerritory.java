package qwezxc.asd.core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;

public class TeamTerritory implements Listener {
    private final Teams teams;

    private final  Map<String, Location> baseTerritories = new HashMap<>();

    public TeamTerritory(Teams teams) {
        this.teams = teams;
        baseTerritories.put("Red", new Location(Bukkit.getWorld("world"), 1.5, 64.5, 85.5));
        baseTerritories.put("Blue", new Location(Bukkit.getWorld("world"), 1.5, 64.5, -85.5));
        baseTerritories.put("Green", new Location(Bukkit.getWorld("world"), -85.5, 64.5, 0.5));
        baseTerritories.put("Yellow", new Location(Bukkit.getWorld("world"), 85.5, 64.5, 0.5));
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        double baseRadius = 14.5;
        if (block == null) return;


        if (block.getType() == Material.FENCE_GATE) {

            // Get the player's team
            Team playerTeam = teams.getTeam(player);

            if (playerTeam == null) {
                return;
            }
            // Check if the player is within any base territory
            boolean isInBaseTerritory = false;
            for (Map.Entry<String, Location> entry : baseTerritories.entrySet()) {
                String teamName = entry.getKey();
                Location baseLocation = entry.getValue();
                if (Math.abs(player.getLocation().getX() - baseLocation.getX()) <= baseRadius &&
                        Math.abs(player.getLocation().getY() - baseLocation.getY()) <= baseRadius +200 &&
                        Math.abs(player.getLocation().getZ() - baseLocation.getZ()) <= baseRadius) {


                    isInBaseTerritory = true;


                    if (playerTeam.getName().equals(teamName)) {
                        event.setCancelled(false);
                    } else {
                        event.setCancelled(true);
                        player.sendMessage(ChatColor.RED + "Вы не можете открывать каликти на территории базы других команд!");
                    }
                    break;
                }
            }

            // If the player is not within any base territory, allow them to open the gate
            if (!isInBaseTerritory) {
                event.setCancelled(false);
            }
        } else if (block.getType() == Material.CHEST) {
            Team playerTeam = teams.getTeam(player);
            if (playerTeam == null) {
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
                        player.sendMessage(ChatColor.RED + "Вы не можете открывать сундук на территории базы других команд!");
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
