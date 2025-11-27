package com.uwi;

import java.util.ArrayList;
import java.util.List;

/*
  Represents the game board for the quiz game.
  Provides methods to build and display a grid of questions
  organized by category and point value.
 */
public class GameBoard {
    //Builds a grid of questions grouped by categories.
    public static List<List<Question>> buildGrid(List<Question> questions, List<String> categories) {
        List<List<Question>> grid = new ArrayList<>();
        for (String cat : categories) {
            List<Question> col = new ArrayList<>();
            for (Question q : questions) {
                if (q.getCategory().equals(cat)) {
                    col.add(q);
            }
        }
            grid.add(col);
        }
        return grid;
    }
//Prints the game board to the console
    public static void printGrid(List<String> categories, List<List<Question>> grid) {
        int maxRows = grid.stream().mapToInt(List::size).max().orElse(0);

        System.out.println("\n================ QUESTION BOARD ================");
        System.out.print("|");
        for (String cat : categories) {
            System.out.printf(" %-15s |", cat);
        }
        System.out.println();
        System.out.println("-".repeat(categories.size() * 19));

        for (int r = 0; r < maxRows; r++) {
            System.out.print("|");
            for (int c = 0; c < categories.size(); c++) {
                List<Question> col = grid.get(c);
                String text;
                 if (r < col.size() && !col.get(r).isAnswered()) {
                     text = col.get(r).getValue() + " pts";
                 } else {
                   text = " ";
                 } 
                System.out.printf(" %-15s |", text);
            }
            System.out.println();
        }
        System.out.println("================================================\n");
    }
}

