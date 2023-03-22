package main.java.martinrahe.playtech_internship_test_blackjack;

import java.util.*;

public class Simulate {
    public static List<String[]> simulateGame(List<String[]> gameData) {
        List<String[]> faultyMoves = new ArrayList<>();
        if (gameData.size() == 0) {
            return faultyMoves;
        }
        int previousID = Integer.parseInt(gameData.get(0)[1]) - 1; //initialize to a lower value than any existing ID
        boolean faultFoundAtId = false; //used to skip over turns with the same id where a faulty move was made
        int gameID;
        String action;
        HashSet<String> allowedActions = new HashSet<>();
        int playerCardCount = 0;
        int dealerCardCount = 0;
        String[] previousTurn = new String[0];

        for (String[] g : gameData) {
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

            String playerCards = g[5].strip();
            String dealerCards = g[4].strip();
            int playerSum = computeCardSum(playerCards);
            int dealerSum = computeCardSum(dealerCards);
            if (playerSum > 21) {
                allowedActions.clear();
                allowedActions.add("P Lose");
                allowedActions.add("P Left");
                allowedActions.add("D Redeal");
            }

            if (dealerSum > 21) {
                allowedActions.clear();
                allowedActions.add("P Win");
                allowedActions.add("P Left");
                allowedActions.add("D Redeal");
            }

            action = g[3].strip();

            if (!allowedActions.contains(action)) {
                faultyMoves.add(g);
                faultFoundAtId = true;
                continue;
            }

            if (action.equals("P Joined")) {
                if (cardCount(dealerCards) != 2 || cardCountRevealed(dealerCards) != 1 ||
                        !allCardsValid(dealerCards) || cardCount(playerCards) != 2 ||
                        cardCountRevealed(playerCards) != 2 || !allCardsValid(playerCards) ||
                        containDuplicateCards(playerCards, dealerCards)) {
                    //the set of cards on the table is a not valid starting position
                    faultyMoves.add(g);
                    faultFoundAtId = true;
                    continue;
                }
                allowedActions.remove("P Joined");
                allowedActions.add("P Hit");
                allowedActions.add("P Stand");
                allowedActions.add("P Left");
                playerCardCount = 2;
                dealerCardCount = 2;
                previousTurn = g;
                continue;
            }
            if (action.equals("P Hit")) {
                if (cardCount(dealerCards) != 2 || cardCountRevealed(dealerCards) != 1 ||
                        !allCardsValid(dealerCards) || cardCount(playerCards) != playerCardCount ||
                        cardCountRevealed(playerCards) != playerCardCount || !allCardsValid(playerCards) ||
                        containDuplicateCards(playerCards, dealerCards)) {
                    //the set of cards on the table is not what it should be based on the last move
                    faultyMoves.add(previousTurn);
                    faultFoundAtId = true;
                    continue;
                }
                playerCardCount++;
                previousTurn = g;
                continue;
            }
            if (action.equals("P Stand")) {
                if (cardCount(dealerCards) != 2 || cardCountRevealed(dealerCards) != 1 ||
                        !allCardsValid(dealerCards) || cardCount(playerCards) != playerCardCount ||
                        cardCountRevealed(playerCards) != playerCardCount || !allCardsValid(playerCards) ||
                        containDuplicateCards(playerCards, dealerCards)) {
                    //the set of cards on the table is not what it should be based on the last move
                    faultyMoves.add(previousTurn);
                    faultFoundAtId = true;
                    continue;
                }
                allowedActions.remove("P Hit");
                allowedActions.remove("P Stand");
                allowedActions.add("D Show");
                previousTurn = g;
                continue;
            }
            if (action.equals("D Show")) {
                if (cardCount(dealerCards) != 2 || cardCountRevealed(dealerCards) != 1 ||
                        !allCardsValid(dealerCards) || cardCount(playerCards) != playerCardCount ||
                        cardCountRevealed(playerCards) != playerCardCount || !allCardsValid(playerCards) ||
                        containDuplicateCards(playerCards, dealerCards)) {
                    //the set of cards on the table is not what it should be based on the last move
                    faultyMoves.add(previousTurn);
                    faultFoundAtId = true;
                    continue;
                }
                allowedActions.remove("D Show");
                allowedActions.add("D Hit");
                allowedActions.add("P Win");
                allowedActions.add("P Lose");
                previousTurn = g;
                continue;
            }
            if (action.equals("D Hit")) {
                if (cardCount(dealerCards) != dealerCardCount || cardCountRevealed(dealerCards) != dealerCardCount ||
                        !allCardsValid(dealerCards) || cardCount(playerCards) != playerCardCount ||
                        cardCountRevealed(playerCards) != playerCardCount || !allCardsValid(playerCards) ||
                        containDuplicateCards(playerCards, dealerCards)) {
                    //the set of cards on the table is not what it should be based on the last move
                    faultyMoves.add(previousTurn);
                    faultFoundAtId = true;
                    continue;
                }
                if (dealerSum > 16) {
                    faultyMoves.add(g);
                    faultFoundAtId = true;
                    continue;
                }
                dealerCardCount++;
                previousTurn = g;
                continue;
            }
            if (action.equals("P Win")) {
                if (cardCount(dealerCards) != dealerCardCount || cardCountRevealed(dealerCards) != dealerCardCount ||
                        !allCardsValid(dealerCards) || cardCount(playerCards) != playerCardCount ||
                        cardCountRevealed(playerCards) != playerCardCount || !allCardsValid(playerCards) ||
                        containDuplicateCards(playerCards, dealerCards)) {
                    //the set of cards on the table is not what it should be based on the last move
                    faultyMoves.add(previousTurn);
                    faultFoundAtId = true;
                    continue;
                }
                if (dealerSum < 17 || playerSum < dealerSum) {
                    faultyMoves.add(g);
                    faultFoundAtId = true;
                    continue;
                }
                allowedActions.clear();
                allowedActions.add("P Left");
                allowedActions.add("D Redeal");
                previousTurn = g;
                continue;
            }
            if (action.equals("P Lose")) {
                if (cardCount(dealerCards) != dealerCardCount || cardCountRevealed(dealerCards) != dealerCardCount ||
                        !allCardsValid(dealerCards) || cardCount(playerCards) != playerCardCount ||
                        cardCountRevealed(playerCards) != playerCardCount || !allCardsValid(playerCards) ||
                        containDuplicateCards(playerCards, dealerCards)) {
                    //the set of cards on the table is not what it should be based on the last move
                    faultyMoves.add(previousTurn);
                    faultFoundAtId = true;
                    continue;
                }
                if (dealerSum < 17 || dealerSum <= playerSum) {
                    faultyMoves.add(g);
                    faultFoundAtId = true;
                    continue;
                }
                allowedActions.clear();
                allowedActions.add("P Left");
                allowedActions.add("D Redeal");
                previousTurn = g;
                continue;
            }
            if (action.equals("D Redeal")) {
                if (cardCount(dealerCards) != 2 || cardCountRevealed(dealerCards) != 1 ||
                        !allCardsValid(dealerCards) || cardCount(playerCards) != 2 ||
                        cardCountRevealed(playerCards) != 2 || !allCardsValid(playerCards) ||
                        containDuplicateCards(playerCards, dealerCards)) {
                    //the set of cards on the table is a not valid starting position
                    faultyMoves.add(g);
                    faultFoundAtId = true;
                    continue;
                }
                allowedActions.clear();
                allowedActions.add("P Hit");
                allowedActions.add("P Stand");
                allowedActions.add("P Left");
                playerCardCount = 2;
                dealerCardCount = 2;
                previousTurn = g;
                continue;
            }
            if (action.equals("P Left")) {
                allowedActions.clear();
            }
        }

        return faultyMoves;
    }

