/**Package contains all game files */
package com.hoehn.game;
import java.util.List;

public final class Status {

	private Status() {}

	/** This method prints the players status.
	 *
	 * @param currentRoom the room the player currently resides in.
	 * @param inventory the player's current inventory.
	 * */
	public static void printStatus(final String currentRoom, final List<String> inventory) {
		
		System.out.println("------------------------------");
		System.out.println("You are in the " + currentRoom);
		System.out.println("Inventory: " + String.join(", ", inventory) + "\n");
	}
}
