package com.uwi;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class GameReportGenerator implements GameObserver {
    private String caseId;
    private List<Event> events = new ArrayList<>();

    public GameReportGenerator(String caseId) {
        this.caseId = caseId;
    }

    @Override
    public void update(Event event) {
        events.add(event);
    }

    public void generate() {
        String fileName = "game_report.txt";
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            pw.println("JEOPARDY PROGRAMMING GAME REPORT");
            pw.println("================================");
            pw.println("Case ID: " + caseId + "\n");

            
            List<String> playerIds = new ArrayList<>();
            for (Event e : events) {
                if (!e.playerId.equals("System")&&!playerIds.contains(e.playerId)) {
                    playerIds.add(e.playerId);
                }
            }

            pw.print("Players: ");
            pw.println(String.join(", ", playerIds));

            pw.println("\nGameplay Summary:");
            pw.println("-----------------");

            int turnNumber = 1;
            for (Event e : events) {
                if ("Answer Question".equals(e.activity)) {
                    pw.printf("Turn %d: %s selected %s for %d pts%n",
                            turnNumber, e.playerId, e.category, e.questionValue);
                    pw.printf("Question: %s%n", e.questionText);
                    pw.printf("Answer: %s - %s (%+d pts)%n",
                            e.answerGiven, e.result, e.pointsChange);
                    pw.printf("Score after turn: %s = %d%n%n",
                            e.playerId, e.scoreAfter);
                    turnNumber++;
                }
            }

            pw.println("Final Scores:");
          
            for (String pid : playerIds) {
                if (!pid.equals("System")) {
                int finalScore = 0;
                for (Event e : events) {
                    if (e.playerId.equals(pid)) {
                        finalScore = e.scoreAfter;
                    }
                }
                pw.printf("%s: %d%n", pid, finalScore);
            }
        } 

            System.out.println("\nSummary Report Saved: " + fileName);
        } catch (IOException e) {
            System.out.println("Unable to generate report.");
        }
    }
}