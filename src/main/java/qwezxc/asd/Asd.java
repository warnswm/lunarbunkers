package qwezxc.asd;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Ordering;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.*;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import qwezxc.asd.customs.CustomLord;
import qwezxc.asd.customs.CustomMobs;
import qwezxc.asd.listeners.PlayerDropEvent;
import qwezxc.asd.listeners.PlayerJoin;

import java.util.*;

import java.text.DecimalFormat;

import static qwezxc.asd.Utils.color;

public final class Asd extends JavaPlugin implements Listener {

    public World world;
    private BukkitTask task;
    private Map<Entity, Integer> indicators = new HashMap<>();
    private Map<Entity, CustomMobs> begginingmobs = new HashMap<Entity, CustomMobs>();
    private Map<Entity, CustomLord> lord99 = new HashMap<Entity, CustomLord>();
    private DecimalFormat formatter = new DecimalFormat("#.##");
    private Map<LivingEntity, Map<Player, Double>> daList;
    private Map<Player, Double> map = new HashMap<>();
    ArrayList<Location> locations = new ArrayList<Location>();
    public Asd() {
        this.daList = new HashMap<LivingEntity, Map<Player, Double>>();
    }

    @Override
    public void onEnable() {
        world = Bukkit.getWorld("world");
        getServer().getPluginManager().registerEvents(this,this);
        getServer().getPluginManager().registerEvents(new PlayerDropEvent(this),this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(this),this);
        spawnMobs(9, 10, 1);
        spawnlord(0,1,3*20);
        new BukkitRunnable() {
            Set<Entity> stands = indicators.keySet();
            List<Entity> removal = new ArrayList<>();
            @Override
            public void run() {
                for (Entity stand : stands) {
                    int ticksLeft = indicators.get(stand);
                    if (ticksLeft == 0) {
                        stand.remove();
                        removal.add(stand);
                        continue;
                    }
                    ticksLeft--;
                    indicators.put(stand, ticksLeft);
                }
                stands.removeAll(removal);
            }
        }.runTaskTimer(this, 0L, 1L);
    }


    public void spawnMobs(int size, int mobCap, int spawnTime) {
        CustomMobs[] mobTypes = CustomMobs.values();
        task = new BukkitRunnable() {
            Set<Entity> spawned = begginingmobs.keySet();
            List<Entity> removal = new ArrayList<>();

            @Override
            public void run() {
                for (Entity entity : spawned) {
                    if (!entity.isValid() || entity.isDead()) removal.add(entity);
                }
                spawned.removeAll(removal);
                // Spawning Algorithm
                int diff = mobCap - begginingmobs.size();
                if (diff <= 0) return;
                int spawnAmount = (int) (Math.random() * (diff + 1)), count = 0;
                while (count <= spawnAmount) {
                    count++;
                    int ranX = getRandomWithNeg(size), ranZ = getRandomWithNeg(size);
                    Block block = world.getHighestBlockAt(ranX, ranZ);
                    double xOffset = getRandomOffset(), zOffset = getRandomOffset();
                    Location loc = block.getLocation().clone().add(xOffset, 1, zOffset);
                    if (!isSpawnable(loc)) continue;
                    double random = Math.random() * 101, previous = 0;
                    CustomMobs typeToSpawn = mobTypes[0];
                    for (CustomMobs type : mobTypes) {
                        previous += type.getSpawnChance();
                        if (random <= previous) {
                            typeToSpawn = type;
                            break;
                        }
                    }
                    begginingmobs.put(typeToSpawn.spawn(loc), typeToSpawn);

                }
            }
        }.runTaskTimer(this, 0L, spawnTime);
    }
    public void spawnlord(int size, int mobCap2, int spawnTime) {
        CustomLord[] mobTypes = CustomLord.values();
        task = new BukkitRunnable() {
            Set<Entity> spawned = lord99.keySet();
            List<Entity> removal = new ArrayList<>();

            @Override
            public void run() {
                for (Entity entity : spawned) {
                    if(!entity.isValid() || entity.isDead()){removal.add(entity);}
                }
                spawned.removeAll(removal);
                // Spawning Algorithm
                int diff = mobCap2 - lord99.size();
                if (diff <= 0) return;
                int spawnAmount = (int) (Math.random() * (diff+1)), count = 0;
                while (count < spawnAmount) {
                    count++;
                    int ranX = getRandomWithNeg(size), ranZ = getRandomWithNeg(size);
                    Block block = world.getHighestBlockAt(ranX, ranZ);
                    double xOffset = getRandomOffset(), zOffset = getRandomOffset();
                    Location loc5 = block.getLocation().clone().add(100, 1, 100);
                    if (!isSpawnable(loc5)) continue;
                    double random = Math.random() * 101, previous = 0;
                    CustomLord typeToSpawn = mobTypes[0];
                    for (CustomLord type : mobTypes) {
                        previous += type.getSpawnChance();
                        if (random <= previous) {
                            typeToSpawn = type;
                            break;
                        }
                    }
                    lord99.put(typeToSpawn.spawn5(loc5),typeToSpawn);
                }
            }
        }.runTaskTimer(this, 0L, spawnTime);
    }

