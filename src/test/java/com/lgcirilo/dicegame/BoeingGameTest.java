package com.lgcirilo.dicegame;

import org.junit.jupiter.api.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BoeingGameTest {
    static Player[] players;
    static BoeingGame bg;
    static Die[] dice;

    @BeforeAll
    public static void beforeAll() {
        players = new Player[]{new Player("player 1"), new Player("player 2")};
        bg = new BoeingGame(6, 3, players);
        dice = new Die[3];
        for (int i = 0; i < 3; i++) {
            dice[i] = new SixSidedDie();
        }
    }

    @Test
    @DisplayName("Is turn over - false")
    void isTurnOver_false() {
        int currentTurn = 3;
        dice[0].setFaceValue(3);
        dice[1].setFaceValue(2);
        dice[2].setFaceValue(1);
        assertFalse(bg.isTurnOver(players[0], dice, currentTurn));

    }

    @Test
    @DisplayName("Is turn over - true")
    void isTurnOver_true() {
        int currentTurn = 3;
        dice[0].setFaceValue(4);
        dice[1].setFaceValue(2);
        dice[2].setFaceValue(1);
        assertTrue(bg.isTurnOver(players[0], dice, currentTurn));
    }

    @Test
    @DisplayName("Is mini bonus - true")
    void isMiniBonus_true() {
        int currentTurn = 2;
        dice[0].setFaceValue(1);
        dice[1].setFaceValue(1);
        dice[2].setFaceValue(1);
        assertTrue(bg.isMiniBonus(dice, currentTurn, players[0]));
    }

    @Test
    @DisplayName("Is mini bonus - false")
    void isMiniBonus_false() {
        int currentTurn = 1;
        dice[0].setFaceValue(1);
        dice[1].setFaceValue(1);
        dice[2].setFaceValue(1);
        assertFalse(bg.isMiniBonus(dice, currentTurn, players[0]));
    }

    @Test
    @DisplayName("Is bonus - true")
    void isBonus_true() {
        int currentTurn = 1;
        dice[0].setFaceValue(1);
        dice[1].setFaceValue(1);
        dice[2].setFaceValue(1);
        assertTrue(bg.isBonus(dice, currentTurn, players[0]));
    }

    @Test
    @DisplayName("Is mini bonus - false")
    void isBonus_false() {
        int currentTurn = 1;
        dice[0].setFaceValue(2);
        dice[1].setFaceValue(2);
        dice[2].setFaceValue(2);
        assertFalse(bg.isBonus(dice, currentTurn, players[0]));
    }

    @Test
    @DisplayName("Compute points")
    void computePointsTest() {
        int currentTurn = 3;
        dice[0].setFaceValue(1);
        dice[1].setFaceValue(3);
        dice[2].setFaceValue(3);
        assertEquals(2, bg.computePoints(players[0], dice, currentTurn));
    }

//    @Test
//    @DisplayName("Were points scored on this turn - regular points")
//    void isPointsScored_regular() {
//    }


    @Test
    @DisplayName("Is roll three of a kind - true")
    void isRollThreeOfAKind_true() {
        dice[0].setFaceValue(3);
        dice[1].setFaceValue(3);
        dice[2].setFaceValue(3);
        assertTrue(bg.isRollThreeOfAKind(dice));
    }

    @Test
    @DisplayName("Is roll three of a kind - false")
    void isRollThreeOfAKind_false() {
        dice[0].setFaceValue(3);
        dice[1].setFaceValue(2);
        dice[2].setFaceValue(3);
        assertFalse(bg.isRollThreeOfAKind(dice));
    }

    @Test
    @DisplayName("Winner - regular score")
    void winner_regularScore() {
        players[0].setScore(2);
        players[1].setScore(1);
        assertEquals(players[0], bg.winner(players));
    }

    @Test
    @DisplayName("Winner - by bonus")
    void winner_byBonus() {
        players[0].setScore(2);
        players[1].setScore(2);
        players[0].setBonusRollsCount(3);
        players[1].setBonusRollsCount(0);
        assertEquals(players[0], bg.winner(players));
    }

    @Test
    @DisplayName("Winner - by mini bonus")
    void winner_byMiniBonus() {
        players[0].setScore(2);
        players[1].setScore(2);
        players[0].setBonusRollsCount(3);
        players[1].setBonusRollsCount(3);
        players[0].setMiniBonusRollsCount(3);
        players[1].setMiniBonusRollsCount(1);
        assertEquals(players[0], bg.winner(players));
    }

    @Test
    @DisplayName("Winner - by tie breaker")
    void winner_byTieBreaker() {
        players[0].setScore(2);
        players[1].setScore(2);
        players[0].setBonusRollsCount(3);
        players[1].setBonusRollsCount(3);
        players[0].setMiniBonusRollsCount(3);
        players[1].setMiniBonusRollsCount(3);
        assertEquals(Player.class, bg.winner(players).getClass());
    }

    // ????????????????????????????????
    @Test
    @Timeout(5)
    @DisplayName("tie breaker")
    void tieBreakerTest() {
        players[0].setScore(7);
        players[1].setScore(7);
        players[0].setMiniBonusRollsCount(0);
        players[1].setMiniBonusRollsCount(0);
        players[0].setBonusRollsCount(0);
        players[1].setBonusRollsCount(0);

        bg.tieBreaker(players);
        assertNotEquals(players[0].getScore(), players[1].getScore());
    }

    @Test
    @DisplayName("roll dice")
    void rollDiceTest() {
        for (Die die: dice) {
            die.roll();
            assertTrue(die.getFaceValue() > 0);
        }
    }
}