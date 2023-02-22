package qwezxc.asd.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import qwezxc.asd.Asd;

public class BalanceCommand implements CommandExecutor {
    private Asd main;

    public BalanceCommand(Asd main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            double balance = Asd.getInstance().getPluginManager().getEconomy().getBalance(player);
            player.sendMessage("Your balance is " + balance);
        } else {
            sender.sendMessage("This command can only be used by a player");
        }
        return true;
    }
}