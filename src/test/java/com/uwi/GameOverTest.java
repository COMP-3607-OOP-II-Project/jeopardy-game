package com.uwi;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.*;

public class GameOverTest {

    @Test
    public void testIsGameOver() {
        JeopardyGame game = new JeopardyGame();
        List<Question> questions = Arrays.asList(
                new Question("Variables and Data Types", 100, "Q1", "A", "A","B","C","D"),
                new Question("Functions", 200, "Q2", "A", "A","B","C","D")
        );

        game.setQuestions(questions); 

        assertFalse(game.isGameOver());

        for (Question q : questions) {
            q.setAnswered(true);
        }
        assertTrue(game.isGameOver());
    }
} 
