package qwezxc.asd.core;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerLivesManager {
    private Map<Player, PlayerLives> playerLivesMap;

    public PlayerLivesManager() {
        playerLivesMap = new HashMap<>();
    }

    public PlayerLives getPlayerLives(Player player) {
        return playerLivesMap.computeIfAbsent(player, p -> new PlayerLives(0));
    }

    public void givePlayerLives(Player player, int amount) {
        PlayerLives playerLives = getPlayerLives(player);
        playerLives.addLives(amount);
    }

    public void takePlayerLives(Player player, int amount) {
        PlayerLives playerLives = getPlayerLives(player);
        playerLives.removeLives(amount);
        int remainingLives = playerLives.getLives();
        player.sendMessage(String.valueOf(remainingLives));
        if (remainingLives <= 0) {
            player.setGameMode(GameMode.SPECTATOR);
        }
    }
    public int getRemainingLives(Player player) {
        PlayerLives playerLives = getPlayerLives(player);
        return playerLives.getLives();
    }

}

