package mru.game.view;

import java.util.Scanner;

import mru.game.controller.GameManager;


public class AppMenu {	
	/**
	 * This class will be used to show the menus and sub menus to the user
	 * It also prompts the user for the inputs and validates them 
	 */
	static Scanner input = new Scanner(System.in);
	
	
	public static void MainMenu() {
		DrawMainMenu();
		char selection = input.nextLine().toUpperCase().charAt(0);
		switch(selection) {
			case 'P':
				//Launch blackjack
				break;
			case 'S':
				SubMenu();
				break;
			case 'E':
				System.out.println("Exiting program, thank you for playing!");
				break;
			default:
				System.out.println("Error, invalid input selected. Please try again!");
				MainMenu();
				break;
		}
		
	}
	
	public static void SubMenu() {
		DrawSubMenu();
		char selection = input.nextLine().toUpperCase().charAt(0);
		switch(selection) {
		case 'T':
			GameManager.showTopPlayer();
			break;
		case 'N':
			//Look for name
			break;
		case 'B':
			MainMenu();
			break;
		default:
			System.out.println("Error, invalid input selected. Please try again!");
			SubMenu();
			break;
		}
	}
	
	public static void DrawMainMenu() {
		System.out.println("Select one of these options:");
		System.out.println("\t" + "\t" + "(P) Play Game");
		System.out.println("\t" + "\t" + "(S) Search");
		System.out.println("\t" + "\t" + "(E) Exit");
		System.out.print("\n" + "Enter a choice: ");
	}

	public static void DrawSubMenu() {
		System.out.println("Select one of these options:");
		System.out.println("\t" + "\t" + "(T) Top Player (Most number of wins)");
		System.out.println("\t" + "\t" + "(N) Looking for a Name");
		System.out.println("\t" + "\t" + "(B) Back to Main menu");
		System.out.print("\n" + "Enter a choice: ");
	}

	public String namePrompt() {
		System.out.print("Enter a name here: ");
		String name = input.nextLine();
		return name;
	}

	

	

}