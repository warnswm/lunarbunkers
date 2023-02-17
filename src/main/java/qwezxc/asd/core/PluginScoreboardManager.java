package qwezxc.asd.core;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import qwezxc.asd.Asd;
import qwezxc.asd.Team;

import java.util.UUID;

public class PluginScoreboardManager {
    private Scoreboard scoreboard;
    private Objective objective;

    public PluginScoreboardManager() {
    }

    public void updateScore(String playerName, int score) {
        Score playerScore = objective.getScore(playerName);
        playerScore.setScore(score);
    }

    public void createScoreboard(Player player){
        UUID uuid = player.getUniqueId();
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("Bunkers", "dummy");
        objective.setDisplayName("Bunkers");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score score =  objective.getScore("Classic: " + String.format("%d:%02d", 5,0));
        score.setScore(8);
        Score score7 = objective.getScore("");
        score7.setScore(7);
        Score score6 = objective.getScore("Team: Выбери команду!" );
        score6.setScore(6);
        score7.setScore(4);
        Score score3 = objective.getScore("Kills: " + Asd.getInstance().getPluginManager().getDatabase().getWins(String.valueOf(uuid)));
        if(score3 == null){
            objective.getScore("Kills: null Сообщите об этом админу");
        }
        score3.setScore(3);
        Score score2 = objective.getScore("Balance: " + Asd.getInstance().getPluginManager().getEconomy().getBalance(uuid));
        if(score2 == null){
            objective.getScore("Balance: null Сообщите об этом админу");
        }
        score2.setScore(2);
        Score score1 = objective.getScore("―――――――――――――");
        score1.setScore(1);
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

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }
    public Objective getObjective(){return this.objective;}
}