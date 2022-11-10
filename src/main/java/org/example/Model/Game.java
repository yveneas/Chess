package org.example.Model;

import lombok.Getter;
import lombok.Setter;
import org.example.Model.Pieces.King;
import org.example.Model.Pieces.Piece;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public Move bestMove(int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0) {
            return null;
        }
        List<Move> equalMoves = new ArrayList<>();
        List<Move> possibleMoves = board.getAllLegalMoves(currentPlayer);
        Move bestMove = null;
        if (maximizingPlayer) {
            int bestValue = Integer.MIN_VALUE;
            for (Move move : possibleMoves) {
                if(board.getTile(move.getEndTile().getX(), move.getEndTile().getY()).getPiece() instanceof King) {
                    return move;
                }
                makeMove(move);
                int value = minimax(depth - 1, alpha, beta, false);
                //System.out.println("move: " + move + " value: " + value);
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
            if(equalMoves.size() > 1) {
                Random random = new Random();
                bestMove = equalMoves.get(random.nextInt(equalMoves.size()));
            }
            return bestMove;
        } else {
            int bestValue = Integer.MAX_VALUE;
            for (Move move : possibleMoves) {
                if(board.getTile(move.getEndTile().getX(), move.getEndTile().getY()).getPiece() instanceof King) {
                    return move;
                }
                makeMove(move);
                int value = minimax(depth - 1, alpha, beta, true);

                undoMove();
                if (value < bestValue) {
                    bestValue = value;
                    bestMove = move;
                    equalMoves.clear();
                    equalMoves.add(move);
                } else if(value == bestValue) {
                    equalMoves.add(move);
                }
                beta = Math.min(beta, bestValue);
                if (beta <= alpha) {
                    break;
                }
            }
            if(equalMoves.size() > 1) {
                Random random = new Random();
                bestMove = equalMoves.get(random.nextInt(equalMoves.size()));
            }
            return bestMove;
        }
    }

    private int minimax(int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || gameStatus != GameStatus.ACTIVE) {
            //System.out.println("Evaluation: " + board.evaluateBoard(currentPlayer, getOpponent()) + " moves: " + moveHistory);
            return board.evaluateBoard(currentPlayer, getOpponent());
        }
        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            List<Move> allMoves = board.getAllLegalMoves(currentPlayer);
            for (Move move : allMoves) {
                if(board.getTile(move.getEndTile().getX(), move.getEndTile().getY()).getPiece() instanceof King) {
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
                if(board.getTile(move.getEndTile().getX(), move.getEndTile().getY()).getPiece() instanceof King) {
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

    public Player getOpponent() {
        return currentPlayer == players.get(0) ? players.get(1) : players.get(0);
    }

    public boolean isCheckMate() {
        return moveHistory.get(moveHistory.size() - 1).getPieceKilled() instanceof King;
    }
}

