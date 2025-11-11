package com.uwi;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class PlayerTest {

    @Test
    public void testScoreUpdatesCorrectly() {
        Player p = new Player("Ana");
        assertEquals(0, p.getScore());

        p.updateScore(100);
        assertEquals(100, p.getScore());

        p.updateScore(-50);
        assertEquals(50, p.getScore());
    }
}

