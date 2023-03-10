package qwezxc.asd.util;


import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import qwezxc.asd.Asd;

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

    public static String addLineBreaks(String string, int afterChars) {
        StringBuilder sb = new StringBuilder(string);
        int i = 0;
        while ((i = sb.indexOf(" ", i + afterChars)) != -1) {
            sb.replace(i, i + 1, "\n");
        }
        return sb.toString();
    }

    public static String declineNoun(String noun, int numeral) {
        if (noun.equals("Дубовая калитка")) {
            if (numeral == 1) {
                return "Дубовую калитку";
            } else if (numeral >= 2 && numeral <= 4) {
                return "Дубовые калитки";
            } else {
                return "Дубовых калиток";
            }
        } else if (noun.equals("Лестница")) { // добавляем склонение для слова "лестница"
            if (numeral == 1) {
                return "Лестницу";
            } else if (numeral >= 2 && numeral <= 4) {
                return "Лестницы";
            } else {
                return "Лестниц";
            }
        } else if (noun.equals("уголь")) {
            if (numeral == 1) {
                return "уголь";
            } else {
                return "угля";
            }
        }
        if (noun.equals("железный слиток")) {
            if (numeral == 1) {
                return "железный слиток";
            } else {
                return "железных слитков";
            }
        }
        if (noun.equals("золотой слиток")) {
            if (numeral == 1) {
                return "золотой слиток";
            } else {
                return "золотых слитков";
            }
        } else { // возвращаем исходное слово, если не знаем его склонение
            if (numeral == 1) {
                return noun;
            } else if (numeral >= 2 && numeral <= 4) {
                return noun + "а";
            } else {
                return noun + "ов";
            }
        }
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


    public static ItemStack createSellItem(Material material, String displayName, int price, Player player) {
        ItemStack item = new ItemStack(material, 1);
        net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString(displayName, "hello");
        nmsItem.setTag(tag);
        ItemStack newItem = CraftItemStack.asBukkitCopy(nmsItem);
        ItemMeta meta = newItem.getItemMeta();
        meta.setDisplayName(ChatColor.BLUE + displayName);
        int itemCount = 0;
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack != null && itemStack.getType() == material) {
                itemCount += itemStack.getAmount();
            }
        }
        if (itemCount != 0) {
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            lore.add(ChatColor.GRAY + "Щелкните левой кнопкой мыши, чтобы продать 1x " + Utils.declineNoun(displayName.replaceFirst("Продать ", ""), 1) + " за " + price + "$");
            int totalPrice = price * itemCount;
            lore.add(ChatColor.GRAY + "Щелкните правой кнопкой мыши, чтобы продать " + itemCount + "x " + Utils.declineNoun(displayName.replaceFirst("Продать ", ""), itemCount) + " за " + totalPrice + "$ ");
            lore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RED + "У вас недостаточно " + displayName.replaceFirst("Продать ", "") + " для продажи ");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createBuyItem(Material material, String displayName, int price, Player player) {
        ItemStack item = new ItemStack(material, 1);
        net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString(displayName, "hello");
        nmsItem.setTag(tag);
        ItemStack newItem = CraftItemStack.asBukkitCopy(nmsItem);
        ItemMeta meta = newItem.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + displayName);
        int itemCount;
        int playerMoney = Asd.getInstance().getPluginManager().getEconomy().getBalance(player);
        if (playerMoney >= price) {
            itemCount = playerMoney / price;
            itemCount = Math.min(itemCount, item.getMaxStackSize());

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            lore.add(ChatColor.GRAY + "Щелкните левой кнопкой мыши, чтобы купить 1 " + Utils.declineNoun(displayName, 1) + " за " + price + "$");

            int totalPrice = price * itemCount;
            lore.add(ChatColor.GRAY + "Щелкните правой кнопкой мыши, чтобы купить " + itemCount + " " + Utils.declineNoun(displayName, itemCount) + " за " + totalPrice + "$ ");
            lore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            meta.setLore(lore);

            item.setItemMeta(meta);
            return item;
        } else {
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.RED + "У вас недостаточно денег для покупки " + displayName);
            meta.setLore(lore);
        }

        item.setItemMeta(meta);


        return item;
    }

    public static ItemStack getItem(Material material, String name, List<String> lore) {
        ItemStack itemStack = new ItemStack(material);
        net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString(name, "hello");
        nmsItem.setTag(tag);
        ItemStack newItem = CraftItemStack.asBukkitCopy(nmsItem);
        ItemMeta itemMeta = newItem.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
    public static ItemStack getPotion(Material material, String name, PotionType potionType, boolean extander, boolean upgraded, List<String> lore) {
        ItemStack itemStack = new ItemStack(material);
        //Add tag
        net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString(name, "hello");
        nmsItem.setTag(tag);
        ItemStack newItem = CraftItemStack.asBukkitCopy(nmsItem);
        //Add displayname
        PotionMeta itemMeta = (PotionMeta) newItem.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setBasePotionData(new PotionData(potionType, extander, upgraded));
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
