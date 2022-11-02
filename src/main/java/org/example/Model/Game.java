package org.example.Model;

import lombok.Getter;
import lombok.Setter;
import org.example.Model.Pieces.King;
import org.example.Model.Pieces.Piece;
import java.util.ArrayList;
import java.util.List;

public class Game {
    @Getter @Setter
    private List<Player> players;
    @Getter @Setter
    private Board board;
    @Getter @Setter
    private Player currentPlayer;
    @Getter @Setter
    public static List<Move> moveHistory;
    @Getter @Setter
    private GameStatus gameStatus;

    public Game(Board board, Player player1, Player player2) {
        this.players = new ArrayList<>();
        this.board = board;
        moveHistory = new ArrayList<>();
        this.initialize(player1, player2);
    }

    private void initialize(Player player1, Player player2) {
        this.players.add(player1);
        this.players.add(player2);
        this.board.resetBoard();
        if (player1.isWhite()) {
            this.currentPlayer = player1;
        } else {
            this.currentPlayer = player2;
        }
        moveHistory.clear();
        gameStatus = GameStatus.ACTIVE;
    }

    public boolean isEnd() {
        return gameStatus != GameStatus.ACTIVE;
    }

    public boolean playerMove(Player player, int startX, int startY, int endX, int endY) {
        Tile startTile = board.getTile(startX, startY);
        Tile endTile = board.getTile(endX, endY);
        Move move = new Move(startTile, endTile, player);
        return this.makeMove(move, player);
    }

    private boolean makeMove(Move move, Player player) {
        Piece sourcePiece = move.getStartTile().getPiece();
        // Check if the player is moving the right piece
        if (sourcePiece == null || player != currentPlayer || player.isWhite() != sourcePiece.isWhite()) {
            return false;
        }

        //Kill
        Piece destPiece = move.getEndTile().getPiece();
        if (destPiece != null) {
            destPiece.setKilled(true);
            move.setPieceKilled(destPiece);
        }

        //Castling
        if (sourcePiece instanceof King && ((King) sourcePiece).isCastlingMove()) {
            move.setCastlingMove(true);
        }

        moveHistory.add(move);

        move.getEndTile().setPiece(move.getStartTile().getPiece());
        move.getStartTile().setPiece(null);

        if (destPiece instanceof King) {
            if (destPiece.isWhite()) {
                gameStatus = GameStatus.WHITE_WIN;
            } else {
                gameStatus = GameStatus.BLACK_WIN;
            }
        }
        board.getTile(move.getEndTile().getX(), move.getEndTile().getY()).setPiece(move.getEndTile().getPiece());
        board.getTile(move.getStartTile().getX(), move.getStartTile().getY()).setPiece(null);
        //Switch player
        currentPlayer = currentPlayer == players.get(0) ? players.get(1) : players.get(0);

        return true;
    }

    public void undoMove() {
        if (moveHistory.size() == 0) {
            return;
        }
        Move lastMove = moveHistory.remove(moveHistory.size() - 1);
        Tile startTile = lastMove.getStartTile();
        Tile endTile = lastMove.getEndTile();
        startTile.setPiece(endTile.getPiece());
        endTile.setPiece(lastMove.getPieceKilled());
        if (lastMove.getPieceKilled() != null) {
            lastMove.getPieceKilled().setKilled(false);
        }
        if (lastMove.isCastlingMove()) {
            ((King) startTile.getPiece()).setCastlingDone(false);
        }
        currentPlayer = currentPlayer == players.get(0) ? players.get(1) : players.get(0);
    }

    public Move minimax(Board board, int depth, int alpha, int beta, boolean isMaximizingPlayer) {
        if (depth == 0) {
            return moveHistory.get(moveHistory.size() - 1);
        }

        if (isMaximizingPlayer) {
            List<Move> possibleMoves = board.getAllLegalMoves(players.get(0));
            Move bestMove = null;
            int maxEval = Integer.MIN_VALUE;
            for (Move move : possibleMoves) {
                makeMove(move, players.get(0));
                int currentEval = minimax(board, depth - 1, alpha, beta, false).getEvaluation();
                undoMove();
                if (currentEval > maxEval) {
                    maxEval = currentEval;
                    bestMove = move;
                }
                alpha = Math.max(alpha, currentEval);
                if (beta <= alpha) {
                    break;
                }
            }
            System.out.println(bestMove);
            return bestMove;
        } else {
            List<Move> possibleMoves = board.getAllLegalMoves(players.get(1));
            Move bestMove = null;
            int minEval = Integer.MAX_VALUE;
            for (Move move : possibleMoves) {
                makeMove(move, players.get(1));
                int currentEval = minimax(board, depth - 1, alpha, beta, true).getEvaluation();
                undoMove();
                if (currentEval < minEval) {
                    minEval = currentEval;
                    bestMove = move;
                }
                beta = Math.min(beta, currentEval);
                if (beta <= alpha) {
                    break;
                }
            }
            return bestMove;
        }
    }

}

