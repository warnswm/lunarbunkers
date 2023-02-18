package qwezxc.asd.core;

import com.comphenix.protocol.PacketType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import qwezxc.asd.Asd;

public class GameManager {
    private Asd  main;
    private Team redTeam;
    private Team greenTeam;
    private Team blueTeam;
    private Team yellowTeam;
    private Teams teams;
    private BukkitRunnable gameStartTimer;

    private int gameTime;
    public GameManager(Asd main, Teams teams) {
        this.teams = teams;
        for (Team team : teams.getTeams().values()) {
            switch (team.getName()) {
                case "Red":
                    redTeam = team;
                    break;
                case "Green":
                    greenTeam = team;
                    break;
                case "Blue":
                    blueTeam = team;
                    break;
                case "Yellow":
                    yellowTeam = team;
                    break;
                default:
                    // Handle any other teams here
                    break;
            }
        }
        this.main = main;
        this.gameTime = 0;
    }
    public void execute() {
        int numPlayers = Bukkit.getOnlinePlayers().size();
        if(numPlayers < 2){
            for (Player player : Bukkit.getOnlinePlayers()){
                player.sendMessage("Для запуска нужно 2 человека");
            }
            return;
        }
        // Start the game after a 5 second countdown
        gameStartTimer = new BukkitRunnable() {
            int countdown = 5;

            @Override
            public void run() {
                if (countdown == 0) {
                    // Start the game
                    startGame();
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
        //updateScoreboard();

        // Teleport players to their bases
        Location redBase = new Location(Bukkit.getWorlds().get(0), 0, 65, 100);
        Location greenBase = new Location(Bukkit.getWorlds().get(0), -100, 65, 0);
        Location blueBase = new Location(Bukkit.getWorlds().get(0), -100, 65, 100);
        Location yellowBase = new Location(Bukkit.getWorlds().get(0), 0, 65, 0);

        for (Player player : Bukkit.getOnlinePlayers()) {
            Team playerTeam = teams.getTeam(player);

            if (playerTeam == null) {
                // Put the player on the team with the fewest players
                if (teams.getNumPlayersInTeam(redTeam) < teams.getNumPlayersInTeam(greenTeam) &&
                        teams.getNumPlayersInTeam(redTeam) < teams.getNumPlayersInTeam(blueTeam) &&
                        teams.getNumPlayersInTeam(redTeam) < teams.getNumPlayersInTeam(yellowTeam)) {
                    Scoreboard scoreboard = player.getScoreboard();
                    Objective objective = scoreboard.getObjective("Bunkers");
                    scoreboard.resetScores(Bukkit.getOfflinePlayer("Team: Выбери команду!" ));
                    teams.addPlayerToTeam(player, redTeam);
                    Team teamafter = teams.getTeam(player);
                    String teamafterr = teamafter.getName();
                    Score score5 = objective.getScore("Team: " + teamafterr);
                    score5.setScore(5);
                    Team playerTeamafter = teams.getTeam(player);
                    ChatColor colorteam = playerTeamafter.getChatColor();
                    player.setPlayerListName("[" + colorteam + playerTeamafter.getName() + ChatColor.RESET + "]" + " " + player.getName());
                } else if (teams.getNumPlayersInTeam(greenTeam) < teams.getNumPlayersInTeam(blueTeam) &&
                        teams.getNumPlayersInTeam(greenTeam) < teams.getNumPlayersInTeam(yellowTeam)) {
                    Scoreboard scoreboard = player.getScoreboard();
                    Objective objective = scoreboard.getObjective("Bunkers");
                    scoreboard.resetScores(Bukkit.getOfflinePlayer("Team: Выбери команду!" ));
                    teams.addPlayerToTeam(player, greenTeam);
                    Team teamafter = teams.getTeam(player);
                    String teamafterr = teamafter.getName();
                    Score score5 = objective.getScore("Team: " + teamafterr);
                    score5.setScore(5);
                    Team playerTeamafter = teams.getTeam(player);
                    ChatColor colorteam = playerTeamafter.getChatColor();
                    player.setPlayerListName("[" + colorteam + playerTeamafter.getName() + ChatColor.RESET + "]" + " " + player.getName());
                } else if (teams.getNumPlayersInTeam(blueTeam) < teams.getNumPlayersInTeam(yellowTeam)) {
                    Scoreboard scoreboard = player.getScoreboard();
                    Objective objective = scoreboard.getObjective("Bunkers");
                    scoreboard.resetScores(Bukkit.getOfflinePlayer("Team: Выбери команду!" ));
                    teams.addPlayerToTeam(player, blueTeam);
                    Team teamafter = teams.getTeam(player);
                    String teamafterr = teamafter.getName();
                    Score score5 = objective.getScore("Team: " + teamafterr);
                    score5.setScore(5);

                    Team playerTeamafter = teams.getTeam(player);
                    ChatColor colorteam = playerTeamafter.getChatColor();
                    player.setPlayerListName("[" + colorteam + playerTeamafter.getName() + ChatColor.RESET + "]" + " " + player.getName());
                } else {
                    Scoreboard scoreboard = player.getScoreboard();
                    Objective objective = scoreboard.getObjective("Bunkers");
                    scoreboard.resetScores(Bukkit.getOfflinePlayer("Team: Выбери команду!" ));
                    teams.addPlayerToTeam(player, yellowTeam);
                    Team teamafter = teams.getTeam(player);
                    String teamafterr = teamafter.getName();
                    Score score5 = objective.getScore("Team: " + teamafterr);
                    score5.setScore(5);
                    Team playerTeamafter = teams.getTeam(player);
                    ChatColor colorteam = playerTeamafter.getChatColor();
                    player.setPlayerListName("[" + colorteam + playerTeamafter.getName() +  ChatColor.RESET + "]" + " " + player.getName());
                }
            }
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            Team playerTeam = teams.getTeam(player);

            // Teleport the player to their team's base
            if (playerTeam == redTeam) {
                player.teleport(redBase);
            } else if (playerTeam == greenTeam) {
                player.teleport(greenBase);
            } else if (playerTeam == blueTeam) {
                player.teleport(blueBase);
            } else if (playerTeam == yellowTeam) {
                player.teleport(yellowBase);
            }

            // Start the game
            player.sendMessage("The game has started!");
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                gameTime++;
                int minutes = gameTime / 60;
                int seconds = gameTime % 60;
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Scoreboard scoreboard = player.getScoreboard();
                    Objective objective = scoreboard.getObjective("Bunkers");
                    if(seconds == 0) {
                        scoreboard.resetScores(Bukkit.getOfflinePlayer("Game Time: " + String.format("%02d:%02d", minutes-1, 59)));
                    }
                    scoreboard.resetScores(Bukkit.getOfflinePlayer("Game Time: " + String.format("%02d:%02d", minutes, seconds-1)));
                    Score score =  objective.getScore("Game Time: " + String.format("%02d:%02d", minutes, seconds));
                    score.setScore(8);
                    player.setScoreboard(scoreboard);
                }
            }
        }.runTaskTimer(main, 20L, 20L);
    }

    public void stopGameStartTimer() {
        if (gameStartTimer != null) {
            gameStartTimer.cancel();
        }
    }

}
