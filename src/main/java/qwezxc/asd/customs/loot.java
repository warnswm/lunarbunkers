package qwezxc.asd.customs;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class loot{

    private ItemStack drop;
    private int min = 1, max = 1;
    private double dropRate;
    private static Random randomiser = new Random();

    public loot(ItemStack drop, double dropRate) {
        this.drop = drop;
        this.dropRate = dropRate;
    }

    public loot(ItemStack drop, int min, int max, double dropRate) {
        this.drop = drop;
        this.min = min;
        this.max = max;
        this.dropRate = dropRate;
    }

    public void tryDropItem(Player p) {
        if (Math.random() * 101 > dropRate) return;
        int amount = randomiser.nextInt(max - min + 1) + min;
        if (amount == 0) return;
        PlayerInventory inv = p.getInventory();
        ItemStack item = this.drop.clone();
        item.setAmount(amount);
        inv.addItem(item);
        /*playerPickupItemEvent.getItem().remove();
        playerPickupItemEvent.setCancelled(true);
        PlManager.getItemStack(item.getItemStack(), PluginListener.plugin.getServer().getPlayer((String)list.get((int)(Math.random() * list.size()))));
        return;*/
    }
}