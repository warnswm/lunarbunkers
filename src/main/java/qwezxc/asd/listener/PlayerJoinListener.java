package qwezxc.asd.listener;

import me.tigerhix.lib.scoreboard.ScoreboardLib;
import me.tigerhix.lib.scoreboard.type.Scoreboard;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import qwezxc.asd.Asd;
import qwezxc.asd.core.*;

import java.util.List;

public class PlayerJoinListener implements Listener {


    public final Asd main;
    private PlayerLivesManager playerLivesManager;
    private PlayerKillsManager playerKillsManager;
    private GameManager gameManager;
    private ScoreBoardLib scoreBoardLib;
    private final Location lobby = new Location(Bukkit.getWorld("world"),0.5,65,206.5);
    public PlayerJoinListener(final Asd main,PlayerLivesManager playerLivesManager,GameManager gameManager,ScoreBoardLib scoreBoardLib,PlayerKillsManager playerKillsManager) {
        this.main = main;
        this.playerLivesManager = playerLivesManager;
        this.gameManager = gameManager;
        this.scoreBoardLib = scoreBoardLib;
        this.playerKillsManager = playerKillsManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.setJoinMessage(null);
        Player player = event.getPlayer();
        player.teleport(lobby);
        player.setGameMode(GameMode.SURVIVAL);
        main.getPluginManager().getDatabase().addPlayertoDatabase(player);
        main.getPluginManager().getEconomyDataBaseOld().addPlayer(player.getUniqueId(),player.getName());
        scoreBoardLib.sendScoreBoard(player,playerLivesManager, playerKillsManager,main.teams);
        playerLivesManager.givePlayerLives(player, 3);
        if (Bukkit.getOnlinePlayers().size() == 2) {
            gameManager.execute();
        }
        if (Bukkit.getOnlinePlayers().isEmpty()){
            Bukkit.getScheduler().runTaskLater(main, CitizensAPI.getNPCRegistry()::deregisterAll, 2);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (Bukkit.getOnlinePlayers().size() != 4) {
            gameManager.stopGameStartTimer();
        }
    }

}
