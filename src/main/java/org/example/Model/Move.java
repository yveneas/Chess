package org.example.Model;

import lombok.Getter;
import lombok.Setter;
import org.example.Model.Pieces.Piece;

/**
 * This class represents a move in the game.
 */
public class Move {
    @Getter @Setter
    private Player player;
    @Getter @Setter
    private Tile startTile;
    @Getter @Setter
    private Tile endTile;
    @Getter @Setter
    private Piece pieceMoved;
    @Getter @Setter
    private Piece pieceKilled;
    @Getter @Setter
    private boolean castlingMove = false;
    @Getter @Setter
    private boolean check = false;

    public Move() {
        player = null;
        startTile = null;
        endTile = null;
        pieceMoved = null;
        pieceKilled = null;
    }

    public Move(Tile startTile, Tile endTile, Player player) {
        this.startTile = startTile;
        this.endTile = endTile;
        this.pieceMoved = startTile.getPiece();
        this.pieceKilled = endTile.getPiece();
        this.player = player;
    }

    /**
     * This method returns the best move in uci notation.
     * @return the best move in uci notation.
     */
    @Override
    public String toString() {
        return "bestmove " + (char) (startTile.getX() + 'a') + (startTile.getY() + 1) +
                (char) (endTile.getX() + 'a') + (endTile.getY() + 1);
    }

    /**
     * This method is used to check if two moves are equal.
     * @param move to compare with.
     * @return true if the moves are equal, false otherwise.
     */
    public boolean equals(Move move) {
        return ((startTile == move.getStartTile() && endTile == move.getEndTile()) || (startTile == move.getEndTile() && endTile == move.getStartTile())) && pieceMoved == move.getPieceMoved() && pieceKilled == move.getPieceKilled();
    }
}
