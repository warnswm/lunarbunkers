package qwezxc.asd.core;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import qwezxc.asd.Asd;
import qwezxc.asd.Team;

import java.util.UUID;

public class PluginScoreboardManager {
    private Scoreboard scoreboard;
    private Objective objective;

    public PluginScoreboardManager() {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("score", "dummy");
        objective.setDisplayName("Score");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void updateScore(String playerName, int score) {
        Score playerScore = objective.getScore(playerName);
        playerScore.setScore(score);
    }

    public void createScoreboard(Player player){
        UUID uuid = player.getUniqueId();
        objective.setDisplayName("Bunkers");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score score =  objective.getScore("Classic: " + String.format("%d:%02d", 5,0));
        score.setScore(7);
        Score score5 = objective.getScore("Team: " + Asd.getInstance().getPluginManager().getTeams().getTeam(player));
        score5.setScore(5);
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
        player.setScoreboard(null);
    }
    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }
    public Objective getObjective(){return this.objective;}
}