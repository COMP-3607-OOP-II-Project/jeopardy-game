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

        List<Player> players = new ArrayList<>();
        Player p1 = new Player("jude");
        p1.updateScore(200);
        players.add(p1);

    
        List<TurnRecord> turnRecords = new ArrayList<>();
        turnRecords.add(new TurnRecord(
                "jude",
                "Variables & Data Types",
                100,
                "Which of the following declares an integer variable in C++?",
                "a",
                "Correct",
                100,
                100
        ));
        turnRecords.add(new TurnRecord(
                "jude",
                "Control Structures",
                100,
                "Which statement is used to make a decision?",
                "a",
                "Correct",
                100,
                200
        ));

        GameReportGenerator reportGenerator = new GameReportGenerator("GAME001", players, turnRecords);
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
        assertTrue(content.contains("jude: 200"));
    }
}
