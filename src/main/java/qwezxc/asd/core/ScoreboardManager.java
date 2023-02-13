package qwezxc.asd.core;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardManager {
    private Scoreboard scoreboard;
    private Objective objective;

    public ScoreboardManager() {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("score", "dummy");
        objective.setDisplayName("Score");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void updateScore(String playerName, int score) {
        Score playerScore = objective.getScore(playerName);
        playerScore.setScore(score);
    }

    public void removeScore(String playerName) {
        scoreboard.resetScores(playerName);
    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }
}
