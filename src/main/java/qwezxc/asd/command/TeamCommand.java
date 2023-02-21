package qwezxc.asd.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import qwezxc.asd.Asd;
import qwezxc.asd.Items.DiamodPick;

public class TeamCommand implements CommandExecutor {

    private Asd main;
    public TeamCommand(final Asd main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        player.getInventory().addItem(DiamodPick.createDiamondPickaxe());
        player.getInventory().addItem(DiamodPick.createStonePickaxe());
        return false;
    }
}
