package com.uwi;

import java.io.IOException;
import java.util.List;

public class JSONCommandLoader implements CommandLoader{
    private String filePath;

    public JSONCommandLoader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Question> execute() throws IOException {
        JSONParser parser = new JSONParser();
        return parser.parseQuestions(filePath);
    }
}
