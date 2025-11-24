package com.uwi;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CSVParserTest {

    @Test
    public void testLoadQuestionsFromCSV() throws Exception {

        Path file = Files.createTempFile("questions", ".csv");
        try (FileWriter fw = new FileWriter(file.toFile())) {

            fw.write("Category,Value,Question,OptionA,OptionB,OptionC,OptionD,CorrectAnswer\n");

            fw.write("Math,100,What is 2+2?,1,2,3,4,D\n");
            fw.write("Science,200,Planet closest to sun?,Earth,Mars,Venus,Mercury,D\n");
        }

        CSVParser loader = new CSVParser();
        List<Question> list = loader.parseQuestions(file.toString());

        assertEquals(2, list.size());
        assertEquals("Math", list.get(0).getCategory());
        assertEquals(100, list.get(0).getValue());
        assertEquals("What is 2+2?", list.get(0).getQuestionText());
        assertEquals("4", list.get(0).getOptionD());
        assertEquals("D", list.get(0).getCorrectAnswer());
    }
}
 