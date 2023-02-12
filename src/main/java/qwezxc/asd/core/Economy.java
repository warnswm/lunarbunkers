package qwezxc.asd.core;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.HashMap;
import java.util.UUID;

public class Economy {
    private HashMap<UUID, Double> balances = new HashMap<>();
    private Connection connection;

    public Economy(){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:plugins/Asd/PlayerBalance.db");
            createTable();
            loadBalances();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable() throws SQLException {
        connection.prepareStatement("CREATE TABLE IF NOT EXISTS players (uuid TEXT PRIMARY KEY, name TEXT, balance REAL)").execute();
    }

    private void loadBalances() throws SQLException {
        ResultSet result = connection.prepareStatement("SELECT * FROM players").executeQuery();
        while (result.next()) {
            UUID uuid = UUID.fromString(result.getString("uuid"));
            double balance = result.getDouble("balance");
            balances.put(uuid, balance);
        }
    }
    private void saveBalances() throws SQLException {
        for (UUID uuid : balances.keySet()) {
            double balance = balances.get(uuid);
            Player player = Bukkit.getPlayer(uuid);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO players (uuid, name ,balance) VALUES (?, ?) ON CONFLICT(uuid) DO UPDATE SET balance = ?");
            statement.setString(1, uuid.toString());
            statement.setString(2, player.getName());
            statement.setDouble(3, balance);
            statement.execute();
        }
    }

    public double getBalance(UUID playerUUID) {
        return balances.getOrDefault(playerUUID, 0.0);
    }

    public void DisableEconomy(){
        try {
            saveBalances();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isPlayerinEconomyBase(UUID uuid) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM players WHERE uuid = ?");
            statement.setString(1, uuid.toString());
            ResultSet result = statement.executeQuery();
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void addPlayertoEconomyBase(Player player) {
        UUID uuid = player.getUniqueId();
        if (!isPlayerinEconomyBase(uuid)) {
            try {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO players (uuid, name, balance) VALUES (?, ?, 0)");
                statement.setString(1, uuid.toString());
                statement.setString(2, player.getName());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
