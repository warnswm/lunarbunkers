package qwezxc.asd.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import qwezxc.asd.Asd;
import qwezxc.asd.Team;
import qwezxc.asd.Teams;
import java.util.UUID;
public class TeamMenuListener implements Listener {
    private Teams teams;

    public TeamMenuListener(Teams teams) {
        this.teams = teams;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();
        ItemStack item = event.getCurrentItem();
        Inventory inventory = event.getInventory();
        if (item == null || item.getType() == Material.AIR) {
            return;
        }
        if (inventory.getTitle().equals("Team Select")) {
            event.setCancelled(true);
            if (item == null) return;
            for (Team team : teams.getTeams().values()) {
                if (item.getType() == team.getWoolBlock()) {
                    Scoreboard scoreboard = player.getScoreboard();
                    Objective objective = scoreboard.getObjective("Bunkers");
                    scoreboard.resetScores(Bukkit.getOfflinePlayer("Team: Выбери команду!" ));
                    Team teamdo = teams.getTeam(player);
                    if(teamdo != null) {
                        String teamdoo = teamdo.getName();
                        scoreboard.resetScores(Bukkit.getOfflinePlayer("Team: " + teamdoo));
                    }
                    teams.addPlayerToTeam(player, team);
                    Team teamafter = teams.getTeam(player);
                    String teamafterr = teamafter.getName();
                    Score score5 = objective.getScore("Team: " + teamafterr);
                    score5.setScore(5);
                    player.sendMessage("You joined the " + team.getName() + " team!");
                    break;
                }
            }
        }
    }
}

