package com.uwi;

import java.io.*;
import java.util.*;
import org.json.*;

public class JSONParser implements QuestionParser {

    @Override
    public List<Question> parseQuestions(String filePath) throws IOException {

        String jsonContent = readFile(filePath);
        JSONArray array = new JSONArray(jsonContent);

        return parseArray(array);
    }

    private String readFile(String filePath) throws FileNotFoundException {

         Scanner scan = new Scanner(new File(filePath));
         scan.useDelimiter("\\Z"); 
         String content = "";
         if (scan.hasNext()) {
            content = scan.next();
         } 
        scan.close();
        return content;
   }


    private List<Question> parseArray(JSONArray array) {

        List<Question> questions = new ArrayList<>();

        for (int i = 0; i < array.length(); i=i+1) {
            JSONObject obj = array.getJSONObject(i);
            questions.add(parseQuestion(obj));
        }

        return questions;
    }

    private Question parseQuestion(JSONObject obj) {

        String category = obj.getString("Category");
        int value = obj.getInt("Value");
        String questionText = obj.getString("Question");

        JSONObject optionsObj = obj.getJSONObject("Options");

        String optionA = optionsObj.getString("A");
        String optionB = optionsObj.getString("B");
        String optionC = optionsObj.getString("C");
        String optionD = optionsObj.getString("D");

        String correctAnswer = obj.getString("CorrectAnswer");

        return new Question(category, value, questionText, optionA, optionB, optionC, optionD, correctAnswer
        );
    }
}
