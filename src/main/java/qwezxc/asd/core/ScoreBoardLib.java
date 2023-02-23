package qwezxc.asd.core;

import me.tigerhix.lib.scoreboard.ScoreboardLib;
import me.tigerhix.lib.scoreboard.common.EntryBuilder;
import me.tigerhix.lib.scoreboard.common.Strings;
import me.tigerhix.lib.scoreboard.common.animate.HighlightedString;
import me.tigerhix.lib.scoreboard.common.animate.ScrollableString;
import me.tigerhix.lib.scoreboard.type.Entry;
import me.tigerhix.lib.scoreboard.type.Scoreboard;
import me.tigerhix.lib.scoreboard.type.ScoreboardHandler;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import qwezxc.asd.Asd;
import qwezxc.asd.listener.DefaultListener;

import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

public class ScoreBoardLib {
    public void sendScoreBoard(Player player,PlayerLivesManager playerLivesManager,PlayerKillsManager playerKillsManager,Teams teams) {
        UUID uuid = player.getUniqueId();
        Scoreboard scoreboard = ScoreboardLib.createScoreboard(player)
                .setHandler(new ScoreboardHandler() {

                    @Override
                    public String getTitle(Player player) {
                        return null;
                    }

                    @Override
                    public List<Entry> getEntries(Player player) {
                        return new EntryBuilder()
                                .next("&aBunkers")
                                .blank()
                                .next("Game Time: " + String.format("%d:%02d", GameManager.gameTime / 60, GameManager.gameTime % 60))
                                .next("Classic: " + String.format("%d:%02d", KOTH.timeLeft / 60, KOTH.timeLeft % 60))
                                .blank()
                                .next("Team: " + getTeamforScoreBoard(teams,player))
                                .next("Осталось жизней: " + playerLivesManager.getRemainingLives(player))
                                .blank()
                                .next("Kills: " + playerKillsManager.getPKills(player) )
                                .next("Balance: " + Asd.getInstance().getPluginManager().getEconomy().getBalance(player))
                                .blank()
                                .build();
                    }

                })
                .setUpdateInterval(2l);
        scoreboard.activate();
    }
    public void enderpearlScoreBoard(Player player,PlayerLivesManager playerLivesManager,PlayerKillsManager playerKillsManager, Teams teams) {

        UUID uuid = player.getUniqueId();
        Scoreboard scoreboard = ScoreboardLib.createScoreboard(player)
                .setHandler(new ScoreboardHandler() {

                    DecimalFormat df = new DecimalFormat("#.##");
                    @Override
                    public String getTitle(Player player) {
                        return null;
                    }

                    @Override
                    public List<Entry> getEntries(Player player) {
                        return new EntryBuilder()
                                .next("&aBunkers")
                                .blank()
                                .next("Game Time: " + String.format("%d:%02d", GameManager.gameTime / 60, GameManager.gameTime % 60))
                                .next("Classic: " + String.format("%d:%02d", KOTH.timeLeft / 60, KOTH.timeLeft % 60))
                                .blank()
                                .next("Team: " + getTeamforScoreBoard(teams,player))
                                .next("Осталось жизней: " + playerLivesManager.getRemainingLives(player))
                                .blank()
                                .next("Kills: " + playerKillsManager.getPKills(player))
                                .next("Balance: " + Asd.getInstance().getPluginManager().getEconomy().getBalance(player))
                                .next("Ender Pearl: " + df.format(DefaultListener.cooldowntime) +"s")
                                .blank()
                                .build();
                    }

                })
                .setUpdateInterval(2l);
        scoreboard.activate();
    }

    private String getTeamforScoreBoard(Teams teams,Player player){
        Team playerTeam = teams.getTeam(player);
        String pTeam;
        if (playerTeam == null){
            pTeam = "Выберите команду";
            return pTeam;
        }
        pTeam = playerTeam.getName();
        return pTeam;
    }
}
