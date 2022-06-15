package com.lgcirilo.dicegame;

public class SixSidedDie implements Die {
    private Integer faceValue;

    @Override
    public void roll() {
        int rolledNumber = (int) Math.floor(Math.random() * 6) + 1;
        setFaceValue(rolledNumber);
    }

    public Integer getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(Integer faceValue) {
        this.faceValue = faceValue;
    }

    @Override
    public String toString() {
        return String.valueOf(faceValue);
    }
}
