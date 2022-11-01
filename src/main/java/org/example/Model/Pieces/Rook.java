package org.example.Model.Pieces;

import org.example.Model.Board;
import org.example.Model.Move;
import org.example.Model.Player;
import org.example.Model.Tile;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

    public Rook(boolean color) {
        super(color);
        this.setWeight(50);
    }

    @Override
    public boolean canMove(Board board, Tile startTile, Tile endTile) {
        int x = Math.abs(startTile.getX() - endTile.getX());
        int y = Math.abs(startTile.getY() - endTile.getY());
        if(endTile.getPiece() != null && endTile.getPiece().isWhite() == this.isWhite()) {
            return false;
        }
        return x == 0 || y == 0;
    }

    @Override
    public List<Move> getLegalMoves(Board board, Tile startTile, Player player) {
        List<Move> legalMoves = new ArrayList<>();
        int x = startTile.getX();
        int y = startTile.getY();
        for(int i = 1; i <= 7; i++) {
            Tile endTile = board.getTile(x + i, y);
            if(endTile == null) {
                break;
            }
            if(endTile.getPiece() != null && endTile.getPiece().isWhite() == this.isWhite()) {
                break;
            } else if(endTile.getPiece() != null && endTile.getPiece().isWhite() != this.isWhite()) {
                legalMoves.add(new Move(startTile, endTile, player));
                break;
            } else {
                legalMoves.add(new Move(startTile, endTile, player));
            }
        }
        for(int i = 1; i <= 7; i++) {
            Tile endTile = board.getTile(x - i, y);
            if(endTile == null) {
                break;
            }
            if(endTile.getPiece() != null && endTile.getPiece().isWhite() == this.isWhite()) {
                break;
            } else if(endTile.getPiece() != null && endTile.getPiece().isWhite() != this.isWhite()) {
                legalMoves.add(new Move(startTile, endTile, player));
                break;
            } else {
                legalMoves.add(new Move(startTile, endTile, player));
            }
        }
        for(int i = 1; i <= 7; i++) {
            Tile endTile = board.getTile(x, y + i);
            if(endTile == null) {
                break;
            }
            if(endTile.getPiece() != null && endTile.getPiece().isWhite() == this.isWhite()) {
                break;
            } else if(endTile.getPiece() != null && endTile.getPiece().isWhite() != this.isWhite()) {
                legalMoves.add(new Move(startTile, endTile, player));
                break;
            } else {
                legalMoves.add(new Move(startTile, endTile, player));
            }
        }
        for(int i = 1; i <= 7; i++) {
            Tile endTile = board.getTile(x, y - i);
            if(endTile == null) {
                break;
            }
            if(endTile.getPiece() != null && endTile.getPiece().isWhite() == this.isWhite()) {
                break;
            } else if(endTile.getPiece() != null && endTile.getPiece().isWhite() != this.isWhite()) {
                legalMoves.add(new Move(startTile, endTile, player));
                break;
            } else {
                legalMoves.add(new Move(startTile, endTile, player));
            }
        }
        return legalMoves;
    }

    @Override
    public String toString() {
        if (this.isWhite()) {
            return "R";
        }
        return "r";
    }
}
