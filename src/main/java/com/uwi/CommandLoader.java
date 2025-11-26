package com.uwi;

import java.io.IOException;
import java.util.List;

// Command interface
public interface CommandLoader {
    List<Question> execute() throws IOException;
} 