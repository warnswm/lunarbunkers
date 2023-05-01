package qwezxc.asd;

import lombok.Getter;
import me.tigerhix.lib.scoreboard.ScoreboardLib;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import qwezxc.asd.command.BalanceCommand;
import qwezxc.asd.command.StartCommand;
import qwezxc.asd.command.TeamCommand;
import qwezxc.asd.command.TestCommand;
import qwezxc.asd.core.PluginManager;
import qwezxc.asd.core.TeamTerritory;
import qwezxc.asd.listener.*;


public final class Asd extends JavaPlugin {
    @Getter
    private static Asd instance;
    public  World world;
    @Getter
    private static DefaultListener defaultListener;
    @Getter
    private PluginManager pluginManager;

    @Override
    public void onEnable() {
        instance = this;
        ScoreboardLib.setPluginInstance(this);
        world = Bukkit.getWorld("world");
        this.pluginManager = PluginManager.getInstance();

        defaultListener = new DefaultListener(this, pluginManager.getScoreBoardLib(), pluginManager.getKoth(), pluginManager.getTeams());

        registerListeners(
                new MainTraderListener(),
                new SellerListener(),
                new TeamTerritory(pluginManager.getTeams()),
                new NPCInteract(),
                new EnchantShopListener(pluginManager.getEconomy()),
                defaultListener,
                pluginManager.getOreRegen(),
                pluginManager.getTeamNPC(),
                new TeamMenuListener(pluginManager.getTeams()),
                new PlayerJoinListener(this, pluginManager.getTeamLivesManager(), pluginManager.getGameManager(), pluginManager.getScoreBoardLib(), pluginManager.getPlayerKillsManager(), pluginManager.getTeams()),
                new RepairListener(),
                new BuildShopListener()
        );

        getCommand("balance").setExecutor(new BalanceCommand(this));
        getCommand("pickaxe").setExecutor(new TeamCommand(this));
        getCommand("testcmd").setExecutor(new TestCommand(this));
        getCommand("start").setExecutor(new StartCommand(this, pluginManager.getGameManager()));
        Bukkit.getScheduler().runTaskLater(this, CitizensAPI.getNPCRegistry()::deregisterAll, 2);
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setThundering(false);
        world.setTime(6000);

    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }
    @Override
    public void onDisable() {

        Asd.getInstance().getPluginManager().getDatabase().disableDatabase();
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
