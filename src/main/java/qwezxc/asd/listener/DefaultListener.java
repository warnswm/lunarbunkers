package qwezxc.asd.listener;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import qwezxc.asd.Asd;
import qwezxc.asd.core.ScoreBoardLib;
import qwezxc.asd.core.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DefaultListener implements Listener {
    private final Asd main;
    private HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
    public static double cooldowntime = 15.0;
    private ScoreBoardLib scoreBoardLib;
    private final HashMap<UUID, Long> captureMessageCooldown = new HashMap<>();
    private final int MESSAGE_COOLDOWN_SECONDS = 10;

    public DefaultListener(final Asd main, ScoreBoardLib scoreBoardLib) {
        this.main = main;
        this.scoreBoardLib = scoreBoardLib;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (event.getItem() == null) return;
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (item.getType() == Material.DIAMOND_BLOCK) {
                event.setCancelled(true);
                Inventory menu = Bukkit.createInventory(null, 9, "Team Select");

                for (Team team : main.teams.getTeams()) {
                    menu.addItem(new ItemStack(team.getWoolBlock(), 1));
                }
                player.openInventory(menu);
            }
        }
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem().getType() == Material.ENDER_PEARL) {
                if (cooldown.containsKey(player.getUniqueId())) {
                    event.setCancelled(true);
                    return;
                }
                cooldown.put(player.getUniqueId(), System.currentTimeMillis());
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        cooldown.remove(player.getUniqueId());
                    }
                }.runTaskLater(main, 15 * 20L);
                scoreBoardLib.enderpearlScoreBoard(player, main.getPluginManager().getPlayerLivesManager(), main.getPluginManager().getPlayerKillsManager(), main.teams);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        cooldowntime -= 0.1;
                        if (cooldowntime <= 0) {
                            cooldowntime = 15.0;
                            scoreBoardLib.sendScoreBoard(player, main.getPluginManager().getPlayerLivesManager(), main.getPluginManager().getPlayerKillsManager(), main.teams);
                            this.cancel();
                        }
                    }
                }.runTaskTimer(main, 2L, 2L);
            }
        }
    }


    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) {
            return;
        }

        Player target = (Player) event.getEntity();
        Player attacker = (Player) event.getDamager();

        UUID targetuuid = target.getUniqueId();
        UUID attackeruuid = attacker.getUniqueId();

        if (main.teams.getTeam(attackeruuid) == main.teams.getTeam(targetuuid)) {
            event.setCancelled(true);
            attacker.sendMessage("You can't attack players from the same team.");
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location playerLoc = player.getLocation();
        double captureRadius = 3.5;
        Location capturePoint = new Location(Bukkit.getWorld("world"), 1.5, 63.5, 0.5);
        if (Math.abs(playerLoc.getX() - capturePoint.getX()) <= captureRadius &&
                Math.abs(playerLoc.getY() - capturePoint.getY()) <= captureRadius &&
                Math.abs(playerLoc.getZ() - capturePoint.getZ()) <= captureRadius) {
            main.koth.startCapture(player);
            long lastMessageTime = captureMessageCooldown.getOrDefault(player.getUniqueId(), 0L);
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastMessageTime > MESSAGE_COOLDOWN_SECONDS * 1000) {
                player.sendMessage(ChatColor.RED + "Точку можно захватить начиная с 5 минуты.");
                captureMessageCooldown.put(player.getUniqueId(), currentTime);
            }
        } else {
            main.koth.stopCapture(player);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        main.koth.stopCapture(player);
    }



}