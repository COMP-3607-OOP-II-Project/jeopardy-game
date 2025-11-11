package com.uwi;

import java.io.IOException;
import java.util.List;

public interface QuestionStrategy {
    List<Question> load (String filePath) throws IOException;   
}