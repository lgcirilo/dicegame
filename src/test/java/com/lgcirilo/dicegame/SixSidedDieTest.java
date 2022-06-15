package com.lgcirilo.dicegame;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SixSidedDieTest {

    @RepeatedTest(50)
    @DisplayName("Face value is in the correct range")
    void roll() {
        SixSidedDie die = new SixSidedDie();
        die.roll();
        assertTrue(die.getFaceValue() >=1 && die.getFaceValue() <=6);
    }
}