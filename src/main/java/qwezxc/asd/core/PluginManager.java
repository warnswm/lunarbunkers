package qwezxc.asd.core;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import qwezxc.asd.Asd;
import qwezxc.asd.data.Database;
import qwezxc.asd.listener.DefaultListener;
import qwezxc.asd.listener.OreRegeneration;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PluginManager {
    static PluginManager instance;
    Database database;
    TeamLivesManager teamLivesManager;
    Economy economy;
    PlayerKillsManager playerKillsManager;
    OreRegeneration oreRegen;
    Teams teams;
    ScoreBoardLib scoreBoardLib;
    KOTH koth;
    GameManager gameManager;
    TeamNPC teamNPC;
    DefaultListener defaultListener;

    private PluginManager() {
        teams = new Teams();
        database = new Database();
        teamLivesManager = new TeamLivesManager();
        economy = new Economy();
        playerKillsManager = new PlayerKillsManager();
        teamNPC = new TeamNPC(teams);
        scoreBoardLib = new ScoreBoardLib();
        oreRegen = new OreRegeneration(Asd.getInstance());
        gameManager = new GameManager(Asd.getInstance(), teams, teamNPC);
        koth = new KOTH(Asd.getInstance(), teams, gameManager);
        defaultListener = new DefaultListener(Asd.getInstance(), scoreBoardLib, koth, teams);
    }

    public static PluginManager getInstance() {
        if (instance == null) {
            instance = new PluginManager();
        }
        return instance;
    }
}
