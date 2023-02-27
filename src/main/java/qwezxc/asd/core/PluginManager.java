package qwezxc.asd.core;

import lombok.Getter;
import qwezxc.asd.Data.Database;

@Getter
public class PluginManager {
    private static PluginManager instance;
    private Database database;
    private Teams teams;
    private PlayerLivesManager playerLivesManager;
    private Economy economy;
    private PlayerKillsManager playerKillsManager;


    private KOTH koth;
    private PluginManager() {
        // Initialize the managers and utilities
        this.teams = new Teams();
        this.database = new Database();
        this.playerLivesManager = new PlayerLivesManager();
        this.economy = new Economy();
        this.playerKillsManager = new PlayerKillsManager();
    }

    public static PluginManager getInstance() {
        if (instance == null) {
            instance = new PluginManager();
        }
        return instance;
    }

}
