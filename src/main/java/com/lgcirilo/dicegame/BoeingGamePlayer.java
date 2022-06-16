package com.lgcirilo.dicegame;

public class BoeingGamePlayer extends Person{
    private Integer score;
    private Integer bonusRollsCount;
    private Integer miniBonusRollsCount;

    public BoeingGamePlayer(String name) {
        super(name);
        this.bonusRollsCount = 0;
        this.miniBonusRollsCount = 0;
        this.score = 0;
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
