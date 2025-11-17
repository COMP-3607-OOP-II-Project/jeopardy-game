package com.uwi;

public class TurnRecord {
    public String playerId;
    public String category;
    public int questionValue;
    public String questionText;
    public String answerGiven;
    public String result;
    public int pointsChange;
    public int scoreAfter;

    public TurnRecord(String playerId, String category, int questionValue, String questionText,
                      String answerGiven, String result, int pointsChange, int scoreAfter) {
        this.playerId = playerId;
        this.category = category;
        this.questionValue = questionValue;
        this.questionText = questionText;
        this.answerGiven = answerGiven;
        this.result = result;
        this.pointsChange = pointsChange;
        this.scoreAfter = scoreAfter;
    }
}
