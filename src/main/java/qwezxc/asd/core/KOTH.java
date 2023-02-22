package qwezxc.asd.core;

import com.google.common.collect.Lists;
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

public class KOTH {
    private Asd main;
    private Teams teams;
    private final Map<Player, Integer> playerPoints = new HashMap<>();
    private Location capturePoint = new Location(Bukkit.getWorld("world"), 10.5, 41.5, 10.5);
    private Scoreboard scoreboard;
    private Map<Team, Integer> captureTimers = new HashMap<>();
    private final Map<Team, Integer> scores;
    private final List<UUID> capturingPlayers;
    private BukkitRunnable captureTimer;
    private int minutes;
    private int seconds;


    public KOTH(Asd main, Teams teams) {
        this.main = main;
        this.teams = teams;
        this.scores = new HashMap<>();
        this.capturingPlayers = new ArrayList<>();
    }

    private boolean isBeingCaptured(Player playeri) {
        Team playerTeam = teams.getTeam(playeri);
        for (UUID uuid : capturingPlayers) {
            Player player = Bukkit.getPlayer(uuid);
            Location playerLoc = player.getLocation();
            if (Math.abs(playerLoc.getX() - capturePoint.getX()) <= 5 &&
                    Math.abs(playerLoc.getY() - capturePoint.getY()) <= 5 &&
                    Math.abs(playerLoc.getZ() - capturePoint.getZ()) <= 5 && teams.getTeam(player) == playerTeam) {
                return true;
            }
        }
        return false;
    }

    private void startCaptureTimer() {
        // Only start the timer if it isn't already running
        if (captureTimer == null) {
            captureTimer = new BukkitRunnable() {
                int timeLeft = 300; // 5 minutes in ticks

                @Override
                public void run() {
                    timeLeft--;
                    minutes = timeLeft / 60;
                    seconds = timeLeft % 60;
                    UUID uuidplayer = capturingPlayers.get(0);
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        Scoreboard scoreboard = players.getScoreboard();
                        Objective objective = scoreboard.getObjective("Bunkers");
                        Score score = objective.getScore("Classic: " + String.format("%d:%02d", minutes, seconds));
                        if (minutes == 4 && seconds == 59) {
                            scoreboard.resetScores(Bukkit.getOfflinePlayer("Classic: 5:00"));
                        }
                        if (minutes == 3 && seconds == 59) {
                            scoreboard.resetScores(Bukkit.getOfflinePlayer("Classic: 4:00"));
                        }
                        if (minutes == 2 && seconds == 59) {
                            scoreboard.resetScores(Bukkit.getOfflinePlayer("Classic: 3:00"));
                        }
                        if (minutes == 1 && seconds == 59) {
                            scoreboard.resetScores(Bukkit.getOfflinePlayer("Classic: 2:00"));
                        }
                        if (minutes == 0 && seconds == 59) {
                            scoreboard.resetScores(Bukkit.getOfflinePlayer("Classic: 1:00"));
                        }
                        scoreboard.resetScores(Bukkit.getOfflinePlayer("Classic: " + String.format("%d:%02d", minutes, seconds + 1)));
                        score.setScore(7);
                        players.setScoreboard(scoreboard);
                    }

                    // Check if the time has run out
                    if (timeLeft == 0) {
                        Team capturingTeam = teams.getTeam(capturingPlayers.get(0));
                        Bukkit.broadcastMessage(ChatColor.GREEN + "The " + capturingTeam.getName() + " team has captured the point!");
                        capturingPlayers.clear();
                        captureTimer.cancel();
                        captureTimer = null;
                        for (NPC npc : Lists.newArrayList(CitizensAPI.getNPCRegistry())) {
                            npc.destroy();
                        }
                    }
                }
            };
            captureTimer.runTaskTimer(main, 0L, 20L); // Start the timer to run every second
        }
    }


    // Method to start the capture point capture for a player
    public void startCapture(Player player) {
        UUID uuid = player.getUniqueId();
        if (capturingPlayers.contains(uuid)) return;

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
        //pizdec
        OfflinePlayer offlinePlayer = player.getPlayer();

        capturingPlayers.remove(player.getUniqueId());


        if (capturingPlayers.isEmpty() && captureTimer != null) {
            captureTimer.cancel();
            captureTimer = null;
            Bukkit.broadcastMessage(ChatColor.RED + "Capture of " + "" + " has been reset!");
            for (Player players : Bukkit.getOnlinePlayers()) {
                Scoreboard scoreboard = players.getScoreboard();
                Objective objective = scoreboard.getObjective("Bunkers");
                scoreboard.resetScores(Bukkit.getOfflinePlayer("Classic: " + String.format("%d:%02d", minutes, seconds)));
                scoreboard.resetScores(Bukkit.getOfflinePlayer("Classic: " + String.format("%d:%02d", minutes, seconds + 1)));
                Score score = objective.getScore("Classic: " + String.format("%d:%02d", 5, 0));
                score.setScore(7);
                players.setScoreboard(scoreboard);
            } 
            minutes = 5;
            seconds = 0;
        }
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

