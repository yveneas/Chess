package org.example.Model;

import java.util.Scanner;

public class UCI {
    public static String engineName = "ChessEngine";

    public static void  uciCommunication(String input) {
        if(input.equals("uci")) {
            System.out.println("id name " + engineName);
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
            //TODO
        }
        else if ("go".equals(input)) {
            //TODO
        }
        else if ("print".equals(input)) {
            //TODO
        }
        else if(input.equals("quit")) {
            System.exit(0);
        }
    }
}
