package mru.game.view;

import java.util.Scanner;


public class AppMenu {

	Scanner input = new Scanner(System.in);


	/**
	 * This class will be used to show the menus and sub menus to the user
	 * It also prompts the user for the inputs and validates them 
	 */

	 public static void MainMenu() {
		System.out.println("Select one of these options:");
		System.out.println("\t" + "\t" + "(P) Play Game");
		System.out.println("\t" + "\t" + "(S) Search");
		System.out.println("\t" + "\t" + "(E) Exit");
		System.out.print("\n" + "Enter a choice");
	}

	public static void SubMenu() {
		System.out.println("Select one of these options:");
		System.out.println("\t" + "\t" + "(T) Top Player (Most number of wins)");
		System.out.println("\t" + "\t" + "(N) Looking for a Name");
		System.out.println("\t" + "\t" + "(B) Back to Main menu");
		System.out.print("\n" + "Enter a choice");
	}

	public String namePrompt() {
		System.out.print("Enter a name here:");
		String name = input.nextLine();
		return name;
	}

	

}