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
import qwezxc.asd.core.Team;
import qwezxc.asd.core.Teams;
public class TeamMenuListener implements Listener {
    private Teams teams;

    public TeamMenuListener(Teams teams) {
        this.teams = teams;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        Inventory inventory = event.getInventory();
        if (item == null || item.getType() == Material.AIR) {
            return;
        }
        if (!inventory.getTitle().equals("Team Select")) return;

        event.setCancelled(true);
        if (item == null) return;
        for (Team team : teams.getTeams()) {
            if (item.getType() == team.getWoolBlock()) {
                if(teams.getNumPlayersInTeam(team) >= team.getMaxPlayers()){
                    player.sendMessage("Команда заполнена");
                    return;
                }
                if(team == teams.getTeam(player)){
                    player.sendMessage("Ты уже находишься в этой команде");
                    return;
                }
                teams.addPlayerToTeam(player, team);
                player.sendMessage("Вы присоединились к команде " + team.getName());
                ChatColor colorteam = team.getChatColor();
                player.setPlayerListName("[" + colorteam + team.getName() + ChatColor.RESET + "]" + " " + player.getName());
                break;
            }
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        String teamMessage;
        Team team = teams.getTeam(player);
        if (team == null) return;
        ChatColor colorteam = team.getChatColor();

        // Check if the message starts with "!"
        if (message.startsWith("!")) {
            // Global chat
            event.setFormat(String.format("[%s] %s: %s", colorteam + team.getName() + ChatColor.RESET, player.getName(), message.substring(1)));
            return;
        }

        // The player is on a team
        teamMessage = "[" + team.getChatColor() + "Team" + ChatColor.RESET + "] " + player.getName() + ": " + message;
        event.setCancelled(true); // Cancel the original event
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (teams.getTeam(p) == team) {
                p.sendMessage(teamMessage); // Send the message to all players on the same team
            }
        }
    }
}

