package qwezxc.asd.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import qwezxc.asd.Asd;

public class PlayerDropEvent implements Listener {
    public static Asd plugin;

    public PlayerDropEvent(Asd main) {
        this.plugin = main;
    }

    @EventHandler
    public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
        event.isCancelled();
        event.setCancelled(true);
    }
}
