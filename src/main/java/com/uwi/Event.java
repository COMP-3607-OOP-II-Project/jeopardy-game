package com.uwi;

import java.time.LocalDateTime;

public class Event {
    public String caseId;
    public String playerId;
    public String activity;
    public LocalDateTime timestamp;
    public String category;
    public int questionValue;
    public String answerGiven;
    public String result;
    public int scoreAfter;

    public Event (String caseId, String playerId, String activity, String category, 
                    int questionValue, String answerGiven, String result, int scoreAfter) {
        this.caseId = caseId;
        this.playerId = playerId;
        this.activity = activity;
        this.timestamp = LocalDateTime.now();
        this.category = category;
        this.questionValue = questionValue;
        this.answerGiven =  answerGiven;
        this.result = result;
        this.scoreAfter = scoreAfter;
    }

    
}
