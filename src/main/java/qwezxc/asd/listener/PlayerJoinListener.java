package qwezxc.asd.listener;

import lombok.Getter;
import lombok.Setter;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerJoinListener implements Listener {


    public final Asd main;
    private final TeamLivesManager teamLivesManager;
    private final PlayerKillsManager playerKillsManager;
    private final GameManager gameManager;
    private final ScoreBoardLib scoreBoardLib;
    private final Teams teams;
    private final Location lobby = new Location(Bukkit.getWorld("world"), 0.5, 65, 206.5);
    public static final List<Player> joinedPlayers = new ArrayList<>();
    public PlayerJoinListener(final Asd main, TeamLivesManager playerLivesManager, GameManager gameManager, ScoreBoardLib scoreBoardLib, PlayerKillsManager playerKillsManager, Teams teams) {
        this.main = main;
        this.teamLivesManager = playerLivesManager;
        this.gameManager = gameManager;
        this.scoreBoardLib = scoreBoardLib;
        this.playerKillsManager = playerKillsManager;
        this.teams = teams;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.setJoinMessage(null);

        Player player = event.getPlayer();
        player.teleport(lobby);
        if (!player.isOp()) {
            player.setGameMode(GameMode.SURVIVAL);
        }
        main.getPluginManager().getDatabase().addPlayer(player.getUniqueId());
        scoreBoardLib.sendScoreBoard(player, teamLivesManager, playerKillsManager, teams);
        joinedPlayers.add(player);
        if (Bukkit.getOnlinePlayers().size() == 8) {
            gameManager.execute();
        }
        if (Bukkit.getOnlinePlayers().isEmpty()) {
            Bukkit.getScheduler().runTaskLater(main, CitizensAPI.getNPCRegistry()::deregisterAll, 2);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage("");
        Player player = event.getPlayer();
        if(GameManager.gameTime > 0){
            joinedPlayers.remove(player);
        }
        if (Bukkit.getOnlinePlayers().size() != 8) {
            gameManager.stopGameStartTimer();
        }
        if (Bukkit.getOnlinePlayers().size() == 1) {
            //ToDo: Добавить победы если остался 1 игрок
        }
        if (Bukkit.getOnlinePlayers().isEmpty()) {
            Bukkit.getScheduler().runTaskLater(main, CitizensAPI.getNPCRegistry()::deregisterAll, 2);
        }

    }

}
