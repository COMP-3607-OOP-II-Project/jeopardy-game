package com.uwi;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class JeopardyGame extends GameTemplate {

    private final Scanner input = new Scanner(System.in);
    private List<Question> questions = new ArrayList<>();
    private final List<TurnRecord> turnRecords = new ArrayList<>();
    private Logger logger;
    private final String caseId = "GAME001";
    private GameEventNotifier notifier = new GameEventNotifier();

    public JeopardyGame() {}

    
    public void setQuestions(List<Question> questions) {
            this.questions = questions;
    }  

    @Override
    protected void setup() {
        System.out.println("Welcome to Jeopardy!");
        logger = new Logger(caseId);

       
        PlayerManager pm = new PlayerManager(input, logger);
        players = pm.registerPlayers();

        QuestionLoader loader = new QuestionLoader(input, logger);
        questions = loader.loadQuestions();

        for (Question q : questions) {
              q.setAnswered(false);
        }

        System.out.println("Setup complete. Good luck!\n");
    } 

    @Override
    protected void playTurn() {
       
        while (!isGameOver()) {
            for (Player p : players) {
                if (!isGameOver()) {
                runPlayerTurn(p);
            }
        }
    }
} 

    private void runPlayerTurn(Player player) {
        System.out.println("\n" + player.getId() + ", your turn.");

        List<String> categories = getRemainingCategories();
        if (categories.isEmpty()) {
            return;
        }

        List<List<Question>> grid = GameBoard.buildGrid(questions, categories);
        GameBoard.printGrid(categories, grid);

        String cat = askCategory(player, categories);
        Question q = askQuestion(player, cat);

        showAndProcessQuestion(player, q);
    }

    private List<String> getRemainingCategories() {
        Set<String> set = new LinkedHashSet<>();
        for (Question q : questions) {
            if (!q.isAnswered()) {
                set.add(q.getCategory());
            }
        }
        return new ArrayList<>(set);
    }

    private String askCategory(Player player, List<String> categories) {

    for (int i = 0; i < categories.size(); i++) {
        System.out.println((i + 1) + ". " + categories.get(i));
    }

    System.out.print("Choose a category (or Q to quit): ");  
    String entry = input.nextLine().trim();

    if (entry.equalsIgnoreCase("Q")) {
        endGame();
        System.exit(0);
    }

    int pos = Integer.parseInt(entry);
    String chosen = categories.get(pos - 1);

    logger.logEvent(player.getId(), "Select Category", chosen, 0, "", "", player.getScore());
    return chosen;
}

private Question askQuestion(Player player, String category) {
    List<Question> list = new ArrayList<>();

    for (Question q : questions) {
        if (!q.isAnswered() && q.getCategory().equals(category)) {
            list.add(q);
        }
    }

    System.out.println("Available questions:");
    for (int i = 0; i < list.size(); i++) {
        Question tmp = list.get(i);
        System.out.println((i + 1) + ". " + tmp.getValue() + " pts");
    }

    System.out.print("Pick a question (or Q to quit): "); 
    String entry = input.nextLine().trim();

    if (entry.equalsIgnoreCase("Q")) {
        endGame();
        System.exit(0);
    }

    int idx = Integer.parseInt(entry);
    Question chosen = list.get(idx - 1);

    logger.logEvent(player.getId(), "Select Question", category, chosen.getValue(), "", "", player.getScore());
    return chosen;
}

    
    private void showAndProcessQuestion(Player player, Question q) {
        displayQuestion(q);
        String answer = readPlayerAnswer();

        boolean correct = isCorrectAnswer(q, answer);
        int change;
        if (correct) {
            change = q.getValue();
        } else {
           change = -q.getValue();
        }
 
        applyAnswerEffects(player, q, change);

        String result;
        if (correct) {
           result = "Correct";
        } else {
        result = "Wrong"; 
        }

        Event e = new Event(
        caseId,
        player.getId(),
        "Answer Question",
        java.time.LocalDateTime.now().toString(), 
        q.getCategory(),
        q.getValue(),    
        answer,
        result,
        player.getScore()
        ); 

     notifier.notifyObservers(e);

        logger.logEvent(player.getId(), 
                "Answer Question",
                q.getCategory(), 
                q.getValue(), 
                answer, 
                result,
                player.getScore());

        logger.logEvent("System", "Score Updated", "", 0, "", "Success", player.getScore());

        System.out.println(result + "! Your score is now " + player.getScore());

        recordTurn(player, q, answer, result, change);
    }

    private void displayQuestion(Question q) {
        System.out.println("\nQuestion: " + q.getQuestionText());
        System.out.println("A: " + q.getOptionA());
        System.out.println("B: " + q.getOptionB());
        System.out.println("C: " + q.getOptionC());
        System.out.println("D: " + q.getOptionD());
    } 

    private String readPlayerAnswer() {
        System.out.print("Your answer: ");
        return input.nextLine().trim();
    }

    private boolean isCorrectAnswer(Question q, String ans) {
        return ans.equalsIgnoreCase(q.getCorrectAnswer());
    }

    private void applyAnswerEffects(Player player, Question q, int change) {
        q.setAnswered(true);
        player.updateScore(change);
    }

    private void recordTurn(Player p, Question q, String answer, String result, int pts) {
        turnRecords.add(new TurnRecord(
                p.getId(),
                q.getCategory(),
                q.getValue(),
                q.getQuestionText(),
                answer,
                result,
                pts,
                p.getScore()
        ));
    }

    private int askNumber(String prompt, int min, int max) {
        int num = -1;
        while (num < min || num > max) {
            try {
                System.out.print(prompt);
                num = Integer.parseInt(input.nextLine());
            } catch (Exception ex) {
                System.out.println("Invalid input. Try again.");
            }
        }
        return num;
    }

    @Override
    protected void endGame() {
        System.out.println("\nGame Over!");

        logger.logEvent("System", "Exit Game", "", 0, "", "N/A", 0);

        System.out.println("Final Scores:");
        for (Player p : players) {
            System.out.println(p.getId() + ": " + p.getScore());
        }

        logger.logEvent("System", "Generate Event Log", "", 0, "", "Success", 0);
        logger.writeToCSV();

        GameReportGenerator report = new GameReportGenerator(caseId, players, turnRecords);
        report.generate();

        logger.logEvent("System", "Generate Report", "", 0, "", "Success", 0);
    }

    @Override
    protected boolean isGameOver() {
        
        return questions.stream().allMatch(Question::isAnswered);
    }
}
