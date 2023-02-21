package qwezxc.asd.core;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.GameModeTrait;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import qwezxc.asd.Asd;

import java.sql.SQLSyntaxErrorException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TeamNPC implements Listener {

    private Teams teams;
    public Map<Integer, Team> npcTeams= new HashMap<>();

    public TeamNPC(Teams teams) {
        this.teams = teams;
    }

    public void spawnAll(Location redLoc, Location blueLoc, Location greenLoc, Location yellowLoc) {
        spawnNPCs("Red", redLoc);
        spawnNPCs("Blue", blueLoc);
        spawnNPCs("Green", greenLoc);
        spawnNPCs("Yellow", yellowLoc);
    }

    public void spawnNPCs(String team, Location loc) {
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.VILLAGER, "CombatShop");
        npc.spawn(loc);
        npc.setName(ChatColor.valueOf(team.toUpperCase()) + "CombatShop");
        npc.getOrAddTrait(GameModeTrait.class).setGameMode(GameMode.SURVIVAL);
        npcTeams.put(npc.getId(), teams.getTeams().get(team));
        System.out.println("npcTeams map: " + npcTeams);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity victim = event.getEntity();
        Entity attacker = event.getDamager();
        if (victim.getType() == EntityType.VILLAGER && attacker instanceof Player) {
            NPC npc = CitizensAPI.getNPCRegistry().getNPC(victim);
            if (npc != null) {
                Player player = (Player) attacker;
                Team playerTeam = teams.getTeam(player);
                System.out.println(playerTeam);
                Team npcTeam = npcTeams.get(npc.getId());
                if (playerTeam != null && npcTeam != null && playerTeam.equals(npcTeam)) {
                    event.setCancelled(true);
                }else{
                    event.setCancelled(false);
                }
            }
        }
    }
}

