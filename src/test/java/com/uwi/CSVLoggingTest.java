package com.uwi;

import org.junit.*;
import java.io.File;
import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.*;

public class CSVLoggingTest {

    @Test
    public void testEventLoggedToCSVWithCorrectFormat() throws Exception {
        Logger logger = new Logger("GAME001");

        
        logger.logEvent(
                "System",            
                "Load File",         
                "",                  
                0,                   
                "",                  
                "Success",           
                0                    
        );

        logger.writeToCSV();

        
        File file = new File("game_event_log.csv"); 

        assertTrue("CSV file should exist", file.exists());

        List<String> lines = Files.readAllLines(file.toPath());
        assertFalse(lines.isEmpty());

        String row = lines.get(1);
        assertTrue(row.contains("GAME001"));
        assertTrue(row.contains("System"));
        assertTrue(row.contains("Load File"));
        assertTrue(row.contains("Success"));
    }
}
