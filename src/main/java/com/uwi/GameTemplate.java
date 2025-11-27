package com.uwi;

/*
  Abstract template for a game using the Template Method pattern.
  Defines the overall structure of a game while allowing subclasses
  to implement specific steps.
 */

import java.util.List;

public abstract class GameTemplate {
    
    protected List<Player> players;
   
    // Perform game-specific setup
    public final void start() {
        setup();
        while (!isGameOver()) {
            playTurn();
        }
        endGame();
    }
// Abstract methods to be implemented by subclasses
    protected abstract void setup(); 
    protected abstract void playTurn();
    protected abstract boolean isGameOver();//
    protected abstract void endGame();
}

