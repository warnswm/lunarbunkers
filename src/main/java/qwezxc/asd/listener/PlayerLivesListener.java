package qwezxc.asd.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import qwezxc.asd.core.PlayerLivesManager;

public class PlayerLivesListener implements Listener {
    private PlayerLivesManager playerLivesManager;

    public PlayerLivesListener(PlayerLivesManager playerLivesManager) {
        this.playerLivesManager = playerLivesManager;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        playerLivesManager.takePlayerLives(player, 1);
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("Bunkers");
        scoreboard.resetScores(Bukkit.getOfflinePlayer("Осталось жизней: " + 3));
        //хахах что я сделал короч n = пред значение p = скок жизей сейчас
        int n = playerLivesManager.getRemainingLives(player) + 1;
        int p = playerLivesManager.getRemainingLives(player);
        scoreboard.resetScores(Bukkit.getOfflinePlayer("Осталось жизней: " + n));
        Score score5 = objective.getScore("Осталось жизней: " + p);
        score5.setScore(5);
        player.setScoreboard(scoreboard);
        player.spigot().respawn();

    }

//

}

