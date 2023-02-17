package qwezxc.asd.core;

import qwezxc.asd.Data.Database;

public class PluginManager {
    private static PluginManager instance;
    private PluginScoreboardManager pluginScoreboardManager;
    private Database database;

    private Teams teams;
    private Economy economy;
    private PlayerLivesManager playerLivesManager;


    private KOTH koth;
    private PluginManager() {
        // Initialize the managers and utilities
        this.teams = new Teams();
        this.database = new Database();
        this.economy = new Economy();
        this.playerLivesManager = new PlayerLivesManager();
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
    public PlayerLivesManager getPlayerLivesManager(){return this.playerLivesManager;}

}
