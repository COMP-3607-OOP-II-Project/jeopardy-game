package com.uwi;


public class Question {
    private String category;
    private int value;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctAnswer;
    private boolean answered;

    public Question(String category, int value, String question, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {

        this.category = category;
        this.value = value;
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
        this.answered = false;
    }

    public String getCategory() {
        return category;
    }

    public int getValue() {
        return value;
    }

    public String getQuestionText() {
        return question;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public boolean isCorrect(String answer) {
        if (answer == null) {
            return false;
        }
        return this.correctAnswer.equals(answer.toUpperCase());
    }

    @Override
    public String toString() {
        return "Category: " + category + "\n" +
               "Value: " + value + "\n" +
               "Question: " + question + "\n" +
               "A: " + optionA + "\n" +
               "B: " + optionB + "\n" +
               "C: " + optionC + "\n" +
               "D: " + optionD + "\n";
    }
}
