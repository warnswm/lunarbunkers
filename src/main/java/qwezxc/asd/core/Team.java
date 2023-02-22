package qwezxc.asd.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;

@Getter
@Setter
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


}
