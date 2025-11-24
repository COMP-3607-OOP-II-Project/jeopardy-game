package com.uwi;

import java.io.*;
import java.util.*;

public class CSVParser implements QuestionParser {

    @Override
    public List<Question> parseQuestions(String filePath) throws IOException {
        List<String> lines = readLines(filePath);
        return parseQuestionsFromLines(lines);
    }

    private List<String> readLines(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); 
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    private List<Question> parseQuestionsFromLines(List<String> lines) {
        List<Question> questions = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            if (isValidLine(parts, line)) {
            questions.add(parseQuestion(parts));
        }
    }
        return questions;
    }

    private boolean isValidLine(String[] parts, String line) {
        if (parts.length < 8) {
            System.out.println("Invalid line (expected 8 fields): " + line);
            return false;
        }
        return true;
    }

    private Question parseQuestion(String[] parts) {
        String category = parts[0].trim();
        int value = Integer.parseInt(parts[1].trim());
        String questionText = parts[2].trim();
        String optionA = parts[3].trim();
        String optionB = parts[4].trim();
        String optionC = parts[5].trim();
        String optionD = parts[6].trim();
        String correctAnswer = parts[7].trim().toUpperCase();

        return new Question(category, value, questionText, optionA, optionB, optionC, optionD, correctAnswer);
    }
}
