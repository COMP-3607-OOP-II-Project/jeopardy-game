package com.uwi;

import java.util.*;
import java.io.IOException;

public class JeopardyGame extends GameTemplate {

    private Scanner input = new Scanner(System.in);
    private List<Question> questions = new ArrayList<>();
    private List<TurnRecord> turnRecords = new ArrayList<>();
    private Logger logger;
    private String caseId = "GAME001";

    public JeopardyGame() {}

    @Override
    protected void setup() {
        System.out.println("Welcome to Jeopardy!");

        // Initialize logger first
        logger = new Logger(caseId);

        int numPlayers = askForPlayerCount();
        players = registerPlayers(numPlayers);

        logger.logEvent("System", "Start Game", "", 0, "", "N/A", 0);

        loadQuestions();
        System.out.println("Game setup complete! Let's start.\n");
    }

    private int askForPlayerCount() {
        while (true) {
            try {
                System.out.print("Enter number of players (1-4): ");
                int n = Integer.parseInt(input.nextLine());
                if (n > 0 && n <= 4) {
                    logger.logEvent("System", "Select Player Count", "", 0, "", "Success", 0);
                    return n;
                }
            } catch (Exception ignored) {}
            System.out.println("Invalid number, try again.");
        }
    }

    private List<Player> registerPlayers(int numPlayers) {
        List<Player> list = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            String name = input.nextLine();
            list.add(new Player(name));
            logger.logEvent("System", "Enter Player Name", "", 0, "", "Success", 0);
        }
        return list;
    }

     private List<Question> loadQuestionsFromFile(CommandLoader command) {
        try {
            List<Question> loaded = command.execute();
            logger.logEvent("System", "Load File", "", 0, "", "Success", 0);
            return loaded;
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
            System.exit(1);
            return List.of(); 
        }
    }

    private void loadQuestions() {
    Map<Integer, CommandLoader> commands = new HashMap<>();
    commands.put(1, new CSVCommandLoader("sample_game_CSV.csv"));
    commands.put(2, new XMLCommandLoader("sample_game_XML.xml"));
    commands.put(3, new JSONCommandLoader("sample_game_JSON.json"));

    CommandLoader command = null;

    while (command == null) {
        System.out.println("Select file type to load questions:");
        System.out.println("1. CSV");
        System.out.println("2. XML");
        System.out.println("3. JSON");
        System.out.print("Enter choice: ");

        String inputLine = input.nextLine().trim();

        try {
            int choice = Integer.parseInt(inputLine);
            command = commands.get(choice);
            if (command == null) {
                System.out.println("Invalid choice. Try again.\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Enter a number.\n");
        }
    }

    questions = loadQuestionsFromFile(command);
    if (questions.isEmpty()) {
        System.out.println("No questions found! Exiting.");
        System.exit(1);
    }
    for (Question q : questions) {
        q.setAnswered(false);
    }
    System.out.println("Questions loaded successfully!");
}


    @Override
    protected void playTurn() {
        while(!isGameOver()) {
        for (Player player : players) {
            if (isGameOver()) break;

            System.out.println("\n" + player.getId() + ", it's your turn!");

            List<String> categories = getAvailableCategories();
            if (categories.isEmpty()) break;

            List<List<Question>> grid = buildCategoryGrid(categories);
            printGrid(categories, grid);

            String chosenCategory = chooseCategory(player, categories);
            Question chosenQuestion = chooseQuestion(player, chosenCategory);

            processAnswer(player, chosenQuestion);
        }
    }
} 

    private List<String> getAvailableCategories() {
        Set<String> set = new LinkedHashSet<>();
        for (Question q : questions) {
            if (!q.isAnswered()) set.add(q.getCategory());
        }
        return new ArrayList<>(set);
    }

    private List<List<Question>> buildCategoryGrid(List<String> categories) {
        List<List<Question>> grid = new ArrayList<>();
        for (String cat : categories) {
            List<Question> list = new ArrayList<>();
            for (Question q : questions) {
                if (q.getCategory().equals(cat)) list.add(q);
            }
            grid.add(list);
        }
        return grid;
    }

    private void printGrid(List<String> categories, List<List<Question>> grid) {
        int maxRows = grid.stream().mapToInt(List::size).max().orElse(0);

        System.out.println("\n================ QUESTION BOARD ================");
        System.out.print("|");
        for (String cat : categories) {
            System.out.printf(" %-15s |", cat);
        }
        System.out.println();
        
        System.out.println("-".repeat(categories.size() * 19));

        for (int r = 0; r < maxRows; r++) {
            System.out.print("|");
            for (int c = 0; c < categories.size(); c++) {
                List<Question> col = grid.get(c);
                String text = (r < col.size() && !col.get(r).isAnswered())
                        ? col.get(r).getValue() + " pts"
                        : " ";
                System.out.printf(" %-15s |", text);
            }
            System.out.println();
        }
        System.out.println("================================================\n");
    }

    private String chooseCategory(Player player, List<String> categories) {
        for (int i = 0; i < categories.size(); i++)
            System.out.println((i + 1) + ". " + categories.get(i));

        int choice = getUserChoice(categories.size(), "Pick a category number: ");
        String selected = categories.get(choice - 1);

        logger.logEvent(player.getId(), "Select Category", selected, 0, "", "", player.getScore());
        return selected;
    }

    private Question chooseQuestion(Player player, String category) {
        List<Question> list = new ArrayList<>();
        for (Question q : questions) {
            if (!q.isAnswered() && q.getCategory().equals(category)) list.add(q);
        }

        System.out.println("Available questions:");
        for (int i = 0; i < list.size(); i++)
            System.out.println((i + 1) + ". " + list.get(i).getValue() + " pts");

        int choice = getUserChoice(list.size(), "Pick a question number: ");
        Question q = list.get(choice - 1);

        logger.logEvent(player.getId(), "Select Question", category, q.getValue(), "", "", player.getScore());
        return q;
    }

    private void processAnswer(Player player, Question q) {
        System.out.println("\nQuestion: " + q.getQuestionText());
        System.out.println("A: " + q.getOptionA());
        System.out.println("B: " + q.getOptionB());
        System.out.println("C: " + q.getOptionC());
        System.out.println("D: " + q.getOptionD());

        System.out.print("Your answer: ");
        String answer = input.nextLine();

        boolean correct = answer.equalsIgnoreCase(q.getCorrectAnswer());
        int points = correct ? q.getValue() : -q.getValue();
        String result = correct ? "Correct" : "Wrong";

        player.updateScore(points);
        q.setAnswered(true);

        logger.logEvent(player.getId(), "Answer Question",
                q.getCategory(), q.getValue(), answer, result, player.getScore());
        logger.logEvent("System", "Score Updated", "", 0, "", "Success", player.getScore());

        System.out.println(result + "! You now have " + player.getScore() + " points.");

        turnRecords.add(new TurnRecord(
                player.getId(), q.getCategory(), q.getValue(),
                q.getQuestionText(), answer, result, points, player.getScore()
        ));
    }

    @Override
    protected void endGame() {
        System.out.println("\nGame Over!");
        logger.logEvent("System", "Exit Game", "", 0, "", "N/A", 0);

        System.out.println("Final Scores:");
        for (Player p : players)
            System.out.println(p.getId() + ": " + p.getScore() + " points");

        logger.logEvent("System", "Generate Event Log", "", 0, "", "Success", 0);
        logger.writeToCSV();

        GameReportGenerator report = new GameReportGenerator(caseId, players, turnRecords);
        report.generate();
        logger.logEvent("System", "Generate Report", "", 0, "", "Success", 0);

        logger.logEvent("System", "Exit Game", "", 0, "", "N/A", 0);
    }

    private int getUserChoice(int max, String prompt) {
        int choice = -1;
        while (choice < 1 || choice > max) {
            try {
                System.out.print(prompt);
                choice = Integer.parseInt(input.nextLine());
            } catch (Exception ignored) {
                System.out.println("Invalid input. Try again.");
            }
        }
        return choice;
    }

    @Override
    protected boolean isGameOver() {
        return questions.isEmpty() || questions.stream().allMatch(Question::isAnswered);
    }
}
