package qwezxc.asd.core;

import com.google.common.collect.Lists;
import me.tigerhix.lib.scoreboard.type.Entry;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import qwezxc.asd.Asd;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class KOTH {

    private Asd main;
    private Teams teams;
    private final List<UUID> capturingPlayers;
    private BukkitRunnable captureTimer;
    public static int timeLeft = 300;
    private static final Map<Integer, String> TIMES_TO_CLEAR = Map.of(
            299, "Classic: 5:00",
            239, "Classic: 4:00",
            179, "Classic: 3:00",
            119, "Classic: 2:00",
            59, "Classic: 1:00"
    );

    public KOTH(Asd main, Teams teams) {
        this.main = main;
        this.teams = teams;
        this.capturingPlayers = new ArrayList<>();
    }

    private void startCaptureTimer() {
        if (captureTimer == null) {
            captureTimer = new BukkitRunnable() {

                @Override
                public void run() {
                    timeLeft--;
                    if (timeLeft == 0) {
                        Team capturingTeam = teams.getTeam(capturingPlayers.get(0));
                        Bukkit.broadcastMessage(ChatColor.GREEN + "The " + capturingTeam.getName() + " team has captured the point!");
                        capturingPlayers.clear();
                        captureTimer.cancel();
                        captureTimer = null;
                        endGame();
                    }
                }
            };
            captureTimer.runTaskTimer(main, 0L, 20L);
        }
    }


    // Method to start the capture point capture for a player
    public void startCapture(Player player) {
        UUID uuid = player.getUniqueId();
        if (capturingPlayers.contains(uuid)) return;
        if(!GameManager.canCapture) return;
        Team playerTeam = teams.getTeam(player);

        if (playerTeam != null) {
            if (capturingPlayers.isEmpty()) {
                capturingPlayers.add(uuid);
                Bukkit.broadcastMessage(ChatColor.GOLD + player.getName() + " from the " + playerTeam.getName() + " team is capturing " + "name" + "!");
                startCaptureTimer();
            } else {
                Team capturingTeam = teams.getTeam(capturingPlayers.get(0));

                if (capturingTeam == playerTeam) {
                    capturingPlayers.add(uuid);
                }
            }
        }
    }


    // Method to stop the capture point capture for a player
    public void stopCapture(Player player) {

        capturingPlayers.remove(player.getUniqueId());

        if (capturingPlayers.isEmpty() && captureTimer != null) {
            captureTimer.cancel();
            captureTimer = null;
            Bukkit.broadcastMessage(ChatColor.RED + "Capture of " + " has been reset!");
            timeLeft = 300;
        }
    }
    private void clearPreviousScores(Scoreboard scoreboard, int minutes, int seconds) {
        int timeLeft = minutes * 60 + seconds;
        if (TIMES_TO_CLEAR.containsKey(timeLeft)) {
            scoreboard.resetScores(Bukkit.getOfflinePlayer(TIMES_TO_CLEAR.get(timeLeft)));
        }
    }

    private void endGame(){
        Bukkit.getScheduler().runTaskLater(main, CitizensAPI.getNPCRegistry()::deregisterAll, 2);
    }
}


//            if (!capturingPlayers.containsKey(player.getUniqueId())) {
//                capturingPlayers.put(player.getUniqueId(), playerTeam);
//                player.sendMessage("TI ZASHEL");
//                startCaptureTimer(playerTeam);
//            }
//        } else {
//            if (capturingPlayers.containsKey(player.getUniqueId())) {
//                capturingPlayers.remove(player.getUniqueId());
//                player.sendMessage("TI VISHEL");
//                capturingPlayers.clear();
//            }
//        }
//    }
//
//    private void startCaptureTimer(Team capturingTeam) {
//        new BukkitRunnable() {
//            int time = 0;
//
//            @Override
//            public void run() {
//                if (capturingPlayers.values().stream().anyMatch(capturingTeam::equals)) {
//                    time++;
//                    for (UUID playeruuid : capturingPlayers.keySet()){
//                        Player player = Bukkit.getPlayer(playeruuid);
//                        player.sendMessage(String.valueOf(time));
//                    }
//                    if (time >= 10) {
//                        Bukkit.broadcastMessage(capturingTeam.getName() + " has captured the point!");
//                        cancel();
//                        time = 0;
//                        capturingPlayers.clear();
//                    }
//                } else {
//                    cancel();
//                    time = 0;
//                    capturingPlayers.clear();
//                }
//            }
//        }.runTaskTimer(Asd.getInstance(), 0, 20);
//    }
//


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

