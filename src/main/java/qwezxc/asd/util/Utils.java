package qwezxc.asd.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void msgPlayer(Player player, String... strings) {
        for (String string : strings) {
            player.sendMessage(color(string));
        }
    }

    public static String declineNoun(String noun, int numeral) {
        if (noun.equals("Дубовую калитку")) {
            if (numeral == 1) {
                return "Дубовую калитку";
            } else {
                return "Дубовых калиток";
            }
        } else {
            if (numeral == 1) {
                return noun;
            } else if (numeral >= 2 && numeral <= 4) {
                return noun + "а";
            } else {
                return noun + "ов";
            }
        }
    }


    public static ItemStack createItem(Material type, int amount, boolean glow, boolean unbreakable, boolean hideUnb, String name, String... lines) {
        ItemStack item = new ItemStack(type, amount);
        ItemMeta meta = item.getItemMeta();
        if (glow) {
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
        }
        if (unbreakable) meta.setUnbreakable(true);
        if (hideUnb) meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        if(name != null) meta.setDisplayName(color(name));
        if (lines!=null){
            List<String>lore=new ArrayList<>();
            for (String line : lines){
                lore.add(color(line));
            }
            meta.setLore(lore);
        }
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack enchantItem(ItemStack item, Enchantment enchant, int level) {
        item.addUnsafeEnchantment(enchant, level);
        return item;
    }

    public static ItemStack[] makeArmorSet(ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
        ItemStack[] armor = new ItemStack[4];
        armor[3] = helmet;
        armor[2] = chestplate;
        armor[1] = leggings;
        armor[0] = boots;
        return armor;
    }

    public String getTimeFormat(int _time) {
        int time = _time;
        boolean plus = time < 0;
        if (time < 0) time = Math.abs(time);
        String first = "";
        if (!plus) first = "-";
        int sec = 0;
        if (time % 60 >= 10) sec = time % 60;

        return first + time/60 + ":" + sec;
    }

}
