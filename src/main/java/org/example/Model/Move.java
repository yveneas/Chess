package org.example.Model;

import lombok.Getter;
import lombok.Setter;
import org.example.Model.Pieces.Piece;

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

    public Move(Tile startTile, Tile endTile, Player player) {
        this.startTile = startTile;
        this.endTile = endTile;
        this.pieceMoved = startTile.getPiece();
        this.pieceKilled = endTile.getPiece();
        this.player = player;
    }
}
