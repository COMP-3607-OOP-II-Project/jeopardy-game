package com.uwi;

import org.junit.*;
import static org.junit.Assert.*;

public class EventTest {

    @Test
    public void testEventInitialization() {
        Event e = new Event("GAME001", "jude", "Answer Question", "2025-11-26T12:00", "Variables and Data Types", 100, "A", "Correct", 100);

        assertEquals("GAME001", e.caseId);
        assertEquals("jude", e.playerId);
        assertEquals("Answer Question", e.activity);
        assertEquals("Variables and Data Types", e.category);
        assertEquals(100, e.questionValue);
        assertEquals("A", e.answerGiven);
        assertEquals("Correct", e.result);
        assertEquals(100, e.scoreAfter);
    }
}
