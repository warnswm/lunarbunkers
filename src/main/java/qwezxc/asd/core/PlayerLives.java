package qwezxc.asd.core;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class PlayerLives {
    private int lives;

    public PlayerLives(int lives) {
        this.lives = lives;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void addLives(int lives) {
        this.lives += lives;
    }

    public void removeLives(int lives) {
        this.lives -= lives;
        if (this.lives < 0) {
            this.lives = 0;
        }
    }
}
