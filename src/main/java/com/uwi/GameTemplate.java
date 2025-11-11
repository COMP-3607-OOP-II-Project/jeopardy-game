package com.uwi;


import java.util.List;

public abstract class GameTemplate {
    protected List<Player> players;
    protected List<Question> questions;
    protected Logger logger;

    public GameTemplate(List<Player> players, List<Question> questions, Logger logger) {
        this.players = players;
        this.questions = questions;
        this.logger = logger;
    }

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

