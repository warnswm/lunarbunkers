package qwezxc.asd.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.nio.ByteBuffer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private Connection connection;
    private final Logger logger = Logger.getLogger(Database.class.getName());

    public Database() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:plugins/Asd/database.db");
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static byte[] uuidToByteArray(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    public void addWin(Player player) {
        UUID uuid = player.getUniqueId();
        byte[] uuidBytes = uuidToByteArray(uuid);
        try {
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE players SET wins = wins + 1 WHERE uuid = ?");
            updateStatement.setBytes(1, uuidBytes);
            int rowsAffected = updateStatement.executeUpdate();
            updateStatement.close();
            logger.log(Level.INFO, rowsAffected + " rows updated in the players table");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating player wins", e);
        }
    }

    public boolean removePlayer(UUID uuid) {
        byte[] uuidBytes = uuidToByteArray(uuid);
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM players WHERE uuid = ?");
            statement.setBytes(1, uuidBytes);
            int rowsAffected = statement.executeUpdate();
            statement.close();
            logger.log(Level.INFO, rowsAffected + " rows deleted from the players table");
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting player from database", e);
            return false;
        }
    }

    private void updateTopPlayers() {
        List<Player> topPlayers = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT uuid, name, wins FROM players ORDER BY wins DESC LIMIT 10");
            while (result.next()) {
                UUID uuid = UUID.nameUUIDFromBytes(result.getBytes("uuid"));
                Player player = Bukkit.getPlayer(uuid);
                if (player != null) {
                    topPlayers.add(player);
                }
            }
            statement.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating top players", e);
        }
    }

    public void addPlayertoDatabase(Player player) {
        UUID uuid = player.getUniqueId();
        byte[] uuidBytes = uuidToByteArray(uuid);
        if (!isPlayerInDatabase(uuidBytes)) {
            try {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO players (uuid, name, wins) VALUES (?, ?, 0)");
                statement.setBytes(1, uuidBytes);
                statement.setString(2, player.getName());
                int rowsAffected = statement.executeUpdate();
                statement.close();
                logger.log(Level.INFO, rowsAffected + " rows inserted into the players table");
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error inserting player into database", e);
            }
        }
    }

    public boolean isPlayerInDatabase(byte[] uuidBytes) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM players WHERE uuid = ?");
            statement.setBytes(1, uuidBytes);
            ResultSet result = statement.executeQuery();
            boolean exists = result.next();
            statement.close();
            return exists;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error checking if player exists in database", e);
            return false;
        }
    }

    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS players (uuid BLOB PRIMARY KEY, name TEXT, wins INTEGER)");
            statement.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error creating players table", e);
        }
    }

    public int getWins(Player player) {
        int wins = 0;
        UUID uuid = player.getUniqueId();
        byte[] uuidBytes = uuidToByteArray(uuid);
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT wins FROM players WHERE uuid = ?");
            statement.setBytes(1, uuidBytes);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                wins = result.getInt("wins");
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wins;
    }

    public void disableDatabase() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}