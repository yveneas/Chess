package org.example;

import org.example.Model.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Board board = new Board();
        Player player = new Player(true);
        Player opponentPlayer = new Player(false);
        Game game = new Game(board, player, opponentPlayer);
        while (game.getGameStatus() == GameStatus.ACTIVE) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            if(input.equals("uci")) {
                System.out.println("id name " + "ChessEngine");
                System.out.println("id author " + "Guillaume-Julien-Kyllian");
                System.out.println("uciok");
            } else if(input.equals("isready")) {
                System.out.println("readyok");
            } else if(input.startsWith("position")) {
                String[] position = input.split(" ");
                if(position[1].equals("startpos")) {
                    if(position.length == 2) {
                        player.setWhite(true);
                    } else if(position.length == 4) {
                        player.setWhite(false);
                    }
                    game.getBoard().resetBoard();
                    if(position.length > 2) {
                        for(int i = 2; i < position.length; i++) {
                            if(position[i].equals("moves")) {
                                for(int j = i + 1; j < position.length; j++) {
                                    Move move = new Move(board.getTile(position[j].substring(0, 2)), board.getTile(position[j].substring(2, 4)), player);
                                    game.makeMove(move);
                                }
                                break;
                            }
                        }
                    }
                }
                System.out.println(game.bestMove(5, Integer.MIN_VALUE, Integer.MAX_VALUE, game.getCurrentPlayer().isWhite()));
            }
        }
        System.out.println(game.getGameStatus());
    }
}