/**Package contains all game files. */
package com.hoehn.game;

import org.apache.commons.text.WordUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/** Author: Kayla Hoehn. */

public final class Driver {

	/** 1000 milliseconds in a second. */
	private static final int MILLISECONDS_TO_SECONDS = 1000;

	private Driver() {}

	/** This method holds the main method for the game.
	 *
	 * @param args unused.
	 * */
	public static void main (final String[] args) {

		Scanner scanner = new Scanner(System.in);

		List<String> inventory = new ArrayList<>();

		// Calls the Commands class to create a list of valid commands
		List<String> commands = Commands.createCommands();

		String userName;

		boolean gameLoop = true;

		userName = Login.loginUser(scanner);

		// Calls the Command class to process the user's input and get the theme
		String filePath = Command.chooseTheme(scanner);

		// Creates a new player state.
		Player player = new Player();

		// Calls World class to build the game world given the chosen theme
		Theme theme = World.createTheme(filePath);

		// Calls the World class to create a hashmap of rooms
		Map<String, Room> rooms = World.createRooms(theme);

		// Gets the type of theme the user picked
		String themeType = theme.getName().split(" ")[0];

		// Gets the starting room given the chosen theme and sets it as the current room
		String startingRoomName = theme.getStartingRoom();
		Room currentRoom = rooms.get(startingRoomName);

		// Grabs the time before starting the game
		Date startDate = new Date();

		// Saves the time in milliseconds
		long startTime = startDate.getTime();

		// Calls the Opening class to print the opening
		Opening.printOpening(theme);

		// Game loop
		while (gameLoop) {

			// Calls the Status class to print the user's current status
			Status.printStatus(currentRoom.getName(), inventory);

			// If current room contains an item, print the item's description
			if (currentRoom.getItem() != null) {
				String itemDescription = currentRoom.getItemDescription();

				System.out.println(itemDescription);
			}
			// If the current room contains the boss, initiates the battle, if the user lost, zeros out their score
			if (currentRoom.getBoss()) {
				int finalMoveScore = Battle.initiateBattle(inventory, theme, player);
				// Gets the time after the user completes the game
				Date endDate = new Date();
				long endTime = endDate.getTime();
				int finalMoves = player.getMoves();
				// Calculates the total time the user took to complete the game in seconds
				long totalTime = (endTime - startTime) / MILLISECONDS_TO_SECONDS;
				// Calls the Score class to calculate the user's total score
				long totalScore = Score.calculateScore(totalTime, finalMoveScore);
				// Calls the DatabaseConnector class to save the user's score to the database.
				DatabaseConnector.saveScore(userName, totalScore, finalMoves, totalTime, themeType);
				// Calls the GameStats class to print the user's end game results
				GameStats.printEndGameStats(finalMoves, totalScore, totalTime, userName);
				gameLoop = false;
				continue;
			}

			// Gets the command from the user
			System.out.print("Enter your command: ");
			String command = WordUtils.capitalizeFully(scanner.nextLine()).trim();

			// Else if user types "Exit", exits the game
			if (command.equals("Exit")) {
				System.out.println("Thank you for playing!");
				gameLoop = false;
				// Otherwise calls the Command class to process their command
			} else {
				currentRoom = Command.processCommand(rooms, currentRoom, commands, inventory, command, player);
			}
		}

		scanner.close();
	}
}
