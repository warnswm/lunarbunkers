package qwezxc.asd.core;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;

public class TeamLivesManager {
    private Map<Team, Integer> teamLivesMap;

    public TeamLivesManager() {
        teamLivesMap = new HashMap<>();
    }

    public int getTeamLives(Team team) {
        if (team == null) {
            return 0;
        }
        return teamLivesMap.computeIfAbsent(team, t -> t.getRemainingLives());
    }

    public void addTeamLives(Team team, int amount) {
        int teamLives = getTeamLives(team);
        teamLivesMap.put(team, teamLives + amount);
    }

    public void removeTeamLives(Team team, int amount) {
        int teamLives = getTeamLives(team);
        teamLivesMap.put(team, teamLives - amount);
        int remainingLives = getTeamLives(team);
        if (remainingLives <= 0) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getScoreboard().getTeam(team.getName()).getEntries().contains(player.getName())) {
                    player.setGameMode(GameMode.SPECTATOR);
                }
            }
        }
    }
}

