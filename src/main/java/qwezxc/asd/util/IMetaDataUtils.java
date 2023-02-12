/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.block.Block
 */
package qwezxc.asd.util;

import org.bukkit.block.Block;

public interface IMetaDataUtils {
    public void setMetaData(Block var1, String var2, String var3);

    public void removeMetaData(Block var1, String var2);

    public String getMetaData(Block var1, String var2);
}

