package org.example.Model.Pieces;

import org.example.Model.Board;
import org.example.Model.Tile;

public class Pawn extends Piece {

    public Pawn(boolean color) {
        super(color);
    }

    @Override
    public boolean canMove(Board board, Tile startTile, Tile endTile) {
        if(endTile.getPiece().isWhite() == this.isWhite()) {
            return false;
        }
        if(this.isWhite()) {
            if(startTile.getY() == 1) {
                return (startTile.getX() == endTile.getX() && (endTile.getY() == startTile.getY() + 1 || endTile.getY() == startTile.getY() + 2));
            }
            return (startTile.getX() == endTile.getX() && endTile.getY() == startTile.getY() + 1);
        }else {
            if(startTile.getY() == 6) {
                return (startTile.getX() == endTile.getX() && (endTile.getY() == startTile.getY() - 1 || endTile.getY() == startTile.getY() - 2));
            }
            return (startTile.getX() == endTile.getX() && endTile.getY() == startTile.getY() - 1);
        }
    }
}
