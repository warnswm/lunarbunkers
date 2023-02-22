package qwezxc.asd.core;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.GameModeTrait;
import net.citizensnpcs.trait.LookClose;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.scheduler.BukkitRunnable;
import qwezxc.asd.Asd;

import java.util.HashMap;
import java.util.Map;

public class TeamNPC implements Listener {

    private Teams teams;
    public Map<Integer, Team> npcTeams= new HashMap<>();
    private int time;
    public TeamNPC(Teams teams) {
        this.teams = teams;
        this.time = 300;
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
        npc.setName(ChatColor.valueOf(team.toUpperCase()) + "Combat Shop");
        npc.getOrAddTrait(GameModeTrait.class).setGameMode(GameMode.SURVIVAL);
        npc.getOrAddTrait(LookClose.class).lookClose(true);
        npc.data().setPersistent(NPC.Metadata.RESPAWN_DELAY, 300*20);
        npcTeams.put(npc.getId(), teams.getTeams().get(team));
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
                Team npcTeam = npcTeams.get(npc.getId());
                if (playerTeam != null && npcTeam != null && playerTeam.equals(npcTeam)) {
                    event.setCancelled(true);
                }else{
                    event.setCancelled(false);
                }
            }
        }
    }
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        Entity victim = event.getEntity();
        Location deathloc = event.getEntity().getLocation();
        if (victim.getType() == EntityType.VILLAGER) {
            NPC npc = CitizensAPI.getNPCRegistry().getNPC(victim);
            if (npc != null) {
                ArmorStand as = (ArmorStand) deathloc.getWorld().spawnEntity(deathloc, EntityType.ARMOR_STAND);
                as.setGravity(false);
                as.setCanPickupItems(false);
                as.setCustomNameVisible(true);
                as.setVisible(false);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        time--;
                        int minutes = time / 60;
                        int seconds = time % 60;
                        as.setCustomName("Возраждение через " + String.format("%02d:%02d", minutes, seconds));
                        if(time == 0){
                            as.remove();
                        }
                    }
                    }
                    .runTaskTimer(Asd.getInstance(),20L,20L);

            }
        }
    }

    @EventHandler
    public void manipulate(PlayerArmorStandManipulateEvent e)
    {
        if(!e.getRightClicked().isVisible())
        {
            e.setCancelled(true);
        }

    }
}

