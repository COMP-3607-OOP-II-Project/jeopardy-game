package com.uwi;

public class Player {
    private String id;
    private int score;

    public Player(String id) {
        this.id = id;
        this.score = 0;
    }

    public String getId() {
        return id;
    }


    public int getScore() {
        return score;
    }

    public void updateScore(int points) {
        score += points;
    }

    @Override
    public String toString() {
        return id + " (Score: " + score + ")";
    }
}
