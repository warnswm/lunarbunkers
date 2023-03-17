package qwezxc.asd.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import qwezxc.asd.Asd;

public class TestCommand implements CommandExecutor {



    private Asd main;

    public TestCommand(final Asd main) {
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = ((Player) sender).getPlayer();
        Asd.getInstance().getPluginManager().getDatabase().addWin(player.getUniqueId());
        return true;
    }

}
