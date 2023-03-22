package main.java.martinrahe.playtech_internship_test_blackjack;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ReadGameData {
    public static List<String[]> readGameData(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("resources/" + fileName));
        String line;
        List<String[]> gameData = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            String[] lineData = line.strip().split(",");
            if (lineData.length == 6) { //remove faulty entries
                gameData.add(lineData);
            }
        }
        gameData.sort((o1, o2) -> { //sort the array
            if (!o1[1].equals(o2[1])) { //sort by game ID
                return Integer.parseInt(o1[1]) - Integer.parseInt(o2[1]);
            }
            return Integer.parseInt(o1[0]) - Integer.parseInt(o2[0]); //sort by time
        });

        return gameData;
    }
}
