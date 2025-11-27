package com.uwi;

/*
 Represents a multiple-choice question in the game.
  Each question has a category, point value, question text, 
  four answer options, and the correct answer.
  It also keeps track of whether it has been answered.
 */
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
 //Returns the category of the question
    public String getCategory() {
        return category;
    }
//Returns the point value of the question
    public int getValue() {
        return value;
    }
//Returns the text of the question
    public String getQuestionText() {
        return question;
    }
//Returns option A
    public String getOptionA() {
        return optionA;
    }
//Returns option B
    public String getOptionB() {
        return optionB;
    }
//Returns option C
    public String getOptionC() {
        return optionC;
    }
//Returns option D
    public String getOptionD() {
        return optionD;
    }
//Returns the correct answer
    public String getCorrectAnswer() {
        return correctAnswer;
    }
//Returns true if the question has been answered
    public boolean isAnswered() {
        return answered;
    }
//Sets the answered status of the question
    public void setAnswered(boolean answered) {
        this.answered = answered;
    }
//Checks if a given answer is correct.
    public boolean isCorrect(String answer) {
        if (answer == null) {
            return false;
        }
        return this.correctAnswer.equals(answer.toUpperCase());
    }
//Returns a string representation of the question,
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
