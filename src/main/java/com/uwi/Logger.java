package com.uwi;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Logger implements GameObserver {
    private String caseId;
    private List<Event> events = new ArrayList<>();
    private final String csvFile = "game_event_log.csv";

    public Logger(String caseId) {
        this.caseId = caseId;

        try (PrintWriter pw = new PrintWriter(new FileWriter(csvFile))) {
            pw.println("Case_ID,Player_ID,Activity,Timestamp,Category,Question_Value,Answer_Given,Result,Score_After_Play");
        } catch (IOException e) {
            System.out.println("Could not initialize log file.");
        }
    }

    @Override
    public void update(Event event) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(csvFile, true))) {

            pw.printf("%s,%s,%s,%s,%s,%d,%s,%s,%d%n",
                    event.caseId,
                    event.playerId,
                    event.activity,
                    event.timestamp,
                    event.category != null ? event.category : "",
                    event.questionValue,
                    event.answerGiven != null ? event.answerGiven : "",
                    event.result != null ? event.result : "",
                    event.scoreAfter
            );

        } catch (IOException e) {
            System.out.println("File could not be updated.");
        }
    }

    public void logEvent(String playerId,
                         String activity,
                         String category,
                         int questionValue,
                         String answer,
                         String result,
                         int scoreAfter) {

        String timestamp = LocalDateTime.now().toString();

        Event event = new Event(
                caseId,
                playerId,
                activity,
                timestamp,
                category,
                questionValue,
                answer,
                result,
                scoreAfter
        );

        update(event);
    }

    public void writeToCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(csvFile, true))) {
            for (Event e : events) {
                pw.printf("%s,%s,%s,%s,%s,%d,%s,%s,%d%n",
                        e.caseId,
                        e.playerId,
                        e.activity,
                        e.timestamp,
                        e.category != null ? e.category : "",
                        e.questionValue,
                        e.answerGiven != null ? e.answerGiven : "",
                        e.result != null ? e.result : "",
                        e.scoreAfter
                );
                
            }
            System.out.println("\nGame Event Log Saved: " + csvFile);
            events.clear();
        } catch (IOException ex) {
            System.out.println("Error writing events to CSV: " + ex.getMessage());
        }
    }
}
