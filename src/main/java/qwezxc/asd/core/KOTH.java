package qwezxc.asd.core;

import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import qwezxc.asd.Asd;
import qwezxc.asd.listener.DefaultListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class KOTH {

    private final Asd main;
    private final Teams teams;
    private final List<UUID> capturingPlayers;
    private BukkitRunnable captureTimer;
    public static int timeLeft;
    private final GameManager gameManager;


    public KOTH(Asd main, Teams teams, GameManager gameManager) {
        this.main = main;
        this.teams = teams;
        this.gameManager = gameManager;
        this.capturingPlayers = new ArrayList<>();
    }

    private void startCaptureTimer() {
        if (captureTimer == null) {
            captureTimer = new BukkitRunnable() {

                @Override
                public void run() {
                    timeLeft--;
                    if (timeLeft == 0) {
                        Team capturingTeam = teams.getTeam(capturingPlayers.get(0));
                        Bukkit.broadcastMessage(ChatColor.GREEN + "The " + capturingTeam.getName() + " team has captured the point!");
                        capturingPlayers.clear();
                        captureTimer.cancel();
                        captureTimer = null;
                        endGame();
                        DefaultListener.removeBlocks();
                        gameManager.stopGameStartTimer();

                    }
                }
            };
            captureTimer.runTaskTimer(main, 0L, 20L);
        }
    }


    // Method to start the capture point capture for a player
    public void startCapture(Player player) {
        UUID uuid = player.getUniqueId();
        if (capturingPlayers.contains(uuid)) return;
        if(!GameManager.canCapture) return;
        Team playerTeam = teams.getTeam(player);

        if (playerTeam != null) {
            if (capturingPlayers.isEmpty()) {
                capturingPlayers.add(uuid);
                Bukkit.broadcastMessage(ChatColor.GOLD + player.getName() + " из команды " + playerTeam.getName() + " начала захват " + "!");
                startCaptureTimer();
            } else {
                Team capturingTeam = teams.getTeam(capturingPlayers.get(0));

                if (capturingTeam == playerTeam) {
                    capturingPlayers.add(uuid);
                }
            }
        }
    }


    // Method to stop the capture point capture for a player
    public void stopCapture(Player player) {

        capturingPlayers.remove(player.getUniqueId());

        if (capturingPlayers.isEmpty() && captureTimer != null) {
            captureTimer.cancel();
            captureTimer = null;
            Bukkit.broadcastMessage(ChatColor.RED + "Захват был сброшен!");
            int gameTime = GameManager.gameTime;
            if (gameTime < 900) timeLeft = 450;
            else if (gameTime > 901 && gameTime < 1500) timeLeft = 300;
            else timeLeft = 150;
        }
    }
    private void endGame(){
        Bukkit.getScheduler().runTaskLater(main, CitizensAPI.getNPCRegistry()::deregisterAll, 2);
    }


}