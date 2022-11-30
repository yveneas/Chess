package org.example.Model.Pieces;

import lombok.Getter;
import org.example.Model.Board;
import org.example.Model.Move;
import org.example.Model.Player;
import org.example.Model.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Knight piece.
 */
public class Knight extends Piece {

    @Getter
    static int[][] knightBoard ={
            {-50,-40,-30,-30,-30,-30,-40,-50},
            {-40,-20,  0,  0,  0,  0,-20,-40},
            {-30,  0, 10, 15, 15, 10,  0,-30},
            {-30,  5, 15, 20, 20, 15,  5,-30},
            {-30,  0, 15, 20, 20, 15,  0,-30},
            {-30,  5, 10, 15, 15, 10,  5,-30},
            {-40,-20,  0,  5,  5,  0,-20,-40},
            {-50,-40,-30,-30,-30,-30,-40,-50}};

    public Knight(boolean color) {
        super(color);
        this.setWeight(30);
    }

    /**
     * This method checks if the Knight can move to the endTile.
     * @param board on which the Knight is placed.
     * @param startTile from which the Knight is moved.
     * @param endTile to which the Knight is moved.
     * @return true if the Knight can move to the endTile, false otherwise.
     */
    @Override
    public boolean canMove(Board board, Tile startTile, Tile endTile) {
        int x = Math.abs(startTile.getX() - endTile.getX());
        int y = Math.abs(startTile.getY() - endTile.getY());

        if (endTile.getPiece() != null && endTile.getPiece().isWhite() == this.isWhite()) {
            return false;
        }

        return x * y == 2;
    }

    /**
     * This method returns a list of all legal moves for the Knight.
     * @param board on which the Knight is placed.
     * @param startTile from which the Knight is moved.
     * @param player who is moving the Knight.
     * @return a list of all legal moves for the Knight.
     */
    @Override
    public List<Move> getLegalMoves(Board board, Tile startTile, Player player) {
        List<Move> legalMoves = new ArrayList<>();
        int x = startTile.getX();
        int y = startTile.getY();

        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                if (x + i >= 0 && x + i < 8 && y + j >= 0 && y + j < 8) {
                    if (canMove(board, startTile, board.getTile(x + i, y + j))) {
                        legalMoves.add(new Move(startTile, board.getTile(x + i, y + j), player));
                    }
                }
            }
        }

        return legalMoves;
    }

    /**
     * This method returns the value of the Knight on the board.
     * @param i the x coordinate of the Knight.
     * @param j the y coordinate of the Knight.
     * @return the value of the Knight on the board.
     */
    @Override
    public int getPiecePlacementScore(int i, int j) {
        return this.isWhite() ? knightBoard[i][j] : knightBoard[7 - i][7 - j];
    }

     /**
     * This method returns the Knight's unicode.
     * @return the Knight's unicode.
     */
    @Override
    public String toString() {
        if (this.isWhite()) {
            return "N";
        }
        return "n";
    }
}
