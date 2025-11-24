
package com.uwi;

import org.junit.Test;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JSONParserTest {

    @Test
    public void testLoadQuestionsFromJSON() throws Exception {

        // Create a temporary JSON file
        Path file = Files.createTempFile("questions", ".json");

        try (FileWriter fw = new FileWriter(file.toFile())) {
            fw.write("[\n" +
                    "  {\n" +
                    "    \"Category\": \"Variables & Data Types\",\n" +
                    "    \"Value\": 100,\n" +
                    "    \"Question\": \"Which of the following declares an integer variable in C++?\",\n" +
                    "    \"Options\": {\n" +
                    "      \"A\": \"int num;\",\n" +
                    "      \"B\": \"float num;\",\n" +
                    "      \"C\": \"num int;\",\n" +
                    "      \"D\": \"integer num;\"\n" +
                    "    },\n" +
                    "    \"CorrectAnswer\": \"A\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"Category\": \"Control Structures\",\n" +
                    "    \"Value\": 200,\n" +
                    "    \"Question\": \"What is the output of: if (5 > 10) cout << 'Hi'; else cout << 'Bye';\",\n" +
                    "    \"Options\": {\n" +
                    "      \"A\": \"Hi\",\n" +
                    "      \"B\": \"Bye\",\n" +
                    "      \"C\": \"Error\",\n" +
                    "      \"D\": \"Nothing\"\n" +
                    "    },\n" +
                    "    \"CorrectAnswer\": \"B\"\n" +
                    "  }\n" +
                    "]");
        }

        // Parse using your JSONParser
        JSONParser parser = new JSONParser();
        List<Question> list = parser.parseQuestions(file.toString());

        // ---------- Assertions ----------
        assertEquals(2, list.size());

        // Question 1
        Question q1 = list.get(0);
        assertEquals("Variables & Data Types", q1.getCategory());
        assertEquals(100, q1.getValue());
        assertEquals("Which of the following declares an integer variable in C++?", q1.getQuestionText());
        assertEquals("int num;", q1.getOptionA());
        assertEquals("float num;", q1.getOptionB());
        assertEquals("num int;", q1.getOptionC());
        assertEquals("integer num;", q1.getOptionD());
        assertEquals("A", q1.getCorrectAnswer());

        // Question 2
        Question q2 = list.get(1);
        assertEquals("Control Structures", q2.getCategory());
        assertEquals(200, q2.getValue());
        assertEquals("What is the output of: if (5 > 10) cout << 'Hi'; else cout << 'Bye';", q2.getQuestionText());
        assertEquals("Hi", q2.getOptionA());
        assertEquals("Bye", q2.getOptionB());
        assertEquals("Error", q2.getOptionC());
        assertEquals("Nothing", q2.getOptionD());
        assertEquals("B", q2.getCorrectAnswer());
    }
}


