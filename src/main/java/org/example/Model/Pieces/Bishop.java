package org.example.Model.Pieces;

import lombok.Getter;
import org.example.Model.Board;
import org.example.Model.Move;
import org.example.Model.Player;
import org.example.Model.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Bishop piece.
 */
public class Bishop extends Piece {

    @Getter
    static int[][] bishopBoard ={
            {-20,-10,-10,-10,-10,-10,-10,-20},
            {-10,  0,  0,  0,  0,  0,  0,-10},
            {-10,  0,  5, 10, 10,  5,  0,-10},
            {-10,  5,  5, 10, 10,  5,  5,-10},
            {-10,  0, 10, 10, 10, 10,  0,-10},
            {-10, 10, 10, 10, 10, 10, 10,-10},
            {-10,  5,  0,  0,  0,  0,  5,-10},
            {-20,-10,-10,-10,-10,-10,-10,-20}};

    public Bishop(boolean color) {
        super(color);
        this.setWeight(30);
    }

    /**
     * This method checks if the Bishop can move to the endTile.
     * @param board on which the Bishop is placed.
     * @param startTile from which the Bishop is moved.
     * @param endTile to which the Bishop is moved.
     * @return true if the Bishop can move to the endTile, false otherwise.
     */
    @Override
    public boolean canMove(Board board, Tile startTile, Tile endTile) {
        int x = Math.abs(startTile.getX() - endTile.getX());
        int y = Math.abs(startTile.getY() - endTile.getY());
        if (endTile.getPiece() != null && endTile.getPiece().isWhite() == this.isWhite()) {
            return false;
        }
        return x == y;
    }

    /**
     * This method returns a list of all legal moves for the Bishop.
     * @param board on which the Bishop is placed.
     * @param startTile from which the Bishop is moved.
     * @param player who is moving the Bishop.
     * @return a list of all legal moves for the Bishop.
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
        return legalMoves;
    }

    /**
     * This method returns the value of the Bishop on the board.
     * @param i the x coordinate of the Bishop.
     * @param j the y coordinate of the Bishop.
     * @return the value of the Bishop on the board.
     */
    @Override
    public int getPiecePlacementScore(int i, int j) {
        return this.isWhite() ? bishopBoard[i][j] : bishopBoard[7 - i][7 - j];
    }

    /**
     * This method returns the Bishop's unicode.
     * @return the Bishop's unicode.
     */
    @Override
    public String toString() {
        if (this.isWhite()) {
            return "B";
        }
        return "b";
    }
}