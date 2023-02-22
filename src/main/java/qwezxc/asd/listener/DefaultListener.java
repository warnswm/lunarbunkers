package qwezxc.asd.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import qwezxc.asd.Asd;
import qwezxc.asd.core.Team;

import java.util.UUID;

public class DefaultListener implements Listener {
    private Asd main;


    public DefaultListener(final Asd main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(item.getType() == Material.DIAMOND_BLOCK ) {
                event.setCancelled(true);
                Inventory menu = Bukkit.createInventory(null, 9, "Team Select");

                for (Team team : main.teams.getTeams()) {
                    menu.addItem(new ItemStack(team.getWoolBlock(), 1));
                }
                player.openInventory(menu);
            }
        }
    }
    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) {
            return;
        }

        Player target = (Player) event.getEntity();
        Player attacker = (Player) event.getDamager();

        UUID targetuuid = target.getUniqueId();
        UUID attackeruuid = attacker.getUniqueId();

        if (main.teams.getTeam(attackeruuid) == main.teams.getTeam(targetuuid)) {
            event.setCancelled(true);
            attacker.sendMessage("You can't attack players from the same team.");
        }
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location playerLoc = player.getLocation();
        double captureRadius = 3.5;
        Location capturePoint = new Location( Bukkit.getWorld("world"), 1.5, 63.5, 0.5);
        if (Math.abs(playerLoc.getX()- capturePoint.getX()) <= captureRadius  &&
                Math.abs(playerLoc.getY() - capturePoint.getY()) <= captureRadius  &&
                Math.abs(playerLoc.getZ() - capturePoint.getZ()) <= captureRadius ) {
            main.koth.startCapture(player);
        }else{
            main.koth.stopCapture(player);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        main.koth.stopCapture(player);
    }
}
