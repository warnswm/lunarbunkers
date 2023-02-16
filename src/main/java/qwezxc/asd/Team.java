package qwezxc.asd;

import org.bukkit.ChatColor;
import org.bukkit.Material;

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

    public void setPrefix() {
        ChatColor color = ChatColor.WHITE;
        if (woolBlock == Material.YELLOW_GLAZED_TERRACOTTA) {
            color = ChatColor.YELLOW;
        } else if (woolBlock == Material.BLUE_GLAZED_TERRACOTTA) {
            color = ChatColor.BLUE;
        } else if (woolBlock == Material.GREEN_GLAZED_TERRACOTTA) {
            color = ChatColor.DARK_GREEN;
        } else if (woolBlock == Material.RED_GLAZED_TERRACOTTA) {
            color = ChatColor.RED;
        }
        String teamName = color + "[" + name + "] ";
        this.name = teamName;
    }
    public String getPrefix() {
        String[] split = this.name.split("\\[");
        if (split.length > 1) {
            String prefix = split[1].split("\\]")[0];
            return prefix;
        } else {
            return "";
        }
    }


}
