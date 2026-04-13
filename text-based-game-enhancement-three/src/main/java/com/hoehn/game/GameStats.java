/**Package contains all game files. */
package com.hoehn.game;

public final class GameStats {

	/** Variable representing 3600 seconds in an hour. */
	private static final int SECONDS_PER_HOUR = 3600;
	/** Variable representing 60 seconds in a minute. */
	private static final int SECONDS_PER_MINUTE = 60;

	private GameStats() {}

	/** This method displays the user's end game stats.
	 *
	 * @param moves the user's total moves to reach the boss.
	 * @param totalScore the user's total score at the end of the game.
	 * @param totalTime the total time it took the user ot reach the boss.
	 * @param userName the player's username.
	 * */
	public static void printEndGameStats(final int moves, final long totalScore, final long totalTime, final String userName) {
		
		long hours = totalTime / SECONDS_PER_HOUR;
		
		long minutes = (totalTime % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE;
		
		long seconds = totalTime % SECONDS_PER_MINUTE;
		
		String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
		
		System.out.println("---------------End Game Stats---------------");
		System.out.println("Total moves to reach the boss: " + moves);
		System.out.println("Total time to reach the boss: " + formattedTime);
		System.out.println("Total score: " + totalScore);

		System.out.println("---------------YOUR TOP 10 GAMES---------------");
		DatabaseConnector.getPersonalBest(userName);

		System.out.println("---------------LEADER BOARD---------------");
		DatabaseConnector.getLeaderboard();
	}

}
