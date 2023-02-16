package qwezxc.asd.listener;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.*;
import qwezxc.asd.Asd;
import qwezxc.asd.Data.Database;

public class PlayerJoinListener implements Listener {

    public Database database;

    public Asd main;
    private Scoreboard board;
    public PlayerJoinListener(final Asd main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        main.getPluginManager().getDatabase().addPlayertoDatabase(player);
        main.getPluginManager().getEconomy().addPlayer(player.getUniqueId(),player.getName());
        main.getPluginManager().getScoreboardManager().createScoreboard(player);
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        player.setScoreboard(scoreboard);
    }

}
