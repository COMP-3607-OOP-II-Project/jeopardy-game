package com.uwi;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class GameBoardTest {

    @Test
    public void testGrid() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Variables and Data Types", 100, "Q1", "A", "A","B","C","D"));
        questions.add(new Question("Functions", 200, "Q2", "A", "A","B","C","D"));

        List<String> categories = List.of("Variables and Data Types", "Functions");
        List<List<Question>> grid = GameBoard.buildGrid(questions, categories);

        assertEquals(2, grid.size());
        assertEquals("Variables and Data Types", grid.get(0).get(0).getCategory());
        assertEquals("Functions", grid.get(1).get(0).getCategory());
    }
}
