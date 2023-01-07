package qwezxc.asd.commands;

import com.google.common.collect.Lists;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.FollowTrait;
import net.citizensnpcs.trait.GameModeTrait;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import qwezxc.asd.Asd;

import java.util.ArrayList;
import java.util.UUID;

public class companioncommand implements CommandExecutor {
    private Asd main;
    public companioncommand(final Asd main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String name = sender.getName();
        NPC guard ;
        boolean npcinworld = false;
        final OfflinePlayer player = Bukkit.getOfflinePlayer(name);
        if (sender instanceof Player) {
            for (NPC npc : Lists.newArrayList(CitizensAPI.getNPCRegistry())) {
                if (npc.getName().equals(name + " pet")) {
                    npc.destroy();
                    npcinworld = true;
                }
            }
            if (npcinworld == false) {
                guard = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, UUID.randomUUID(), 1, name + " pet");
                guard.spawn(((Player) sender).getLocation());
                guard.getOrAddTrait(FollowTrait.class).toggle(player,true);
                guard.getOrAddTrait(GameModeTrait.class).setGameMode(GameMode.SURVIVAL);
                guard.data().setPersistent(NPC.Metadata.COLLIDABLE, false);
            }
        }
        return true;
    }
}
