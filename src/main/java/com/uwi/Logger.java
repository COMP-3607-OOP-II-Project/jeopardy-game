package com.uwi;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

// Concrete Observer

public class Logger implements GameObserver{
    private String caseId;
    private String csvFile = "game_event_log.csv";

    public Logger (String caseId) {
        this.caseId = caseId;
        File file = new File(csvFile);
        if (!file.exists()) {
            try (PrintWriter pw = new PrintWriter (new FileWriter(csvFile))) {
                pw.println("Case_Id, Player_ID, Activity,Tmestamp,Category,Question_Value, Answer_Given,Result,Score_After_Play");

            } catch (IOException e) {
                 System.out.println("Could not create log file.");
            }
        }
    }

    @Override 
    public void update(Event event) {
        try(PrintWriter pw = new PrintWriter(new FileWriter(csvFile, true))) {
            pw.printf("%s, %s, %s, %s, %s, %d, %s, %s, %d%n",
                event.caseId,
                event.playerId,
                event.activity,
                event.timestamp,
                event.category != null ? event.category : "",
                event.questionValue,
                event.answerGiven != null ? event.answerGiven: "",
                event.result != null ? event.result: "",
                event.scoreAfter
            );
    
        } catch (IOException e) {
            System.out.println("File could not be updated.");
        
        }
    }

    public void logEvent (String playerId, String activity, String category, int questionValue, String answer, String result, int scoreAfter) {
        Event event = new Event (caseId, playerId, activity, category, questionValue, answer, result, scoreAfter);
        update (event);
    }
    
}
