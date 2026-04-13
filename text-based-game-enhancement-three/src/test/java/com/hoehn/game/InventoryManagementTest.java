package com.hoehn.game;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryManagementTest {

    @Test
    void testAddToInventory() {
        Room room = new Room();
        room.setItem("Blaster Gun");

        List<String> inventory = new ArrayList<>();

        InventoryManagement.manageInventory(inventory, "Blaster Gun", room);

        assertNull(room.getItem());
        assertTrue(inventory.contains("Blaster Gun"));
    }

    @Test
    void testNotAddedToInventory() {
        Room room = new Room();
        room.setItem("Blaster Gun");

        List<String> inventory = new ArrayList<>();

        InventoryManagement.manageInventory(inventory, "Infodisk", room);

        assertNotNull(room.getItem());
        assertFalse(inventory.contains("Infodisk"));
    }
}
