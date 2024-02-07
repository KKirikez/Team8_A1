package mru.game.model;

import mru.game.view.AppMenu;


public class Player {
	
	/**
	 * This class represent each player record in the Database
	 * It is basically a model class for each record in the txt file
	 */

	 String name;
	 int numOfWins;
	 int balance;

	 //Constructor for Player Class
	 public Player(String name, int numOfWins, int balance) {
		this.name = name;
		this.numOfWins = numOfWins;
		this.balance = balance;

	 }
	 
	 // Setters for Player Class
	 public void setName(String name) {
		this.name = name;
	 }

	 public void setNumOfWins(int numOfWins) {
		this.numOfWins = numOfWins;
	 }

	 public void setBalance(int balance) {
		this.balance = balance;
	}

	//Getters for Player Class
	public String getName() {
		return name;
	}

	public int getNumOfWins() {
		return numOfWins;
	}
	
	public int getBalance() {
		return balance;
	}
	
	}
