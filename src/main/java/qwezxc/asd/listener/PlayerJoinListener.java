package qwezxc.asd.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import qwezxc.asd.Asd;
import qwezxc.asd.Data.Database;

public class PlayerJoinListener implements Listener {

    public Database database;

    public Asd main;

    public PlayerJoinListener(final Asd main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        main.getPluginManager().getDatabase().addPlayertoDatabase(player);
        main.getPluginManager().getEconomy().addPlayertoEconomyBase(player);
    }

}
