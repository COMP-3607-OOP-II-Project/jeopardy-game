package com.uwi;


public class Question {
    private String category;
    private int value;
    private String text;
    private String answer;
    private boolean used;

    public Question(String category, int value, String text, String answer) {
        this.category = category;
        this.value = value;
        this.text = text;
        this.answer = answer;
        this.used = false;
    }

    public String getCategory() {
        return category;
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isUsed() {
        return used;
    }

    public void markUsed() {
        used = true;
    }
}
