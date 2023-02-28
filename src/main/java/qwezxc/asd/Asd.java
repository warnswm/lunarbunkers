package qwezxc.asd;

import me.tigerhix.lib.scoreboard.ScoreboardLib;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import qwezxc.asd.Data.Database;
import qwezxc.asd.command.BalanceCommand;
import qwezxc.asd.command.StartCommand;
import qwezxc.asd.command.TeamCommand;
import qwezxc.asd.command.TestCommand;
import qwezxc.asd.core.*;
import qwezxc.asd.listener.*;

import java.util.Arrays;
import java.util.List;


public final class Asd extends JavaPlugin{
    private static Asd instance;
    private Database database;
    public World world;
    private PlayerJoinListener playerJoinListener;
    private TeamLivesManager teamLivesManager;
    private PluginManager pluginManager;
    public KOTH koth;
    public Economy economy;
    public GameManager gameManager;
    public TeamNPC teamNPC;
    private OreRegeneration oreRegen;
    public Teams teams = new Teams();
    private ScoreBoardLib scoreBoardLib;
    private PlayerKillsManager playerKillsManager;
    @Override
    public void onLoad() {

        instance = this;
    }

    @Override
    public void onEnable() {
        ScoreboardLib.setPluginInstance(this);
        world = Bukkit.getWorld("world");
        this.pluginManager = PluginManager.getInstance();
        this.database = new Database();
        this.teams = new Teams();
        this.teamNPC = new TeamNPC(teams);
        this.gameManager = new GameManager(this, teams, teamNPC);
        this.economy = new Economy();
        this.scoreBoardLib = new ScoreBoardLib();
        this.teamLivesManager = new TeamLivesManager();
        this.playerKillsManager = new PlayerKillsManager();
        koth = new KOTH(this, teams, gameManager);
        oreRegen = new OreRegeneration(this);

        registerListeners(Arrays.asList(
                new MainTraderListener(),
                new SellerListener(),
                new TeamTerritory(teams),
                new NPCInteract(),
                new EnchantShopListener(),
                new DefaultListener(this, scoreBoardLib),
                oreRegen,
                teamNPC,
                new TeamMenuListener(teams),
                new PlayerLivesListener(teamLivesManager, teams, playerKillsManager),
                new PlayerJoinListener(this, teamLivesManager, gameManager, scoreBoardLib, playerKillsManager),
                new RepairListener(),
                new BuildShopListener()
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

    public void createDaggerItem() {
        ItemStack dagger = new ItemStack(Material.IRON_AXE);
        ItemMeta daggerMeta = dagger.getItemMeta();
        daggerMeta.setDisplayName(ChatColor.GREEN + "Dagger");
        dagger.setItemMeta(daggerMeta);
        NamespacedKey daggerKey = new NamespacedKey(this, "dagger");
        ShapedRecipe daggerRecipe = new ShapedRecipe(daggerKey, dagger);
        daggerRecipe.shape(" # ", "#X#", " # ");
        daggerRecipe.setIngredient('#', Material.IRON_INGOT);
        daggerRecipe.setIngredient('X', Material.STICK);
        Bukkit.addRecipe(daggerRecipe);
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
