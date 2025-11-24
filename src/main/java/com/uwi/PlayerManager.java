package com.uwi;

import java.util.*;

public class PlayerManager {

    private Scanner input;
    private Logger logger;

    public PlayerManager(Scanner input, Logger logger) {
        this.input = input;
        this.logger = logger;
    }

    public List<Player> registerPlayers() {
        int numPlayers = askForPlayerCount();
        List<Player> players = new ArrayList<>();

        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            String name = input.nextLine();
            players.add(new Player(name));
            logger.logEvent("System", "Enter Player Name", "", 0, "", "Success", 0);
        }
        return players;
    }

    private int askForPlayerCount() {
        int n = -1;
        while (n < 1 || n > 4) {
            try {
                System.out.print("Enter number of players (1-4): ");
                n = Integer.parseInt(input.nextLine().trim());
            } catch (Exception ignored) {}
            if (n < 1 || n > 4) System.out.println("Invalid number, try again.");
        }
        logger.logEvent("System", "Select Player Count", "", 0, "", "Success", 0);
        return n;
    }
}
