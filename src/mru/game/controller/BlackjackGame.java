package mru.game.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import mru.game.model.Player;
import mru.game.model.Card;
import mru.game.model.CardDeck;

/**
 * The BlackjackGame class represents a game of Blackjack.
 * It contains methods for starting the game, playing turns, determining the winner, and calculating hand values.
 */
public class BlackjackGame {
	
    private static final int BLACKJACK_VALUE = 21;
    private static final int DEALER_STAND_VALUE = 17;

    public static void main(String[] args) {
        try {
            GameManager.startGame();
            startBlackjack();
            GameManager.saveData();
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public static void startBlackjack() {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;

        while (playAgain) {
            CardDeck deck = new CardDeck();
            ArrayList<Card> playerHand = new ArrayList<>();
            ArrayList<Card> dealerHand = new ArrayList<>();
            Player player = getPlayer();

            System.out.println("Welcome " + player.getName() + "!");

            dealInitialCards(deck, playerHand, dealerHand);
            playerTurn(deck, playerHand, scanner);
            dealerTurn(deck, dealerHand);
            determineWinner(player, playerHand, dealerHand);

            System.out.print("Do you want to play again? (y/n): ");
            String playAgainInput = scanner.next();
            playAgain = playAgainInput.equalsIgnoreCase("y");
        }

        scanner.close();
    }

    private static Player getPlayer() {
        try {
            GameManager.loadData();
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            for (Player player : GameManager.players) {
                if (player.getName().equalsIgnoreCase(name)) {
                    System.out.println("Welcome back, " + player.getName() + "! Your balance is $" + player.getBalance());
                    return player;
                }
            }
            System.out.println("Welcome, " + name + "! Your balance is $100");
            return new Player(name, 0, 100); 
        } catch (IOException e) {
            System.out.println("An error occurred while loading player data: " + e.getMessage());
            return null;
        }
    }

    private static void dealInitialCards(CardDeck deck, ArrayList<Card> playerHand, ArrayList<Card> dealerHand) {
        playerHand.add(deck.drawCard());
        playerHand.add(deck.drawCard());
        dealerHand.add(deck.drawCard());
        dealerHand.add(deck.drawCard());
    }

    private static void playerTurn(CardDeck deck, ArrayList<Card> playerHand, Scanner scanner) {
        while (calculateHandValue(playerHand) < BLACKJACK_VALUE) {
            System.out.println("Your hand: " + playerHand);
            System.out.print("Do you want to hit or stand? (hit/stand): ");
            String choice = scanner.next();
            if (choice.equalsIgnoreCase("hit")) {
                playerHand.add(deck.drawCard());
            } else if (choice.equalsIgnoreCase("stand")) {
                break;
            }
        }
    }

    private static void dealerTurn(CardDeck deck, ArrayList<Card> dealerHand) {
        while (calculateHandValue(dealerHand) < DEALER_STAND_VALUE) {
            dealerHand.add(deck.drawCard());
        }
    }

    private static void determineWinner(Player player, ArrayList<Card> playerHand, ArrayList<Card> dealerHand) {
        int playerValue = calculateHandValue(playerHand);
        int dealerValue = calculateHandValue(dealerHand);

        if (playerValue > BLACKJACK_VALUE) {
            System.out.println("Player busted! Dealer wins.");
            return;
        }

        if (dealerValue > BLACKJACK_VALUE || playerValue > dealerValue) {
            System.out.println("Player wins!");
            player.setNumOfWins(player.getNumOfWins() + 1);
        } else if (dealerValue > playerValue) {
            System.out.println("Dealer wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    private static int calculateHandValue(ArrayList<Card> hand) {
        int value = 0;
        int numAces = 0;

        for (Card card : hand) {
            if (card.getRank() >= 10) {
                value += 10;
            } else if (card.getRank() == 1) {
                numAces++;
                value += 11; // Assume ace as 11 initially
            } else {
                value += card.getRank();
            }
        }

        while (value > BLACKJACK_VALUE && numAces > 0) {
            value -= 10; // Change ace value from 11 to 1
            numAces--;
        }

        return value;
    }
}
