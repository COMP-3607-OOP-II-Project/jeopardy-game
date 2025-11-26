package com.uwi;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


public class GameReportGenerator {
    private List<Player> players;
    private String caseId;
    private List<TurnRecord> turnRecords;

    public GameReportGenerator (String caseId, List<Player> players, List<TurnRecord> turnRecords) {
        this.caseId = caseId;
        this.players = players;
        this.turnRecords = turnRecords;
    }

    public void generate() {
        String fileName = "game_report.txt";

        try(PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            pw.println("JEOPARDY PROGRAMMING GAME REPORT");
            pw.println("================================");
            pw.println("Case ID: " + caseId + "\n");

            pw.print("Players: ");

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < players.size(); i++) {
                sb.append(players.get(i).getId());
                if (i != players.size() - 1) {
                    sb.append(", ");
                }
            }
            pw.println(sb.toString());
            
            pw.println("\nGameplay Summary: ");
            pw.println("-----------------");
            

            int turnNumber = 1;
            for (TurnRecord record: turnRecords) {
                
                pw.printf("Turn %d: %s selected %s for %d pts%n",
                    turnNumber, record.playerId, record.category, record.questionValue);
                pw.printf("Question: %s%n", record.questionText);
                pw.printf("Answer: %s - %s (%+d pts)%n",
                    record.answerGiven, record.result, record.pointsChange);
                pw.printf("Score after turn: %s = %d%n%n", 
                    record.playerId, record.scoreAfter);
                turnNumber++;
            }

            pw.println("Final Scores:");
            for (Player p: players) {
                pw.printf("%s: %d%n", p.getId(), p.getScore());
            }

            System.out.println("\nSummary Report Saved: " + fileName);
        } catch (IOException e) {
            System.out.println("Unable to genrate report.");
        }
    }
  
}

