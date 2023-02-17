package qwezxc.asd.util;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.Inventory;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class ItemBuilder {

    public static void create(String name, String lore, String lore2, Material mat, Inventory inv, int invp) {
        ItemStack stack = new ItemStack(mat);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(new String[] { ChatColor.GRAY + lore, ChatColor.GRAY + lore2 }));
        meta.setUnbreakable(true);
        stack.setItemMeta(meta);
        inv.setItem(invp, stack);
    }


    public static void createDurability(String name, String lore, String lore2, Material mat, short dura, Inventory inv, int invp) {
        ItemStack stack = new ItemStack(mat);
        stack.setDurability(dura);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(new String[] { ChatColor.GRAY + lore, ChatColor.GRAY + lore2 }));
        meta.setUnbreakable(true);
        stack.setItemMeta(meta);
        inv.setItem(invp, stack);
    }

    public static void createSkull(String name, String lore, String lore2, String owningplayer, Inventory inv, int invp) {
//        ItemStack stack = new ItemStack(Material., 1, (short) SkullType.PLAYER.ordinal());
//        SkullMeta smeta = (SkullMeta) stack.getItemMeta();
//        smeta.setOwner(owningplayer);
//        smeta.setDisplayName(name);
//        smeta.setLore(Arrays.asList(new String[] { ChatColor.GRAY + lore, ChatColor.GRAY + lore2 }));
//        smeta.setUnbreakable(true);
//        stack.setItemMeta(smeta);
//        inv.setItem(invp, stack);
    }

    public static void createArmor(String name, String lore, String lore2, Color color, Inventory inv, int invp) {
        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta chestmeta = (LeatherArmorMeta) chest.getItemMeta();
        chestmeta.setColor(color);
        chestmeta.setDisplayName(name);
        chestmeta.setLore(Arrays.asList(lore, lore2));
        chestmeta.setUnbreakable(true);
        chest.setItemMeta(chestmeta);
        inv.setItem(invp, chest);
    }

}
