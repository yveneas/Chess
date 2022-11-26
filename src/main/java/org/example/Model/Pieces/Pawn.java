package org.example.Model.Pieces;

import org.example.Model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Pawn piece.
 */
public class Pawn extends Piece {

    public Pawn(boolean color) {
        super(color);
        this.setWeight(10);
    }

    /**
     * This method checks if the Pawn can move to the endTile.
     * @param board on which the Pawn is placed.
     * @param startTile from which the Pawn is moved.
     * @param endTile to which the Pawn is moved.
     * @return true if the Pawn can move to the endTile, false otherwise.
     */
    @Override
    public boolean canMove(Board board, Tile startTile, Tile endTile) {
        if (endTile.getPiece() != null && endTile.getPiece().isWhite() == this.isWhite()) {
            return false;
        }

        if (this.isWhite()) {
            if (startTile.getY() == 1 && board.getTile(startTile.getX(), startTile.getY() + 1).getPiece() == null
                    && board.getTile(startTile.getX(), startTile.getY() + 2).getPiece() == null
                    && startTile.getX() == endTile.getX()
                    && startTile.getY() + 2 == endTile.getY()) {
                return true;
            }
            if (endTile.getPiece() == null && endTile.getX() == startTile.getX() && endTile.getY() == startTile.getY() + 1) {
                return endTile.getY() == startTile.getY() + 1;
            }
            else
                return endTile.getPiece() != null && this.isWhite() != endTile.getPiece().isWhite()
                        && (endTile.getX() == startTile.getX() + 1 || endTile.getX() == startTile.getX() - 1)
                        && endTile.getY() == startTile.getY() + 1;
        } else {
            if (startTile.getY() == 6 && board.getTile(startTile.getX(), startTile.getY() - 1).getPiece() == null
                    && board.getTile(startTile.getX(), startTile.getY() - 2).getPiece() == null
                    && startTile.getX() == endTile.getX()
                    && startTile.getY() - 2 == endTile.getY()) {
                return true;
            }
            if (endTile.getPiece() == null && endTile.getX() == startTile.getX() && endTile.getY() == startTile.getY() - 1) {
                return endTile.getY() == startTile.getY() - 1;
            }
            else
                return endTile.getPiece() != null && this.isWhite() != endTile.getPiece().isWhite()
                        && (endTile.getX() == startTile.getX() + 1 || endTile.getX() == startTile.getX() - 1)
                        && endTile.getY() == startTile.getY() - 1;
        }
    }

    /**
     * This method returns a list of all legal moves for the Pawn.
     * @param board on which the Pawn is placed.
     * @param startTile from which the Pawn is moved.
     * @param player who is moving the Pawn.
     * @return a list of all legal moves for the Pawn.
     */
    @Override
    public List<Move> getLegalMoves(Board board, Tile startTile, Player player) {
        List<Move> legalMoves = new ArrayList<>();
        int x = startTile.getX();
        int y = startTile.getY();

        for (int i = -1; i <= 1; i++) {
            for (int j = -2; j <= 2; j++) {
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

        // En passant
        if (!Game.getMoveHistory().isEmpty()) {
            Piece pieceMoved = Game.getMoveHistory().get(Game.getMoveHistory().size() - 1).getPieceMoved();

            // Black pawn takes white pawn
            if (y == 3) {
                if (x > 0 && board.getTile(x - 1, y).getPiece() == pieceMoved &&
                        board.getTile(x - 1, y).getPiece().isWhite() != this.isWhite() &&
                        board.getTile(x - 1, y).getPiece() instanceof Pawn) {
                    legalMoves.add(new Move(startTile, board.getTile(x - 1, y + 1), player));
                }
                if (x < 7 && board.getTile(x + 1, y).getPiece() == pieceMoved &&
                        board.getTile(x + 1, y).getPiece().isWhite() != this.isWhite() &&
                        board.getTile(x + 1, y).getPiece() instanceof Pawn) {
                    legalMoves.add(new Move(startTile, board.getTile(x + 1, y + 1), player));
                }

            // White pawn takes black pawn
            } else if (y == 4) {
                if (x > 0 && board.getTile(x - 1, y).getPiece() == pieceMoved &&
                        board.getTile(x - 1, y).getPiece().isWhite() != this.isWhite() &&
                        board.getTile(x - 1, y).getPiece() instanceof Pawn) {
                    legalMoves.add(new Move(startTile, board.getTile(x - 1, y - 1), player));
                }
                if (x < 7 && board.getTile(x + 1, y).getPiece() == pieceMoved &&
                        board.getTile(x + 1, y).getPiece().isWhite() != this.isWhite() &&
                        board.getTile(x + 1, y).getPiece() instanceof Pawn) {
                    legalMoves.add(new Move(startTile, board.getTile(x + 1, y - 1), player));
                }
            }
        }

        return legalMoves;
    }

    /**
     * This method returns the Pawn's unicode.
     * @return the Pawn's unicode.
     */
    @Override
    public String toString() {
        if (this.isWhite()) {
            return "P";
        }
        return "p";
    }
}
