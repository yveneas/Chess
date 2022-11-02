package org.example;

import org.example.Model.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Board board = new Board();
        Player player1 = new Player(true);
        Player player2 = new Player(false);
        Game game = new Game(board, player1, player2);
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
                game.newGame(player1, player2);
            }
            else if (input.startsWith("position")) {
                Move move = game.minimax(board, 4, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
                System.out.println(board.getTile(move.getStartTile().getX(), move.getStartTile().getY()));
                boolean test =  game.playerMove(game.getCurrentPlayer(), move.getStartTile().getX(), move.getStartTile().getY(), move.getEndTile().getX(), move.getEndTile().getY());
                System.out.println(board);
            }
            else if ("go".equals(input)) {
                Move move = game.minimax(board, 1, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
            }
            else if ("print".equals(input)) {
                //TODO
                System.out.println(board);
            }
            else if(input.equals("quit")) {
                System.exit(0);
            }
        }
        //System.out.println(board);
        //System.out.println(board.getTile(3, 0));
        //System.out.println(board.getTile(1, 1).getPiece().canMove(board, board.getTile(1, 1), board.getTile(1, 2)));
        //System.out.println(board.getTile(1, 7).getPiece().isWhite());
        //System.out.println(board.getTile(3, 0).getPiece().getLegalMoves(board, board.getTile(2, 3), player1).size());
    }
}