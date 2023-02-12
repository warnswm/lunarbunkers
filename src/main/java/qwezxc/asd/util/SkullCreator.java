/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.properties.Property
 *  com.mojang.authlib.properties.PropertyMap
 *  org.bukkit.Bukkit
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package qwezxc.asd.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public abstract class SkullCreator {
    public abstract ItemStack createHeadItem(String var1);

    protected GameProfile createProfileWithTexture(String texture) {
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
        PropertyMap propertyMap = gameProfile.getProperties();
        propertyMap.put((String) "textures", (Property) new Property("textures", texture));
        return gameProfile;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void setHeadTexture(ItemStack itemStack, String texture) {
        if (itemStack != null) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            try {
                Field field = itemMeta.getClass().getDeclaredField("profile");
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                try {
                    field.set((Object)itemMeta, (Object)this.createProfileWithTexture(texture));
                }
                finally {
                    if (!field.isAccessible()) {
                        field.setAccessible(false);
                    }
                }
            }
            catch (Exception ex) {
                Bukkit.getConsoleSender().sendMessage("Error!");
            }
            itemStack.setItemMeta(itemMeta);
        }
    }
}

