package com.hoehn.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    @Test
    void testIncrementMoves() {
        Player player = new Player();

        player.incrementMoves();

        assertEquals(1, player.getMoves());
    }

    @Test
    void testScorePenalty() {
        Player player = new Player();
        int movePenalty = 10;

       player.scorePenalty(movePenalty);

       assertEquals(990, player.getMoveScore());
    }
}
