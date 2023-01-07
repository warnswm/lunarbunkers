package qwezxc.asd.listeners;

import com.google.common.collect.Lists;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import qwezxc.asd.Asd;
import qwezxc.asd.commands.companioncommand;

import java.util.*;

public class PlayerJoinORleave implements Listener {
    public static Asd plugin;
    public static companioncommand companioncommand;
    public HashMap<NPC, Location> leavedplayercompanion = new HashMap<>();
    ArrayList<Location> locations = new ArrayList<Location>();
    private World world;

    public PlayerJoinORleave(Asd main) {
        this.plugin = main;
    }

    @EventHandler
    public void onPlayerleaveEvent(PlayerQuitEvent e){
        String player = e.getPlayer().getName();
        for (NPC npc : Lists.newArrayList(CitizensAPI.getNPCRegistry())) {
            if (npc.getName().equals(player + " pet")) {
                Location npcloc = npc.getStoredLocation();
                leavedplayercompanion.put(npc,npcloc);
                npc.despawn();
                System.out.println(leavedplayercompanion.get(npcloc));

            }
        }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e){
        Player p = e.getPlayer();
        String player = e.getPlayer().getName();
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
        if (!leavedplayercompanion.isEmpty()){
            Location locplayer = e.getPlayer().getLocation();
            for (NPC npc : Lists.newArrayList(CitizensAPI.getNPCRegistry())) {
                if (npc.getName().equals(player + " pet")) {
                    npc.spawn(locplayer);
                }
            }
        }

    }
}
