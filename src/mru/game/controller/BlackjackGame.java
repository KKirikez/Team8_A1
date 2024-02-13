package mru.game.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import mru.game.model.Player;
import mru.game.controller.*;

/**
 * The BlackjackGame class represents a game of Blackjack.
 * It contains methods for starting the game, playing turns, determining the winner, and calculating hand values.
 */
public class BlackjackGame {
	
    private static final int BLACKJACK_VALUE = 21;
    private static final int DEALER_STAND_VALUE = 17;
    private static ArrayList<Card> playerHand = new ArrayList<>();
    private static ArrayList<Card> dealerHand = new ArrayList<>();
    private static int bet = 0;
    private static Player player = new Player();

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
        CardDeck deck = new CardDeck();
        getPlayer();
        
        while (playAgain) {    	
        	
        	System.out.println("How much would you like to bet this round? ");
            bet = scanner.nextInt();
            
            dealInitialCards(deck, playerHand, dealerHand);
            playerTurn(deck, playerHand, scanner);
            dealerTurn(deck, dealerHand);
            displayCards();
            determineWinner(player, playerHand, dealerHand);
            
            playerHand.clear();
            dealerHand.clear();
            
            System.out.print("Do you want to play again? (y/n): ");
            String playAgainInput = scanner.next();
            playAgain = playAgainInput.equalsIgnoreCase("y");
            System.out.println("Your balance is $" + player.getBalance());
            if(player.getBalance() <= 0) {
        		System.out.println("I'm sorry " + player.getName() + ", but you have lost all your money.");
        		System.out.println("Please come back to our casino once you have some more money.");
        		break;
        	}
        }
        GameManager.players.add(player);
        

    }

    private static void dealInitialCards(CardDeck deck, ArrayList<Card> playerHand, ArrayList<Card> dealerHand) {
        playerHand.add(deck.drawCard());
        playerHand.add(deck.drawCard());
        dealerHand.add(deck.drawCard());
        dealerHand.add(deck.drawCard());
    }

    private static void playerTurn(CardDeck deck, ArrayList<Card> playerHand, Scanner scanner) {
        while (calculateHandValue(playerHand) < BLACKJACK_VALUE) {
        	displayCards();
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
            System.out.println("You lost $" + bet);
            player.setBalance(player.getBalance() - bet);
            return;
        }

        if (dealerValue > BLACKJACK_VALUE) {
            System.out.println("Dealer busted! You win.");
            System.out.println("You won $" + bet);
            player.setBalance(player.getBalance() + bet);
            player.setNumOfWins(player.getNumOfWins() + 1);
        } else if (dealerValue > playerValue) {
            System.out.println("Dealer wins!");
            System.out.println("You lost $" + bet);
            player.setBalance(player.getBalance() - bet);
        } else if (playerValue > dealerValue) {
        	System.out.println("You win!");
        	System.out.println("You won $" + bet);
            player.setBalance(player.getBalance() + bet);
    	}	else {
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
    
    private static void displayCards() {
		System.out.println("\n           - Blackjack -           ");
		System.out.println("+================+================+");
		System.out.printf("|%-16s|%-16s|\n", "Players Name", "Dealer");
		System.out.println("+================+================+");
		int biggest = Math.max(playerHand.size(), dealerHand.size());
		for(int i = 0; i < biggest; i++) {
		    String dealerCard = (i < dealerHand.size()) ? dealerHand.get(i).toString() : ""; 
		    String playerCard = (i < playerHand.size()) ? playerHand.get(i).toString() : ""; 
		    System.out.printf("|%-16s|%-16s|\n", playerCard, dealerCard);
		    System.out.println("+----------------+----------------+");
		}
    }
    
    private static void getPlayer() {
        try {
            GameManager.loadData();
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            boolean flag = false;
            for (Player foundPlayer : GameManager.players) {
            	if (foundPlayer.getName().equalsIgnoreCase(name)) {
                	System.out.println("****************************************");
                    System.out.printf("%12s %n", "Welcome back, " + foundPlayer.getName() + "! Your balance is $" + foundPlayer.getBalance());
                    System.out.println("****************************************");
                    player.setName(foundPlayer.getName());
                    player.setBalance(foundPlayer.getBalance());
                    player.setNumOfWins(foundPlayer.getNumOfWins()); 
                    GameManager.players.remove(foundPlayer);
                    flag = true;
                }
            }
            if(!flag) {
            	System.out.println("****************************************");
                System.out.printf("%16s", "Welcome, " + name + "! Your balance is $100\n");
                System.out.println("****************************************");
                player.setName(name);
                player.setBalance(100);
                player.setNumOfWins(0); 
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading player data: " + e.getMessage());
        }
    }
}
