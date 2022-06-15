package com.lgcirilo.dicegame;

public class Player {
    private String name;
    private Integer score;
    private Integer bonusRollsCount;
    private Integer miniBonusRollsCount;

    public Player(String name) {
        this.name = name;
        this.bonusRollsCount = 0;
        this.miniBonusRollsCount = 0;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBonusRollsCount() {
        return bonusRollsCount;
    }

    public void setBonusRollsCount(Integer bonusRollsCount) {
        this.bonusRollsCount = bonusRollsCount;
    }

    public Integer getMiniBonusRollsCount() {
        return miniBonusRollsCount;
    }

    public void setMiniBonusRollsCount(Integer miniBonusRollsCount) {
        this.miniBonusRollsCount = miniBonusRollsCount;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void increaseBonusRolls() {
        setBonusRollsCount(getBonusRollsCount() + 1);
    }

    public void increaseMiniBonusRolls() {
        setMiniBonusRollsCount(getMiniBonusRollsCount() + 1);
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", bonusRollsCount=" + bonusRollsCount +
                ", miniBonusRollsCount=" + miniBonusRollsCount +
                '}';
    }
}
