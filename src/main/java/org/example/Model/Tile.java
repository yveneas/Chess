package org.example.Model;

import lombok.Getter;
import lombok.Setter;
import org.example.Model.Pieces.Piece;

/**
 * This class represents a tile on the board.
 */
public class Tile {
    @Getter @Setter
    private int x;
    @Getter @Setter
    private int y;
    @Getter @Setter
    private Piece piece;

    public Tile(int x, int y, Piece piece) {
        this.x = x;
        this.y = y;
        this.piece = piece;
    }

    public Tile(Tile tile) {
        this.x = tile.getX();
        this.y = tile.getY();
        this.piece = tile.getPiece();
    }

    /**
     * This method checks if the tile is empty.
     * @return true if the tile is empty, false otherwise.
     */
    public boolean isOccupied() {
        return piece != null;
    }

    /**
     * This method is used to print the tile.
     * @return a string representation of the tile.
     */
    @Override
    public String toString() {
        if (piece == null) {
            return "Tile{x=" + x + ", y=" + y + ", piece=null" +'}';
        }

        return "Tile{x=" + x + ", y=" + y + ", piece=" + piece + '}';
    }
}
