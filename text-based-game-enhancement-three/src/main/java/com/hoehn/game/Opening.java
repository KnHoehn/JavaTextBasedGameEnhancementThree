/**Package contains all game files. */
package com.hoehn.game;

public final class Opening {

	private Opening() {}

	/** This class prints the opening story and instructions given the player's choice of theme.
	 *
	 * @param theme the theme the user chose.
	 * */
	public static void printOpening(final Theme theme) {
		
		String opening = theme.getStory();

		System.out.println(theme.getName() + "\n");
		
		System.out.println(opening);
		
		Instructions.printInstructions();
	}
}
