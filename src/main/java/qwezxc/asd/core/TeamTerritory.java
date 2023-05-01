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
        Team playerTeam = teams.getTeam(player);
        if (playerTeam == null) return;

        if (block == null) return;

        if (block.getType() != Material.FENCE_GATE || block.getType() != Material.CHEST) return;
        if (!isPlayerInBaseTerritory(player)) {
            event.setCancelled(false);
            return;
        }
        String baseTerritoryTeam = getBaseTerritoryTeam(block.getLocation());
        if (playerTeam.getName().equals(baseTerritoryTeam)) {
            event.setCancelled(false);
        } else {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "Вы не можете с этим взаимодействовать");
        }
    }

    private boolean isPlayerInBaseTerritory(Player player) {
        double baseRadius = 14.5;
        Location playerloc = player.getLocation();
        for (Location value : baseTerritories.values()) {
            if (Math.abs(playerloc.getX() - value.getX()) <= baseRadius &&
                    Math.abs(playerloc.getY() - value.getY()) <= baseRadius &&
                    Math.abs(playerloc.getZ() - value.getZ()) <= baseRadius) {
                return true;
            }
        }
        return false;
    }

    private String getBaseTerritoryTeam(Location location) {
        for (Map.Entry<String, Location> entry : baseTerritories.entrySet()) {
            String teamName = entry.getKey();
            Location baseLocation = entry.getValue();
            if (location.equals(baseLocation)) {
                return teamName;
            }
        }
        return null;
    }


}
