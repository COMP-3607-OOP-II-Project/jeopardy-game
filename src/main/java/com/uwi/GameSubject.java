package com.uwi;


// Subject interface
public interface GameSubject {
    void attach (GameObserver o);
    void detach (GameObserver o);
    void notifyObservers (Event event);
}
