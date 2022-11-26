package org.example.Model.Pieces;

import org.example.Model.Board;
import org.example.Model.Move;
import org.example.Model.Player;
import org.example.Model.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Queen piece.
 */
public class Queen extends Piece {

    public Queen(boolean color) {
        super(color);
        this.setWeight(90);
    }

    /**
     * This method checks if the Queen can move to the endTile.
     * @param board on which the Queen is placed.
     * @param startTile from which the Queen is moved.
     * @param endTile to which the Queen is moved.
     * @return true if the Queen can move to the endTile, false otherwise.
     */
    @Override
    public boolean canMove(Board board, Tile startTile, Tile endTile) {
        int x = Math.abs(startTile.getX() - endTile.getX());
        int y = Math.abs(startTile.getY() - endTile.getY());
        if (endTile.getPiece() != null && endTile.getPiece().isWhite() == this.isWhite()) {
            return false;
        }

        return x == y || x == 0 || y == 0;
    }

    /**
     * This method returns a list of all legal moves for the Queen.
     * @param board on which the Queen is placed.
     * @param startTile from which the Queen is moved.
     * @param player who is moving the Queen.
     * @return a list of all legal moves for the Queen.
     */
    @Override
    public List<Move> getLegalMoves(Board board, Tile startTile, Player player) {
        List<Move> legalMoves = new ArrayList<>();
        int x = startTile.getX();
        int y = startTile.getY();

        for (int i = 1; i < 7; i++) {
            Tile endTile = board.getTile(x + i, y + i);
            if (endTile == null) {
                break;
            }
            if (endTile.getPiece() != null && endTile.getPiece().isWhite() == this.isWhite()) {
                break;
            } else if (endTile.getPiece() != null && endTile.getPiece().isWhite() != this.isWhite()) {
                legalMoves.add(new Move(startTile, endTile, player));
                break;
            } else {
                legalMoves.add(new Move(startTile, endTile, player));
            }
        }

        for (int i = 1; i < 7; i++) {
            Tile endTile = board.getTile(x + i, y - i);
            if (endTile == null) {
                break;
            }
            if (endTile.getPiece() != null && endTile.getPiece().isWhite() == this.isWhite()) {
                break;
            } else if (endTile.getPiece() != null && endTile.getPiece().isWhite() != this.isWhite()) {
                legalMoves.add(new Move(startTile, endTile, player));
                break;
            } else {
                legalMoves.add(new Move(startTile, endTile, player));
            }
        }

        for (int i = 1; i < 7; i++) {
            Tile endTile = board.getTile(x - i, y + i);
            if (endTile == null) {
                break;
            }
            if (endTile.getPiece() != null && endTile.getPiece().isWhite() == this.isWhite()) {
                break;
            } else if (endTile.getPiece() != null && endTile.getPiece().isWhite() != this.isWhite()) {
                legalMoves.add(new Move(startTile, endTile, player));
                break;
            } else {
                legalMoves.add(new Move(startTile, endTile, player));
            }
        }

        for (int i = 1; i < 7; i++) {
            Tile endTile = board.getTile(x - i, y - i);
            if (endTile == null) {
                break;
            }
            if (endTile.getPiece() != null && endTile.getPiece().isWhite() == this.isWhite()) {
                break;
            } else if (endTile.getPiece() != null && endTile.getPiece().isWhite() != this.isWhite()) {
                legalMoves.add(new Move(startTile, endTile, player));
                break;
            } else {
                legalMoves.add(new Move(startTile, endTile, player));
            }
        }

        for (int i = 1; i <= 7; i++) {
            Tile endTile = board.getTile(x + i, y);
            if (endTile == null) {
                break;
            }
            if (endTile.getPiece() != null && endTile.getPiece().isWhite() == this.isWhite()) {
                break;
            } else if (endTile.getPiece() != null && endTile.getPiece().isWhite() != this.isWhite()) {
                legalMoves.add(new Move(startTile, endTile, player));
                break;
            } else {
                legalMoves.add(new Move(startTile, endTile, player));
            }
        }

        for (int i = 1; i <= 7; i++) {
            Tile endTile = board.getTile(x - i, y);
            if (endTile == null) {
                break;
            }
            if (endTile.getPiece() != null && endTile.getPiece().isWhite() == this.isWhite()) {
                break;
            } else if (endTile.getPiece() != null && endTile.getPiece().isWhite() != this.isWhite()) {
                legalMoves.add(new Move(startTile, endTile, player));
                break;
            } else {
                legalMoves.add(new Move(startTile, endTile, player));
            }
        }

        for (int i = 1; i <= 7; i++) {
            Tile endTile = board.getTile(x, y + i);
            if (endTile == null) {
                break;
            }
            if (endTile.getPiece() != null && endTile.getPiece().isWhite() == this.isWhite()) {
                break;
            } else if (endTile.getPiece() != null && endTile.getPiece().isWhite() != this.isWhite()) {
                legalMoves.add(new Move(startTile, endTile, player));
                break;
            } else {
                legalMoves.add(new Move(startTile, endTile, player));
            }
        }

        for (int i = 1; i <= 7; i++) {
            Tile endTile = board.getTile(x, y - i);
            if (endTile == null) {
                break;
            }
            if (endTile.getPiece() != null && endTile.getPiece().isWhite() == this.isWhite()) {
                break;
            } else if (endTile.getPiece() != null && endTile.getPiece().isWhite() != this.isWhite()) {
                legalMoves.add(new Move(startTile, endTile, player));
                break;
            } else {
                legalMoves.add(new Move(startTile, endTile, player));
            }
        }

        return legalMoves;
    }

    /**
     * This method returns the Queen's unicode.
     * @return the Queen's unicode.
     */
    @Override
    public String toString() {
        if (this.isWhite()) {
            return "Q";
        }
        return "q";
    }
}
