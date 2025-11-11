package com.uwi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class CSVLoader implements QuestionStrategy {

    @Override
    public List<Question> load(String filePath) throws IOException { 
        List<Question> questions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 4) continue;
                String cat = parts[0].trim();
                int value = Integer.parseInt(parts[1].trim());
                String text = parts[2].trim();
                String ans = parts[3].trim();
                questions.add(new Question(cat, value, text, ans));
            }
        }
        return questions;
    }
}
