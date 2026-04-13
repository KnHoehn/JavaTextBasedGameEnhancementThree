package com.hoehn.game;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandsTest {

    @Test
    void testCommandsAdded() {
        List<String> commands = Commands.createCommands();

        assertTrue(commands.contains("Go North"));
        assertTrue(commands.contains("Go South"));
        assertTrue(commands.contains("Go East"));
        assertTrue(commands.contains("Go West"));
        assertTrue(commands.contains("Exit"));
        assertTrue(commands.contains("I"));
    }
}
