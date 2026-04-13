package com.hoehn.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreTest {

    @Test
    void testCorrectScoreCalculation() {

        Long totalTime = 300L;
        int moveScore = 500;

        long totalScore = Score.calculateScore(totalTime, moveScore);

        assertEquals(150000, totalScore);
    }

    @Test
    void testScoreCalculationIfTimeGreaterThanTenMinutes() {

        Long totalTime = 700L;
        int moveScore = 500;

        long totalScore = Score.calculateScore(totalTime, moveScore);

        assertEquals(500, totalScore);
    }

    @Test
    void testScoreCantGoNegative() {

        Long totalTime = 300L;
        int moveScore = -500;

        long totalScore = Score.calculateScore(totalTime, moveScore);

        assertEquals(0, totalScore);
    }
}
