package qwezxc.asd.core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import qwezxc.asd.Asd;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameManager {
    private static final int CAPTURE_POINT_ACTIVATE_5MIN = 300;
    private static final int CAPTURE_POINT_CHANGE_TIME_15MIN = 900;
    private static final int CAPTURE_POINT_CHANGE_TIME_25MIN = 1500;
    public static boolean canCapture = false;
    public static int gameTime = 0;
    private final Asd main;
    private final Teams teams;
    private Team redTeam;
    private Team greenTeam;
    private Team blueTeam;
    private Team yellowTeam;
    private BukkitRunnable gameStartTimer;
    private final TeamNPC teamNPC;

    public GameManager(Asd main, Teams teams, TeamNPC teamNPC) {
        this.teams = teams;
        redTeam = teams.getTeamByName("Red");
        greenTeam = teams.getTeamByName("Green");
        blueTeam = teams.getTeamByName("Blue");
        yellowTeam = teams.getTeamByName("Yellow");
        this.main = main;
        this.teamNPC = teamNPC;
    }
    public void execute() {
        int numPlayers = Bukkit.getOnlinePlayers().size();
//        if(numPlayers < 2){
//            for (Player player : Bukkit.getOnlinePlayers()){
//                player.sendMessage("Для запуска нужно 2 человека");
//            }
//            return;
//        }
        // Start
        gameStartTimer = new BukkitRunnable() {
            int countdown = 3;

            @Override
            public void run() {
                if (countdown == 0) {
                    // Start the game
                    startGame();
                    for (Player player1 : Bukkit.getOnlinePlayers()){
                        Asd.getInstance().getPluginManager().getEconomy().addBalance(player1, 10000);
                    }
                    Asd.getInstance().getPluginManager().getDefaultListener().playerLocationChecker();
                    cancel();
                } else {
                    // Countdown message
                    Bukkit.broadcastMessage(ChatColor.GREEN + "Game starting in " + countdown + " seconds.");
                    countdown--;
                }
            }
        };
        gameStartTimer.runTaskTimer(main, 0, 20);
    }

    private void startGame() {
        Map<Team, Integer> teamSizes = new HashMap<>();
        teamSizes.put(redTeam, teams.getNumPlayersInTeam(redTeam));
        teamSizes.put(greenTeam, teams.getNumPlayersInTeam(greenTeam));
        teamSizes.put(blueTeam, teams.getNumPlayersInTeam(blueTeam));
        teamSizes.put(yellowTeam, teams.getNumPlayersInTeam(yellowTeam));

        for (Player player : Bukkit.getOnlinePlayers()) {
            Team playerTeam = teams.getTeam(player);
            if (playerTeam == null) {
                Team smallestTeam = Collections.min(teamSizes.entrySet(), Map.Entry.comparingByValue()).getKey();
                teams.addPlayerToTeam(player, smallestTeam);
                playerTeam = smallestTeam; // assign the newly added team to the playerTeam variable
                updatePlayerListName(player);
            }

            // update the number of players for the player's team
            int teamSize = teams.getNumPlayersInTeam(playerTeam);
            teamSizes.put(playerTeam, teamSize);
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            Team playerTeam = teams.getTeam(player);
            player.closeInventory();
            if (playerTeam != null) {
                player.teleport(playerTeam.getBase());
                player.sendMessage("Игра началась!");
            }
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                gameTime++;
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Asd.getInstance().getPluginManager().getEconomy().addBalance(player, 1);
                }
                switch (gameTime) {
                    case CAPTURE_POINT_ACTIVATE_5MIN:
                        canCapture = true;
                        KOTH.timeLeft = 450;
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.sendMessage(ChatColor.ITALIC + "Точка захвата стала активна");
                        }
                        break;
                    case CAPTURE_POINT_CHANGE_TIME_15MIN:
                        if(KOTH.timeLeft < 300) return;
                        KOTH.timeLeft = 300;
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.sendMessage(ChatColor.ITALIC + "Теперь точку захвата можно захватить за 5:00");
                        }
                        break;
                    case CAPTURE_POINT_CHANGE_TIME_25MIN:
                        if(KOTH.timeLeft < 150) return;
                        KOTH.timeLeft = 150;
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.sendMessage(ChatColor.ITALIC + "Теперь точку захвата можно захватить за 2:30");
                        }
                        break;
                    default:
                        break;
                }
            }
        }.runTaskTimer(main, 20L, 20L);
        teamNPC.spawnAll();
    }

    public void stopGameStartTimer() {
        if (gameStartTimer != null) {
            gameStartTimer.cancel();
        }
    }

    private void updatePlayerListName(Player player) {
        Team playerTeam = teams.getTeam(player);
        if (playerTeam != null) {
            ChatColor colorteam = playerTeam.getChatColor();
            player.setPlayerListName("[" + colorteam + playerTeam.getName() + ChatColor.RESET + "]" + " " + player.getName());
        }
    }
}
