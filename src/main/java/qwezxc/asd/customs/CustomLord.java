package qwezxc.asd.customs;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

import static qwezxc.asd.Utils.*;


public enum CustomLord {
    LordLich("LordLicch", 1500, 100, EntityType.SKELETON, new ItemStack(Material.IRON_HOE), null, new loot(createItem(Material.ROTTEN_FLESH, 1, true, true, true, "&fPreserved Flesh", "&7A preserved flesh from a rotting corpse", "&7Not sure what you'd want this for, though", "&7", "&9Foodstuff"), 1, 3, 100)),
    ;

    public Chunk loc;
    private String Name;
    private double MaxHealth,spawnChance;
    private EntityType type;
    private List<loot> loolTable2 = new ArrayList<>();
    private ItemStack mainItem;
    private ItemStack[] armor;

    CustomLord(String name, double maxHealth, double spawnChance, EntityType type, ItemStack mainItem, ItemStack[] armor, loot... lootItems) {
        Name = name;
        MaxHealth = maxHealth;
        this.spawnChance = spawnChance;
        this.type = type;
        this.mainItem = mainItem;
        this.armor = armor;
        loolTable2 = Arrays.asList(lootItems);
    }

    public LivingEntity spawn5(Location location) {
        LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, type);
        entity.setCustomNameVisible(true);
        entity.setCustomName(color(" &r&c" + (int) MaxHealth + "❤"));
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(MaxHealth);
        entity.setHealth(MaxHealth);
        EntityEquipment inv = entity.getEquipment();
        if (armor != null) inv.setArmorContents(armor);
        inv.setHelmetDropChance(0f);
        inv.setChestplateDropChance(0f);
        inv.setLeggingsDropChance(0f);
        inv.setBootsDropChance(0f);
        inv.setItemInMainHand(mainItem);
        inv.setItemInMainHandDropChance(0f);
        return entity;
    }

    public void tryDropLoot(Player playerInventory) {
        for (loot item : loolTable2) {
            item.tryDropItem(playerInventory);
        }
    }


    public String getName() {
        return Name;
    }
    public double getSpawnChance() {
        return spawnChance;
    }
    public double getMaxHealth() {
        return MaxHealth;
    }
}
