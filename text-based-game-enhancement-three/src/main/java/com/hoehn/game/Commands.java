/**Package contains all game files. */
package com.hoehn.game;

import java.util.ArrayList;
import java.util.List;

public final class Commands {

	private Commands() {}

	/** This method creates a list of valid commands the user can enter.
	 *
	 * @return a list of valid commands.
	 */
	public static List<String> createCommands() {
		
		List<String> commands = new ArrayList<>();
		
		commands.add("Go North");
		commands.add("Go South");
		commands.add("Go East");
		commands.add("Go West");
		commands.add("Exit");
		commands.add("I");
		
		return commands;
	}
}
