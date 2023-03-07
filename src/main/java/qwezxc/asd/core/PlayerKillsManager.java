package qwezxc.asd.core;

import lombok.AllArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import qwezxc.asd.Asd;

import java.util.HashMap;
import java.util.Map;


@AllArgsConstructor
public class PlayerKillsManager implements Listener {
    private Map<Player, PlayerKills> playerKillsMap = new HashMap<>();
    private TeamLivesManager teamLivesManager;
    private Teams teams;

    public PlayerKillsManager() {

    }

    public PlayerKills getPlayerKills(Player player) {
        return playerKillsMap.computeIfAbsent(player, p -> new PlayerKills(0));
    }

    public void givePlayerKills(Player player, int amount) {
        PlayerKills playerKills = getPlayerKills(player);
        playerKills.addKills(amount);
    }

    public int getPKills(Player player) {
        PlayerKills playerKills = getPlayerKills(player);
        return playerKills.getKills();
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) return;
        if (GameManager.gameTime <= 1) {
            event.setCancelled(true);
        }
        Entity victim = event.getEntity();
        Entity damager = event.getDamager();
        double finaldmg = event.getFinalDamage();
        if (victim instanceof Player && damager instanceof Player) {
            Player target = ((Player) victim).getPlayer();
            Player attacker = ((Player) damager).getPlayer();
            Team targetTeam = teams.getTeam(target);
            if (targetTeam == null) return;
            Location targetBase = targetTeam.getBase();
            if (target.getHealth() - finaldmg < 0) {
                event.setCancelled(true);
                Location playerLoc = target.getLocation();
                for (ItemStack item : target.getInventory().getContents()) {
                    if (item == null || item.getType() == Material.AIR) {
                        continue;
                    }
                    target.getWorld().dropItemNaturally(playerLoc, item);
                }
                givePlayerKills(attacker, 1);
                switch (getPKills(attacker)) {
                    case 1:
                        Asd.getInstance().getPluginManager().getEconomy().addBalance(attacker, 250);
                        attacker.sendMessage(ChatColor.GREEN + " Вы получили $250 за 1 убийство");
                        break;
                    case 2:
                        attacker.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 2));
                        attacker.sendMessage(ChatColor.GREEN + " Вы получили 2 золотых яблока за 2 убийства");
                        break;
                }
                target.teleport(targetBase);
                target.setHealth(20);
                target.getInventory().clear();
                teamLivesManager.removeTeamLives(targetTeam, 1, target);
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);
        Player player = event.getEntity();
        Team targetTeam = teams.getTeam(player);
        if (targetTeam == null) return;
        Location playerLoc = player.getLocation();
        Location targetBase = targetTeam.getBase();
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null || item.getType() == Material.AIR) {
                continue;
            }
            player.getWorld().dropItemNaturally(playerLoc, item);
        }
        event.getEntity().spigot().respawn();
        player.teleport(targetBase);
        teamLivesManager.removeTeamLives(targetTeam, 1, player);
    }

}
