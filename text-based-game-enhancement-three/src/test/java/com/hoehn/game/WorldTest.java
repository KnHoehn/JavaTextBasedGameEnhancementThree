/**Package contains all game files. */
package com.hoehn.game;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;


class WorldTest {

    @Test
    void testCreateTheme() {

        String filepath =  "src/main/resources/space-theme.json";

        Theme theme = World.createTheme(filepath);

        assertEquals("Space Text Adventure Game", theme.getName());
        assertEquals("Alien", theme.getBoss());
        assertEquals("Engine Room", theme.getStartingRoom());
        assertEquals("You and your crew are on a mission on Mars when your spaceship is invaded by an Alien.\nYou will need to defeat the Alien before your crew becomes its’ dinner,\nbut first you will need a collection of items to aid you in your fight.\nYou will need a space blaster to shoot the alien\na space phone to call the base station for back up,\nspace boots for gravitational manipulation to help dodge the aliens’ attacks,\na bowl of Marshmallow Moonies for the energy you will need for the battle,\nan infodisk to learn the aliens’ weaknesses\nand a weight to buff yourself up for battle.", theme.getStory());
        assertEquals("You were eaten by the Alien!", theme.getBattleLossMessage());
    }

    @Test
    void testCreateRooms() {

        String filepath =  "src/main/resources/space-theme.json";

        Theme theme = World.createTheme(filepath);

        Map<String, Room> rooms = World.createRooms(theme);

        assertEquals(8, rooms.size());
    }
}
