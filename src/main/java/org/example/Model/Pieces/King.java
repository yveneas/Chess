package org.example.Model.Pieces;

import lombok.Getter;
import lombok.Setter;
import org.example.Model.Board;
import org.example.Model.Move;
import org.example.Model.Player;
import org.example.Model.Tile;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    @Getter @Setter
    private boolean castlingDone = false;
    public King(boolean color) {
        super(color);
        this.setWeight(900);
    }

    @Override
    public boolean canMove(Board board, Tile startTile, Tile endTile) {
        int x = Math.abs(startTile.getX() - endTile.getX());
        int y = Math.abs(startTile.getY() - endTile.getY());
        if(endTile.getPiece() != null && endTile.getPiece().isWhite() == this.isWhite()) {
            return false;
        }
        return x + y <= 2 && x + y > 0;
    }

    @Override
    public List<Move> getLegalMoves(Board board, Tile startTile, Player player) {
        List<Move> legalMoves = new ArrayList<>();
        int x = startTile.getX();
        int y = startTile.getY();
        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                if(x + i >= 0 && x + i < 8 && y + j >= 0 && y + j < 8) {
                    if(canMove(board, startTile, board.getTile(x + i, y + j))) {
                        legalMoves.add(new Move(startTile, board.getTile(x + i, y + j), player));
                    }
                }
            }
        }
        return legalMoves;
    }

    public boolean isCastlingMove() {
        return false;
    }

    @Override
    public String toString() {
        if(this.isWhite()) {
            return "K";
        }
        return "k";
    }


}
