/**Package contains all game files. */
package com.hoehn.game;

import java.util.List;

public final class Battle {

	/** Number of items needed to be in inventory to win the game. */
	private static final int NUM_ITEMS_TO_WIN = 6;

	private Battle() {}

	/** This method determine if the user won or lost against the alien.
	 *
	 * @param inventory the player's inventory.
	 * @param theme the theme the player chose.
	 * @param player the player object.
	 * @return the player's final move score.
	 * */
	public static int initiateBattle(final List<String> inventory, final Theme theme, final Player player) {

		String bossName = theme.getBoss();
		String loseBattleMessage = theme.getBattleLossMessage();
		int totalInventory = inventory.size();
		int finalMoveScore = player.getMoveScore();

		System.out.println("You see the " + bossName);
		System.out.println("A battle ensues");
		System.out.println("...");
		
		// If player has all obtainable items, player wins
		if (totalInventory == NUM_ITEMS_TO_WIN) {
			System.out.println("Congratulations! You defeated the " + theme.getBoss() + "!");
			System.out.println("Thank you for playing!");
			// Else loses
		} else {
			System.out.println(loseBattleMessage + " Game over");
			finalMoveScore = 0;
		}

		return finalMoveScore;
	}
}
