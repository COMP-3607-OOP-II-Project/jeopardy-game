package com.uwi;

public class Event {
    public String caseId;
    public String playerId;
    public String activity;
    public String timestamp; 
    public String category;
    public int questionValue;
    public String answerGiven;
    public String result;
    public int scoreAfter;

    public Event(String caseId, String playerId, String activity, String timestamp, String category,
                 int questionValue, String answerGiven, String result, int scoreAfter) {
        this.caseId = caseId;
        this.playerId = playerId;
        this.activity = activity;
        this.timestamp = timestamp;
        this.category = category;
        this.questionValue = questionValue;
        this.answerGiven = answerGiven;
        this.result = result;
        this.scoreAfter = scoreAfter;
    }
}
