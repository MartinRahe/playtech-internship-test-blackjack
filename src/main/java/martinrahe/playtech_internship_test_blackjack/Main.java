package main.java.martinrahe.playtech_internship_test_blackjack;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String fileName = "game_data.txt";
        try {
            ReadGameData.readGameData(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
