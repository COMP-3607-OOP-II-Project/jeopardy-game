package com.uwi;

import java.util.ArrayList;
import java.util.List;

// Concrete Subject
public class GameEventNotifier implements GameSubject {
    private List<GameObserver> observers = new ArrayList<>();

    @Override
    public void attach (GameObserver o) {
        observers.add(o);
    }

    @Override
    public void detach (GameObserver o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers (Event event) {
        for (GameObserver o: observers) {
            o.update(event);
        }
    }

    public List<GameObserver> getObservers() {
        return observers;
    }
}
