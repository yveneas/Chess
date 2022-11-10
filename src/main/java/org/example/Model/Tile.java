package org.example.Model;

import lombok.Getter;
import lombok.Setter;
import org.example.Model.Pieces.Piece;

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

    public boolean isOccupied() {
        return piece != null;
    }

    @Override
    public String toString() {
        if(piece == null) {
            return "Tile{" +
                    "x=" + x +
                    ", y=" + y +
                    ", piece=null" +
                    '}';
        }
        return "Tile{" +
                "x=" + x +
                ", y=" + y +
                ", piece=" + piece +
                '}';
    }
}
