package com.uwi;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("=== JEOPARDY ===");
        System.out.print("Enter path to CSV file: ");
        String file = input.nextLine();

        QuestionStrategy strategy = new CSVLoader();
        List <Question> questions; 

        CSVLoader loader = new CSVLoader();
        try {
            questions = loader.load(file);
        } catch (Exception e) {
            System.out.println("Could not load questions.");
            return;
        }

        System.out.print("How many players (1-4)? ");
        int n = Integer.parseInt(input.nextLine());
        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            System.out.print("Enter player " + i + " name: ");
            players.add(new Player(input.nextLine()));
        }

        Logger logger = new Logger("game_log.csv");
        JeopardyGame game = new JeopardyGame(players, questions, logger);
        game.start();
    }
}  
