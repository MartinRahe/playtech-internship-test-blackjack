package main.java.martinrahe.playtech_internship_test_blackjack;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String fileName = "game_data.txt";
        List<String[]> gameData = null;
        try {
            gameData = ReadGameData.readGameData(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        List<String[]> faultyMoves = Simulate.simulateGame(gameData);
        try {
            WriteGameData.writeGameData(faultyMoves);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }


    }
}
