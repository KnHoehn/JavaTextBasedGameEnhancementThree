package com.hoehn.game;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BattleTest {

    @Test
    void testScoreWhenPlayerWins() {

        Player player = new Player();

        Theme theme = new Theme();

        List<String> inventory = new ArrayList<>();

        inventory.add("Item1");
        inventory.add("Item2");
        inventory.add("Item3");
        inventory.add("Item4");
        inventory.add("Item5");
        inventory.add("Item6");

        int score = Battle.initiateBattle(inventory, theme, player);

        assertEquals(1000, score);

    }

    @Test
    void testScoreWhenPlayerLoses() {

        Player player = new Player();

        Theme theme = new Theme();

        List<String> inventory = new ArrayList<>();

        inventory.add("Item1");
        inventory.add("Item2");

        int score = Battle.initiateBattle(inventory, theme, player);

        assertEquals(0, score);

    }
}
