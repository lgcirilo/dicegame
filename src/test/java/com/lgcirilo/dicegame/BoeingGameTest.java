package com.lgcirilo.dicegame;

import com.lgcirilo.dicegame.interfaces.Die;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class BoeingGameTest {
    static BoeingGamePlayer[] boeingGamePlayers;
    static BoeingGame bg;
    static Die[] dice;

    @BeforeAll
    public static void beforeAll() {
        boeingGamePlayers = new BoeingGamePlayer[]{new BoeingGamePlayer("player 1"), new BoeingGamePlayer("player 2")};
        bg = new BoeingGame(6, 3, boeingGamePlayers);
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
        assertFalse(bg.isTurnOver(boeingGamePlayers[0], dice, currentTurn));

    }

    @Test
    @DisplayName("Is turn over - true")
    void isTurnOver_true() {
        int currentTurn = 3;
        dice[0].setFaceValue(4);
        dice[1].setFaceValue(2);
        dice[2].setFaceValue(1);
        assertTrue(bg.isTurnOver(boeingGamePlayers[0], dice, currentTurn));
    }

    @Test
    @DisplayName("Is mini bonus - true")
    void isMiniBonus_true() {
        int currentTurn = 2;
        dice[0].setFaceValue(1);
        dice[1].setFaceValue(1);
        dice[2].setFaceValue(1);
        assertTrue(bg.isMiniBonus(dice, currentTurn, boeingGamePlayers[0]));
    }

    @Test
    @DisplayName("Is mini bonus - false")
    void isMiniBonus_false() {
        int currentTurn = 1;
        dice[0].setFaceValue(1);
        dice[1].setFaceValue(1);
        dice[2].setFaceValue(1);
        assertFalse(bg.isMiniBonus(dice, currentTurn, boeingGamePlayers[0]));
    }

    @Test
    @DisplayName("Is bonus - true")
    void isBonus_true() {
        int currentTurn = 1;
        dice[0].setFaceValue(1);
        dice[1].setFaceValue(1);
        dice[2].setFaceValue(1);
        assertTrue(bg.isBonus(dice, currentTurn, boeingGamePlayers[0]));
    }

    @Test
    @DisplayName("Is mini bonus - false")
    void isBonus_false() {
        int currentTurn = 1;
        dice[0].setFaceValue(2);
        dice[1].setFaceValue(2);
        dice[2].setFaceValue(2);
        assertFalse(bg.isBonus(dice, currentTurn, boeingGamePlayers[0]));
    }

    @Test
    @DisplayName("Compute points")
    void computePointsTest() {
        int currentTurn = 3;
        dice[0].setFaceValue(1);
        dice[1].setFaceValue(3);
        dice[2].setFaceValue(3);
        assertEquals(2, bg.computePoints(boeingGamePlayers[0], dice, currentTurn));
    }

    @Test
    @DisplayName("Were points scored on this turn - regular points")
    void isPointsScored_regular_true() {
        int currentTurn = 3;
        dice[0].setFaceValue(1);
        dice[1].setFaceValue(3);
        dice[2].setFaceValue(3);
        assertTrue(bg.isPointsScored(dice, currentTurn, boeingGamePlayers[0]));
    }

    @Test
    @DisplayName("Were points scored on this turn - regular points")
    void isPointsScored_regular_false() {
        int currentTurn = 3;
        dice[0].setFaceValue(1);
        dice[1].setFaceValue(2);
        dice[2].setFaceValue(4);
        assertFalse(bg.isPointsScored(dice, currentTurn, boeingGamePlayers[0]));
    }

    @Test
    @DisplayName("Were points scored on this turn - minibonus")
    void isPointsScored_miniBonus() {
        int currentTurn = 3;
        dice[0].setFaceValue(1);
        dice[1].setFaceValue(1);
        dice[2].setFaceValue(1);
        assertTrue(bg.isPointsScored(dice, currentTurn, boeingGamePlayers[0]));
    }

    @Test
    @DisplayName("Were points scored on this turn - bonus")
    void isPointsScored_bonus() {
        int currentTurn = 3;
        dice[0].setFaceValue(3);
        dice[1].setFaceValue(3);
        dice[2].setFaceValue(3);
        assertTrue(bg.isPointsScored(dice, currentTurn, boeingGamePlayers[0]));
    }


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
        boeingGamePlayers[0].setScore(2);
        boeingGamePlayers[1].setScore(1);
        assertEquals(boeingGamePlayers[0], bg.winner(boeingGamePlayers));
    }

    @Test
    @DisplayName("Winner - by bonus")
    void winner_byBonus() {
        boeingGamePlayers[0].setScore(2);
        boeingGamePlayers[1].setScore(2);
        boeingGamePlayers[0].setBonusRollsCount(3);
        boeingGamePlayers[1].setBonusRollsCount(0);
        assertEquals(boeingGamePlayers[0], bg.winner(boeingGamePlayers));
    }

    @Test
    @DisplayName("Winner - by mini bonus")
    void winner_byMiniBonus() {
        boeingGamePlayers[0].setScore(2);
        boeingGamePlayers[1].setScore(2);
        boeingGamePlayers[0].setBonusRollsCount(3);
        boeingGamePlayers[1].setBonusRollsCount(3);
        boeingGamePlayers[0].setMiniBonusRollsCount(3);
        boeingGamePlayers[1].setMiniBonusRollsCount(1);
        assertEquals(boeingGamePlayers[0], bg.winner(boeingGamePlayers));
    }

    @Test
    @DisplayName("Winner - by tie breaker")
    void winner_byTieBreaker() {
        boeingGamePlayers[0].setScore(2);
        boeingGamePlayers[1].setScore(2);
        boeingGamePlayers[0].setBonusRollsCount(3);
        boeingGamePlayers[1].setBonusRollsCount(3);
        boeingGamePlayers[0].setMiniBonusRollsCount(3);
        boeingGamePlayers[1].setMiniBonusRollsCount(3);
        assertEquals(BoeingGamePlayer.class, bg.winner(boeingGamePlayers).getClass());
    }

    @Test
    @Timeout(5)
    @DisplayName("tie breaker")
    void tieBreakerTest() {
        boeingGamePlayers[0].setScore(7);
        boeingGamePlayers[1].setScore(7);
        boeingGamePlayers[0].setMiniBonusRollsCount(0);
        boeingGamePlayers[1].setMiniBonusRollsCount(0);
        boeingGamePlayers[0].setBonusRollsCount(0);
        boeingGamePlayers[1].setBonusRollsCount(0);
        bg.tieBreaker(boeingGamePlayers);
        Integer player0Score = boeingGamePlayers[0].getScore();
        Integer player1Score = boeingGamePlayers[1].getScore();
        assertNotEquals(player0Score, player1Score);
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