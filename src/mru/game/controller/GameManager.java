package mru.game.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import mru.game.view.AppMenu;
import mru.game.model.Player;

/**
 * The GameManager class is responsible for managing the game and player data.
 */
public class GameManager {
	
	private final static String FILE_PATH = "./res/Casinoinfo.txt";
	public static ArrayList<Player> players;
	
	/**
	 * Calls methods in the correct order
	 * @throws Exception
	 */
	public static void startGame() throws Exception {
		loadData();
		AppMenu.mainMenu();
		saveData();
	}
	
	/**
	 * Creates the players arraylist
	 * @throws Exception
	 */
	public GameManager() throws Exception {
		players = new ArrayList<Player>();
	}
	
	/**
	 * Loads files into players arraylist from file
	 * @throws FileNotFoundException
	 */
	public static void loadData() throws FileNotFoundException {
		File db = new File(FILE_PATH);
		
		if (db.exists()) {
			players = new ArrayList<Player>();
			Scanner fileReader = new Scanner(db);
			while (fileReader.hasNextLine()) {
				String currentLine = fileReader.nextLine();
				String[] splittedLine = currentLine.split(",");
				Player p = new Player(splittedLine[0], Integer.parseInt(splittedLine[1]), Integer.parseInt(splittedLine[2]));
				players.add(p);
			}
			fileReader.close();
		} else {
			System.out.println("Error, file not found!");
		}
	}
	
	/**
	 * Saves players arraylist to file
	 * @throws IOException
	 */
	public static void saveData() throws IOException {
		FileWriter fileWriter = new FileWriter(FILE_PATH);
		PrintWriter printWriter = new PrintWriter(fileWriter);
		for (Player player : players) {
			String playerData = String.format("%s,%d,%d", player.getName(), player.getBalance(), player.getNumOfWins());
			printWriter.println(playerData);
		}
		printWriter.close();
	}
	
	/**
	 * Searches for a name in the database and displays their information
	 * @throws FileNotFoundException
	 */
	public static void searchName() throws FileNotFoundException {
		loadData();
		AppMenu menu = new AppMenu();
		String name = menu.namePrompt(); 
		boolean playerFound = false;
		
		for (Player player : players) {
			if (player.getName().equalsIgnoreCase(name)) {
				System.out.println("\n                  - PLAYER INFO -                  ");
				System.out.println("+================+================+================+");
				System.out.printf("|%-16s|%-16s|%-16s|\n", "NAME", "# WINS", "BALANCE");
				System.out.println("+================+================+================+");
				System.out.printf("|%-16s|%-16d|%-16s|\n", player.getName(), player.getNumOfWins(),player.getBalance() + " $");
				System.out.println("+================+================+================+");
				playerFound = true;
				break;
			}
		}
	
		if (!playerFound) {
			System.out.println("\nPlayer not found in the database.");
		}
		System.out.println("Press \"Enter\" to continue...");
		new Scanner(System.in).nextLine(); 
		
	}
	
	/**
	 * Searches for and shows the top players information
	 */
	public static void showTopPlayer() {
		Player topPlayer = players.get(0);
		for (Player player : players) {
			if (player.getNumOfWins() > topPlayer.getNumOfWins()) {
				topPlayer = player;
			}
		}
		
		System.out.println("\n          - TOP PLAYER -           ");
		System.out.println("+================+================+");
		System.out.printf("|%-16s|%-16s|\n", "NAME", "WINS");
		System.out.println("+================+================+");
		System.out.printf("|%-16s|%-16d|\n", topPlayer.getName(), topPlayer.getNumOfWins());
		System.out.println("+================+================+");
		System.out.println("Press \"Enter\" to continue...");
		new Scanner(System.in).nextLine(); 
	}
}