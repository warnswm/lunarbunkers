package qwezxc.asd;

import com.google.common.collect.Lists;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.*;


import java.util.*;

import org.bukkit.entity.Player;

import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.potion.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import qwezxc.asd.Data.Database;
import qwezxc.asd.command.BalanceCommand;
import qwezxc.asd.command.teamcmd;
import qwezxc.asd.command.testcmd;
import qwezxc.asd.core.Economy;
import qwezxc.asd.core.KOTH;
import qwezxc.asd.core.PluginManager;
import qwezxc.asd.listener.OreRegeneration;
import qwezxc.asd.listener.PlayerJoinListener;
import qwezxc.asd.listener.TeamMenuListener;


public final class Asd extends JavaPlugin implements Listener {
    private static Asd instance;
    private Database database;
    private Economy economy;
    private Scoreboard scoreboard;
    public World world;

    private Map<UUID, Integer> minutesInCenter = new HashMap<>();
    private PluginManager pluginManager;
    public KOTH koth;
    public double kothRadius = 5.0;


    Teams teams = new Teams();
    public Map<UUID, Team> playerTeams = teams.getPlayers();
    @Override
    public void onLoad() {

        instance = this;
    }

    @Override
    public void onEnable() {
        checkWorldsOnServer();
        world = Bukkit.getWorld("world");
        this.pluginManager = PluginManager.getInstance();
        database = new Database();
        economy = new Economy();
        teams = new Teams();

        getServer().getPluginManager().registerEvents(new TeamMenuListener(teams), this);
        KOTH koth = new KOTH(teams, new Location(Bukkit.getWorld("world"), 10.5, 41.5, 10.5), 4.5);
        Bukkit.getPluginManager().registerEvents(koth, this);


        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new OreRegeneration(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);


        saveDefaultConfig();

        getCommand("balance").setExecutor(new BalanceCommand(this));
        getCommand("pickaxe").setExecutor(new teamcmd(this));
        getCommand("testcmd").setExecutor(new testcmd(this));
        boolean npcinworld = getConfig().getBoolean("npcinworld");
        for (NPC npc : Lists.newArrayList(CitizensAPI.getNPCRegistry())) {
            if (!npc.getName().equals("Trader")) {
                getConfig().set("npcinworld", false);
            } else {
                getConfig().set("npcinworld", true);
            }
        }
        if (npcinworld == false) {
            NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.VILLAGER, "Trader");
            npc.spawn(new Location(Bukkit.getWorld("world"), 0, 41, 80));
            getConfig().set("npcinworld", true);
        }
        saveConfig();


    }

    public void checkWorldsOnServer() {
        List<World> worlds = getServer().getWorlds();
        for (World world : worlds) {
            System.out.println("World on server: " + world.getName());
        }
    }

    @EventHandler
    public void onNPCInteract(NPCRightClickEvent event) {
        NPC npc = event.getNPC();
        if (!npc.getName().equals("Trader")) return;

        Player player = event.getClicker();

        Inventory inventory = Bukkit.createInventory(null, 54, "Trader");

        ItemStack speedPotion = new ItemStack(Material.POTION);
        PotionMeta speedPotionItemMeta = (PotionMeta) speedPotion.getItemMeta();
        speedPotionItemMeta.setDisplayName(ChatColor.AQUA + "Speed Potion II");
        speedPotionItemMeta.setBasePotionData(new PotionData(PotionType.SPEED, false, true));
        List<String> speedlore = new ArrayList<>();
        speedlore.add(ChatColor.DARK_GRAY + "―――――――――――――――――――――――――――");
        speedlore.add(ChatColor.DARK_GRAY +  "x1 Speed II Potion");
        speedlore.add(ChatColor.DARK_GRAY + "―――――――――――――――――――――――――――");
        speedlore.add(ChatColor.WHITE + "Price: " + ChatColor.GREEN + "$10");
        speedPotionItemMeta.setLore(speedlore);
        speedPotion.setItemMeta(speedPotionItemMeta);

        ItemStack healPotion = new ItemStack(Material.SPLASH_POTION);
        PotionMeta healPotionItemMeta = (PotionMeta) healPotion.getItemMeta();
        healPotionItemMeta.setDisplayName(ChatColor.GREEN + "Health Potion II");
        healPotionItemMeta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL, false, true));
        List<String> heallore = new ArrayList<>();
        heallore.add(ChatColor.DARK_GRAY + "―――――――――――――――――――――――――――");
        heallore.add(ChatColor.DARK_GRAY +  "x1 Health Splash Potion");
        heallore.add(ChatColor.YELLOW + "- " + ChatColor.GREEN + "Right-click to fill your inventory");
        heallore.add(ChatColor.DARK_GRAY + "―――――――――――――――――――――――――――");
        heallore.add(ChatColor.WHITE + "Price: " + ChatColor.GREEN + "$5");
        healPotionItemMeta.setLore(heallore);
        healPotion.setItemMeta(healPotionItemMeta);

        ItemStack firePotion = new ItemStack(Material.POTION);
        PotionMeta firePotionItemMeta = (PotionMeta) firePotion.getItemMeta();
        firePotionItemMeta.setDisplayName(ChatColor.GOLD + "Fire Resistance Potion (3:00)");
        firePotionItemMeta.setBasePotionData(new PotionData(PotionType.FIRE_RESISTANCE, false, false));
        List<String> firelore = new ArrayList<>();
        firelore.add(ChatColor.DARK_GRAY + "―――――――――――――――――――――――――――");
        firelore.add(ChatColor.DARK_GRAY +  "x1 Fire Resistance Potion");
        firelore.add(ChatColor.DARK_GRAY + "―――――――――――――――――――――――――――");
        firelore.add(ChatColor.WHITE + "Price: " + ChatColor.GREEN + "$25");
        firePotionItemMeta.setLore(firelore);
        firePotion.setItemMeta(firePotionItemMeta);

        ItemStack slownesspotion = new ItemStack(Material.SPLASH_POTION);
        PotionMeta slownesspotionItemMeta = (PotionMeta) slownesspotion.getItemMeta();
        slownesspotionItemMeta.setDisplayName(ChatColor.GRAY + "Slowness Potion (1:07)");
        slownesspotionItemMeta.setBasePotionData(new PotionData(PotionType.SLOWNESS, false, false));
        List<String> slownesslore = new ArrayList<>();
        slownesslore.add(ChatColor.DARK_GRAY + "―――――――――――――――――――――――――――");
        slownesslore.add(ChatColor.DARK_GRAY +  "x1 Slowness Splash Potion");
        slownesslore.add(ChatColor.DARK_GRAY + "―――――――――――――――――――――――――――");
        slownesslore.add(ChatColor.WHITE + "Price: " + ChatColor.GREEN + "$50");

        slownesspotionItemMeta.setLore(slownesslore);
        slownesspotion.setItemMeta(slownesspotionItemMeta);



        inventory.setItem(14,speedPotion);
        inventory.setItem(15,firePotion);
        inventory.setItem(24,healPotion);
        inventory.setItem(34,slownesspotion);

        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();
        ItemStack item = event.getCurrentItem();
        Inventory inventory = event.getInventory();
        int emptySlots = -1;
        for (ItemStack i : player.getInventory().getStorageContents()) {
            if (i == null || i.getType() == Material.AIR) {
                emptySlots++;
            }
        }
        if (inventory.getTitle().equals("Trader")) {

            event.setCancelled(true);



            if (item == null) return;

            if (item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Speed Potion II")) {
                if (player.getInventory().contains(Material.GOLD_INGOT)) {
                    if (emptySlots == -1) {
                        player.sendMessage("Inventory is full");
                    }
                    else{
                    player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT, 1));
                    player.getInventory().addItem(new ItemStack(Material.POTION, 1, (short) 8226));
                    player.sendMessage("You exchanged 1 gold for 1 diamond.");}
                }
            }
            if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Health Potion II")) {
                if (player.getInventory().contains(Material.GOLD_INGOT)) {
                    if (emptySlots == -1) {
                        player.sendMessage("Inventory is full");
                    } else  if (event.getClick() == ClickType.RIGHT) {
                        for (ItemStack i : player.getInventory().getStorageContents()) {
                            if (i == null || i.getType() == Material.AIR) {
                                player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT, 1));
                                player.getInventory().addItem(new ItemStack(Material.POTION, 1, (short) 16421));
                                player.sendMessage(String.valueOf(emptySlots));
                            }
                        }
                    }
                    else{
                    player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT, 1));
                    player.getInventory().addItem(new ItemStack(Material.POTION, 1, (short) 16421));
                    player.sendMessage("You exchanged 1 gold for 1 diamond.");}
                }
            }
            if (item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Fire Resistance Potion (3:00)")) {
                if (player.getInventory().contains(Material.GOLD_INGOT)) {
                    if (emptySlots == -1) {
                        player.sendMessage("Inventory is full");
                    } else {
                    player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT, 1));
                    player.getInventory().addItem(new ItemStack(Material.POTION, 1, (short) 8195));
                    player.sendMessage("You exchanged 1 gold for 1 diamond.");}
                }
            }
            if (item.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Slowness Potion (1:07)")) {
                if (player.getInventory().contains(Material.GOLD_INGOT)) {
                    if (emptySlots == -1) {
                        player.sendMessage("Inventory is full");
                    } else {
                    player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT, 1));
                    player.getInventory().addItem(new ItemStack(Material.POTION, 1, (short) 16394));
                    player.sendMessage("You exchanged 1 gold for 1 diamond.");}
                }
            }
            if (item.getItemMeta().getDisplayName() == null){
                return;
            }
        }

    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(item.getType() == Material.DIAMOND_BLOCK ) {
                event.setCancelled(true);
                Inventory menu = Bukkit.createInventory(null, 9, "Team Select");

                for (Team team : teams.getTeams().values()) {
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

        UUID attackerUUID = attacker.getUniqueId();
        UUID defenderUUID = target.getUniqueId();

        if (playerTeams.get(attackerUUID) == playerTeams.get(defenderUUID)) {
            event.setCancelled(true);
            attacker.sendMessage("You can't attack players from the same team.");
        }


    }

    public World getWorld(String name) {
        Iterator<World> worlds = Bukkit.getServer().getWorlds().iterator();
        while(worlds.hasNext()) {
            World w = worlds.next();
            String wName = w.getName().toLowerCase().trim();
            System.out.print(wName);
            System.out.print(name);
            if(wName.equalsIgnoreCase(name.toLowerCase().trim())) {
                System.out.print("debug");
                return w;
            }
        }

        return null;
    }



    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
    }
    private static int taskId;

    int time = 0;


    public static void stopTracking() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    public int getTime() {
        return time;
    }


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

    }


    public static Asd getInstance() {
        return instance;
    }


    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    @Override
    public void onDisable() {
        Asd.getInstance().getPluginManager().getDatabase().DisableDatabase();
        Asd.getInstance().getPluginManager().getEconomy().DisableEconomy();
    }
}