    private static int computeCardSum(String cardsString) {
        Map<String, Integer> cardValues  = Map.of("A", 11, "J", 10, "Q", 10, "K", 10, "?", 0);
        String[] cards = cardsString.toUpperCase().strip().split("-");
        int sum = 0;
        String cardValue;
        for (String c : cards) {
            if (c.equals("?")) {
                continue;
            }
            cardValue = c.substring(0,c.length()-1);
            if (cardValues.containsKey(cardValue)) {
                sum += cardValues.get(cardValue);
                continue;
            }
            sum += Integer.parseInt(cardValue);
        }
        return sum;
    }

    private static int cardCount(String cardsString) {
        String[] cards = cardsString.toUpperCase().strip().split("-");
        return cards.length;
    }

    private static int cardCountRevealed(String cardsString) {
        String[] cards = cardsString.toUpperCase().strip().split("-");
        int count = 0;
        for (String c : cards) {
            if (c.equals("?")) {
                continue;
            }
            count++;
        }
        return count;
    }

    private static boolean allCardsValid(String cardsString) {
        String[] cards = cardsString.toUpperCase().strip().split("-");
        String type;
        String cardValue;
        for (String c : cards) {
            if (c.equals("?")) {
                continue;
            }
            if (c.length() < 2) {
                return false;
            }
            type = c.substring(c.length()-1);
            cardValue = c.substring(0,c.length()-1);
            if (!"SHCD".contains(type)) {
                return false;
            }
            if (!"AJQK".contains(cardValue)) {
                try {
                    int num = Integer.parseInt(cardValue);
                    if (num < 2 || num > 10) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean containDuplicateCards(String playerCards, String dealerCards) {
        String cardsString = playerCards.strip() + "-" + dealerCards.strip();
        String[] cards = cardsString.toUpperCase().split("-");
        HashSet<String> cardSet = new HashSet<>();
        for (String c : cards) {
            if (c.equals("?")) {
                continue;
            }
            if (cardSet.contains(c)) {
                return true;
            }
            cardSet.add(c);
        }
        return false;
    }
}
