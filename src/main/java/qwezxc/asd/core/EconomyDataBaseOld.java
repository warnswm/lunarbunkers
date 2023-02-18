package qwezxc.asd.core;

import java.util.UUID;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EconomyDataBaseOld {

    private Connection connection;

    public EconomyDataBaseOld() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:plugins/Asd/PlayerBalance.db");
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS players (" + "uuid TEXT PRIMARY KEY," + "name TEXT," + "balance REAL" + ")");
        statement.execute();
    }

    public void addBalance(UUID uuid, double amount) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE players SET balance = balance + ? WHERE uuid = ?");
            statement.setDouble(1, amount);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getBalance(UUID uuid) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT balance FROM players WHERE uuid = ?");
            statement.setString(1, uuid.toString());
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void removeBalance(UUID uuid, double amount) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE players SET balance = balance - ? WHERE uuid = ?"
            );
            statement.setDouble(1, amount);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void resetBalance(String uuid) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE players SET balance = 0 WHERE uuid = ?"
            );
            statement.setString(1, uuid);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPlayer(UUID uuid, String name) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT OR IGNORE INTO players (uuid, name, balance) VALUES (?, ?, 0)");
            statement.setString(1, uuid.toString());
            statement.setString(2, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean hasEnoughMoney(UUID uuid, double amount) {
        double balance = getBalance(uuid);
        return balance >= amount;
    }

}