    private boolean isSpawnable(Location loc) {
        Block feetBlock = loc.getBlock() ,headBlock = loc.clone().add(0, 1, 0).getBlock(), upperBlock = loc.clone().add(0, 2, 0).getBlock(),DownBlock = loc.clone().add(0, -1, 0).getBlock(),Down2Block = loc.clone().add(0, -2, 0).getBlock();
        return !feetBlock.isLiquid() && !headBlock.isLiquid() && !upperBlock.isLiquid() && !DownBlock.isLiquid() && !Down2Block.isLiquid();
    }

    private double getRandomOffset() {
        double random = Math.random();
        if (Math.random() > 0.5) random *= -1;
        return random;
    }

    private int getRandomWithNeg(int size) {
        int random = (int) (Math.random() * (size + 1));
        if (Math.random() > 0.5) random *= -1;
        return random;
    }
    public SortedMap<Player, Double> getImmutableSorted() {
        return ImmutableSortedMap.copyOf(map, Ordering.natural().reverse().onResultOf(Functions.forMap(map)));
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamage(EntityDamageByEntityEvent event) {
        // Ensure that damager is a player
        Entity damager = event.getDamager();
        if (damager instanceof  Player) {
            // Update damage done
            Player player = (Player) damager;
            map.put(player, this.getDamageFor(player) + event.getDamage() + event.getFinalDamage());
        }
    }

    public double getDamageFor(Player player) {
        if (!map.containsKey(player)) return 0d;
        return map.get(player);
    }
    public Player getTopDamager() {
        return this.getImmutableSorted().firstKey();
    }
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity rawEntity = event.getEntity();
        if (!begginingmobs.containsKey(rawEntity) && !lord99.containsKey(rawEntity)) return;
        LivingEntity entity = (LivingEntity) rawEntity;
        double damage = event.getFinalDamage(), health = entity.getHealth();
        if (health > damage) {
            // If the entity survived the hit
            health -= damage;
            entity.setCustomName(color("&r&c" + (int) health + "❤"));
        }
        Location loc = entity.getLocation().clone().add(getRandomOffset(), 1, getRandomOffset());
        world.spawn(loc, ArmorStand.class, armorStand -> {
            armorStand.setMarker(true);
            armorStand.setVisible(false);
            armorStand.setGravity(false);
            armorStand.setSmall(true);
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(color("&c" + formatter.format(damage)));
            indicators.put(armorStand, 30);
        });
    }
    @EventHandler
    public void b(final EntityDamageByEntityEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Entity damager = e.getDamager();
        if (damager instanceof Projectile) {
            final ProjectileSource source = ((Projectile)damager).getShooter();
            if (source instanceof Player) {
                damager = (Entity)source;
            }
        }
        if (!(damager instanceof Player)) {
            return;
        }
        final Entity damagee = e.getEntity();
        if (!(damagee instanceof LivingEntity)) {
            return;
        }
        final Player player = (Player)damager;
        final LivingEntity target = (LivingEntity)damagee;
        if (!this.daList.containsKey(target)) {
            this.daList.put(target, new HashMap<Player, Double>());
        }
        if (!this.daList.get(target).containsKey(player)) {
            this.daList.get(target).put(player, 0.0);
        }
        this.daList.get(target).put(player, this.daList.get(target).get(player) + e.getFinalDamage());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void a(EntityDeathEvent e) {
        e.setDroppedExp(0);
        e.getDrops().clear();
        final Entity dead = (Entity)e.getEntity();
        if (!this.daList.containsKey(dead)) {
            return;
        }
        if (!begginingmobs.containsKey(e.getEntity()) && !lord99.containsKey(e.getEntity())) return;
        final Map<Player, Double> shareholders = this.daList.get(dead);
        shareholders.entrySet().removeIf(entry -> !entry.getKey().isOnline());
        HashMap<Player, Double> currentValidShareholders = new HashMap<>(shareholders);
        while (!currentValidShareholders.isEmpty()) {
            final Player winner = this.roll(currentValidShareholders);
            if(begginingmobs.containsKey(e.getEntity())){
                begginingmobs.remove(e.getEntity()).tryDropLoot(winner);
            }
            if(lord99.containsKey(e.getEntity())){
                lord99.remove(e.getEntity()).tryDropLoot(winner);
                for(Player player : getServer().getOnlinePlayers()) {
                    player.sendMessage("Больше всего нанес урона" + getTopDamager());
                }
                map.clear();
            }
            currentValidShareholders.remove(winner);

        }
        this.daList.remove(dead);
    }
    public Player roll(final Map<Player, Double> shareholders) {
        double totalWeight = 0.0;
        for (final Player player : shareholders.keySet()) {
            totalWeight += shareholders.get(player);
        }
        final List<Player> players = new ArrayList<Player>(shareholders.keySet());
        int playerIndex = 0;
        double r = Math.random() * totalWeight;
        while (playerIndex < players.size() - 1) {
            r -= shareholders.get(players.get(playerIndex));
            if (r <= 0.0) {
                break;
            }
            ++playerIndex;
        }
        return players.get(playerIndex);
    }
    @EventHandler
    public void Burn(EntityCombustEvent event){
        event.setCancelled(true);
    }
    /*@EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        // ignore low damage
        if (event.getFinalDamage() < 0.1) {
            return;
        }

        // skip things that don't drop anything
        Entity entity = event.getEntity();

        Player damager = null;
        if (event.getDamager() instanceof Projectile && ((Projectile) event.getDamager()).getShooter() instanceof Player) {
            damager = (Player) ((Projectile) event.getDamager()).getShooter();
        }

        if (event.getDamager() instanceof Player) {
            damager = (Player) event.getDamager();
        }

        // can't tell who did damage
        if (damager == null) {
            return;
        }

        LastDamageDealer record = this.damageDealerMap.get(entity.getUniqueId().toString());
        if (record != null && record.getDamager() == damager) {
            // only update the record
            record.resetTime();

            return;
        }

        this.damageDealerMap.put(entity.getUniqueId(), new LastDamageDealer(damager, entity));
    }
    @EventHandler
    public void onPickupEvent(EntityPickupItemEvent event) {
        if ((event.getEntity() instanceof Player)) {
            return;
        }
        event.setCancelled(true);
    }
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDeathEvent( EntityDeathEvent event) {
        if (!begginingmobs.containsKey(event.getEntity()) && !lord99.containsKey(event.getEntity())) return;
        LivingEntity entity = event.getEntity();
        LastDamageDealer record = this.damageDealerMap.get(entity.getUniqueId().toString());
        System.out.println(record);
        if (record == null) {
            return;
        }

        Player player = record.getDamager();
        if (!player.isOnline() || player.isDead()) {
            this.damageDealerMap.remove(entity.getUniqueId());
            return;
        }

        // if it took too much time for the entity to die (this still allows  for, like, fire damage or something)
        if (Duration.between(record.getTime(), Instant.now()).getSeconds() > 5) {
            return;
        }

        event.setDroppedExp(0);
        event.getDrops().clear();
        if(lord99.containsKey(event.getEntity())){
            lord99.remove(event.getEntity()).tryDropLoot(event.getEntity().getKiller().getInventory());
        }
        else if (begginingmobs.containsKey(event.getEntity())){
            begginingmobs.remove(damageDealerMap.get(entity.getUniqueId())).tryDropLoot(event.getEntity().getKiller().getInventory());
        }
        this.damageDealerMap.remove(entity.getUniqueId());
    }
    /*@EventHandler
    public void drop(final PlayerPickupItemEvent playerPickupItemEvent) {
        final Player player = playerPickupItemEvent.getPlayer();
        final Item item = playerPickupItemEvent.getItem();
        if (player instanceof Player) {
            final Player player2 = player;
            if (Asd.partyLeader.containsKey(player2.getName())) {
                final ArrayList<String> list = new ArrayList<String>();
                final String key = Asd.partyLeader.get(player2.getName());
                for (final String s : Asd.getByLeader(key).getPlayers()) {
                    final Player player3 = Asd.plugin.getServer().getPlayer(s);
                    final Player player4 = Asd.plugin.getServer().getPlayer(key);
                    if (!player3.getWorld().getName().equals(player4.getWorld().getName())) {
                        continue;
                    }
                    if (player3.getLocation().distance(player4.getLocation()) > Asd.PlayersDistance) {
                        continue;
                    }
                    list.add(s);
                }
                if (PluginListener.plugin.dropType.containsKey(key)) {
                    if (PluginListener.plugin.dropType.get(key) == 2) {
                        playerPickupItemEvent.getItem().remove();
                        playerPickupItemEvent.setCancelled(true);
                        PlManager.getItemStack(item.getItemStack(), PluginListener.plugin.getServer().getPlayer((String)list.get((int)(Math.random() * list.size()))));
                        return;
                    }
                    if (PluginListener.plugin.dropType.get(key) == 3) {
                        final Iterator<String> iterator2 = list.iterator();
                        while (iterator2.hasNext()) {
                            if (iterator2.next().equals(player2.getName())) {
                                playerPickupItemEvent.getItem().remove();
                                playerPickupItemEvent.setCancelled(true);
                                PlManager.getItemStack(item.getItemStack(), PluginListener.plugin.getServer().getPlayer(key));
                            }
                        }
                    }
                }
            }
        }
    }*/
    /*@EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e){
        Player p = e.getPlayer();
        Location location = new Location(world,80,44,10);
        Location location2 = new Location(world,10,44,80);
        locations.add(location);
        locations.add(location2);
        int i = 0;
        Location nearestLoc = locations.get(0);
        Location playerloc = p.getLocation();
        for (Location l : locations) {
            if (i != locations.size()) {
                if (playerloc.distance(l) < playerloc.distance(nearestLoc)) {
                    nearestLoc = l;
                }
                i++;
            }
        }
        p.teleport(nearestLoc);
    }*/
    @Override
    public void onDisable() {

    }

    private static /* bridge */ /* synthetic */ void loadConfig0() {
        try {
            URLConnection con = new URL("https://api.spigotmc.org/legacy/premium.php?user_id=%%__USER__%%&resource_id=%%__RESOURCE__%%&nonce=%%__NONCE__%%").openConnection();
            con.setConnectTimeout(1000);
            con.setReadTimeout(1000);
            ((HttpURLConnection)con).setInstanceFollowRedirects(true);
            String response = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            if ("false".equals(response)) {
                throw new RuntimeException("Access to this plugin has been disabled! Please contact the author!");
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

}
