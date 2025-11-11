package com.uwi;


import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CSVLoaderTest {

    @Test
    public void testLoadQuestionsFromCSV() throws Exception {
        // Create a temporary CSV file
        Path file = Files.createTempFile("questions", ".csv");
        try (FileWriter fw = new FileWriter(file.toFile())) {
            fw.write("category,value,text,answer\n");
            fw.write("Math,100,What is 2+2?,4\n");
            fw.write("Science,200,Planet closest to sun?,Mercury\n");
        }

        CSVLoader loader = new CSVLoader();
        List<Question> list = loader.load(file.toString());

        assertEquals(2, list.size());
        assertEquals("Math", list.get(0).getCategory());
        assertEquals(100, list.get(0).getValue());
    }
}

