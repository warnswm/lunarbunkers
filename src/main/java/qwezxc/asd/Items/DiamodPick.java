package qwezxc.asd.Items;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagList;
import net.minecraft.server.v1_12_R1.NBTTagString;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import qwezxc.asd.Asd;

import java.util.ArrayList;
import java.util.List;

public class DiamodPick {
    public static ItemStack createDiamondPickaxe() {
        ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);


        List<String> canBreak = new ArrayList<>();
        canBreak.add("minecraft:diamond_ore");
        canBreak.add("minecraft:gold_ore");
        canBreak.add("minecraft:iron_ore");
        canBreak.add("minecraft:coal_ore");

        net.minecraft.server.v1_12_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(pickaxe);
        NBTTagList tagList = new NBTTagList();

        for (String block : canBreak) {
            NBTTagString tag = new NBTTagString(block);
            tagList.add(tag);
        }

        NBTTagCompound tag = new NBTTagCompound();
        tag.set("CanDestroy", tagList);
        tag.setBoolean("Unbreakable", true);
        nmsItemStack.setTag(tag);
        nmsItemStack.getTag().setInt("HideFlags", 2);
        nmsItemStack.getTag().setInt("HideFlags", 3);
        nmsItemStack.getTag().setInt("HideFlags", 4);
        pickaxe = CraftItemStack.asBukkitCopy(nmsItemStack);

        return pickaxe;


    }
    public static ItemStack createStonePickaxe() {
        // Create a new ItemStack for a diamond pickaxe
        ItemStack pickaxe = new ItemStack(Material.STONE_PICKAXE);


        List<String> canBreak = new ArrayList<>();
        canBreak.add("minecraft:diamond_ore");
        canBreak.add("minecraft:gold_ore");
        canBreak.add("minecraft:iron_ore");
        canBreak.add("minecraft:coal_ore");

        net.minecraft.server.v1_12_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(pickaxe);
        NBTTagList tagList = new NBTTagList();

        for (String block : canBreak) {
            NBTTagString tag = new NBTTagString(block);
            tagList.add(tag);
        }

        NBTTagCompound tag = new NBTTagCompound();
        tag.set("CanDestroy", tagList);
        tag.setBoolean("Unbreakable", true);
        nmsItemStack.setTag(tag);
        nmsItemStack.getTag().setInt("HideFlags", 2);
        nmsItemStack.getTag().setInt("HideFlags", 3);
        nmsItemStack.getTag().setInt("HideFlags", 4);
        pickaxe = CraftItemStack.asBukkitCopy(nmsItemStack);

        return pickaxe;

    }
}

