package com.uwi;


import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReportGenerator {
    public void generate(List<Player> players, String filename) {
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write("=== Jeopardy Summary ===\n\n");
            for (Player p : players) {
                fw.write(p.getName() + ": " + p.getScore() + " points\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing summary file.");
        }
    }
}
