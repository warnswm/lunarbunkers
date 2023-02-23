package qwezxc.asd.core;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
        playerKills.addKills(amount);
    }

    public void takePlayerKills(Player player, int amount) {
        PlayerKills playerkills = getPlayerKills(player);
        playerkills.removeKills(amount);

    }
    public int getPKills(Player player) {
        PlayerKills playerKills = getPlayerKills(player);
        return playerKills.getKills();
    }

}
