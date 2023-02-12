package qwezxc.asd.core;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import qwezxc.asd.Data.Database;

public class PluginManager {
    private static PluginManager instance;

    private Database database;

    private Economy economy;

    private KOTH koth;
    private PluginManager() {
        // Initialize the managers and utilities
        this.database = new Database();
        this.economy = new Economy();
    }

    public static PluginManager getInstance() {
        if (instance == null) {
            instance = new PluginManager();
        }
        return instance;
    }

    public Database getDatabase() {
        return this.database;
    }

    public Economy getEconomy(){return this.economy;}

}
