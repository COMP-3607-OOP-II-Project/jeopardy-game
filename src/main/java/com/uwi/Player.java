package com.uwi;
/*
  Represents a player in the game.
  Each player has a unique identifier and a score that can be updated.
 */
public class Player {
    private String id;
    private int score;

    public Player(String id) {
        this.id = id;
        this.score = 0;
    }
   //Returns the player's unique identifier
    public String getId() {
        return id;
    }

   //Returns the current score of the player
    public int getScore() {
        return score;
    }
  //Updates the player's score by adding the specified points.
    public void updateScore(int points) {
        score += points;
    }
  //Returns a string representation of the player
    @Override
    public String toString() {
        return id + " (Score: " + score + ")";
    }
}
