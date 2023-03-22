package main.java.martinrahe.playtech_internship_test_blackjack;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class WriteGameData {
    public static void writeGameData(List<String[]> gameData) throws IOException {
        FileWriter fw = new FileWriter("analyzer_results.txt");
        for (String[] g: gameData) {
            fw.write(String.join(",", g) + "\n");
        }
        fw.close();
    }
}
