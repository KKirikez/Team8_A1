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

public class GameManager {
	
	/* In this class toy'll need these methods:
	 * A constructor
	 * A method to load the txt file into an arraylist (if it exists, so you check if the txt file exists first)
	 * A save method to store the arraylist into the the txt file 
	 * A method to search for a player based their name
	 * A method to find the top players
	 * Depending on your designing technique you may need and you can add more methods here 
	 */
	 private final String FILE_PATH = "Casinoinfo,txt";
    ArrayList<Player> players;

	//initalize players
    public GameManager() throws Exception {
        players = new ArrayList<>();
	
	}
    
	//adds player fields to a formatted arraylist
	private void loadData() throws FileNotFoundException {
		File db = new File(FILE_PATH);
		
		if (db.exists()) {
			Scanner fileReader = new Scanner(db);
			
			while (fileReader.hasNextLine()) {
				String currentLine = fileReader.nextLine();
				String[] splittedLine = currentLine.split(",");
				Player p = new Player(splittedLine[0], Integer.parseInt(splittedLine[1]), Integer.parseInt(splittedLine[2]));
				players.add(p);
			}
			fileReader.close();
		}
	}

	//saves the arraylist into the txt file
	public void saveData() throws IOException {
        FileWriter fileWriter = new FileWriter(FILE_PATH);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for (Player player : players) {
            String playerData = String.format("%s,%d,%d", player.getName(), player.getNumOfWins(), player.getBalance());
            printWriter.println(playerData);
        }
        printWriter.close();
	}
	
	//Search for a player based off their name
	public void searchName() {
		AppMenu menu = new AppMenu();
		String name = menu.namePrompt(); 
		boolean playerFound = false;
		
		for (Player player : players) {
			if (player.getName().equalsIgnoreCase(name)) {
				System.out.println("\n - PLAYER INFO - ");
				System.out.println("=======================================");
				System.out.printf("|%-10s|%-6s|%-8s|\n", "NAME", "WINS", "BALANCE");
				System.out.println("=======================================");
				System.out.printf("|%-10s|%6d|%8d $|\n", player.getName(), player.getNumOfWins(), player.getBalance());
				System.out.println("=======================================");
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

	
	

}
