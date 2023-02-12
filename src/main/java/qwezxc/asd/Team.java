package qwezxc.asd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class Team {
    private String name;
    private Material woolBlock;

    public Team(String name, Material woolBlock) {
        this.name = name;
        this.woolBlock = woolBlock;
    }

    public String getName() {
        return name;
    }

    public Material getWoolBlock() {
        return woolBlock;
    }

}
