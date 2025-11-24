package com.uwi;

import java.util.List;
import java.io.IOException;

public class CSVCommandLoader implements CommandLoader {
    private String filePath;

    public CSVCommandLoader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Question> execute() throws IOException {
        CSVParser parser = new CSVParser();
        return parser.parseQuestions(filePath);
    }
    
}
