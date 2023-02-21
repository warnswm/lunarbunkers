package qwezxc.asd;

import com.google.common.collect.Lists;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
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
import org.bukkit.plugin.java.JavaPlugin;
import qwezxc.asd.Data.Database;
import qwezxc.asd.command.BalanceCommand;
import qwezxc.asd.command.start;
import qwezxc.asd.command.teamcmd;
import qwezxc.asd.command.testcmd;
import qwezxc.asd.core.*;
import qwezxc.asd.listener.*;

import java.util.*;


public final class Asd extends JavaPlugin implements Listener {
    private static Asd instance;
    private Database database;
    private EconomyDataBaseOld economyDataBaseOld;
    public World world;
    private PlayerJoinListener playerJoinListener;
    private PlayerLivesManager playerLivesManager;

    private Map<UUID, Integer> minutesInCenter = new HashMap<>();
    private PluginManager pluginManager;
    public KOTH koth;
    public Economy economy;
    public GameManager gameManager;
    public TeamNPC teamNPC;
    private OreRegeneration oreRegen;
    private Teams teams = new Teams();
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
        this.database = new Database();
        this.economyDataBaseOld = new EconomyDataBaseOld();
        this.teams = new Teams();
        this.teamNPC = new TeamNPC(teams);
        this.gameManager = new GameManager(this, teams, teamNPC);
        this.economy = new Economy();
        playerJoinListener = new PlayerJoinListener(this, playerLivesManager, gameManager);
        playerLivesManager = new PlayerLivesManager();
        getServer().getPluginManager().registerEvents(new TeamMenuListener(teams), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLivesListener(playerLivesManager), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this, playerLivesManager, gameManager), this);
        koth = new KOTH(this, teams);

        Bukkit.getPluginManager().registerEvents(this, this);
        oreRegen = new OreRegeneration(this);
        Bukkit.getPluginManager().registerEvents(oreRegen, this);
        Bukkit.getPluginManager().registerEvents(new MainTrader(this), this);
        Bukkit.getPluginManager().registerEvents(new SellerListener(this), this);
        Bukkit.getPluginManager().registerEvents(new TeamTerritory(this, teams), this);
        Bukkit.getPluginManager().registerEvents(new NPCInteract(this), this);
        Bukkit.getPluginManager().registerEvents(teamNPC, this);

        getCommand("balance").setExecutor(new BalanceCommand(this));
        getCommand("pickaxe").setExecutor(new teamcmd(this));
        getCommand("testcmd").setExecutor(new testcmd(this));
        getCommand("start").setExecutor(new start(this, gameManager));


        world.setGameRuleValue("doDaylightCycle", "false");
        world.setThundering(false);
        world.setWeatherDuration(0);
        world.setTime(6000);

    }

    public void checkWorldsOnServer() {
        List<World> worlds = getServer().getWorlds();
        for (World world : worlds) {
            System.out.println("World on server: " + world.getName());
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

        UUID targetuuid = target.getUniqueId();
        UUID attackeruuid = attacker.getUniqueId();

        if (teams.getTeam(attackeruuid) == teams.getTeam(targetuuid)) {
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
        Location playerLoc = player.getLocation();
        double captureRadius = 3.5;
        Location capturePoint = new Location( Bukkit.getWorld("world"), 1.5, 63.5, 0.5);
        if (Math.abs(playerLoc.getX()- capturePoint.getX()) <= captureRadius  &&
                Math.abs(playerLoc.getY() - capturePoint.getY()) <= captureRadius  &&
                Math.abs(playerLoc.getZ() - capturePoint.getZ()) <= captureRadius ) {
            koth.startCapture(player);
        }else{
            koth.stopCapture(player);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        koth.stopCapture(player);
    }


    public static Asd getInstance() {
        return instance;
    }


    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    @Override
    public void onDisable() {
        for (NPC npc : Lists.newArrayList(CitizensAPI.getNPCRegistry())) {
            npc.destroy();
        }
        Asd.getInstance().getPluginManager().getDatabase().DisableDatabase();
    }

}
