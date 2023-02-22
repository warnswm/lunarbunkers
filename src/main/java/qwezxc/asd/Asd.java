package qwezxc.asd;

import com.google.common.collect.Lists;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import qwezxc.asd.Data.Database;
import qwezxc.asd.command.BalanceCommand;
import qwezxc.asd.command.StartCommand;
import qwezxc.asd.command.TeamCommand;
import qwezxc.asd.command.TestCommand;
import qwezxc.asd.core.*;
import qwezxc.asd.listener.*;

import java.util.*;


public final class Asd extends JavaPlugin{
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
    public Teams teams = new Teams();
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
        this.playerLivesManager = new PlayerLivesManager();
        koth = new KOTH(this, teams);
        oreRegen = new OreRegeneration(this);

        registerListeners(Arrays.asList(
                new MainTrader(),
                new SellerListener(),
                new TeamTerritory(teams),
                new NPCInteract(),
                new DefaultListener(this),
                oreRegen,
                teamNPC,
                new TeamMenuListener(teams),
                new PlayerLivesListener(playerLivesManager,teams),
                new PlayerJoinListener(this, playerLivesManager, gameManager)
        ));

        getCommand("balance").setExecutor(new BalanceCommand(this));
        getCommand("pickaxe").setExecutor(new TeamCommand(this));
        getCommand("testcmd").setExecutor(new TestCommand(this));
        getCommand("start").setExecutor(new StartCommand(this, gameManager));
        Bukkit.getScheduler().runTaskLater(this, CitizensAPI.getNPCRegistry()::deregisterAll, 2);
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setThundering(false);
        world.setTime(6000);

    }

    public void checkWorldsOnServer() {
        List<World> worlds = getServer().getWorlds();
        for (World world : worlds) {
            System.out.println("World on server: " + world.getName());
        }
    }

    private void registerListeners(List<Listener> listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
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
        for (Location loc: OreRegeneration.breakBlocks.keySet()) {
            loc.getBlock().setType(OreRegeneration.breakBlocks.get(loc));
        }
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof ArmorStand) {
                    entity.remove();
                }
            }
        }
    }
}
