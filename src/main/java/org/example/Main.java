package org.example;

import org.example.Model.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Board board = new Board();
        Player whitePlayer = new Player(true);
        Player blackPlayer = new Player(false);
        Game game = new Game(board, whitePlayer, blackPlayer);

        while (true) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            if(input.equals("uci")) {
                System.out.println("id name " + "ChessEngine");
                System.out.println("id author " + "Guillaume-Julien-Kyllian");
                System.out.println("uciok");
            }
            else if(input.equals("isready")) {
                System.out.println("readyok");
            }
            else if (input.startsWith("setoption")) {
                //TODO
            }
            else if ("ucinewgame".equals(input)) {
                //TODO
            }
            else if (input.startsWith("position")) {
                while(!board.isCheck(whitePlayer) && !board.isCheck(blackPlayer) && GameStatus.ACTIVE.equals(game.getGameStatus())) {
                    System.out.println(game.getCurrentPlayer().isWhite() ? "White" : "Black");
                    Move move = game.bestMove(3, Integer.MIN_VALUE, Integer.MAX_VALUE, game.getCurrentPlayer().isWhite());
                    game.makeMove(move);
                    System.out.println("Evaluation: " + board.evaluateBoard(game.getCurrentPlayer(), game.getOpponent()));
                    System.out.println(board);
                    if(game.isCheckMate()) {
                        if(game.getCurrentPlayer().isWhite()) {
                            System.out.println("Black wins");
                        }
                        else {
                            System.out.println("White wins");
                        }
                        break;
                    }
                }
                System.out.println("Situation d'échec");
            }
            else if(input.equals("go")) {
                System.out.println(game.getCurrentPlayer().isWhite() ? "White" : "Black");
                Move move = game.bestMove(3, Integer.MIN_VALUE, Integer.MAX_VALUE, game.getCurrentPlayer().isWhite());
                game.makeMove(move);
                System.out.println("Evaluation: " + board.evaluateBoard(game.getCurrentPlayer(), game.getOpponent()));
                System.out.println(board);
            }
            else if ("print".equals(input)) {
                //TODO
                System.out.println(board);
            }
            else if(input.equals("legalmoves")) {
                for(Move move : game.getBoard().getAllLegalMoves(game.getCurrentPlayer())) {
                    //System.out.println(move + " " + move.getEvaluation(board));
                }
            }
            else if(input.equals("undo")) {
                game.undoMove();
            }
            else if(input.equals("quit")) {
                System.exit(0);
            }
            else if(input.equals("eval")) {
                System.out.println(board.evaluateBoard(game.getCurrentPlayer(), game.getOpponent()));;
            }
            else {
                System.out.println("Commande inconnue");
            }
        }
        //System.out.println(board);
        //System.out.println(board.getTile(3, 0));
        //System.out.println(board.getTile(1, 1).getPiece().canMove(board, board.getTile(1, 1), board.getTile(1, 2)));
        //System.out.println(board.getTile(1, 7).getPiece().isWhite());
        //System.out.println(board.getTile(3, 0).getPiece().getLegalMoves(board, board.getTile(2, 3), whitePlayer).size());
    }
}