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

           String categoryStr;
           if (event.category != null) {
               categoryStr = event.category;
            } else {
              categoryStr = "";
            }

            String answerGivenStr;
            if (event.answerGiven != null) {
                answerGivenStr = event.answerGiven;
            } else {
               answerGivenStr = "";
            }

            String resultStr;
            if (event.result != null) {
                resultStr = event.result;
            } else {
              resultStr = "";
            }

            pw.printf("%s,%s,%s,%s,%s,%d,%s,%s,%d%n",
                      event.caseId,
                      event.playerId,
                      event.activity,
                      event.timestamp,
                      categoryStr,
                      event.questionValue,
                      answerGivenStr,
                      resultStr,
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
                scoreAfter,
                null,
                0
        );

        update(event);
    }

    public void writeToCSV() {
    try (PrintWriter pw = new PrintWriter(new FileWriter(csvFile, true))) {
        for (Event e : events) {
            String category;
            if (e.category != null) {
                category = e.category;
            } else {
                category = "";
            }

            String answerGiven;
            if (e.answerGiven != null) {
                answerGiven = e.answerGiven;
            } else {
                answerGiven = "";
            }

            String result;
            if (e.result != null) {
                result = e.result;
            } else {
                result = "";
            }

            pw.printf("%s,%s,%s,%s,%s,%d,%s,%s,%d%n",
                    e.caseId,
                    e.playerId,
                    e.activity,
                    e.timestamp,
                    category,
                    e.questionValue,
                    answerGiven,
                    result,
                    e.scoreAfter
            );
        }
    } catch (IOException ex) {
        System.out.println("Error writing to CSV: " + ex.getMessage());
    }
}

}
