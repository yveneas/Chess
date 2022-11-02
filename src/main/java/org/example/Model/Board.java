package org.example.Model;

import lombok.Getter;
import lombok.Setter;
import org.example.Model.Pieces.*;

import java.util.ArrayList;
import java.util.List;

public class Board {
    @Getter
    private List<List<Tile>> tiles;

    public Board() {
        this.resetBoard();
    }

    /*private void customBoard() {
        tiles = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            tiles.add(new ArrayList<>());
            for (int j = 0; j < 8; j++) {
                tiles.get(i).add(new Tile(i, j, null));
            }
        }

        //White side
        tiles.get(0).set(0, new Tile(0, 0, null));
        tiles.get(0).set(1, new Tile(1, 0, new Rook(true)));
        tiles.get(0).set(2, new Tile(2, 0, new Bishop(true)));
        tiles.get(0).set(3, new Tile(3, 0, new Queen(true)));
        tiles.get(0).set(4, new Tile(4, 0, new King(true)));
        tiles.get(0).set(5, new Tile(5, 0, new Bishop(true)));
        tiles.get(0).set(6, new Tile(6, 0, new Knight(true)));
        tiles.get(0).set(7, new Tile(7, 0, new Rook(true)));
        tiles.get(1).set(0, new Tile(0, 1, null));
        for(int i = 1; i < 8; i++) {
            tiles.get(1).set(i, new Tile(i, 1, new Pawn(true)));
        }

        //Black side
        tiles.get(7).set(0, new Tile(0, 7, new Rook(false)));
        tiles.get(7).set(1, new Tile(1, 7, new Knight(false)));
        tiles.get(7).set(2, new Tile(2, 7, new Bishop(false)));
        tiles.get(7).set(3, new Tile(3, 7, new Queen(false)));
        tiles.get(7).set(4, new Tile(4, 7, new King(false)));
        tiles.get(7).set(5, new Tile(5, 7, new Bishop(false)));
        tiles.get(7).set(6, new Tile(6, 7, new Knight(false)));
        tiles.get(7).set(7, new Tile(7, 7, new Rook(false)));
        tiles.get(6).set(0, new Tile(0, 6, null));
        for(int i = 1; i < 8; i++) {
            tiles.get(6).set(i, new Tile(i, 6, new Pawn(false)));
        }

        //Empty tiles
        for(int i = 0; i < 8; i++) {
            for(int j = 2; j < 6; j++) {
                if (i==0 && j==3) {
                    tiles.get(j).set(i, new Tile(i, j, new Pawn(true)));
                }
                else if (i==0 && j==4) {
                    tiles.get(j).set(i, new Tile(i, j, new Pawn(false)));
                }
                else if (i==2 && j==2) {
                    tiles.get(j).set(i, new Tile(i, j, new Knight(true)));
                }
                else
                    tiles.get(j).set(i, new Tile(i, j, null));
            }
        }
    }*/
    public void resetBoard() {
        tiles = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            tiles.add(new ArrayList<>());
            for (int j = 0; j < 8; j++) {
                tiles.get(i).add(new Tile(i, j, null));
            }
        }
        //White side
        tiles.get(0).set(0, new Tile(0, 0, new Rook(true)));
        tiles.get(0).set(1, new Tile(1, 0, new Knight(true)));
        tiles.get(0).set(2, new Tile(2, 0, new Bishop(true)));
        tiles.get(0).set(3, new Tile(3, 0, new Queen(true)));
        tiles.get(0).set(4, new Tile(4, 0, new King(true)));
        tiles.get(0).set(5, new Tile(5, 0, new Bishop(true)));
        tiles.get(0).set(6, new Tile(6, 0, new Knight(true)));
        tiles.get(0).set(7, new Tile(7, 0, new Rook(true)));
        for(int i = 0; i < 8; i++) {
            tiles.get(1).set(i, new Tile(i, 1, new Pawn(true)));
        }

        //Black side
        tiles.get(7).set(0, new Tile(0, 7, new Rook(false)));
        tiles.get(7).set(1, new Tile(1, 7, new Knight(false)));
        tiles.get(7).set(2, new Tile(2, 7, new Bishop(false)));
        tiles.get(7).set(3, new Tile(3, 7, new Queen(false)));
        tiles.get(7).set(4, new Tile(4, 7, new King(false)));
        tiles.get(7).set(5, new Tile(5, 7, new Bishop(false)));
        tiles.get(7).set(6, new Tile(6, 7, new Knight(false)));
        tiles.get(7).set(7, new Tile(7, 7, new Rook(false)));
        for(int i = 0; i < 8; i++) {
            tiles.get(6).set(i, new Tile(i, 6, new Pawn(false)));
        }

        //Empty tiles
        for(int i = 0; i < 8; i++) {
            for(int j = 2; j < 6; j++) {
                tiles.get(j).set(i, new Tile(i, j, null));
            }
        }
    }

    public Tile getTile(int x, int y) {
        if(x < 0 || x > 7 || y < 0 || y > 7) {
            return null;
        }
        return tiles.get(y).get(x);
    }

    public int colorScore(boolean white) {
        int score = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Piece piece = tiles.get(i).get(j).getPiece();
                if(piece != null && piece.isWhite() == white) {
                    score += piece.getWeight();
                }
            }
        }
        return score;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Piece piece = getTile(j, 7 - i).getPiece();
                if(piece == null) {
                    builder.append(" E ");
                } else {
                    builder.append(" ").append(piece).append(" ");
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public List<Move> getAllLegalMoves(Player player) {
        List<Move> legalMoves = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Piece piece = getTile(i, j).getPiece();
                //System.out.println(i + " " + j);
                if(piece != null && player.isWhite() == piece.isWhite()) {
                    legalMoves.addAll(piece.getLegalMoves(this, getTile(i, j), player));
                }
            }
        }
        return legalMoves;
    }
}
