package org.example.Model.Pieces;

import lombok.Getter;
import lombok.Setter;
import org.example.Model.Board;
import org.example.Model.Move;
import org.example.Model.Player;
import org.example.Model.Tile;

import java.util.List;

public abstract class Piece {
    @Getter @Setter
    private boolean killed;
    @Getter @Setter
    private boolean white;

    @Getter @Setter
    private int weight;

    public Piece(boolean color) {
        this.killed = false;
        this.white = color;
    }

    public boolean isWhite() {
        return white;
    }

    public boolean isKilled() {
        return killed;
    }
    public abstract boolean canMove(Board board, Tile startTile, Tile endTile);

    public abstract List<Move> getLegalMoves(Board board, Tile startTile, Player player);
    @Override
    public abstract String toString();
}
