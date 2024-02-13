package mru.game.application;

import java.io.FileNotFoundException;

import mru.game.controller.GameManager;

/**
 * The main class that serves as the entry point for the application.
 * It calls the startGame() method from the GameManager class to start the app.
 */
public class AppDriver {

	public static void main(String[] args) {

		// This is the starting point of the project!
		// hint - It usually calls method from GameManager class to start the app, so
		// only one or two lines will be written here

		try {
			GameManager.startGame();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
