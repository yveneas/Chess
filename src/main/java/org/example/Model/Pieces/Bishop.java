package org.example.Model.Pieces;

import org.example.Model.Board;
import org.example.Model.Tile;

public class Bishop extends Piece {

    public Bishop(boolean color) {
        super(color);
    }

    @Override
    public boolean canMove(Board board, Tile startTile, Tile endTile) {
        int x = Math.abs(startTile.getX() - endTile.getX());
        int y = Math.abs(startTile.getY() - endTile.getY());
        if(endTile.getPiece().isWhite() == this.isWhite()) {
            return false;
        }
        return x == y;
    }
}
