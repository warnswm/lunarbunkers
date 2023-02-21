package qwezxc.asd.core;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

public class Economy {
    private HashMap<UUID, Double> balances;

    public Economy() {
        balances = new HashMap<>();
    }

    public void addBalance(Player player, double amount) {
        UUID uuid = player.getUniqueId();
        double balance = getBalance(player);
        balance += amount;
        balances.put(uuid, balance);
    }

    public double getBalance(Player player) {
        UUID uuid = player.getUniqueId();
        if (balances.containsKey(uuid)) {
            return balances.get(uuid);
        } else {
            return 0;
        }
    }

    public boolean hasEnoughMoney(Player player, double amount) {
        double balance = getBalance(player);
        return balance >= amount;
    }

    public void removeBalance(Player player, double amount) {
        addBalance(player, -amount);
    }
}

