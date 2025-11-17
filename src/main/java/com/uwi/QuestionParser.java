package com.uwi;

import java.io.IOException;
import java.util.List;

// Strategy interface

public interface QuestionParser {
    List<Question> parseQuestions(String filePath) throws IOException;
}