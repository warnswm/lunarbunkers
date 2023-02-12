package qwezxc.asd.core;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import qwezxc.asd.Asd;
import qwezxc.asd.Team;
import qwezxc.asd.Teams;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class KOTH implements Listener {
    private Teams teams;
    private Location capturePoint;
    private double captureRadius;
    private Map<UUID, Team> capturingPlayers;

    public KOTH(Teams teams, Location capturePoint, double captureRadius) {
        this.teams = teams;
        this.capturePoint = capturePoint;
        this.captureRadius = captureRadius;
        this.capturingPlayers = new HashMap<>();
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Team playerTeam = teams.getTeam(player);

        if (playerTeam == null) {
            return;
        }

        Location playerLoc = player.getLocation();
        if (Math.abs(playerLoc.getX()- capturePoint.getX()) <= captureRadius  &&
                Math.abs(playerLoc.getY() - capturePoint.getY()) <= captureRadius  &&
                Math.abs(playerLoc.getZ() - capturePoint.getZ()) <= captureRadius ) {
            if (!capturingPlayers.containsKey(player.getUniqueId())) {
                capturingPlayers.put(player.getUniqueId(), playerTeam);
                player.sendMessage("TI ZASHEL");
                startCaptureTimer(playerTeam);
            }
        } else {
            if (capturingPlayers.containsKey(player.getUniqueId())) {
                capturingPlayers.remove(player.getUniqueId());
                player.sendMessage("TI VISHEL");
                capturingPlayers.clear();
            }
        }
    }

    private void startCaptureTimer(Team capturingTeam) {
        new BukkitRunnable() {
            int time = 0;

            @Override
            public void run() {
                if (capturingPlayers.values().stream().anyMatch(capturingTeam::equals)) {
                    time++;
                    for (UUID playeruuid : capturingPlayers.keySet()){
                        Player player = Bukkit.getPlayer(playeruuid);
                        player.sendMessage(String.valueOf(time));
                    }
                    if (time >= 10) {
                        Bukkit.broadcastMessage(capturingTeam.getName() + " has captured the point!");
                        cancel();
                        time = 0;
                        capturingPlayers.clear();
                    }
                } else {
                    cancel();
                    time = 0;
                    capturingPlayers.clear();
                }
            }
        }.runTaskTimer(Asd.getInstance(), 0, 20);
    }


}
















//    private Map<UUID, PluginTeam> players;
//    private Plugin plugin;
//    private Location hillLocation;
//    private Map<UUID, Long> playersOnHill = new HashMap<>();
//    public KOTH(Map<UUID, PluginTeam> players, Plugin plugin,Location hillLocation) {
//        this.players = players;
//        this.plugin = plugin;
//        this.hillLocation = hillLocation;
//    }
//
//    @Override
//    public void run() {
//        for (UUID playerUUID : playersOnHill.keySet()) {
//            Player player = Bukkit.getPlayer(playerUUID);
//            if (player != null) {
//                if (System.currentTimeMillis() - playersOnHill.get(playerUUID) >= 300000) {
//                    player.sendMessage("You are the King of the Hill!");
//                    playersOnHill.remove(playerUUID);
//                }
//            } else {
//                playersOnHill.remove(playerUUID);
//            }
//        }
//    }
//
//    @EventHandler
//    public void onPlayerMove(PlayerMoveEvent event) {
//        Player player = event.getPlayer();
//        Location from = event.getFrom();
//        Location to = event.getTo();
//        if (from.getBlockX() == to.getBlockX() && from.getBlockZ() == to.getBlockZ()) {
//            return;
//        }
//        if (to.getBlockY() >= kingOfTheHillTask.getHillLocation().getBlockY()) {
//            kingOfTheHillTask.addPlayerToHill(player);
//        } else {
//            kingOfTheHillTask.removePlayerFromHill(player);
//        }
//    }

