package qwezxc.asd.core;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Team {
    //List<UUID> player = new ArrayList<>();
    final String name;
    final ChatColor chatColor;
    final Material woolBlock;
    final int maxPlayers;
    final Location base;
    int remainingLives;

//    public void addplayer(UUID uuid){
//        player.add(uuid);
//    }
//
//    public void addplayer(Player player){
//        addplayer(player.getUniqueId());
//    }
}
