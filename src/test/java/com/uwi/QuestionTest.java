package com.uwi;

import static org.junit.Assert.*;
import org.junit.Test;

public class QuestionTest {

    @Test
    public void questionAnswered() {
        Question q = new Question(
                "Math", 200, "2+2=?",
                "1", "2", "3", "4",
                "D"
        );

        assertFalse(q.isAnswered());

        q.setAnswered(true);
        assertTrue(q.isAnswered());
    }

    @Test
    public void testQuestionFields() {
        Question q = new Question(
                "Science", 100, "Planet closest to the sun?",
                "Earth", "Venus", "Mercury", "Mars",
                "C"
        );

        assertEquals("Science", q.getCategory());
        assertEquals(100, q.getValue());
        assertEquals("Planet closest to the sun?", q.getQuestionText());
        assertEquals("Earth", q.getOptionA());
        assertEquals("Venus", q.getOptionB());
        assertEquals("Mercury", q.getOptionC());
        assertEquals("Mars", q.getOptionD());
        assertEquals("C", q.getCorrectAnswer());
    }

    @Test
    public void testIsCorrect() {
        Question q = new Question(
                "General", 50, "Capital of France?",
                "Berlin", "Madrid", "Paris", "Rome",
                "C"
        );

        assertTrue(q.isCorrect("c"));      
        assertTrue(q.isCorrect("C"));      
        assertFalse(q.isCorrect("A"));    
        assertFalse(q.isCorrect(null));
    }
}
