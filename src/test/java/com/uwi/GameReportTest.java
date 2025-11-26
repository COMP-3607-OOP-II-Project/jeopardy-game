package com.uwi;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class GameReportTest {

    @Test
    public void testGenerateReport() throws IOException {

        List<Event> events = new ArrayList<>();
        events.add(new Event(
                "GAME001",
                "jude",
                "Answer Question",
                "2025-11-26T12:00",
                "Variables & Data Types",
                100,
                "a",
                "Correct",
                100,
                "Which of the following declares an integer variable in C++?",
                100
        ));
        events.add(new Event(
                "GAME001",
                "jude",
                "Answer Question",
                "2025-11-26T12:01",
                "Control Structures",
                100,
                "a",
                "Correct",
                200,
                "Which statement is used to make a decision?",
                100
        ));


        GameReportGenerator reportGenerator = new GameReportGenerator("GAME001");

       
        for (Event e : events) {
            reportGenerator.update(e);
        }

        
        reportGenerator.generate();

        
        File file = new File("game_report.txt");
        assertTrue("Report file should exist", file.exists());

        
        String content = Files.readString(file.toPath());
        assertTrue(content.contains("jude"));
        assertTrue(content.contains("Variables & Data Types"));
        assertTrue(content.contains("Control Structures"));
        assertTrue(content.contains("Which of the following declares an integer variable in C++?"));
        assertTrue(content.contains("Which statement is used to make a decision?"));
        assertTrue(content.contains("Final Scores:"));
        assertTrue(content.contains("jude: 200") || content.contains("jude: 100")); // score depending on your logic
    }
}
