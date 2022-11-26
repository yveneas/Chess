package org.example.Model;

import lombok.Getter;
import lombok.Setter;
import org.example.Model.Pieces.King;
import org.example.Model.Pieces.Piece;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class represents the game itself.
 */
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

    /**
     * This method initializes the game.
     * @param player1 The first player.
     * @param player2 The second player.
     */
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

    /**
     * This method is used to make a move.
     * @param move to be made.
     */
    public void makeMove(Move move) {
        if (move.getPieceKilled() instanceof King) {
            if (move.getPlayer().isWhite()) {
                gameStatus = GameStatus.WHITE_WIN;
            } else {
                gameStatus = GameStatus.BLACK_WIN;
            }
        }
        moveHistory.add(move);
        board.getTile(move.getEndTile().getX(), move.getEndTile().getY()).setPiece(move.getPieceMoved());
        board.getTile(move.getStartTile().getX(), move.getStartTile().getY()).setPiece(null);
        if (currentPlayer.isWhite()) {
            currentPlayer = players.get(1);
        } else {
            currentPlayer = players.get(0);
        }
    }

    /**
     * This method is used to undo a move.
     */
    public void undoMove() {
        if (moveHistory.size() > 0) {
            Move lastMove = moveHistory.get(moveHistory.size() - 1);
            board.getTile(lastMove.getStartTile().getX(), lastMove.getStartTile().getY()).setPiece(lastMove.getPieceMoved());
            board.getTile(lastMove.getEndTile().getX(), lastMove.getEndTile().getY()).setPiece(lastMove.getPieceKilled());
            moveHistory.remove(moveHistory.size() - 1);
            if (currentPlayer.isWhite()) {
                currentPlayer = players.get(1);
            } else {
                currentPlayer = players.get(0);
            }
        }
        gameStatus = GameStatus.ACTIVE;
    }

    /**
     * This method is used to get the best move for the current player.
     * @param depth of the search tree
     * @param alpha the alpha value
     * @param beta the beta value
     * @param maximizingPlayer the player who is maximizing
     * @return the best move
     */
    public Move bestMove(int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0) {
            return null;
        }
        List<Move> equalMoves = new ArrayList<>();
        List<Move> possibleMoves = board.getAllLegalMoves(currentPlayer);
        Move bestMove = null;
        int bestValue;
        if (maximizingPlayer) {
            bestValue = Integer.MIN_VALUE;
            for (Move move : possibleMoves) {
                if (board.getTile(move.getEndTile().getX(), move.getEndTile().getY()).getPiece() instanceof King) {
                    return move;
                }
                makeMove(move);
                int value = minimax(depth - 1, alpha, beta, false) + board.evaluateBoard(currentPlayer, getOpponent());
                undoMove();
                if (value > bestValue) {
                    bestValue = value;
                    bestMove = move;
                    equalMoves.clear();
                    equalMoves.add(move);
                } else if(value == bestValue) {
                    equalMoves.add(move);
                }
                alpha = Math.max(alpha, bestValue);
                if (beta <= alpha) {
                    break;
                }
            }
            if (equalMoves.size() > 1) {
                Random random = new Random();
                bestMove = equalMoves.get(random.nextInt(equalMoves.size()));
            }
        } else {
            bestValue = Integer.MAX_VALUE;
            for (Move move : possibleMoves) {
                if (board.getTile(move.getEndTile().getX(), move.getEndTile().getY()).getPiece() instanceof King) {
                    return move;
                }
                makeMove(move);
                int value = minimax(depth - 1, alpha, beta, true) + board.evaluateBoard(currentPlayer, getOpponent());
                undoMove();
                if (value < bestValue) {
                    bestValue = value;
                    bestMove = move;
                    equalMoves.clear();
                    equalMoves.add(move);
                } else if (value == bestValue) {
                    equalMoves.add(move);
                }
                beta = Math.min(beta, bestValue);
                if (beta <= alpha) {
                    break;
                }
            }
            if (equalMoves.size() > 1) {
                Random random = new Random();
                bestMove = equalMoves.get(random.nextInt(equalMoves.size()));
            }
        }

        return bestMove;
    }

    /**
     * This method implements the minimax algorithm.
     * @param depth of the search tree
     * @param alpha the alpha value
     * @param beta the beta value
     * @param maximizingPlayer the player who is maximizing
     * @return the best value
     */
    private int minimax(int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || gameStatus != GameStatus.ACTIVE) {
            return board.evaluateBoard(currentPlayer, getOpponent());
        }

        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            List<Move> allMoves = board.getAllLegalMoves(currentPlayer);
            for (Move move : allMoves) {
                Piece destPiece = board.getTile(move.getEndTile().getX(), move.getEndTile().getY()).getPiece();
                if (destPiece != null && destPiece.isWhite() == currentPlayer.isWhite() &&  destPiece instanceof King) {
                    return Integer.MAX_VALUE;
                }
                makeMove(move);
                int eval = minimax(depth - 1, alpha, beta, false);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                undoMove();
                if (beta <= alpha) {
                    break;
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            List<Move> allMoves = board.getAllLegalMoves(currentPlayer);
            for (Move move : allMoves) {
                Piece destPiece = board.getTile(move.getEndTile().getX(), move.getEndTile().getY()).getPiece();
                if (destPiece != null && destPiece.isWhite() == currentPlayer.isWhite() &&  destPiece instanceof King) {
                    return Integer.MIN_VALUE;
                }
                makeMove(move);
                int eval = minimax(depth - 1, alpha, beta, true);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                undoMove();
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }
    }

    /**
     * This method is used to get the opponent of the current player.
     * @return the opponent player
     */
    public Player getOpponent() {
        return currentPlayer == players.get(0) ? players.get(1) : players.get(0);
    }
}

