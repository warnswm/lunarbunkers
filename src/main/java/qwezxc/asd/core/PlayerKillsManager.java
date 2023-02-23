package qwezxc.asd.core;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerKillsManager {
    private Map<Player, PlayerKills> playerKillsMap;

    public PlayerKillsManager() {
        playerKillsMap = new HashMap<>();
    }

    public PlayerKills getPlayerKills(Player player) {
        return playerKillsMap.computeIfAbsent(player, p -> new PlayerKills(0));
    }

    public void givePlayerKills(Player player, int amount) {
        PlayerKills playerKills = getPlayerKills(player);
        playerKills.setKills(amount);
    }

    public int getKills(Player player) {
        PlayerKills playerKills = getPlayerKills(player);
        return playerKills.getKills();
    }
    public void addPlayerKills(Player player){
        PlayerKills playerKills = getPlayerKills(player);
        playerKills.addKills(1);
    }

}
