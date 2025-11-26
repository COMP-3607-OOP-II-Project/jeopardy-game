package com.uwi;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class XMLParserTest {

    @Test
    public void XMLQuestions() throws Exception {

        XMLParser parser = new XMLParser();

        String filePath = "sample_game_XML.xml";

        List<Question> questions = parser.parseQuestions(filePath);

        assertEquals(25, questions.size());

        Question q = questions.get(0);

        assertEquals("Variables & Data Types", q.getCategory());
        assertEquals(100, q.getValue());
        assertEquals("Which of the following declares an integer variable in C++?", q.getQuestionText());

        assertEquals("int num;", q.getOptionA());
        assertEquals("float num;", q.getOptionB());
        assertEquals("num int;", q.getOptionC());
        assertEquals("integer num;", q.getOptionD());

        assertEquals("A", q.getCorrectAnswer());
    }
}

