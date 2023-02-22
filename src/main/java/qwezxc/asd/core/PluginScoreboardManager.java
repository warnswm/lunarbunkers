package qwezxc.asd.core;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import qwezxc.asd.Asd;

import java.util.UUID;

public class PluginScoreboardManager {

    private PlayerLivesManager playerLivesManager;
    public PluginScoreboardManager() {
        playerLivesManager = new PlayerLivesManager();
    }

    public void createScoreboard(Player player){
        UUID uuid = player.getUniqueId();
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("Bunkers", "dummy");
        objective.setDisplayName("Bunkers");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score score =  objective.getScore("Classic: " + String.format("%d:%02d", 5,0));
        score.setScore(7);
        Score score7 = objective.getScore(" " + "" + " ");
        score7.setScore(6);
        Score score6 = objective.getScore("Team: Выбери команду!" );
        score6.setScore(5);

        // дада это надо менять
        // getRemainingLives dasn't work idk
        Score score5 = objective.getScore("Осталось жизней: " + 3);
        score5.setScore(4);
        Score score4 = objective.getScore(" ");
        score4.setScore(3);
        Score score3 = objective.getScore("Kills: " + Asd.getInstance().getPluginManager().getDatabase().getWins(String.valueOf(uuid)));
        if(score3 == null){
            objective.getScore("Kills: null Сообщите об этом админу");
        }
        score3.setScore(2);
        Score score2 = objective.getScore("Balance: " + Asd.getInstance().getPluginManager().getEconomy().getBalance(player));
        if(score2 == null){
            objective.getScore("Balance: null Сообщите об этом админу");
        }
        score2.setScore(1);
        Score score1 = objective.getScore("―――――――――――――");
        score1.setScore(0);
        player.setScoreboard(scoreboard);
    }

    public void removeScoreboard(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        if (scoreboard != null) {
            Objective objective = scoreboard.getObjective("Bunkers");
            if (objective != null) {
                objective.unregister();
            }
            player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }
    }

}