package qwezxc.asd.core;

import lombok.Getter;
import qwezxc.asd.Data.Database;

@Getter
public class PluginManager {
    private static PluginManager instance;
    private PluginScoreboardManager pluginScoreboardManager;
    private Database database;
    private Teams teams;
    private EconomyDataBaseOld economyDataBaseOld;
    private PlayerLivesManager playerLivesManager;
    private Economy economy;


    private KOTH koth;
    private PluginManager() {
        // Initialize the managers and utilities
        this.teams = new Teams();
        this.database = new Database();
        this.economyDataBaseOld = new EconomyDataBaseOld();
        this.playerLivesManager = new PlayerLivesManager();
        this.pluginScoreboardManager = new PluginScoreboardManager();
        this.economy = new Economy();
    }

    public static PluginManager getInstance() {
        if (instance == null) {
            instance = new PluginManager();
        }
        return instance;
    }

}
