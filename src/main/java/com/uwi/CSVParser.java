package com.uwi;

import java.io.*;
import java.util.*;

public class CSVParser implements QuestionParser {

    @Override
    public List<Question> parseQuestions(String filePath) throws IOException { 
        List<Question> questions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if(parts.length < 8) {
                    System.out.println("Invalid line (expected 8 fields): " + line);
                    continue;
                }

            
                String category = parts[0].trim();
                int value = Integer.parseInt(parts[1].trim());
                String questionText = parts[2].trim();
                String optionA = parts[3].trim();
                String optionB = parts[4].trim();
                String optionC = parts[5].trim();
                String optionD = parts[6].trim();
                String correctAnswer = parts[7].trim().toUpperCase();


                questions.add(new Question(category, value, questionText, optionA, optionB, optionC, optionD, correctAnswer));

            }
                
        } 
        return questions;
    }
}
