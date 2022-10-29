package org.example.Model.Pieces;

import lombok.Getter;
import lombok.Setter;
import org.example.Model.Board;
import org.example.Model.Tile;

public abstract class Piece {
    @Getter @Setter
    private boolean killed;
    @Getter @Setter
    private boolean white;

    public Piece(boolean color) {
        this.killed = false;
        this.white = false;
    }

    public boolean isWhite() {
        return white;
    }

    public boolean isKilled() {
        return killed;
    }
    public abstract boolean canMove(Board board, Tile startTile, Tile endTile);
}
