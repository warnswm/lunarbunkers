package qwezxc.asd.Items;

import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public class ItemInfo {
    private Material type;
    private int value;
    private int singleValue;

    public ItemInfo(Material type, int value, int singleValue) {
        this.type = type;
        this.value = value;
        this.singleValue = singleValue;
    }

    public Material getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public int getSingleValue() {
        return singleValue;
    }


    public static final List<ItemInfo> ITEM_INFOS = Arrays.asList(
            new ItemInfo(Material.COAL, 10, 10),
            new ItemInfo(Material.IRON_INGOT, 15, 15),
            new ItemInfo(Material.GOLD_INGOT, 25, 25),
            new ItemInfo(Material.DIAMOND, 30, 30)
            //ToDo: if added Emerald sell iron 20, gold 25, emerald 40
    );
}