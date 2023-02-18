package qwezxc.asd.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import qwezxc.asd.core.Team;
import qwezxc.asd.core.Teams;
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
                    if(teams.getNumPlayersInTeam(team) >= team.getMaxPlayers()){
                        player.sendMessage("Max player in team");
                        return;
                    }
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
                    ChatColor colorteam = team.getChatColor();
                    player.setPlayerListName("[" + colorteam + team.getName() + ChatColor.RESET + "]" + " " + player.getName());
                    break;
                }
            }
        }
    }



    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Team team = teams.getTeam(player);
        ChatColor colorteam = ChatColor.WHITE;
        if(team != null) {
            colorteam = team.getChatColor();
        }
        // Add the player's team name before their username in the chat
        if (team != null) {
            String message = String.format("[%s] %s: %s", colorteam + team.getName() + ChatColor.RESET, player.getName(), event.getMessage());
            event.setFormat(message);
        }
    }
}

