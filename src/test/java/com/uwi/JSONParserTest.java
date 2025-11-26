
package com.uwi;

import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class JSONParserTest {

@Test
public void JSONQuestions() throws Exception {
    
    String filePath = "sample_game_JSON.json";

    
    JSONParser parser = new JSONParser();
    List<Question> list = parser.parseQuestions(filePath);

 
    assertEquals(25, list.size()); 
 
   
    Question q1 = list.get(0);
    assertEquals("Variables & Data Types", q1.getCategory());
    assertEquals(100, q1.getValue());
    assertEquals("Which of the following declares an integer variable in C++?", q1.getQuestionText());
    assertEquals("int num;", q1.getOptionA());
    assertEquals("float num;", q1.getOptionB());
    assertEquals("num int;", q1.getOptionC());
    assertEquals("integer num;", q1.getOptionD());
    assertEquals("A", q1.getCorrectAnswer());

   Question q7 = list.get(6); 
   assertEquals("Control Structures", q7.getCategory());
   assertEquals(200, q7.getValue());
   assertEquals("What is the output of: if (5 > 10) cout << 'Hi'; else cout << 'Bye';", q7.getQuestionText());
   assertEquals("Hi", q7.getOptionA());
   assertEquals("Bye", q7.getOptionB());
   assertEquals("Error", q7.getOptionC());
   assertEquals("Nothing", q7.getOptionD());
   assertEquals("B", q7.getCorrectAnswer());

   
} 
}


