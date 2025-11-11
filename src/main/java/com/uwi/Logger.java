package com.uwi;


import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Logger {
    private String filename;

    public Logger(String filename) {
        this.filename = filename;
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write("Time,Player,Action,Category,Value,Answer,Result,Score\n");
        } catch (IOException e) {
            System.out.println("Could not create log file.");
        }
    }

    public void log(String player, String action, String category, int value, String answer, String result, int score) {
        try (FileWriter fw = new FileWriter(filename, true)) {
            fw.write(String.format("%s,%s,%s,%s,%d,%s,%s,%d\n",
                    LocalDateTime.now(), player, action, category, value, answer, result, score));
        } catch (IOException e) {
            System.out.println("Error writing to log.");
        }
    }
}
