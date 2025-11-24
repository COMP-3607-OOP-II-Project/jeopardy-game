package com.uwi;

import java.io.IOException;
import java.util.List;

public class XMLCommandLoader implements CommandLoader{
    private String filePath;

    public XMLCommandLoader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Question> execute() throws IOException {
        XMLParser parser = new XMLParser();
        return parser.parseQuestions(filePath);
    }
    
}
