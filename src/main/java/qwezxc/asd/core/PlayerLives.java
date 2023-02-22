package qwezxc.asd.core;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.StandardException;

@Getter
@Setter
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
