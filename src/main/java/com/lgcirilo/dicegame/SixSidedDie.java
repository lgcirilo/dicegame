package com.lgcirilo.dicegame;

import com.lgcirilo.dicegame.interfaces.Die;

public class SixSidedDie implements Die {
    private Integer faceValue;
    private final int numberOfSides = 6;

    @Override
    public void roll() {
        int rolledNumber = (int) Math.floor(Math.random() * numberOfSides) + 1;
        setFaceValue(rolledNumber);
    }

    @Override
    public Integer getFaceValue() {
        return faceValue;
    }

    @Override
    public void setFaceValue(Integer faceValue) {
        this.faceValue = faceValue;
    }

    @Override
    public int getNumberOfSides() {
        return numberOfSides;
    }

    public String toString() {
        return String.valueOf(faceValue);
    }
}
