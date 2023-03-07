package qwezxc.asd.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;

@Getter
@Setter
@AllArgsConstructor
public class Team {
    private final String name;
    private final ChatColor chatColor;
    private final Material woolBlock;
    private final int maxPlayers;
    private final Location base;
    private int remainingLives;
}
