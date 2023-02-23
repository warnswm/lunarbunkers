package qwezxc.asd.core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import qwezxc.asd.Asd;

public class GameManager {
    private final Asd  main;
    private Team redTeam;
    private Team greenTeam;
    private Team blueTeam;
    private Team yellowTeam;
    private final Teams teams;
    private BukkitRunnable gameStartTimer;
    private final TeamNPC teamNPC;

    public static int gameTime = 0;
    public GameManager(Asd main, Teams teams,TeamNPC teamNPC) {
        this.teams = teams;
        for (Team team : teams.getTeams()) {
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
        this.teamNPC=teamNPC;
    }
    public void execute() {
        int numPlayers = Bukkit.getOnlinePlayers().size();
//        if(numPlayers < 2){
//            for (Player player : Bukkit.getOnlinePlayers()){
//                player.sendMessage("Для запуска нужно 2 человека");
//            }
//            return;
//        }
        // Start the game after a 5 second countdown
        gameStartTimer = new BukkitRunnable() {
            int countdown = 5;

            @Override
            public void run() {
                if (countdown == 0) {
                    // Start the game
                    startGame();
                    for (Player player1 : Bukkit.getOnlinePlayers()){
                        Asd.getInstance().getPluginManager().getEconomy().addBalance(player1,100);
                    }
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

        for (Player player : Bukkit.getOnlinePlayers()) {

            Team playerTeam = teams.getTeam(player);

            if (playerTeam == null) {
                if (teams.getNumPlayersInTeam(redTeam) < teams.getNumPlayersInTeam(greenTeam) &&
                        teams.getNumPlayersInTeam(redTeam) < teams.getNumPlayersInTeam(blueTeam) &&
                        teams.getNumPlayersInTeam(redTeam) < teams.getNumPlayersInTeam(yellowTeam)) {

                    teams.addPlayerToTeam(player, redTeam);
                    Team playerTeamafter = teams.getTeam(player);
                    ChatColor colorteam = playerTeamafter.getChatColor();
                    player.setPlayerListName("[" + colorteam + playerTeamafter.getName() + ChatColor.RESET + "]" + " " + player.getName());
                } else if (teams.getNumPlayersInTeam(greenTeam) < teams.getNumPlayersInTeam(blueTeam) &&
                        teams.getNumPlayersInTeam(greenTeam) < teams.getNumPlayersInTeam(yellowTeam)) {
                    teams.addPlayerToTeam(player, greenTeam);
                    Team playerTeamafter = teams.getTeam(player);
                    ChatColor colorteam = playerTeamafter.getChatColor();
                    player.setPlayerListName("[" + colorteam + playerTeamafter.getName() + ChatColor.RESET + "]" + " " + player.getName());
                } else if (teams.getNumPlayersInTeam(blueTeam) < teams.getNumPlayersInTeam(yellowTeam)) {
                    teams.addPlayerToTeam(player, blueTeam);
                    Team playerTeamafter = teams.getTeam(player);
                    ChatColor colorteam = playerTeamafter.getChatColor();
                    player.setPlayerListName("[" + colorteam + playerTeamafter.getName() + ChatColor.RESET + "]" + " " + player.getName());
                } else {
                    teams.addPlayerToTeam(player, yellowTeam);
                    Team playerTeamafter = teams.getTeam(player);
                    ChatColor colorteam = playerTeamafter.getChatColor();
                    player.setPlayerListName("[" + colorteam + playerTeamafter.getName() + ChatColor.RESET + "]" + " " + player.getName());
                }
            }
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            Team playerTeam = teams.getTeam(player);

            // Teleport the player to their team's base
            if (playerTeam == redTeam) {
                player.teleport(playerTeam.getBase());
            } else if (playerTeam == greenTeam) {
                player.teleport(playerTeam.getBase());
            } else if (playerTeam == blueTeam) {
                player.teleport(playerTeam.getBase());
            } else if (playerTeam == yellowTeam) {
                player.teleport(playerTeam.getBase());
            }

            // Start the game
            player.sendMessage("The game has started!");
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                gameTime++;
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Asd.getInstance().getPluginManager().getEconomy().addBalance(player, 1);
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
}
