/**Package contains all game files. */
package com.hoehn.game;

import java.util.List;

public final class InventoryManagement {

	private InventoryManagement() {}

	/** This method processes the logic to add items to your inventory and remove the item from the room.
	 *
	 * @param inventory the player's current inventory.
	 * @param commandTwo the item the player wants to pick up.
	 * @param currentRoom the room the player currently resides in.
	 */
	public static void manageInventory(final List<String> inventory, final String commandTwo, final Room currentRoom) {
		
		String item;
		
		if (commandTwo.equals(currentRoom.getItem())) {
			
			item = currentRoom.getItem();
			
			inventory.add(item);
			currentRoom.setItem(null);
		} else {
			System.out.println("No such item Found!");
		}
	}
}
