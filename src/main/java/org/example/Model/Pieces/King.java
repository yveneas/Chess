package org.example.Model.Pieces;

import lombok.Getter;
import lombok.Setter;
import org.example.Model.Board;
import org.example.Model.Move;
import org.example.Model.Player;
import org.example.Model.Tile;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a King piece.
 */
public class King extends Piece {
    @Getter @Setter
    private boolean castlingDone = false;

    @Getter
    static int[][] kingMidBoard ={
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-20,-30,-30,-40,-40,-30,-30,-20},
            {-10,-20,-20,-20,-20,-20,-20,-10},
            { 20, 20,  0,  0,  0,  0, 20, 20},
            { 20, 30, 10,  0,  0, 10, 30, 20}};
    @Getter
    static int[][] kingEndBoard ={
            {-50,-40,-30,-20,-20,-30,-40,-50},
            {-30,-20,-10,  0,  0,-10,-20,-30},
            {-30,-10, 20, 30, 30, 20,-10,-30},
            {-30,-10, 30, 40, 40, 30,-10,-30},
            {-30,-10, 30, 40, 40, 30,-10,-30},
            {-30,-10, 20, 30, 30, 20,-10,-30},
            {-30,-30,  0,  0,  0,  0,-30,-30},
            {-50,-30,-30,-30,-30,-30,-30,-50}};

    public King(boolean color) {
        super(color);
        this.setWeight(900);
    }

    /**
     * This method checks if the King can move to the endTile.
     * @param board on which the King is placed.
     * @param startTile from which the King is moved.
     * @param endTile to which the King is moved.
     * @return true if the King can move to the endTile, false otherwise.
     */
    @Override
    public boolean canMove(Board board, Tile startTile, Tile endTile) {
        int x = Math.abs(startTile.getX() - endTile.getX());
        int y = Math.abs(startTile.getY() - endTile.getY());

        if (endTile.getPiece() != null && endTile.getPiece().isWhite() == this.isWhite()) {
            return false;
        }

        return x + y <= 2 && x + y > 0;
    }

    /**
     * This method returns a list of all legal moves for the King.
     * @param board on which the King is placed.
     * @param startTile from which the King is moved.
     * @param player who is moving the King.
     * @return a list of all legal moves for the King.
     */
    @Override
    public List<Move> getLegalMoves(Board board, Tile startTile, Player player) {
        List<Move> legalMoves = new ArrayList<>();
        int x = startTile.getX();
        int y = startTile.getY();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
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
     * This method returns the value of the King on the board.
     * @param i the x coordinate of the King.
     * @param j the y coordinate of the King.
     * @return the value of the King on the board.
     */
    @Override
    public int getPiecePlacementScore(int i, int j) {
        return this.isWhite() ? kingMidBoard[i][j] : kingMidBoard[7 - i][7 - j];
    }

    /**
     * This method returns the King's unicode.
     * @return the King's unicode.
     */
    @Override
    public String toString() {
        if(this.isWhite()) {
            return "K";
        }
        return "k";
    }
}
