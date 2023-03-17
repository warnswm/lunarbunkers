package qwezxc.asd.data;

import com.mongodb.MongoException;
import com.mongodb.client.*;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private MongoClient mongoClient;
    private MongoCollection<Document> playersCollection;
    private final Logger logger = Logger.getLogger(Database.class.getName());

    public Database() {
        try {
            mongoClient = MongoClients.create("url");
            MongoDatabase database = mongoClient.getDatabase("Asd");
            playersCollection = database.getCollection("players");
            createCollection();
        } catch (MongoException e) {
            logger.log(Level.SEVERE, "Error creating database connection", e);
        }
    }

    public void addWin(@NotNull UUID uuid) {
        Document playerDoc = playersCollection.find(new Document("uuid", uuid.toString())).first();
        if (playerDoc != null) {
            playersCollection.updateOne(new Document("uuid", uuid.toString()),
                    new Document("$inc", new Document("wins", 1)));
            logger.log(Level.INFO, "Player wins updated successfully");
        } else {
            addPlayer(uuid);
        }
    }

    public boolean removePlayer(@NotNull UUID uuid) {
        playersCollection.deleteOne(new Document("uuid", uuid.toString()));
        logger.log(Level.INFO, "Player deleted successfully");
        return true;
    }

    public List<Player> updateTopPlayers() {
        List<Player> topPlayers = new ArrayList<>();
        List<Document> topPlayerDocs = playersCollection.find()
                .sort(new Document("wins", -1)).limit(10).into(new ArrayList<>());
        for (Document playerDoc : topPlayerDocs) {
            UUID uuid = UUID.fromString(playerDoc.getString("uuid"));
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                topPlayers.add(player);
            }
        }
        return topPlayers;
    }

    public void addPlayer(@NotNull UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (playersCollection.find(new Document("uuid", uuid.toString())).first() != null) {
            logger.log(Level.WARNING, "Player already exists in database");
            return;
        }
        Document newPlayer = new Document("uuid", uuid.toString())
                .append("name", player.getName())
                .append("wins", 0)
                .append("kills",0)
                .append("death",0)
                .append("capturing_point",0);
        playersCollection.insertOne(newPlayer);
        logger.log(Level.INFO, "New player added to the database");
    }

    private void createCollection() {
        try {
            MongoIterable<String> collections = mongoClient.getDatabase("Asd").listCollectionNames();
            boolean collectionExists = false;
            for (String collectionName : collections) {
                if (collectionName.equals("players")) {
                    collectionExists = true;
                    break;
                }
            }
            if (!collectionExists) {
                mongoClient.getDatabase("Asd").createCollection("players");
                logger.log(Level.INFO, "Created players collection");
            } else {
                logger.log(Level.INFO, "Players collection already exists");
            }
        } catch (MongoException e) {
            logger.log(Level.SEVERE, "Error creating players collection", e);
        }
    }

    public int getWins(Player player) {
        int wins = 0;
        if (player == null) {
            return wins;
        }
        UUID uuid = player.getUniqueId();
        Document playerDoc = playersCollection.find(new Document("uuid", uuid.toString())).first();
        if (playerDoc != null) {
            wins = playerDoc.getInteger("wins");
        }
        return wins;
    }

    public void disableDatabase() {
        mongoClient.close();
        logger.log(Level.INFO, "Database connection closed");
    }
}
