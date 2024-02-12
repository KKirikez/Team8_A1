package mru.game.controller;

public class BlackjackGame {

	/**
	 * In this class you implement the game
	 * You should use CardDeck class here
	 * See the instructions for the game rules
	 */

package mru.game.controller;

import java.util.Scanner;

public class BlackjackGame {
    private Player player;
    private Dealer dealer;
    private CardDeck deck;
    private Scanner scanner;

    public BlackjackGame(Player player, Scanner scanner) {
        this.player = player;
        this.dealer = new Dealer();
        this.deck = new CardDeck();
        this.scanner = scanner;
    }

    public void startGame() {
        System.out.println("Welcome to Blackjack!");
        System.out.println("Let's get started.\n");

        while (true) {
            System.out.println("Your balance: $" + player.getBalance());
            System.out.println("Place your bet (Minimum $2, Maximum $" + player.getBalance() + "):");

            double bet = getValidBet();

            if (bet == 0) {
                System.out.println("You don't have enough balance to place a bet. Exiting game...");
                return;
            }

            Hand playerHand = new Hand();
            Hand dealerHand = new Hand();

            dealInitialCards(playerHand, dealerHand);

            boolean playerBusted = playerTurn(playerHand);
            if (playerBusted) {
                System.out.println("You busted! Dealer wins.");
                player.setBalance(player.getBalance() - bet);
                continue;
            }

            dealerTurn(dealerHand);

            determineWinner(playerHand, dealerHand, bet);

            System.out.println("Do you want to play again? (Y/N)");
            String playAgain = scanner.next().trim();
            if (!playAgain.equalsIgnoreCase("Y")) {
                System.out.println("Thank you for playing Blackjack!");
                return;
            }
        }
    }

    private double getValidBet() {
        double bet;
        while (true) {
            try {
                bet = scanner.nextDouble();
                if (bet < 2 || bet > player.getBalance()) {
                    throw new IllegalArgumentException();
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid bet amount. Please enter a valid bet:");
                scanner.nextLine(); // 
            }
        }
        return bet;
    }

    private void dealInitialCards(Hand playerHand, Hand dealerHand) {
        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());
        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());

        System.out.println("Your cards: " + playerHand);
        System.out.println("Dealer's cards: " + dealerHand.getVisibleCards());
    }

    private boolean playerTurn(Hand playerHand) {
        while (true) {
            System.out.println("Do you want to Hit (H) or Stand (S)?");
            String decision = scanner.next().trim();
            if (decision.equalsIgnoreCase("H")) {
                playerHand.addCard(deck.drawCard());
                System.out.println("Your cards: " + playerHand);
                if (playerHand.getTotal() > 21) {
                    return true;
                }
            } else if (decision.equalsIgnoreCase("S")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter 'H' to Hit or 'S' to Stand:");
            }
        }
        return false;
    }

    private void dealerTurn(Hand dealerHand) {
        System.out.println("Dealer's turn:");
        System.out.println("Dealer's cards: " + dealerHand);

        while (dealerHand.getTotal() < 17) {
            dealerHand.addCard(deck.drawCard());
            System.out.println("Dealer draws a card.");
            System.out.println("Dealer's cards: " + dealerHand);
        }
    }

    private void determineWinner(Hand playerHand, Hand dealerHand, double bet) {
        int playerTotal = playerHand.getTotal();
        int dealerTotal = dealerHand.getTotal();

        System.out.println("Final hands:");
        System.out.println("Your cards: " + playerHand + " (Total: " + playerTotal + ")");
        System.out.println("Dealer's cards: " + dealerHand + " (Total: " + dealerTotal + ")");

        if (playerTotal > 21) {
            System.out.println("You busted! Dealer wins.");
            player.setBalance(player.getBalance() - bet);
        } else if (dealerTotal > 21) {
            System.out.println("Dealer busted! You win.");
            player.setBalance(player.getBalance() + bet);
        } else if (playerTotal > dealerTotal) {
            System.out.println("You win!");
            player.setBalance(player.getBalance() + bet);
        } else if (playerTotal < dealerTotal) {
            System.out.println("Dealer wins.");
            player.setBalance(player.getBalance() - bet);
        } else {
            System.out.println("It's a tie.");
        }
    }
}


}
