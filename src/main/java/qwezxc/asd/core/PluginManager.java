package qwezxc.asd.core;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import qwezxc.asd.Data.Database;
import qwezxc.asd.Teams;

public class PluginManager {
    private static PluginManager instance;
    private PluginScoreboardManager pluginScoreboardManager;
    private Database database;

    private Teams teams;
    private Economy economy;

    private KOTH koth;
    private PluginManager() {
        // Initialize the managers and utilities
        this.teams = new Teams();
        this.database = new Database();
        this.economy = new Economy();
        this.pluginScoreboardManager = new PluginScoreboardManager();
    }

    public static PluginManager getInstance() {
        if (instance == null) {
            instance = new PluginManager();
        }
        return instance;
    }

    public Teams getTeams(){return this.teams;}
    public PluginScoreboardManager getScoreboardManager(){return this.pluginScoreboardManager;}
    public Database getDatabase() {
        return this.database;
    }

    public Economy getEconomy(){return this.economy;}

}
