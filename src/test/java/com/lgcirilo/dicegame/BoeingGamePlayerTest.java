package com.lgcirilo.dicegame;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoeingGamePlayerTest {

    @Test
    @DisplayName("Increase bonus roll count")
    void increaseBonusRolls() {
        BoeingGamePlayer player = new BoeingGamePlayer("test player");
        player.setBonusRollsCount(0);
        player.increaseBonusRolls();
        assertEquals(1, player.getBonusRollsCount());
    }

    @Test
    @DisplayName("")
    void increaseMiniBonusRolls() {
        BoeingGamePlayer player = new BoeingGamePlayer("test player");
        player.setMiniBonusRollsCount(0);
        player.increaseMiniBonusRolls();
        assertEquals(1, player.getMiniBonusRollsCount());
    }
}