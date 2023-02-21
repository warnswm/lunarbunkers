package qwezxc.asd.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import qwezxc.asd.Asd;

import java.util.HashMap;

public class OreRegeneration implements Listener {
    private Asd main;

    public OreRegeneration(final Asd main) {
        this.main = main;
    }

    public static HashMap<Location, Material> breakBlocks = new HashMap<>();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        Material blockType = event.getBlock().getType();

        breakBlocks.put(block.getLocation(), blockType);

        //Cancel experience drops
        event.setExpToDrop(0);

        switch (blockType) {
            case COAL_ORE:
                block.setType(Material.AIR);
                player.getInventory().addItem(new ItemStack(Material.COAL, 1));
                Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
                    block.setType(Material.COBBLESTONE);
                    block.setMetadata("RegenBlock", new FixedMetadataValue(main, "true"));
                });
                Bukkit.getScheduler().runTaskLater(main, () -> block.setType(blockType), 5 * 20);
                return;
            case GOLD_ORE:
                block.setType(Material.AIR);
                player.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, 1));
                Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
                    block.setType(Material.COBBLESTONE);
                    block.setMetadata("RegenBlock", new FixedMetadataValue(main, "true"));
                });
                Bukkit.getScheduler().runTaskLater(main, () -> block.setType(blockType), 17 * 20);
                return;
            case DIAMOND_ORE:
                block.setType(Material.AIR);
                player.getInventory().addItem(new ItemStack(Material.DIAMOND, 1));
                Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
                    block.setType(Material.COBBLESTONE);
                    block.setMetadata("RegenBlock", new FixedMetadataValue(main, "true"));
                });
                Bukkit.getScheduler().runTaskLater(main, () -> block.setType(blockType), 28 * 20);
                return;
            case IRON_ORE:
                block.setType(Material.AIR);
                player.getInventory().addItem(new ItemStack(Material.IRON_INGOT, 1));
                Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
                    block.setType(Material.COBBLESTONE);
                    block.setMetadata("RegenBlock", new FixedMetadataValue(main, "true"));
                });
                Bukkit.getScheduler().runTaskLater(main, () -> block.setType(blockType), 11 * 20);
                return;
            case COBBLESTONE:
                if (block.hasMetadata("RegenBlock")) {
                    event.setCancelled(true);
                }
            default:
                if (!player.isOp()) {
                    event.setCancelled(true);
                } else if (player.isOp() && player.getGameMode().equals(GameMode.CREATIVE) && player.getName().equals("nayl_l_lnik")){
                    event.setCancelled(false);
                }
        }
    }
}