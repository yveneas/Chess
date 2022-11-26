package org.example.Model;

import lombok.Getter;
import org.example.Model.Pieces.*;

import java.util.ArrayList;
import java.util.List;

public class Board {
    @Getter
    private List<List<Tile>> tiles;

    public Board() {
        this.resetBoard();
    }

    public Board(Board board) {
        this.tiles = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            this.tiles.add(new ArrayList<>());
            for (int j = 0; j < 8; j++) {
                this.tiles.get(i).add(new Tile(board.getTile(i, j)));
            }
        }
    }

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

    public int evaluateBoard(Player currentPlayer, Player opponent) {
        int score = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Piece piece = tiles.get(i).get(j).getPiece();
                if(currentPlayer.isWhite()) {
                    if(piece != null) {
                        if(piece.isWhite() == currentPlayer.isWhite()) {
                            score += piece.getWeight();
                            score += currentPlayer.getPiecePlacementScore(piece, i, j);
                        } else {
                            score -= piece.getWeight();
                            score -= currentPlayer.getPiecePlacementScore(piece, i, j);
                        }
                    }
                } else {
                    if(piece != null) {
                        if(piece.isWhite() == currentPlayer.isWhite()) {
                            score -= piece.getWeight();
                            score -= currentPlayer.getPiecePlacementScore(piece, i, j);
                        } else {
                            score += piece.getWeight();
                            score += currentPlayer.getPiecePlacementScore(piece, i, j);
                        }
                    }
                }
            }
        }
        //Si le joueur blanc (joueur courant) est en échec OU si le joueur noir met en échec le joueur blanc on retire 850 points
        if(currentPlayer.isWhite() && isCheck(currentPlayer) || !currentPlayer.isWhite() && isCheck(opponent)) {
            score -= 850;
        //Si le joueur noir (joueur courant) est en échec OU si le joueur blanc met en échec le joueur noir on ajoute 850 points
        } else if(!currentPlayer.isWhite() && isCheck(currentPlayer) || currentPlayer.isWhite() && isCheck(opponent)) {
            score += 850;
        }
        if(Game.moveHistory.size() > 0 && Game.moveHistory.get(Game.moveHistory.size() - 1).getPieceKilled() != null) {
            if(currentPlayer.isWhite() == Game.moveHistory.get(Game.moveHistory.size() - 1).getPlayer().isWhite()) {
                return score + Game.moveHistory.get(Game.moveHistory.size() - 1).getPieceKilled().getWeight();
            } else {
                return score - Game.moveHistory.get(Game.moveHistory.size() - 1).getPieceKilled().getWeight();
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
                    builder.append(" _ ");
                } else {
                    builder.append(" ").append(piece).append(" ");
                }
            }
            String str = "  " + (7 - i) + "\n";
            builder.append(str);
        }
        builder.append(" A  B  C  D  E  F  G  H \n");
        return builder.toString();
    }

    public boolean isCheck(Player player) {
        Tile kingTile = null;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Piece piece = getTile(i, j).getPiece();
                if(piece != null && piece.isWhite() == player.isWhite() && piece instanceof King) {
                    kingTile = getTile(i, j);
                    break;
                }
            }
        }
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Piece piece = getTile(i, j).getPiece();
                if(piece != null && piece.isWhite() != player.isWhite()) {
                    List<Move> legalMoves = piece.getLegalMoves(this, getTile(i, j), player);
                    for(Move move : legalMoves) {
                        if(move.getEndTile().equals(kingTile)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public List<Move> getAllLegalMoves(Player player) {
        List<Move> legalMoves = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Piece piece = getTile(i, j).getPiece();
                //System.out.println(i + " " + j);
                if(piece != null && player.isWhite() == piece.isWhite()) {
                    List<Move> pieceLegalMoves = piece.getLegalMoves(this, getTile(i, j), player);
                    for(Move move : pieceLegalMoves) {
                        makeMove(move);
                        if(!isCheck(player)) {
                            legalMoves.add(move);
                        }
                        undoMove(move);
                        //legalMoves.add(move);
                    }
                    //legalMoves.addAll(piece.getLegalMoves(this, getTile(i, j), player));
                }
            }
        }
        return legalMoves;
    }

    private List<Move> getAllLegalMovesInCheck(Player player) {
        List<Move> legalMoves = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Piece piece = getTile(i, j).getPiece();
                if(piece != null && player.isWhite() == piece.isWhite()) {
                    legalMoves.addAll(piece.getLegalMoves(this, getTile(i, j), player));
                }
            }
        }
        List<Move> movesToRemove = new ArrayList<>();
        for(Move move : legalMoves) {
            Board board = new Board(this);
            board.makeMove(move);
            if(board.isCheck(player)) {
                movesToRemove.add(move);
            }
        }
        legalMoves.removeAll(movesToRemove);
        return legalMoves;
    }

    private void makeMove(Move move) {
        getTile(move.getEndTile().getX(), move.getEndTile().getY()).setPiece(move.getPieceMoved());
        getTile(move.getStartTile().getX(), move.getStartTile().getY()).setPiece(null);
    }

    private void undoMove(Move move) {
        getTile(move.getStartTile().getX(), move.getStartTile().getY()).setPiece(move.getPieceMoved());
        getTile(move.getEndTile().getX(), move.getEndTile().getY()).setPiece(move.getPieceKilled());
    }

    public Tile getKingTile(Player player) {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Piece piece = getTile(i, j).getPiece();
                if(piece != null && piece.isWhite() == player.isWhite() && piece instanceof King) {
                    return getTile(i, j);
                }
            }
        }
        return null;
    }

    public Tile getTile(String string) {
        return getTile(string.charAt(0) - 'a', (string.charAt(1) - '1'));
    }
}
