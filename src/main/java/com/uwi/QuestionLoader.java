package com.uwi;

import java.io.IOException;
import java.util.*;

public class QuestionLoader {

    private Scanner input;
    private Logger logger;

    public QuestionLoader(Scanner input, Logger logger) {
        this.input = input;
        this.logger = logger;
    }

    public List<Question> loadQuestions() {
        Map<Integer, CommandLoader> commands = Map.of(
            1, new CSVCommandLoader("sample_game_CSV.csv"),
            2, new XMLCommandLoader("sample_game_XML.xml"),
            3, new JSONCommandLoader("sample_game_JSON.json")
        );

        CommandLoader command = null;

        while (command == null) {
            System.out.println("Select file type to load questions:");
            System.out.println("1. CSV");
            System.out.println("2. XML");
            System.out.println("3. JSON");
            System.out.print("Enter choice: ");

            try {
                int choice = Integer.parseInt(input.nextLine().trim());
                command = commands.get(choice);
                if (command == null) System.out.println("Invalid choice. Try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number.");
            }
        }

        try {
            List<Question> questions = command.execute();
            if (questions.isEmpty()) {
                System.out.println("No questions found! Exiting.");
                System.exit(1);
            }
            questions.forEach(q -> q.setAnswered(false));
            logger.logEvent("System", "Load File", "", 0, "", "Success", 0);
            System.out.println("Questions loaded successfully!");
            return questions;
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
            System.exit(1);
            return List.of();
        }
    }
}

