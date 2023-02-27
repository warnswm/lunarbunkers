package qwezxc.asd.Items;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DiamodPick {
    public static ItemStack createDiamondPickaxe() {
        // Create a new ItemStack for a diamond pickaxe
        ItemStack picaxe = new ItemStack(Material.DIAMOND_PICKAXE);

        net.minecraft.server.v1_12_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(picaxe);

        NBTTagCompound tag = new NBTTagCompound();
        tag.setBoolean("Unbreakable", true);
        nmsItemStack.setTag(tag);

        ItemStack it = CraftItemStack.asBukkitCopy(nmsItemStack);
        ItemMeta itemMeta = it.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + "Алмазная кирка");
        it.setItemMeta(itemMeta);

        return it;
    }
    public static ItemStack createStonePickaxe() {
        // Create a new ItemStack for a diamond pickaxe
        ItemStack picaxe = new ItemStack(Material.STONE_PICKAXE);

        net.minecraft.server.v1_12_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(picaxe);

        NBTTagCompound tag = new NBTTagCompound();
        tag.setBoolean("Unbreakable", true);
        tag.setBoolean("Soulbound", true);
        nmsItemStack.setTag(tag);

        ItemStack it = CraftItemStack.asBukkitCopy(nmsItemStack);
        ItemMeta itemMeta = it.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + "Каменная кирка");
        it.setItemMeta(itemMeta);

        return it;

    }

    public static ItemStack createStoneAxe() {
        // Create a new ItemStack for a diamond pickaxe
        ItemStack pickaxe = new ItemStack(Material.STONE_AXE);

        net.minecraft.server.v1_12_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(pickaxe);
        NBTTagList tagList = new NBTTagList();

        NBTTagCompound tag = new NBTTagCompound();
        tag.set("CanDestroy", tagList);
        tag.setBoolean("Unbreakable", true);
        tag.setBoolean("Soulbound", true);
        pickaxe = CraftItemStack.asBukkitCopy(nmsItemStack);

        return pickaxe;

    }

    public static ItemStack createDiamondAxe() {
        // Create a new ItemStack for a diamond pickaxe
        ItemStack axe = new ItemStack(Material.DIAMOND_AXE);

        net.minecraft.server.v1_12_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(axe);

        NBTTagCompound tag = new NBTTagCompound();
        tag.setBoolean("Unbreakable", true);
        nmsItemStack.setTag(tag);

        ItemStack it = CraftItemStack.asBukkitCopy(nmsItemStack);
        ItemMeta itemMeta = it.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + "Алмазный топор");
        it.setItemMeta(itemMeta);

        return it;
    }
}

