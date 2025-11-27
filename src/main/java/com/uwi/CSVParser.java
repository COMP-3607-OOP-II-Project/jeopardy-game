package com.uwi;

import java.io.*;
import java.util.*;

public class CSVParser implements QuestionParser {

    @Override
    public List<Question> parseQuestions(String filePath) throws IOException { // Reads all lines from a CSV file (via readLines) and converts them into a list of Question objects using parseLines

        List<String> lines = readLines(filePath);
        return parseLines(lines);
    }

    public List<String> readLines(String filePath) throws FileNotFoundException { //Reads all lines from a CSV file and returns them as a list.

        List<String> lines = new ArrayList<>();
        try (Scanner scan = new Scanner(new File(filePath))) {

        if (scan.hasNextLine()) {
            scan.nextLine(); 
        }
        while (scan.hasNextLine()) {
               lines.add(scan.nextLine());
        }
    } 
    return lines;
    }

    public List<Question> parseLines(List<String> lines) { //Converts each CSV line into a Question object, printing a message for invalid lines.

        List<Question> questions = new ArrayList<>();

        for (String line : lines) {
             String[] parts = line.split(",");

             if (parts.length >=8) {
                 questions.add(parseQuestion(parts));
            }
            else {
                System.out.println("Invalid line (8 fields is expected): " + line);
            }
       } 
    return questions;
    } 

    public Question parseQuestion(String[] parts) { // Converts an array of 8 strings into a Question instance, trimming whitespace and formatting the correct answer.

        String category = parts[0].trim();
        int value = Integer.parseInt(parts[1].trim());
        String question = parts[2].trim();
        String optionA = parts[3].trim();
        String optionB = parts[4].trim();
        String optionC = parts[5].trim(); 
        String optionD = parts[6].trim();
        String correctAnswer = parts[7].trim().toUpperCase();

        return new Question(category, value, question, optionA, optionB, optionC, optionD, correctAnswer);
    }
}
