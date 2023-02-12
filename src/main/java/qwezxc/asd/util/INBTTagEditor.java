/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.inventory.ItemStack
 */
package qwezxc.asd.util;

import org.bukkit.inventory.ItemStack;

public interface INBTTagEditor {
    public ItemStack addNBTTag(ItemStack var1, String var2, String var3);

    public String getNBTTagValue(ItemStack var1, String var2);

    public boolean hasNBTTag(ItemStack var1, String var2);
}

