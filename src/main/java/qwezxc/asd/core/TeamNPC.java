package qwezxc.asd.core;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.GameModeTrait;
import net.citizensnpcs.trait.LookClose;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
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

    private final Map<String, Location> locations = new HashMap<String, Location>() {{
        put("combatshopredLoc", new Location(Bukkit.getWorld("world"), 1.5, 65.5, 95.5));
        put("combatshopblueLoc", new Location(Bukkit.getWorld("world"), 1.5, 65.5, -95.5));
        put("combatshopgreenLoc", new Location(Bukkit.getWorld("world"), -95.5, 65.5, 0.5));
        put("combatshopyellowLoc", new Location(Bukkit.getWorld("world"), 95.5, 65.5, 0.5));
        put("sellershopredLoc", new Location(Bukkit.getWorld("world"), -2.5, 65.5, 95.5));
        put("sellershopblueLoc", new Location(Bukkit.getWorld("world"), 5.5, 65.5, -95.5));
        put("sellershopgreenLoc", new Location(Bukkit.getWorld("world"), -95.5, 65.5, -3.5));
        put("sellershopyellowLoc", new Location(Bukkit.getWorld("world"), 95.5, 65.5, 4.5));
        put("buildershopredLoc", new Location(Bukkit.getWorld("world"), 5.5, 65.5, 95.5));
        put("buildershopblueLoc", new Location(Bukkit.getWorld("world"), -2.5, 65.5, -95.5));
        put("buildershopgreenLoc", new Location(Bukkit.getWorld("world"), -95.5, 65.5, 4.5));
        put("buildershopyellowLoc", new Location(Bukkit.getWorld("world"), 95.5, 65.5, -3.5));
    }};

    private int time;
    public TeamNPC(Teams teams) {
        this.teams = teams;
        this.time = 300;
    }

    public void spawnAll() {
        spawnCombatShop(teams.getTeams().get(0), locations.get("combatshopredLoc"));
        spawnCombatShop(teams.getTeams().get(1), locations.get("combatshopblueLoc"));
        spawnCombatShop(teams.getTeams().get(2), locations.get("combatshopgreenLoc"));
        spawnCombatShop(teams.getTeams().get(3), locations.get("combatshopyellowLoc"));
        spawnSellerShop(teams.getTeams().get(0), locations.get("sellershopredLoc"));
        spawnSellerShop(teams.getTeams().get(1), locations.get("sellershopblueLoc"));
        spawnSellerShop(teams.getTeams().get(2), locations.get("sellershopgreenLoc"));
        spawnSellerShop(teams.getTeams().get(3), locations.get("sellershopyellowLoc"));
        spawnBuilderShop(teams.getTeams().get(0), locations.get("buildershopredLoc"));
        spawnBuilderShop(teams.getTeams().get(1), locations.get("buildershopblueLoc"));
        spawnBuilderShop(teams.getTeams().get(2), locations.get("buildershopgreenLoc"));
        spawnBuilderShop(teams.getTeams().get(3), locations.get("buildershopyellowLoc"));
    }

    public void spawnCombatShop(Team team, Location loc) {
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.VILLAGER, "Combat Shop");
        npc.setName(team.getChatColor() + "Combat Shop");
        npc.data().set("Combat", "hello");
        setDefaultSettings(npc, team, loc);
    }
    public void spawnSellerShop(Team team, Location loc) {
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.VILLAGER, "SellerShop");
        npc.setName(team.getChatColor() + "Seller Shop");
        npc.data().set("Seller", "hello");
        setDefaultSettings(npc, team, loc);
    }
    public void spawnBuilderShop(Team team, Location loc) {
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.VILLAGER, "BuilderShop");
        npc.setName(team.getChatColor() + "Builder Shop");
        npc.data().set("Builder", "hello");
        setDefaultSettings(npc, team, loc);
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
                event.setCancelled(playerTeam != null && npcTeam != null && playerTeam == npcTeam);
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity victim = event.getEntity();
        Location deathloc = event.getEntity().getLocation();
        if (victim.getType() == EntityType.VILLAGER) {
            NPC npc = CitizensAPI.getNPCRegistry().getNPC(victim);
            if (npc == null) return;
            ArmorStand as = spawnArmorStand(deathloc);
            new BukkitRunnable() {
                @Override
                public void run() {
                    time--;
                    int minutes = time / 60;
                    int seconds = time % 60;
                    as.setCustomName("Возрождение через " + String.format("%02d:%02d", minutes, seconds));
                    if (time == 0) as.remove();
                }
            }.runTaskTimer(Asd.getInstance(), 20L, 20L);
        }
    }

    @EventHandler
    public void manipulate(PlayerArmorStandManipulateEvent e) {
        if (!e.getRightClicked().isVisible()) {
            e.setCancelled(true);
        }

    }

    private ArmorStand spawnArmorStand(Location location) {
        World world = location.getWorld();
        ArmorStand as = (ArmorStand) world.spawnEntity(location, EntityType.ARMOR_STAND);
        as.setGravity(false);
        as.setCanPickupItems(false);
        as.setCustomNameVisible(true);
        as.setVisible(false);
        as.setCustomName("Возрождение через 5:00");
        return as;
    }

    private void setDefaultSettings(NPC npc, Team team, Location loc) {
        npc.spawn(loc);
        npc.getOrAddTrait(GameModeTrait.class).setGameMode(GameMode.SURVIVAL);
        npc.getOrAddTrait(LookClose.class).lookClose(true);
        npc.data().setPersistent(NPC.Metadata.RESPAWN_DELAY, 300 * 20);
        npcTeams.put(npc.getId(), team);
    }
}

