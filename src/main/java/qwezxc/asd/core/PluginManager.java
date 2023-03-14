package qwezxc.asd.core;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import qwezxc.asd.Asd;
import qwezxc.asd.data.Database;
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

    private PluginManager() {
        this.teams = new Teams();
        this.database = new Database();
        this.teamLivesManager = new TeamLivesManager();
        this.economy = new Economy();
        this.playerKillsManager = new PlayerKillsManager();
        this.teamNPC = new TeamNPC(teams);
        this.gameManager = new GameManager(Asd.getInstance(), teams, teamNPC);
        this.scoreBoardLib = new ScoreBoardLib();
        koth = new KOTH(Asd.getInstance(), teams, gameManager);
        oreRegen = new OreRegeneration(Asd.getInstance());
    }

    public static PluginManager getInstance() {
        if (instance == null) {
            instance = new PluginManager();
        }
        return instance;
    }

}
