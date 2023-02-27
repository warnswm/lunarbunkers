package qwezxc.asd.core;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Economy {
    private final HashMap<UUID, Integer> balances;

    public Economy() {
        balances = new HashMap<>();
    }

    public void addBalance(Player player, int amount) {
        UUID uuid = player.getUniqueId();
        int balance = getBalance(player);
        balance += amount;
        balances.put(uuid, balance);
    }

    public int getBalance(Player player) {
        UUID uuid = player.getUniqueId();
        if (balances.containsKey(uuid)) {
            return balances.get(uuid);
        } else {
            return 0;
        }
    }

    public boolean hasEnoughMoney(Player player, int amount) {
        int balance = getBalance(player);
        return balance >= amount;
    }

    public void removeBalance(Player player, int amount) {
        addBalance(player, -amount);
    }
}

