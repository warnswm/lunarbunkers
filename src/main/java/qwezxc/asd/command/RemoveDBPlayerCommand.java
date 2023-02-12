package qwezxc.asd.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import qwezxc.asd.Asd;
import qwezxc.asd.Data.Database;

import java.util.UUID;

public class RemoveDBPlayerCommand implements CommandExecutor {

    private Asd main;

    public RemoveDBPlayerCommand(final Asd main){
        this.main = main;
    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return false;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage("Usage: /removeplayer <player>");
            return false;
        }

        String targetName = args[0];
        UUID targetUUID = Bukkit.getOfflinePlayer(targetName).getUniqueId();

        // Use the Database class to remove the player from the database
        Database database = main.getInstance().getPluginManager().getDatabase();
        boolean removed = database.removePlayer(targetUUID);

        if (!removed) {
            player.sendMessage("Player " + targetName + " was not found in the database.");
            return false;
        }

        player.sendMessage("Player " + targetName + " was successfully removed from the database.");
        return true;
    }

}
