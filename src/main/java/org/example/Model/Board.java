package org.example.Model;

import org.example.Model.Pieces.*;

public class Board {
    private Tile[][] tiles;

    public Board() {
        this.resetBoard();
    }

    public void resetBoard() {
        //White side
        tiles[0][0] = new Tile(0, 0, new Rook(true));
        tiles[0][1] = new Tile(0, 1, new Knight(true));
        tiles[0][2] = new Tile(0, 2, new Bishop(true));
        tiles[0][3] = new Tile(0, 3, new Queen(true));
        tiles[0][4] = new Tile(0, 4, new King(true));
        tiles[0][5] = new Tile(0, 5, new Bishop(true));
        tiles[0][6] = new Tile(0, 6, new Knight(true));
        tiles[0][7] = new Tile(0, 7, new Rook(true));
        for(int i = 0; i < 8; i++) {
            tiles[1][i] = new Tile(1, i, new Pawn(true));
        }

        //Black side
        tiles[7][0] = new Tile(7, 0, new Rook(false));
        tiles[7][1] = new Tile(7, 1, new Knight(false));
        tiles[7][2] = new Tile(7, 2, new Bishop(false));
        tiles[7][3] = new Tile(7, 3, new Queen(false));
        tiles[7][4] = new Tile(7, 4, new King(false));
        tiles[7][5] = new Tile(7, 5, new Bishop(false));
        tiles[7][6] = new Tile(7, 6, new Knight(false));
        tiles[7][7] = new Tile(7, 7, new Rook(false));
        for(int i = 0; i < 8; i++) {
            tiles[6][i] = new Tile(6, i, new Pawn(false));
        }

        //Empty tiles
        for(int i = 2; i < 6; i++) {
            for(int j = 0; j < 8; j++) {
                tiles[i][j] = new Tile(i, j, null);
            }
        }
    }

    public Tile getTile(int x, int y) {
        if(x < 0 || x > 7 || y < 0 || y > 7) {
            return null;
        }
        return tiles[x][y];
    }
}
