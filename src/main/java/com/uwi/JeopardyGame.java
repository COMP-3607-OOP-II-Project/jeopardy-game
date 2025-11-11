package com.uwi;


import java.util.List;
import java.util.Scanner;

public class JeopardyGame extends GameTemplate {
    private Scanner input = new Scanner(System.in);
    private ReportGenerator report = new ReportGenerator();

    public JeopardyGame(List<Player> players, List<Question> questions, Logger logger) {
        super(players, questions, logger);
    }

    @Override
    protected void setup() {
        System.out.println("Welcome to Jeopardy!");
        logger.log("System", "Game Start", "", 0, "", "", 0);
    }

    @Override
    protected void playTurn() {
        for (Player player : players) {
            System.out.println("\n" + player.getName() + ", it's your turn!");
            List<Question> available = questions.stream()
                    .filter(q -> !q.isUsed())
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
            System.out.println("\nQuestion: " + q.getText());
            System.out.print("Your answer: ");
            String answer = input.nextLine();
            boolean correct = answer.equalsIgnoreCase(q.getAnswer());
            int points = correct ? q.getValue() : -q.getValue();
            player.updateScore(points);
            q.markUsed();

            String result = correct ? "Correct" : "Wrong";
            System.out.println(result + "! You now have " + player.getScore() + " points.");
            logger.log(player.getName(), "Answered", q.getCategory(), q.getValue(), answer, result, player.getScore());
        }
    }

    @Override
    protected boolean isGameOver() {
        return questions.stream().allMatch(Question::isUsed);
    }

    @Override
    protected void endGame() {
        System.out.println("\nGame Over!");
        logger.log("System", "Game End", "", 0, "", "", 0);
        report.generate(players, "summary.txt");
        System.out.println("Summary saved to summary.txt");
    }
}
