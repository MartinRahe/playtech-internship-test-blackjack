package main.java.martinrahe.playtech_internship_test_blackjack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Simulate {
    public static List<String[]> simulateGame(List<String[]> gameData) {
        List<String[]> faultyMoves = new ArrayList<>();
        if (gameData.size() == 0) {
            return faultyMoves;
        }
        int previousID = Integer.parseInt(gameData.get(0)[1]) - 1; //initialize to a lower value than any existing ID
        boolean faultFoundAtId = false; //used to skip over turns with the same id where a faulty move was made
        System.out.println(previousID);
        int gameID;
        String action;
        HashSet<String> allowedActions = new HashSet<>();
        for (String[] g : gameData) {
            System.out.println(Arrays.toString(g));
            gameID = Integer.parseInt(g[1]);
            if (gameID == previousID && faultFoundAtId) {
                continue;
            }
            faultFoundAtId = false;

            if (gameID != previousID) {
                allowedActions.clear();
                allowedActions.add("P Joined");
                previousID = gameID;
            }

            action = g[3].strip();
            if (!allowedActions.contains(action)) {
                faultyMoves.add(g);
                faultFoundAtId = true;
                continue;
            }
            if (action.equals("P Joined")) {
                allowedActions.remove("P Joined");
                allowedActions.add("P Hit");
                allowedActions.add("P Stand");
                allowedActions.add("P Left");
            }
            if (action.equals("P Stand")) {
                allowedActions.remove("P Hit");
                allowedActions.remove("P Stand");
                allowedActions.add("D Show");
            }
            if (action.equals("D Show")) {
                allowedActions.remove("D Show");
                allowedActions.add("D Hit");
                allowedActions.add("P Win");
                allowedActions.add("P Lose");
            }
            if (action.equals("P Win")) {
                allowedActions.clear();
                allowedActions.add("P Left");
                allowedActions.add("D Redeal");
            }
            if (action.equals("P Lose")) {
                allowedActions.clear();
                allowedActions.add("P Left");
                allowedActions.add("D Redeal");
            }
            if (action.equals("D Redeal")) {
                allowedActions.clear();
                allowedActions.add("P Hit");
                allowedActions.add("P Stand");
                allowedActions.add("P Left");
            }
            if (action.equals("P Left")) {
                allowedActions.clear();
            }
            System.out.printf("%d %s\n", gameID, action);
        }

        return faultyMoves;
    }
}
