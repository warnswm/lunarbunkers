package qwezxc.asd.listener;

import com.comphenix.protocol.PacketType;
import net.minecraft.server.v1_12_R1.Items;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.yaml.snakeyaml.util.ArrayStack;
import qwezxc.asd.Asd;
import qwezxc.asd.core.PlayerKillsManager;
import qwezxc.asd.core.PlayerLivesManager;
import qwezxc.asd.core.Team;
import qwezxc.asd.core.Teams;

public class PlayerLivesListener implements Listener {
    private PlayerLivesManager playerLivesManager;

    private PlayerKillsManager playerKillsManager;
    private Teams teams;
    public PlayerLivesListener(PlayerLivesManager playerLivesManager,Teams teams,PlayerKillsManager playerKillsManager) {
        this.playerLivesManager = playerLivesManager;
        this.teams = teams;
        this.playerKillsManager = playerKillsManager;
    }

    @EventHandler
    public void damage(EntityDamageByEntityEvent event) {
        Entity victim = event.getEntity();
        Entity damager = event.getDamager();
        double finaldmg = event.getFinalDamage();
        if (victim instanceof Player && damager instanceof Player) {
            Player target = ((Player) victim).getPlayer();
            Player attacker = ((Player) damager).getPlayer();
            Team targetTeam = teams.getTeam(target);
            if (targetTeam == null) return;
            Location targetBase = targetTeam.getBase();
            if (target.isDead() || target.getHealth() - finaldmg < 0) {
                event.setCancelled(true);
                Location playerLoc = target.getLocation();
                for (ItemStack item : target.getInventory().getContents()) {
                    if (item == null || item.getType() == Material.AIR) {
                        continue;
                    }
                    target.getWorld().dropItemNaturally(playerLoc, item);
                }
                playerKillsManager.givePlayerKills(attacker,1);
                if(playerKillsManager.getPKills(attacker) == 1){
                    Asd.getInstance().getPluginManager().getEconomy().addBalance(attacker, 250);
                    attacker.sendMessage(ChatColor.GREEN + " Вы получили $250 за 1 убийство");
                }
                if(playerKillsManager.getPKills(attacker) == 2){
                    attacker.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE,2));
                    attacker.sendMessage(ChatColor.GREEN + " Вы получили 2 золотых яблока за 2 убийства");
                }
                target.teleport(targetBase);
                target.setHealth(20);
                target.getInventory().clear();
                playerLivesManager.takePlayerLives(target, 1);
            }
        }
    }
}

