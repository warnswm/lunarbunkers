package qwezxc.asd.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import qwezxc.asd.Asd;
import qwezxc.asd.core.GameManager;

public class StartCommand implements CommandExecutor {

    private Asd main;
    private GameManager gameManager;

    public StartCommand(Asd main, GameManager gameManager){
        this.gameManager = gameManager;
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        gameManager.execute();
        return false;
    }
}
