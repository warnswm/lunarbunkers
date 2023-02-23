package qwezxc.asd.core;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PlayerKills {
    private int kills;

    public PlayerKills(int kills) {
        this.kills = kills;
    }

    public void removeKills(int lives) {
        this.kills -= lives;
        if (this.kills < 0) {
            this.kills = 0;
        }
    }
    public void addKills(int kills) {
        this.kills += kills;
    }
}
