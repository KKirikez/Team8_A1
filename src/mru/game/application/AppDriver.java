package mru.game.application;


import java.io.FileNotFoundException;

import mru.game.controller.GameManager;

public class AppDriver {

	public static void main(String[] args){
		
		// This is the starting point of the project!
		// hint - It usually calls method from GameManager class to start the app, so only one or two lines will be written here
		
		try {
			GameManager.startGame();
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}

}
