package com.uwi;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
/*
  Handles loading questions from different file formats (CSV, XML, JSON).
  Prompts the user to select a file type, uses the appropriate loader,
  and notifies observers of the event.
 */
public class QuestionLoader {

    private Scanner input;
    private GameEventNotifier notifier;
    private String caseId;
/*Constructs a QuestionLoader with the given input source,
 event notifier, and case identifier. */
    public QuestionLoader(Scanner input, GameEventNotifier notifier, String caseId ) {
        this.input = input;
        this.notifier = notifier;
        this.caseId = caseId;
    }
     /*
      Prompts the user to select a file type and loads questions
      from the chosen file using the appropriate loader.
      */
     public List<Question> loadQuestions() {
        Map<Integer, CommandLoader> options = Map.of(
            1, new CSVCommandLoader("sample_game_CSV.csv"),
            2, new XMLCommandLoader("sample_game_XML.xml"),
            3, new JSONCommandLoader("sample_game_JSON.json")
        );

        CommandLoader chosenLoader = null;

        while (chosenLoader == null) {
            System.out.println("Choose the type of file to load questions from:");
            System.out.println("1 - CSV");
            System.out.println("2 - XML");
            System.out.println("3 - JSON");
            System.out.print("Your choice: ");

            try {
                int selection = Integer.parseInt(input.nextLine().trim());
                chosenLoader = options.get(selection);

                if (chosenLoader == null) {
                    System.out.println("That's not a valid choice. Try again.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }

        try {
            List<Question> questions = chosenLoader.execute();

            if (questions.isEmpty()) {
                System.out.println("No questions were loaded. Exiting.");
                System.exit(1);
            }

            
            for (Question q : questions) {
                q.setAnswered(false);
            }

            notifier.notifyObservers(new Event(caseId, "System", "Load File",
                java.time.LocalDateTime.now().toString(), "", 0, "", "Success", 0, "", 0));
            
                System.out.println("Questions loaded successfully!");
            return questions;

        } catch (IOException e) {
            System.out.println("Oops! There was an error loading the file: " + e.getMessage());
            System.exit(1);
            return List.of(); 
        }
    } 
}
