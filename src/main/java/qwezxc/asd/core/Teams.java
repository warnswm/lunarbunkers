package qwezxc.asd.core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Teams {
    private Map<UUID, Team> players = new HashMap<>();
    private Map<String, Team> teams = new HashMap<>();

    public Teams() {
        teams.put("Red", new Team("Red", ChatColor.RED, Material.RED_GLAZED_TERRACOTTA, 2, new Location(Bukkit.getWorld("world"), 1.5, 64.5, 85.5)));
        teams.put("Blue", new Team("Blue", ChatColor.BLUE, Material.BLUE_GLAZED_TERRACOTTA, 1, new Location(Bukkit.getWorld("world"), 1.5, 64.5, -85.5)));
        teams.put("Green", new Team("Green", ChatColor.GREEN, Material.GREEN_GLAZED_TERRACOTTA, 1, new Location(Bukkit.getWorld("world"), -85.5, 64.5, 0.5)));
        teams.put("Yellow", new Team("Yellow", ChatColor.YELLOW, Material.YELLOW_GLAZED_TERRACOTTA, 1, new Location(Bukkit.getWorld("world"), 85.5, 64.5, 0.5)));
    }

    public Map<String, Team> getTeams() {
        return teams;
    }

    public Map<UUID, Team> getPlayers() {
        return players;
    }

    public void addPlayerToTeam(Player player, Team team) {
        players.put(player.getUniqueId(), team);
    }

    public void removePlayerFromTeam(Player player) {
        players.remove(player.getUniqueId());
    }

    public Team getTeam(Player player) {
        return getTeam(player.getUniqueId());
    }

    public Team getTeam(UUID uuid) {
        return this.players.get(uuid);
    }

    public int getNumPlayersInTeam(Team team) {
        int count = 0;
        for (Team t : players.values()) {
            if (t.equals(team)) {
                count++;
            }
        }
        return count;
    }

}





















































//public class Teams implements Listener {
//    private Map<UUID, Team> playerTeams = new HashMap<>();
//    private Map<String, Team> teams = new HashMap<>();
//
//    public Teams() {
//        // Create the teams
//        teams.put("red", new Team("red", ChatColor.RED));
//        teams.put("blue", new Team("blue", ChatColor.BLUE));
//        teams.put("green", new Team("green", ChatColor.GREEN));
//        teams.put("yellow", new Team("yellow", ChatColor.YELLOW));
//    }
//
//
//    @EventHandler
//    public void onPlayerInteract(PlayerInteractEvent event) {
//        Player player = event.getPlayer();
//        ItemStack item = player.getInventory().getItemInMainHand();
//
//        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
//            if(item.getType() == Material.DIAMOND_BLOCK ) {
//                event.setCancelled(true);
//                Inventory menu = Bukkit.createInventory(null, 9, "Teams");
//
//                for (Team team : teams.values()) {
//                    ItemStack itemStack = new ItemStack(team.getWoolColor().getWoolData().getItemType());
//                    ItemMeta itemMeta = itemStack.getItemMeta();
//                    itemMeta.setDisplayName(team.getColor() + team.getName());
//                    itemStack.setItemMeta(itemMeta);
//                    menu.addItem(itemStack);
//                }
//
//                player.openInventory(menu);
//            }
//        }
//    }
//
//    @EventHandler
//    public void onInventoryClick(InventoryClickEvent event) {
//        Player player = (Player) event.getWhoClicked();
//        ItemStack item = event.getCurrentItem();
//
//        if (event.getView().getTitle().equals("Teams") && item != null && item.hasItemMeta()) {
//            event.setCancelled(true);
//            String teamName = ChatColor.stripColor(item.getItemMeta().getDisplayName().toLowerCase());
//            Team team = teams.get(teamName);
//            playerTeams.put(player.getUniqueId(), team);
//            player.sendMessage(team.getColor() + "You have joined the " + team.getName() + " team!");
//            player.closeInventory();
//        }
//    }
//
//    public Team getTeam(UUID playerUUID) {
//        return playerTeams.get(playerUUID);
//    }
//
//    public void addPlayerToTeam(UUID playerUUID, Team team) {
//        playerTeams.put(playerUUID, team);
//    }
//
//    public void removePlayerFromTeam(UUID playerUUID) {
//        playerTeams.remove(playerUUID);
//    }
//
//    public void showTeamNames() {
//        for (Team team : teams.values()) {
//            Bukkit.getServer().broadcastMessage(team.getColor() + "Team Name: " + team.getName());
//        }
//    }
//
//    public void showPlayerTeam(UUID playerUUID) {
//        Player player = Bukkit.getPlayer(playerUUID);
//        Team team = playerTeams.get(playerUUID);
//        if (team != null) {
//            player.sendMessage(team.getColor() + "You are on the " + team.getName() + " team.");
//        } else {
//            player.sendMessage(ChatColor.RED + "You are not on any team.");
//        }
//    }
//
//    @EventHandler
//    public void onPlayerDamage(EntityDamageByEntityEvent event) {
//        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) {
//            return;
//        }
//
//        Player target = (Player) event.getEntity();
//        Player attacker = (Player) event.getDamager();
//
//        UUID attackerUUID = attacker.getUniqueId();
//        UUID defenderUUID = target.getUniqueId();
//
//        Team attackingTeam = playerTeams.get(attackerUUID);
//        Team attackedTeam = playerTeams.get(defenderUUID);
//
//        if (attackingTeam != null && attackedTeam != null && attackingTeam.equals(attackedTeam)) {
//            Player attackingPlayer = Bukkit.getPlayer(attackerUUID);
//            attackingPlayer.sendMessage(ChatColor.RED + "You can't attack players on your own team.");
//        }
//
//
//    }
//}
//
//
//    private final String Teamname;
//
//    private Map<UUID, Team> players = new HashMap<>();
//    public Team(String teamname) {
//        this.Teamname = teamname;
//    }
//
//    public void addPlayer(Player player) {
//        UUID uuid = player.getUniqueId();
//        players.put(uuid, this);
//        player.sendMessage(color + "You have joined the " + color.name().toLowerCase() + " team.");
//    }
//
//    public void removePlayer(Player player) {
//        UUID uuid = player.getUniqueId();
//        players.remove(uuid);
//        player.sendMessage(color + "You have left the " + color.name().toLowerCase() + " team.");
//    }
//
//    public ChatColor getColor() {
//        return color;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Team team = (Team) o;
//
//        return color == team.color;
//    }
//
//    @Override
//    public int hashCode() {
//        return color != null ? color.hashCode() : 0;
//    }