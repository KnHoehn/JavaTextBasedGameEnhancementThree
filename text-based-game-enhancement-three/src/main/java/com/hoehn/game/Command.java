/**Package contains all game files. */
package com.hoehn.game;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public final class Command {

	/** Variable represents the amount taken off their score for making a move. */
	private static final int MOVE_PENALTY = 10;

	private Command() {}

	/** This method determines what to do next given the user's command.
	 *
	 * @param rooms the map of the game world.
	 * @param currentRoom the room the players currently resides in.
	 * @param commands list of valid commands the player can take.
	 * @param inventory the player's current inventory.
	 * @param command the command the player entered.
	 * @param player the player object.
	 * @return the room the player enters next given the command.
	 */
	public static Room processCommand(final Map<String, Room> rooms, final Room currentRoom, final List<String> commands, final List<String> inventory, final String command, final Player player) {

		Room nextRoom = currentRoom;
		String commandOne;
		String commandTwo = null;
		String[] splitCommand = command.split(" ", 2);
		commandOne = splitCommand[0];

		if (splitCommand.length > 1) {
			commandTwo = splitCommand[1];
		}
		// If command is valid
		if (commands.contains(command)) {
			// If user types "Go" calls the Command class to navigate rooms and increments the users moves and -10 to their move score
			if (command.contains("Go") && commandTwo != null) {
				// Goes the direction the user input
				if (currentRoom.getConnectedRooms().containsKey(commandTwo)) {
					nextRoom = rooms.get(currentRoom.getConnectedRooms().get(commandTwo));
				} else {
					System.out.println("Can't go that way!");
				}
				player.incrementMoves();
				player.scorePenalty(MOVE_PENALTY);
				// Else if user types "I", prints the instructions
			} else if (command.equals("I")) {
				Instructions.printInstructions();
			} else {
			System.out.println("Invalid Move!");
			}
			// Else if the user types "Get", calls the InventoryManagement class to manage their inventory
		} else if (commandOne.equals("Get") && commandTwo != null) {
			InventoryManagement.manageInventory(inventory, commandTwo, currentRoom);
		} else {
			System.out.println("Invalid Move!");
		}

		return nextRoom;
	}

	/** This method prompts the user to pick a theme and determines which filepath to use given the user's choice.
	 *
	 * @param scanner gets user input.
	 * @return the filepath for the theme.
	 * */
	public static String chooseTheme(final Scanner scanner) {
		
		// Prompts user to choose a theme
		System.out.println("Enter a number to choose a theme.");
		System.out.println("1. Space.");
		System.out.println("2. Medieval");
		System.out.println("3. Cyberpunk.");

		String filepath = null;

		while (filepath == null) {
			
			String command = scanner.nextLine();
			
			// Save the appropriate JSON file path given the user's choice
			switch (command) {

			case "1":
				filepath = "src/main/resources/space-theme.json";
				break;
			case "2":
				filepath = "src/main/resources/medieval-theme.json";
				break;
			case "3":
				filepath = "src/main/resources/cyberpunk-theme.json";
				break;
			default:
				System.out.println("Please enter a valid number");
			}
		}

		return filepath;
	}
}
