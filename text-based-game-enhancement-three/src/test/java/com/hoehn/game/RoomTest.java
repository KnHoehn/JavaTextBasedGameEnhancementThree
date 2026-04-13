/**Package contains all game files. */
package com.hoehn.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoomTest {

    @Test
    void testSetItem() {
        Room room = new Room();

        room.setItem("Marshmallow Moonies");

        assertEquals("Marshmallow Moonies", room.getItem());
    }
}
