package qwezxc.asd.customs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static qwezxc.asd.Utils.*;


public enum CustomMobs {
    Lich("&9Lich", 15, 5, EntityType.SKELETON, null, null, new loot(createItem(Material.ROTTEN_FLESH, 1, true, true, true, "&fPreserved Flesh", "&7A preserved flesh from a rotting corpse", "&7Not sure what you'd want this for, though", "&7", "&9Foodstuff"), 1, 3, 100)),
    ZOMBIE_SQUIRE("&bZombie Squire", 20, 60, EntityType.ZOMBIE, null, makeArmorSet(new ItemStack(Material.CHAINMAIL_HELMET), new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.IRON_BOOTS)), new loot(new ItemStack(Material.CHAINMAIL_CHESTPLATE), 35), new loot(new ItemStack(Material.CHAINMAIL_LEGGINGS), 35), new loot(new ItemStack(Material.CHAINMAIL_HELMET), 35), new loot(new ItemStack(Material.IRON_BOOTS), 25), new loot(new ItemStack(Material.IRON_SWORD), 40)),

    CHARRED_ARCHER("&8Charred Archer", 50, 35, EntityType.WITHER_SKELETON, enchantItem(new ItemStack(Material.BOW), Enchantment.ARROW_KNOCKBACK, 2), null, new loot(enchantItem(enchantItem(createItem(Material.BOW, 1, false, false, false, "&cBurnt Bow", "&7This bow is burnt to a crisp but remains intact", "&8due to special enchantments"), Enchantment.ARROW_FIRE, 1), Enchantment.ARROW_KNOCKBACK, 2), 100), new loot(new ItemStack(Material.BONE), 1, 5, 100)),
    ;
    private String Name;
    private double MaxHealth,spawnChance;
    private EntityType type;
    private List<loot> loolTable = new ArrayList<>();
    private ItemStack mainItem;
    private ItemStack[] armor;

    CustomMobs(String name, double maxHealth, double spawnChance, EntityType type, ItemStack mainItem, ItemStack[] armor,loot... lootItems) {
        Name = name;
        MaxHealth = maxHealth;
        this.spawnChance = spawnChance;
        this.type = type;
        this.mainItem = mainItem;
        this.armor = armor;
        loolTable = Arrays.asList(lootItems);
    }
    public LivingEntity spawn(Location location) {
        LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, type);
        entity.setRemoveWhenFarAway(true);
        entity.setCustomName(color(" &r&c" + (int) MaxHealth + "❤"));
        entity.setCustomNameVisible(true);
        entity.getFireTicks();
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
        if (entity.getType() == EntityType.ZOMBIE && ((Zombie) entity).isBaby()) {
            ((Zombie) entity).setBaby(false);
        } else if(entity.getType() == EntityType.HUSK && ((Husk) entity).isBaby()) {
            ((Husk) entity).setBaby(false);
        }
        return entity;
    }

    public void tryDropLoot(Player playerInventory) {
        for (loot item : loolTable) {
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
