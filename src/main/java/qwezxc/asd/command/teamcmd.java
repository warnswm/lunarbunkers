package qwezxc.asd.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import qwezxc.asd.Asd;
import qwezxc.asd.Items.DiamodPick;

import java.util.Collections;

public class teamcmd implements CommandExecutor {

    private Asd main;
    public teamcmd(final Asd main) {
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
