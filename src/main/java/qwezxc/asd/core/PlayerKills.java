package qwezxc.asd.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlayerKills {
    private int kills;

    public void removeKills(int lives) {
        this.kills = Math.max(this.kills - lives, 0);
    }

    public void addKills(int kills) {
        this.kills += kills;
    }
}
