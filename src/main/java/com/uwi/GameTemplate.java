package com.uwi;


import java.util.List;

public abstract class GameTemplate {
    protected List<Player> players;

    public final void start() {
        setup();
        while (!isGameOver()) {
            playTurn();
        }
        endGame();
    }

    protected abstract void setup();
    protected abstract void playTurn();
    protected abstract boolean isGameOver();
    protected abstract void endGame();
}

