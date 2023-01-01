package qwezxc.asd.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import qwezxc.asd.Asd;

import java.util.*;

public class PlayerJoin implements Listener {
    public static Asd plugin;
    ArrayList<Location> locations = new ArrayList<Location>();
    private World world;

    public PlayerJoin(Asd main) {
        this.plugin = main;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e){
        Player p = e.getPlayer();
        world = Bukkit.getWorld("world");
        Location location = new Location(world,80,44,10);
        Location location2 = new Location(world,10,44,80);
        locations.add(location);
        locations.add(location2);
        int i = 0;
        Location nearestLoc = locations.get(0);
        Location playerloc = p.getLocation();
        for (Location l : locations) {
            if (i != locations.size()) {
                if (playerloc.distance(l) < playerloc.distance(nearestLoc)) {
                    nearestLoc = l;
                }
                i++;
            }
        }
        p.teleport(nearestLoc);
    }
}
