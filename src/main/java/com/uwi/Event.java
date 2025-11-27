package com.uwi;

/*
  Represents an event that occurs during the game.
  Used to log activities such as player actions, score updates, or system events.
 */
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

    public String questionText;
    public int pointsChange;

//Constructs a new Event with the given details.
    public Event(String caseId, String playerId, String activity, String timestamp, String category,
                 int questionValue, String answerGiven, String result, int scoreAfter,
                  String questionText, int pointsChange) {
        this.caseId = caseId;
        this.playerId = playerId;
        this.activity = activity;
        this.timestamp = timestamp;
        this.category = category;
        this.questionValue = questionValue;
        this.answerGiven = answerGiven;
        this.result = result;
        this.scoreAfter = scoreAfter;
        this.questionText = questionText;
        this.pointsChange = pointsChange;
    }
}
