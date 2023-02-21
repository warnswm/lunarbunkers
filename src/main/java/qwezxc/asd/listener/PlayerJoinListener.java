package qwezxc.asd.listener;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import qwezxc.asd.Asd;
import qwezxc.asd.core.GameManager;
import qwezxc.asd.core.PlayerLivesManager;

public class PlayerJoinListener implements Listener {


    public Asd main;
    private PlayerLivesManager playerLivesManager;
    private GameManager gameManager;
    private World world;
    public PlayerJoinListener(final Asd main,PlayerLivesManager playerLivesManager,GameManager gameManager) {
        this.main = main;
        this.playerLivesManager = playerLivesManager;
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.setJoinMessage(null);
        Player player = event.getPlayer();
        main.getPluginManager().getDatabase().addPlayertoDatabase(player);
        main.getPluginManager().getEconomy().addPlayer(player.getUniqueId(),player.getName());
        main.getPluginManager().getScoreboardManager().createScoreboard(player);
        playerLivesManager.givePlayerLives(player, 3);
        if (Bukkit.getOnlinePlayers().size() == 2) {
            gameManager.execute();
        }
        world = player.getWorld();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        main.getPluginManager().getScoreboardManager().removeScoreboard(player);
        if (Bukkit.getOnlinePlayers().size() != 4) {
            gameManager.stopGameStartTimer();
        }
    }

    public World getWorld() {
        return this.world;
    }
}
