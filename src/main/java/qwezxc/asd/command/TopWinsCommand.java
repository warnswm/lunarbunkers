package qwezxc.asd.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scoreboard.Scoreboard;
import qwezxc.asd.Asd;

public class TopWinsCommand implements CommandExecutor {
    private Asd main;
    private Scoreboard scoreboard;

    public TopWinsCommand(final Asd main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
