package qwezxc.asd.Data;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Database {
    private Connection connection;

    public Database() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:plugins/Asd/database.db");
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addWin(Player player) {
        UUID uuid = player.getUniqueId();
        String name = player.getName();
        try {
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE players SET wins = wins + 10 WHERE uuid = ?");
            updateStatement.setString(1, uuid.toString());
            updateStatement.executeUpdate();
            updateStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean removePlayer(UUID uuid) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM players WHERE uuid=?");
            statement.setString(1, uuid.toString());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void updateTopPlayers() {
        List<OfflinePlayer> topPlayers = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT uuid, name, wins FROM players ORDER BY wins DESC LIMIT 10");
            while (result.next()) {
                UUID uuid = UUID.fromString(result.getString("uuid"));
                OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
                topPlayers.add(player);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        for (int i = 0; i < topPlayers.size(); i++) {
//            OfflinePlayer player = topPlayers.get(i);
//            Team team = scoreboard.getTeam("top" + (i + 1));
//            if (team == null) {
//                team = scoreboard.registerNewTeam("top" + (i + 1));
//            }
//            team.addEntry(player.getName());
//            Objective objective = scoreboard.getObjective("wins");
//            Score score = objective.getScore(player.getName());
//            score.setScore(i + 1);
//        }
//        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
//            onlinePlayer.setScoreboard(scoreboard);
//        }
    }


    public void displayTopPlayers(Scoreboard scoreboard) {
        try {
            Objective objective = scoreboard.getObjective("top_players");
            if (objective == null) {
                objective = scoreboard.registerNewObjective("top_players", "dummy");
            }
            objective.setDisplayName("Top Players");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM players ORDER BY wins DESC LIMIT 10");
            ResultSet result = statement.executeQuery();
            int rank = 1;
            while (result.next()) {
                String uuid = result.getString("uuid");
                OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
                int wins = result.getInt("wins");
                Score score = objective.getScore(player);
                score.setScore(rank);
                rank++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPlayertoDatabase(Player player) {
        UUID uuid = player.getUniqueId();
        if (!isPlayerInDatabase(uuid)) {
            try {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO players (uuid, name, wins) VALUES (?, ?, 0)");
                statement.setString(1, uuid.toString());
                statement.setString(2, player.getName());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isPlayerInDatabase(UUID uuid) {
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


    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS players (uuid TEXT PRIMARY KEY, name TEXT, wins INTEGER)");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getWins(String uuid) {
        int wins = 0;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT wins FROM players WHERE uuid = ?");
            statement.setString(1, uuid);
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
    public void DisableDatabase(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
