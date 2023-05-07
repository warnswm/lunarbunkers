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
        //ToDo: Поменять код чтобы не создавал много runTaskLater
        Bukkit.getScheduler().runTaskLater(main, () -> block.setType(oreMaterial), delay * 20);
    }
}

//public class OreRegeneration implements Listener {
//    private static final Set<Location> brokenBlockLocations = new HashSet<>();
//    private static final Map<Location, Long> regenTimes = new HashMap<>();
//    private final Asd main;
//    private final int regenIntervalTicks;
//
//    public OreRegeneration(final Asd main, int regenIntervalSeconds) {
//        this.main = main;
//        // Convert regenIntervalSeconds to ticks
//        this.regenIntervalTicks = regenIntervalSeconds * 20;
//
//        // Schedule the periodic regeneration task
//        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, this::regenerateBlocks, regenIntervalTicks, regenIntervalTicks);
//    }
//
//    @EventHandler
//    public void onBlockBreak(BlockBreakEvent event) {
//        Block block = event.getBlock();
//        Player player = event.getPlayer();
//
//        // Store the location and current time of the broken block
//        brokenBlockLocations.add(block.getLocation());
//        regenTimes.put(block.getLocation(), System.currentTimeMillis());
//
//        //Cancel experience drops
//        event.setExpToDrop(0);
//
//        OreType oreType = Arrays.stream(OreType.values())
//                .filter(type -> type.getOreMaterial() == block.getType())
//                .findFirst()
//                .orElse(null);
//
//        if (oreType != null) {
//            mineOre(player, oreType.getOreMaterial(), oreType.getDelay(), block);
//            return;
//        }
//
//        switch (block.getType()) {
//            case COBBLESTONE:
//                if (block.hasMetadata("RegenBlock")) {
//                    event.setCancelled(true);
//                }
//                return;
//            case FENCE_GATE:
//            case CHEST:
//            case LADDER:
//                event.setCancelled(false);
//                return;
//            default:
//                if (!player.isOp()) {
//                    event.setCancelled(true);
//                } else if (player.isOp() && player.getGameMode().equals(GameMode.CREATIVE)) {
//                    brokenBlockLocations.remove(block.getLocation());
//                    regenTimes.remove(block.getLocation());
//                    event.setCancelled(false);
//                }
//        }
//    }
//
//    private void mineOre(Player player, Material oreMaterial, int delay, Block block) {
//        block.setType(Material.AIR);
//        player.getInventory().addItem(new ItemStack(oreMaterial, 1));
//
//        // Remove the block location from the set and add its regeneration time to the map
//        brokenBlockLocations.remove(block.getLocation());
//        regenTimes.put(block.getLocation(), System.currentTimeMillis() + delay * 1000L);
//    }
//
//    private void regenerateBlocks() {
//        long currentTime = System.currentTimeMillis();
//
//        // Iterate over the set of broken block locations and regenerate blocks that have exceeded their delay time
//        Iterator<Location> iterator = brokenBlockLocations.iterator();
//        while (iterator.hasNext()) {
//            Location loc = iterator.next();
//            long regenTime = regenTimes.getOrDefault(loc, 0L);
//
//            if (currentTime >= regenTime) {
//                Block block = loc.getBlock();
//                if (block.getType() == Material.COBBLESTONE && block.hasMetadata("RegenBlock")) {
//                    block.setType(Material.STONE);
//                    block.removeMetadata("RegenBlock", main);
//                } else {
//                    block.setType(Material.AIR);
//                    block.getState().update(true, false);
//                }
//
//                // Remove the location from the set and the map
//                iterator.remove();
//                regenTimes.remove(loc);
//            }
//        }
//    }
//
//    private enum OreType {
//        COAL(Material.COAL, 5),
//        IRON(Material.IRON_INGOT, 11),
//        GOLD(Material.GOLD_INGOT, 17),
//        DIAMOND(Material.DIAMOND, 28);
//
//        private final Material oreMaterial;
//        private final int delay;
//
//        OreType(Material oreMaterial, int delay) {
//            this.oreMaterial = oreMaterial;
//            this.delay = delay;
//        }
//
//        public Material getOreMaterial() {
//            return oreMaterial;
//        }
//
//        public int getDelay() {
//            return delay;
//        }
//    }
//}


//public class OreRegeneration implements Listener {
//    private static final Set<Location> brokenBlockLocations = new HashSet<>();
//    private final Asd main;
//
//    public OreRegeneration(final Asd main) {
//        this.main = main;
//    }
//
//    @EventHandler
//    public void onBlockBreak(BlockBreakEvent event) {
//        Block block = event.getBlock();
//        Player player = event.getPlayer();
//
//        // Store the location of the broken block
//        brokenBlockLocations.add(block.getLocation());
//
//        //Cancel experience drops
//        event.setExpToDrop(0);
//
//        OreType oreType = Arrays.stream(OreType.values())
//                .filter(type -> type.getOreMaterial() == block.getType())
//                .findFirst()
//                .orElse(null);
//
//        if (oreType != null) {
//            mineOre(player, oreType.getOreMaterial(), oreType.getDelay(), block);
//            return;
//        }
//
//        switch (block.getType()) {
//            case COBBLESTONE:
//                if (block.hasMetadata("RegenBlock")) {
//                    event.setCancelled(true);
//                }
//                return;
//            case FENCE_GATE:
//            case CHEST:
//            case LADDER:
//                event.setCancelled(false);
//                return;
//            default:
//                if (!player.isOp()) {
//                    event.setCancelled(true);
//                } else if (player.isOp() && player.getGameMode().equals(GameMode.CREATIVE)) {
//                    brokenBlockLocations.remove(block.getLocation());
//                    event.setCancelled(false);
//                }
//        }
//    }
//
//    private void mineOre(Player player, Material oreMaterial, int delay, Block block) {
//        block.setType(Material.AIR);
//        player.getInventory().addItem(new ItemStack(oreMaterial, 1));
//
//        Bukkit.getScheduler().runTaskLater(main, () -> {
//            block.setType(oreMaterial);
//            block.setMetadata("RegenBlock", new FixedMetadataValue(main, "true"));
//            brokenBlockLocations.remove(block.getLocation());
//        }, delay * 20);
//    }
//
//    private enum OreType {
//        COAL(Material.COAL, 5),
//        IRON(Material.IRON_INGOT, 11),
//        GOLD(Material.GOLD_INGOT, 17),
//        DIAMOND(Material.DIAMOND, 28);
//
//        private final Material oreMaterial;
//        private final int delay;
//
//        OreType(Material oreMaterial, int delay) {
//            this.oreMaterial = oreMaterial;
//            this.delay = delay;
//        }
//
//        public Material getOreMaterial() {
//            return oreMaterial;
//        }
//
//        public int getDelay() {
//            return delay;
//        }
//    }
//}
