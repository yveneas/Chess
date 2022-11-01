package org.example.Model.Pieces;

import org.example.Model.Board;
import org.example.Model.Move;
import org.example.Model.Player;
import org.example.Model.Tile;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(boolean color) {
        super(color);
        this.setWeight(10);
    }

    @Override
    public boolean canMove(Board board, Tile startTile, Tile endTile) {
        //System.out.println(startTile.getX() + " " + startTile.getY());
        if (endTile.getPiece() != null && endTile.getPiece().isWhite() == this.isWhite()) {
            return false;
        }
        if (this.isWhite()) {
            if (startTile.getY() == 1) {
                return (startTile.getX() == endTile.getX() && (endTile.getY() == startTile.getY() + 1 || endTile.getY() == startTile.getY() + 2));
            } else if(startTile.getY() < 7) {
                if(startTile.getX() < 7 && (board.getTile(startTile.getX() + 1, startTile.getY()).getPiece() != null && board.getTile(startTile.getX() + 1, startTile.getY()).getPiece().isWhite() != this.isWhite())) {
                    return (startTile.getX() + 1 == endTile.getX() && startTile.getY() + 1 == endTile.getY());
                } else if (startTile.getX() > 0 && board.getTile(startTile.getX() - 1, startTile.getY()).getPiece() != null && board.getTile(startTile.getX() - 1, startTile.getY()).getPiece().isWhite() != this.isWhite()) {
                    return (startTile.getX() - 1 == endTile.getX() && startTile.getY() + 1 == endTile.getY());
                }
                return (startTile.getX() == endTile.getX() && endTile.getY() == startTile.getY() + 1);
            }
        } else {
            if (startTile.getY() == 6) {
                return (startTile.getX() == endTile.getX() && (endTile.getY() == startTile.getY() - 1 || endTile.getY() == startTile.getY() - 2));
            } else {
                if(startTile.getX() < 7 && (board.getTile(startTile.getX() + 1, startTile.getY()).getPiece() != null && board.getTile(startTile.getX() + 1, startTile.getY()).getPiece().isWhite() != this.isWhite())) {
                    return (startTile.getX() + 1 == endTile.getX() && startTile.getY() - 1 == endTile.getY());
                } else if (startTile.getX() > 0 && board.getTile(startTile.getX() - 1, startTile.getY()).getPiece() != null && board.getTile(startTile.getX() - 1, startTile.getY()).getPiece().isWhite() != this.isWhite()) {
                    return (startTile.getX() - 1 == endTile.getX() && startTile.getY() - 1 == endTile.getY());
                }
            }
            return (startTile.getX() == endTile.getX() && endTile.getY() == startTile.getY() - 1);
        }
        return false;
    }

    @Override
    public List<Move> getLegalMoves(Board board, Tile startTile, Player player) {
        List<Move> legalMoves = new ArrayList<>();
        int x = startTile.getX();
        int y = startTile.getY();
        for(int i = -1; i <= 1; i++) {
            for(int j = -2; j <= 2; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                Tile endTile = board.getTile(x + i, y + j);
                if (x + i >= 0 && x + i < 8 && y + j >= 0 && y + j < 8) {
                    if (canMove(board, startTile, endTile)) {
                        legalMoves.add(new Move(startTile, endTile, player));
                    }
                }
            }
        }
        //En passant

        return legalMoves;
    }

    @Override
    public String toString() {
        if (this.isWhite()) {
            return "P";
        }
        return "p";
    }
}
