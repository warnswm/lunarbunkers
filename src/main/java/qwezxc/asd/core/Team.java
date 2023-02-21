package qwezxc.asd.core;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;

public class Team {
    private String name;
    private Material woolBlock;
    private int maxPlayers;
    private ChatColor chatColor;
    private String prefix;
    private final Location base;

    public Team(String name, ChatColor chatColor, Material woolBlock, int maxPlayers, Location base) {
        this.name = name;
        this.chatColor = chatColor;
        this.woolBlock = woolBlock;
        this.maxPlayers = maxPlayers;
        this.base = base;
    }

    public Location getBase() {
        return base;
    }

    public String getName() {
        return name;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public Material getWoolBlock() {
        return woolBlock;
    }
    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

}
