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
    private static final int COAL_DELAY = 5;
    private static final int IRON_DELAY = 11;
    private static final int GOLD_DELAY = 17;
    private static final int DIAMOND_DELAY = 28;
    private final Asd main;

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
                mineOre(player, Material.COAL, COAL_DELAY, block);
                return;
            case GOLD_ORE:
                mineOre(player, Material.GOLD_INGOT, GOLD_DELAY, block);
                return;
            case DIAMOND_ORE:
                mineOre(player, Material.DIAMOND, DIAMOND_DELAY, block);
                return;
            case IRON_ORE:
                mineOre(player, Material.IRON_INGOT, IRON_DELAY, block);
                return;
            case COBBLESTONE:
                if (block.hasMetadata("RegenBlock")) {
                    event.setCancelled(true);
                }
                return;
            case FENCE_GATE:
            case CHEST:
            case LADDER:
                event.setCancelled(false);
                return;
            default:
                if (!player.isOp()) {
                    event.setCancelled(true);
                } else if (player.isOp() && player.getGameMode().equals(GameMode.CREATIVE)) {
                    breakBlocks.remove(block.getLocation());
                    event.setCancelled(false);
                }
        }
    }

    private void mineOre(Player player, Material oreMaterial, int delay, Block block) {
        block.setType(Material.AIR);
        player.getInventory().addItem(new ItemStack(oreMaterial, 1));
        Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
            block.setType(Material.COBBLESTONE);
            block.setMetadata("RegenBlock", new FixedMetadataValue(main, "true"));
        });
        Bukkit.getScheduler().runTaskLater(main, () -> block.setType(oreMaterial), delay * 20);
    }
}