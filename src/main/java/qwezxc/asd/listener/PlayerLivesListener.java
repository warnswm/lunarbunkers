package qwezxc.asd.listener;

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
import qwezxc.asd.core.*;

public class PlayerLivesListener implements Listener {
    private TeamLivesManager teamLivesManager;

    private PlayerKillsManager playerKillsManager;
    private Teams teams;
    public PlayerLivesListener(TeamLivesManager teamLivesManager,Teams teams,PlayerKillsManager playerKillsManager) {
        this.teamLivesManager = teamLivesManager;
        this.teams = teams;
        this.playerKillsManager = playerKillsManager;
    }

    @EventHandler
    public void damage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) return;
        if(GameManager.gameTime <= 1){
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
                playerKillsManager.givePlayerKills(attacker, 1);
                if (playerKillsManager.getPKills(attacker) == 1) {
                    Asd.getInstance().getPluginManager().getEconomy().addBalance(attacker, 250);
                    attacker.sendMessage(ChatColor.GREEN + " Вы получили $250 за 1 убийство");
                }
                if(playerKillsManager.getPKills(attacker) == 2){
                    attacker.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 2));
                    attacker.sendMessage(ChatColor.GREEN + " Вы получили 2 золотых яблока за 2 убийства");
                }
                target.teleport(targetBase);
                target.setHealth(20);
                target.getInventory().clear();
                teamLivesManager.removeTeamLives(targetTeam, 1);
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);
        Player player = event.getEntity();
        Team targetTeam = teams.getTeam(player);
        if (targetTeam == null) return;
        Location targetBase = targetTeam.getBase();
        event.getEntity().spigot().respawn();
        player.teleport(targetBase);
        teamLivesManager.removeTeamLives(targetTeam, 1);
    }

}

