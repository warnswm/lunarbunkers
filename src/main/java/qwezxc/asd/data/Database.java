package qwezxc.asd.data;

import com.mongodb.MongoCommandException;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
        ClientSession session = mongoClient.startSession();
        try {
            session.startTransaction();

            List<Player> topPlayers = playersCollection.find()
                    .sort(new Document("wins", -1))
                    .projection(Projections.include("uuid"))
                    .limit(10)
                    .into(new ArrayList<>())
                    .stream()
                    .map(playerDoc -> Bukkit.getPlayer(UUID.fromString(playerDoc.getString("uuid"))))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            session.commitTransaction();
            return topPlayers;
        } catch (MongoCommandException e) {
            session.abortTransaction();
        } finally {
            session.close();
        }

        return new ArrayList<>();
    }





    public boolean addPlayer(@NotNull UUID uuid) {
        ClientSession session = mongoClient.startSession();
        try {
            session.startTransaction();
            if (playersCollection.find(Filters.eq("uuid", uuid.toString()))
                    .iterator()
                    .hasNext()) return true;
            playersCollection.insertOne(new Document("uuid", uuid.toString())
                    .append("wins", 0)
                    .append("kills",0)
                    .append("death",0)
                    .append("capturing_point",0));
            session.commitTransaction();
            return true;
        } catch (MongoCommandException e) {
            session.abortTransaction();
        } finally {
            session.close();
        }
        return false;
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
