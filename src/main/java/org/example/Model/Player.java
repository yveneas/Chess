package org.example.Model;

import lombok.Getter;
import lombok.Setter;
import org.example.Model.Pieces.*;

/**
 * This class represents a player.
 */
public class Player {
    @Getter @Setter
    private boolean white;
    @Getter @Setter
    private boolean turn;

    public Player(boolean white) {
        this.white = white;
        this.turn = false;
    }
}
