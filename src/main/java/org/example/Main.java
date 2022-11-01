package org.example;

import org.example.Model.Board;
import org.example.Model.UCI;

public class Main {
    public static void main(String[] args) {

        Board board = new Board();
        System.out.println(board);
        System.out.println(board.getTile(3, 0));
        //System.out.println(board.getTile(1, 1).getPiece().canMove(board, board.getTile(1, 1), board.getTile(1, 2)));
        //System.out.println(board.getTile(1, 7).getPiece().isWhite());
        //System.out.println(board.getTile(3, 0).getPiece().getLegalMoves(board, board.getTile(2, 3)).size());
    }
}