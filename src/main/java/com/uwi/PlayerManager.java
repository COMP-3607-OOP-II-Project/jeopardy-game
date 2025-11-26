package com.uwi;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlayerManager {

    private Scanner input;
    private GameEventNotifier notifier;
    private String caseId;

    public PlayerManager(Scanner input, GameEventNotifier notifier, String caseId) {
        this.input = input;
        this.notifier = notifier;
        this.caseId = caseId;
    }

    public List<Player> registerPlayers() {
        int totalPlayers = askForPlayerCount();
        List<Player> players = new ArrayList<>();

        for (int i = 1; i <= totalPlayers; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            String name = input.nextLine().trim();

            players.add(new Player(name));

            notifier.notifyObservers(new Event(caseId, "System", "Enter Player Name",
                java.time.LocalDateTime.now().toString(), "", 0, "", "Success", 0, "", 0));
           
        }

        return players;
    }

    private int askForPlayerCount() {
        int count = -1;

        while (count < 1 || count > 4) {
            System.out.print("How many players? (1-4): ");
            try {
                count = Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                
            } 

            if (count < 1 || count > 4) {
                System.out.println("Oops! That's not valid. Try again.");
            }
        }

        notifier.notifyObservers(new Event(caseId, "System", "Select Player Count",
                java.time.LocalDateTime.now().toString(), "", 0, "", "Success", 0, "", 0));
        
                return count;
    }
}
