package qwezxc.asd.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import qwezxc.asd.Asd;
import qwezxc.asd.core.PlayerLives;
import qwezxc.asd.core.PlayerLivesManager;

import java.util.Map;

public class PlayerJoinListener implements Listener {


    public Asd main;
    private PlayerLivesManager playerLivesManager;
    public PlayerJoinListener(final Asd main,PlayerLivesManager playerLivesManager) {
        this.main = main;
        this.playerLivesManager = playerLivesManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.setJoinMessage(null);
        Player player = event.getPlayer();
        main.getPluginManager().getDatabase().addPlayertoDatabase(player);
        main.getPluginManager().getEconomy().addPlayer(player.getUniqueId(),player.getName());
        main.getPluginManager().getScoreboardManager().createScoreboard(player);
        playerLivesManager.givePlayerLives(player, 3);
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        main.getPluginManager().getScoreboardManager().removeScoreboard(player);
    }

}
