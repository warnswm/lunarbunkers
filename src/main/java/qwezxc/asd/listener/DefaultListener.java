package qwezxc.asd.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import qwezxc.asd.Asd;
import qwezxc.asd.core.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DefaultListener implements Listener {
    private static final int MESSAGE_COOLDOWN_SECONDS = 10;
    public static double cooldowntime = 15.0;
    private final Asd main;
    private final Teams teams;
    private final KOTH koth;
    private HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
    private ScoreBoardLib scoreBoardLib;
    private final HashMap<UUID, Long> captureMessageCooldown = new HashMap<>();
    public static List<Block> placedBlocks = new ArrayList<>();

    public DefaultListener(final Asd main, ScoreBoardLib scoreBoardLib, KOTH koth, Teams teams) {
        this.main = main;
        this.scoreBoardLib = scoreBoardLib;
        this.koth = koth;
        this.teams = teams;
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
                for (Team team : teams.getTeams()) {
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
                scoreBoardLib.enderpearlScoreBoard(player, main.getPluginManager().getTeamLivesManager(), main.getPluginManager().getPlayerKillsManager(), teams);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        cooldowntime -= 0.1;
                        if (cooldowntime <= 0) {
                            cooldowntime = 15.0;
                            scoreBoardLib.sendScoreBoard(player, main.getPluginManager().getTeamLivesManager(), main.getPluginManager().getPlayerKillsManager(), teams);
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

        if (teams.getTeam(attackeruuid) == teams.getTeam(targetuuid)) {
            event.setCancelled(true);
            attacker.sendMessage("You can't attack players from the same team.");
        }
    }

    public static void removeBlocks() {
        for (Block block : placedBlocks) {
            block.setType(Material.AIR);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        koth.stopCapture(player);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location playerLoc = player.getLocation();
        double captureRadius = 3.5;
        Location capturePoint = new Location(Bukkit.getWorld("world"), 1.5, 63.5, 0.5);
//        double distanceSquared = playerLoc.distanceSquared(capturePoint);
//        if (distanceSquared <= captureRadius * captureRadius) {
        if (Math.abs(playerLoc.getX() - capturePoint.getX()) <= captureRadius &&
                Math.abs(playerLoc.getY() - capturePoint.getY()) <= captureRadius &&
                Math.abs(playerLoc.getZ() - capturePoint.getZ()) <= captureRadius) {
            koth.startCapture(player);
            if (GameManager.gameTime < 300) {
                long lastMessageTime = captureMessageCooldown.getOrDefault(player.getUniqueId(), 0L);
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastMessageTime > MESSAGE_COOLDOWN_SECONDS * 1000) {
                    player.sendMessage(ChatColor.RED + "Точку можно захватить начиная с 5 минуты.");
                    captureMessageCooldown.put(player.getUniqueId(), currentTime);
                }
            }
        } else {
            koth.stopCapture(player);
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        e.setCancelled(e.toWeatherState());
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (!player.isOp()) {
            placedBlocks.add(block);
        }
    }

    @EventHandler
    public void onBlockKOTH(BlockPlaceEvent event) {
        Location blocklocaiton = event.getBlock().getLocation();
        double captureRadius = 10.5;
        Location capturePoint = new Location(Bukkit.getWorld("world"), 1.5, 63.5, 0.5);
        if (Math.abs(blocklocaiton.getX() - capturePoint.getX()) <= captureRadius &&
                Math.abs(blocklocaiton.getY() - capturePoint.getY()) <= captureRadius - 5 &&
                Math.abs(blocklocaiton.getZ() - capturePoint.getZ()) <= captureRadius) {
            event.setCancelled(true);
        }
    }

}