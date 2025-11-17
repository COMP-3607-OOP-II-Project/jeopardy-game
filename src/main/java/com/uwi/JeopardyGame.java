/* package com.uwi;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JeopardyGame extends GameTemplate {
    private Scanner input = new Scanner(System.in);
    private List<TurnRecord> turnRecords = new ArrayList<>();


    public JeopardyGame(List<Player> players, List<Question> questions, Logger logger) {
        super(players, questions, logger);
    }

    @Override
    protected void setup() {
        System.out.println("Welcome to Jeopardy!");
        logger.logEvent("System", "Start Game", "", 0,"", "N/A", 0);
    }

    @Override
    protected void playTurn() {
        for (Player player : players) {
            System.out.println("\n" + player.getId() + ", it's your turn!");
            List<Question> available = questions.stream()
                    .filter(q -> !q.isAnswered())
                    .toList();

            if (available.isEmpty()) return;

            // show available questions
            int i = 1;
            for (Question q : available) {
                System.out.println(i + ". [" + q.getCategory() + " - " + q.getValue() + "]");
                i++;
            }

            System.out.print("Pick a question number: ");
            int choice;
            try {
                choice = Integer.parseInt(input.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input. Turn skipped.");
                continue;
            }

            if (choice < 1 || choice > available.size()) {
                System.out.println("Invalid number. Turn skipped.");
                continue;
            }

            Question q = available.get(choice - 1);
            System.out.println("\nQuestion: " + q.getQuestionText());
            System.out.print("Your answer: ");
            String answer = input.nextLine();
            boolean correct = answer.equalsIgnoreCase(q.getCorrectAnswer());
            int points = correct ? q.getValue() : -q.getValue();
            player.updateScore(points);
            q.setAnswered(true);

            String result = correct ? "Correct" : "Wrong";
            System.out.println(result + "! You now have " + player.getScore() + " points.");
            

            logger.logEvent(player.getId(), "Answer Question", q.getCategory(), q.getValue(), answer, result, player.getScore());
        }
    }

    @Override
    protected boolean isGameOver() {
        return questions.stream().allMatch(Question::isAnswered);
    }

    @Override
    protected void endGame() {
        System.out.println("\nGame Over!");
        logger.logEvent("System", "Game End", "",0, "", "N/A", 0); 

    
        System.out.println("Final Scores:");
        for (Player player : players) {
            System.out.println(player.getId() + ": " + player.getScore() + " points");
        }

        GameReportGenerator report = new GameReportGenerator("GAME001", players, turRecords);
        report.generate();

        System.out.println("Summary saved to summary.txt");
    }
}
 */

 package com.uwi;

import java.util.*;
import java.io.IOException;


public class JeopardyGame extends GameTemplate {
    private Scanner input = new Scanner(System.in);
    private List<Question> questions;
    private Logger logger;
    private List<TurnRecord> turnRecords = new ArrayList<>();
    private String caseId = "GAME001";

    public JeopardyGame() {
    }

    @Override
    protected void setup() {
        System.out.println("Welcome to Jeopardy!");

        int numPlayers = 0;
        while (numPlayers <= 0) {
            try {
                System.out.print("Enter number of players: ");
                numPlayers = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, try again.");
            }
        }

        players = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            String name = input.nextLine();
            players.add(new Player(name));
        }

        logger = new Logger(caseId);
        logger.logEvent("System", "Start Game", "", 0, "", "N/A", 0);

        try {
            CSVParser parser = new CSVParser();
            questions = parser.parseQuestions("sample_game_CSV.csv");
            logger.logEvent("System", "Load File", "", 0, "",  "Success", 0);
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
            System.exit(1);
        }

        System.out.println("Game setup complete! Let's start.\n");
    }

    @Override
    protected void playTurn() {
        for (Player player : players) {
            System.out.println("\n" + player.getId() + ", it's your turn!");

            Set<String> categories = new LinkedHashSet<>();
            for (Question q : questions) if (!q.isAnswered()) categories.add(q.getCategory());
            if (categories.isEmpty()) return;

            List<String> catList = new ArrayList<>(categories);
            System.out.println("Available categories:");
            for (int i = 0; i < catList.size(); i++) System.out.println((i + 1) + ". " + catList.get(i));

            int catChoice = getUserChoice(catList.size(), "Pick a category number: ");
            String chosenCategory = catList.get(catChoice - 1);
            logger.logEvent(player.getId(), "Select Category", chosenCategory, 0, "", "", player.getScore());

        
            List<Question> available = new ArrayList<>();
            for (Question q : questions) if (!q.isAnswered() && q.getCategory().equals(chosenCategory)) available.add(q);

            System.out.println("Available questions:");
            for (int i = 0; i < available.size(); i++) System.out.println((i + 1) + ". " + available.get(i).getValue() + " pts");

            int qChoice = getUserChoice(available.size(), "Pick a question number: ");
            Question q = available.get(qChoice - 1);
            logger.logEvent(player.getId(), "Select Question", chosenCategory, q.getValue(), "", "", player.getScore());


            System.out.println("\nQuestion: " + q.getQuestionText());
            System.out.println("\nA: " + q.getOptionA());
            System.out.println("\nB: " + q.getOptionB());
            System.out.println("\nC: " + q.getOptionC());
            System.out.println("\nD: " + q.getOptionD());
            
            
            System.out.print("Your answer: ");
            String answer = input.nextLine();
            boolean correct = answer.equalsIgnoreCase(q.getCorrectAnswer());
            int points = correct ? q.getValue() : -q.getValue();
            player.updateScore(points);
            q.setAnswered(true);

            String result = correct ? "Correct" : "Wrong";
            System.out.println(result + "! You now have " + player.getScore() + " points.");

            logger.logEvent(player.getId(), "Answer Question", q.getCategory(), q.getValue(), answer, result, player.getScore());
            
            // Record turn for report
            turnRecords.add(new TurnRecord(
                player.getId(),       
                q.getCategory(),      
                q.getValue(),         
                q.getQuestionText(),  
                answer,               
                result,               
                points,               
                player.getScore()     
            ));

        }
    }

    @Override
    protected boolean isGameOver() {
        return questions.stream().allMatch(Question::isAnswered);
    }

    @Override
    protected void endGame() {
        System.out.println("\nGame Over!");
        logger.logEvent("System", "Game End", "", 0, "", "N/A", 0);

        System.out.println("Final Scores:");
        for (Player p : players) System.out.println(p.getId() + ": " + p.getScore() + " points");

   
        GameReportGenerator report = new GameReportGenerator(caseId, players, turnRecords);
        report.generate();

        System.out.println("Summary saved to summary.txt");
    }

    private int getUserChoice(int max, String prompt) {
        int choice = -1;
        while (choice < 1 || choice > max) {
            try {
                System.out.print(prompt);
                choice = Integer.parseInt(input.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input. Try again.");
            }
        }
        return choice;
    }
}
