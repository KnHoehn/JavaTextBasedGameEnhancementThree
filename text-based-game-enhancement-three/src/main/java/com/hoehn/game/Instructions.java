/**Package contains all game files. */
package com.hoehn.game;

public final class Instructions {

	private Instructions() {}

	/** This method print the instructions for the game. */
	public static void printInstructions() {
		
		System.out.println("Movement commands: Go North, Go South, Go East, Go West");
		System.out.println("Add to inventory: Get <item name>");
		System.out.println("Type 'Exit' to exit game");
		System.out.println("Type 'I' to show instructions again");
	}
}
