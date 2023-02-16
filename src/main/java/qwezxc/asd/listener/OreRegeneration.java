package qwezxc.asd.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import qwezxc.asd.Asd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class OreRegeneration implements Listener {
    private Asd main;

    public OreRegeneration(final Asd main) {
        this.main = main;
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Location location = event.getBlock().getLocation();
        Player player = event.getPlayer();
        ItemStack hand = player.getInventory().getItemInMainHand();

        if (!hand.hasItemMeta() || !hand.getItemMeta().hasLore() || !hand.getItemMeta().getLore().contains("Special Pickaxe")) {
            return;
        }

        Material blockType = event.getBlock().getType();

        //Cancel experience drops
        event.setExpToDrop(0);

        switch (blockType) {
            case COAL_ORE:
                block.setType(Material.AIR);
                player.getInventory().addItem(new ItemStack(Material.COAL, 1));
                Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                    @Override
                    public void run() {
                        block.setType(Material.COBBLESTONE);
                        block.setMetadata("RegenBlock",new FixedMetadataValue(main, "true"));
                    }
                });
                Bukkit.getScheduler().runTaskLater(main, () -> block.setType(blockType), 5*20);
                return;
            case GOLD_ORE:
                block.setType(Material.AIR);
                player.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, 1));
                Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                    @Override
                    public void run() {
                        block.setType(Material.COBBLESTONE);
                        block.setMetadata("RegenBlock",new FixedMetadataValue(main, "true"));
                    }
                });
                Bukkit.getScheduler().runTaskLater(main, () -> block.setType(blockType), 17*20);
                return;
            case DIAMOND_ORE:
                block.setType(Material.AIR);
                player.getInventory().addItem(new ItemStack(Material.DIAMOND, 1));
                Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                    @Override
                    public void run() {
                        block.setType(Material.COBBLESTONE);
                        block.setMetadata("RegenBlock",new FixedMetadataValue(main, "true"));
                    }
                });
                Bukkit.getScheduler().runTaskLater(main, () -> block.setType(blockType), 28*20);
                return;
            case IRON_ORE:
                block.setType(Material.AIR);
                player.getInventory().addItem(new ItemStack(Material.IRON_INGOT, 1));
                Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                    @Override
                    public void run() {
                        block.setType(Material.COBBLESTONE);
                        block.setMetadata("RegenBlock",new FixedMetadataValue(main, "true"));
                    }
                });
                Bukkit.getScheduler().runTaskLater(main, () -> block.setType(blockType), 11*20);
                return;
            case COBBLESTONE:
                if (block.hasMetadata("RegenBlock")){
                    event.setCancelled(true);
                }
            default:
                if (!player.isOp()) {
                    event.setCancelled(true);
                }else if(player.isOp()){
                    event.setCancelled(true);
                }
                else if (player.isOp() && player.getGameMode().equals(GameMode.CREATIVE) && player.getName().equals("nayl_l_lnik")){
                    event.setCancelled(false);
                }
        }
    }
}